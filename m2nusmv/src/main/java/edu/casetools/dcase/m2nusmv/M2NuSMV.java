/*
 * 	 This file is part of M2NuSMV.
 *
 *   M2NuSMV is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License.
 *
 *   M2NuSMV is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with M2NuSMV.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */
package edu.casetools.dcase.m2nusmv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2nusmv.data.MData;
import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator;
import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator.BOP_TYPE;
import edu.casetools.dcase.m2nusmv.data.elements.Rule;
import edu.casetools.dcase.m2nusmv.data.elements.Specification;
import edu.casetools.dcase.m2nusmv.data.elements.State;
import edu.casetools.dcase.m2nusmv.data.elements.Time;

public class M2NuSMV {

    private FileWriter filestream;
    private BufferedWriter writer;
    private MData data;

    public void writeModel(MData data) throws IOException {
		initialiseData(data);
		writeMainModule();
		writeBopModules(data);
		writer.close();
    }

    private void initialiseData(MData data) {

	try {
	    this.data = data;
	    this.data.groupStrs();
	    this.data.groupNtrs();
	    filestream = new FileWriter(data.getFilePath());
	    writer = new BufferedWriter(filestream);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private void writeBopModules(MData data) throws IOException {
		if (data.getBopNumber(BOP_TYPE.STRONG_IMMEDIATE_PAST) > 0)
		    writeStrongImmediatePastModule();
		if (data.getBopNumber(BOP_TYPE.WEAK_IMMEDIATE_PAST) > 0)
		    writeWeakImmediatePastModule();
		if (data.getBopNumber(BOP_TYPE.STRONG_ABSOLUTE_PAST) > 0)
		    writeStrongAbsolutePastModule();
		if (data.getBopNumber(BOP_TYPE.WEAK_ABSOLUTE_PAST) > 0)
		    writeWeakAbsolutePastModule();
    }

    private void writeMainModule() throws IOException {
		writer.append("MODULE main \n");
		writeVariables();
		writeValueAssignations();
		writeSpecifications();
		this.writer.append("\n");
    }

    protected void writeValueAssignations() throws IOException {    	
		writer.append("ASSIGN\n");
		writer.append("\tinit(time) := 0; \n");
		generateInitialisations();
		writer.append("\n");
	
		for (Rule rule : data.getStrs())
		    writeSTR(rule);
	
		for (Rule rule : data.getNtrs())
		    writeNTR(rule);
	
		writer.append("\tnext(time) := case \r\n\t\t\t\t\t (time < " + data.getMaxIteration()
			+ ") : time+1;\r\n\t\t\t\t\t TRUE : " + data.getMaxIteration() + ";\r\n\t\t\t\t  esac;\n\n");
    }

    private void generateInitialisations() throws IOException {
		List<State> auxiliaryStates = new ArrayList<>();
		auxiliaryStates = getStates(auxiliaryStates, data.getStrs());
		for (State state : auxiliaryStates) {
		    if (!state.isIndependent())
			writeInitialisation(state.getName() + "_aux", state.getInitialValue());
		}
		auxiliaryStates = new ArrayList<>();
		auxiliaryStates = getStates(auxiliaryStates, data.getNtrs());
		for (State state : auxiliaryStates) {
		    if (!state.isIndependent())
			writeInitialisation(state.getName(), state.getInitialValue());
		}
    }

    private void writeInitialisation(String name, String value) throws IOException {
    	writer.append("\tinit(" + name + ") := " + value + ";\n");
    }

    protected void writeVariables() throws IOException {
		List<State> auxiliaryStates = new ArrayList<>();
		writer.append("VAR\n");
		writer.append("\ttime : 0.." + data.getMaxIteration() + "; \n\n");
	
		for (State state : data.getStates())
		    writeStateVariable(state);
		writer.append("\n");
	
		auxiliaryStates = getStates(auxiliaryStates, data.getStrs());
	
		for (State state : auxiliaryStates)
		    writeAuxiliaryStateVariables(state);
		writer.append("\n\n");
	
		for (BoundedOperator bop : data.getBops())
		    writeTemporalOperatorVariables(bop);
		writer.append("\n\n");
    }

    private List<State> getStates(List<State> auxiliaryStates, List<Rule> rules) {
    	State state;
		for (Rule rule : rules) {
		    state = findState(rule.getConsequent().getName());
		    if (state != null) {
			auxiliaryStates.add(state);
		    }
		}
		return auxiliaryStates;
	    }
	
	    private State findState(String name) {
		for (State state : data.getStates()) {
		    if (state.getName().equals(name))
			return state;
		}
		return null;
    }

    protected void writeSpecifications() throws IOException {
		for (Specification specification : data.getSpecifications()) {
		    String header = getSpecificationHeader(specification);
		    if (header != null)
			writer.append("\t" + header + " \n\t\t" + specification.getSpec() + "\n\n");
		}
    }

    private String getSpecificationHeader(Specification specification) {
		switch (specification.getType()) {
			case CTL:
			    return "CTLSPEC";
			case LTL:
			    return "LTLSPEC";
			case PSL:
			    return "PSLSPEC";
			case INVARIANT:
			    return "INVARSPEC";
			case COMPUTE:
			    return "COMPUTE";
		}
		return null;
    }

    protected void writeNTR(Rule rule) throws IOException {
		writer.append("\tnext(" + rule.getConsequent().getName() + ") := case\n");
		writeRule(rule);
		writeSameConsequentRules(rule);
		writer.append("\t\t\t\t\t\tTRUE : " + rule.getConsequent().getName() + ";\n");
		writer.append("\t\t\t\t    esac;\n\n");
    }

    protected void writeSTR(Rule rule) throws IOException {
		writer.append("\t" + rule.getConsequent().getName() + " := case\n");
		writeRule(rule);
		writeSameConsequentRules(rule);
		writer.append("\t\t\t\tTRUE : " + rule.getConsequent().getName() + "_aux;\n");
		writer.append("\t\t\t  esac;\n\n");
		writer.append(
			"\tnext(" + rule.getConsequent().getName() + "_aux) := " + rule.getConsequent().getName() + ";\n\n");
    }

    private void writeSameConsequentRules(Rule rule) throws IOException {
		for (Rule sameConsequentRule : rule.getSameConsequentRules()) {
		    writeRule(sameConsequentRule);
		}
    }

    private void writeRule(Rule rule) throws IOException {
		boolean hasStateAntecedents = false;
		for (int i = 0; i < rule.getAntecedents().size(); i++) {
		    writeHeader(i, hasStateAntecedents);
		    writer.append("(" + rule.getAntecedents().get(i).getName() + " = "
			    + rule.getAntecedents().get(i).getStatus().toUpperCase() + ")");
		    hasStateAntecedents = true;
		}
		
		for(int i = 0; i < rule.getTimeReferences().size();i++){
		    writeHeader(i, hasStateAntecedents);
		    writer.append("( time >= " + rule.getTimeReferences().get(i).getLowBound()+")");
		    if(rule.getTimeReferences().get(i).getHighBound() != Time.EMPTY)
		    	writer.append(" & ( time <= " + rule.getTimeReferences().get(i).getHighBound()+")");		
			hasStateAntecedents = true;
		}

		for (int i = 0; i < rule.getBops().size(); i++) {
		    writeHeader(i, hasStateAntecedents);
		    writer.append("(" + rule.getBops().get(i).getOperatorName() + ".live" + " = TRUE )");
		    hasStateAntecedents = true;
		}
	
		writer.append(": " + rule.getConsequent().getStatus().toUpperCase() + ";\n");
	}
	
	private void writeHeader(int i, boolean hasAntecedents) throws IOException {
		if ((i == 0) && (!hasAntecedents))
		    writer.append("\t\t\t\t");
		else
		    writer.append(" & ");
    }

    protected void writeTemporalOperatorVariables(BoundedOperator bop) throws IOException {
		switch (bop.getType()) {
			case STRONG_IMMEDIATE_PAST:
			    writeOperator(bop.getOperatorName(), "strong_immediate_past", bop.getStateName(), bop.getLowBound(),
				    bop.getStatus());
			    break;
			case WEAK_IMMEDIATE_PAST:
			    writeOperator(bop.getOperatorName(), "weak_immediate_past", bop.getStateName(), bop.getLowBound(),
				    bop.getStatus());
			    break;
			case STRONG_ABSOLUTE_PAST:
			    writeOperator(bop.getOperatorName(), "strong_absolute_past", bop.getStateName(),
				    bop.getLowBound() + "," + bop.getUppBound() + ",time", bop.getStatus());
			    break;
			case WEAK_ABSOLUTE_PAST:
			    writeOperator(bop.getOperatorName(), "weak_absolute_past", bop.getStateName(),
				    bop.getLowBound() + "," + bop.getUppBound() + ",time", bop.getStatus());
			    break;
			default:
			    break;
		}
    }

    private void writeOperator(String operatorName, String operatorType, String stateName, String bound, String status)
	    throws IOException {
		writer.append("\t" + operatorName + " : " + operatorType + "(" + getBopStatus(status) + stateName + "," + bound
			+ "); \n");
    }

    private String getBopStatus(String status) {
		if ("FALSE".equalsIgnoreCase(status)) {
		    return "!";
		} else
		    return "";
    }

    protected void writeStateVariable(State state) throws IOException {
    	writer.append("\t" + state.getName() + " : boolean; \n");
    }

    protected void writeAuxiliaryStateVariables(State state) throws IOException {
    	writer.append("\t" + state.getName() + "_aux : boolean; \n");
    }

    private void writeStrongImmediatePastModule() throws IOException {
		writer.append(
				"MODULE strong_immediate_past(state,bound)\r\nVAR\r\n  counter : 0..bound;\r\n  live  : boolean;\r\n  \r\nASSIGN\r\n  init(counter) := 0;\r\n  \r\n  live :=\t case\r\n\t\t  \t\t(counter = bound): TRUE;\r\n\t\t  \t\tTRUE: FALSE;\r\n\t\t     esac;\r\n  \r\n  next(counter) :=  case\r\n\t\t  \t\t\t\t(state=TRUE & counter < bound) : counter+1;\r\n\t\t\t\t\t\t(state=TRUE & counter = bound) : bound;\r\n\t\t  \t\t\t\tTRUE: 0;\r\n\t\t\t\t    esac;\n\n\n");
    }

    private void writeWeakImmediatePastModule() throws IOException {
    	writer.append(
    			"MODULE weak_immediate_past(state,bound)\r\nVAR\r\n  counter  : 0..bound;\r\n  live\t: boolean;\r\n  live_aux : boolean;\r\n  \r\nASSIGN\r\n  init(counter) := 0;  \r\n  init(live_aux) := FALSE;\r\n  \r\n  live := case\r\n\t\t\t\t(state=TRUE)  : TRUE;\r\n\t\t\t\t(state=FALSE) & (counter = bound) : FALSE;\r\n\t\t\t\tTRUE: live_aux;\r\n\t\t  esac;\r\n\t\t  \r\n  next(live_aux) := live;\t  \r\n\t\t  \r\n  next(counter) := \tcase\r\n\t\t\t\t\t\t(state = TRUE) : 0;\r\n\t\t  \t\t\t\t(live_aux=TRUE) & (counter < bound) : counter+1;\r\n\t\t  \t\t\t\tTRUE: 0;\r\n\t\t\t\t    esac;\t\n\n\n");
    }

    private void writeStrongAbsolutePastModule() throws IOException {
    	writer.append(
    			"MODULE strong_absolute_past(state,low_bound,upp_bound,t)\r\nVAR\r\n  veredict\t : boolean;\r\n  veredict_aux  : boolean;\r\n  live \t\t\t: boolean;\r\n  \r\nASSIGN \r\n  init(veredict_aux) := TRUE;\r\n  init(live) := FALSE;\r\n  \r\n  veredict := case\r\n\t\t\t\t((state=FALSE) & (t >= low_bound) & ( t <= upp_bound))  : FALSE;\r\n\t\t\t\tTRUE: veredict_aux;\r\n\t\t\t  esac;  \r\n\n  next(veredict_aux) := veredict;\t  \r\n\n  next(live) := \tcase\r\n\t\t\t\t\t\t(t >= upp_bound) : veredict;\r\n\t\t  \t\t\t\tTRUE: FALSE;\r\n\t\t\t\t\tesac;\n\n\n");
    }

    private void writeWeakAbsolutePastModule() throws IOException {
    	writer.append(
    			"MODULE weak_absolute_past(state,low_bound,upp_bound,t)\r\nVAR\r\n  veredict\t : boolean;\r\n  veredict_aux  : boolean;\r\n  live \t\t\t: boolean;\r\n  \r\nASSIGN\r\n  init(veredict_aux) := FALSE;\r\n  init(live) := FALSE;\r\n  \r\n  veredict := case\r\n\t\t\t\t(state=TRUE) & (t >= low_bound) & ( t <= upp_bound)  : TRUE;\r\n\t\t\t\tTRUE: veredict_aux;\r\n\t\t\t  esac;  \r\n\n  next(veredict_aux) := veredict;\t  \r\n\n  next(live) := \tcase\r\n\t\t\t\t\t\t(upp_bound >= t) : veredict;\r\n\t\t  \t\t\t\tTRUE: FALSE;\r\n\t\t\t\t    esac;\n\n\n");
    }

    public MData getData() {
    	return data;
    }

}
