package edu.casetools.dcase.m2nusmv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2nusmv.data.MData;
import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator;
import edu.casetools.dcase.m2nusmv.data.elements.Rule;
import edu.casetools.dcase.m2nusmv.data.elements.Specification;
import edu.casetools.dcase.m2nusmv.data.elements.State;

public class M2NuSMV {

    private FileWriter filestream;
    private BufferedWriter writer;
    private MData data;
  
    public M2NuSMV(String filename) throws IOException {
		filestream = new FileWriter(filename);
		writer = new BufferedWriter(filestream);
    }
    

    public void writeModel(MData data) throws IOException {
    	this.data = data;
		writeMainModule();
		writer.append("\n");
		writeStrongImmediatePastModule();
		writeWeakImmediatePastModule();
		writeStrongAbsolutePastModule();
		writeWeakAbsolutePastModule();
		this.writer.close();
    }

    private void writeMainModule() throws IOException {
		writer.append("MODULE main \n");
		writeVariables();
		writeValueAssignations();
		writeSpecifications();
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
		auxiliaryStates = getStates(auxiliaryStates,data.getStrs());
		for (State state : auxiliaryStates) {
		    writeInitialisation(state.getName() + "_aux", state.getInitialValue());
		}
		auxiliaryStates = new ArrayList<>();
		auxiliaryStates = getStates(auxiliaryStates,data.getNtrs());
		for (State state : auxiliaryStates) {
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
			if(state != null) {
				auxiliaryStates.add(state);
			}
		}
		return auxiliaryStates;
	}

    private State findState(String name) {
		for(State state : data.getStates()){
			if(state.getName().equals(name)) 
				return state; 
		}
		return null;
	}


	protected void writeSpecifications() throws IOException {
		for(Specification specification : data.getCtlSpecifications())
			writer.append("\tSPEC \n\t\t"+specification.getSpec());
    }

    protected void writeNTR(Rule rule) throws IOException {
		writer.append("\tnext(" + rule.getConsequent().getName() + ") := case\n");
		writeRule(rule);
		writer.append("\t\t\t\t\t\tTRUE : " + rule.getConsequent().getName() + ";\n");
		writer.append("\t\t\t\t    esac;\n\n");
    }

    protected void writeSTR(Rule rule) throws IOException {
		writer.append("\t" + rule.getConsequent().getName() + " := case\n");
		writeRule(rule);
		writer.append("\t\t\t\tTRUE : " + rule.getConsequent().getName() + "_aux;\n");
		writer.append("\t\t\t  esac;\n\n");
		writer.append(
			"\tnext(" + rule.getConsequent().getName() + "_aux) := " + rule.getConsequent().getName() + ";\n\n");
    }

    private void writeRule(Rule rule) throws IOException {
		for (int i = 0; i < rule.getAntecedents().size(); i++) {
		    writeHeader(i);
		    writer.append("(" + rule.getAntecedents().get(i).getName() + " = "
			    + rule.getAntecedents().get(i).getStatus().toUpperCase() + ")");
		}
		writer.append(": " + rule.getConsequent().getStatus().toUpperCase() + ";\n");
    }

    private void writeHeader(int i) throws IOException {
		if (i == 0)
		    writer.append("\t\t\t\t");
		else
		    writer.append(" & ");
    }

    protected void writeTemporalOperatorVariables(BoundedOperator bop) throws IOException {
		switch (bop.getType()) {
			case STRONG_IMMEDIATE_PAST:
			    writeOperator(bop.getOperatorName(), "strong_immediate_past", bop.getStateName(), bop.getLowBound());
			    break;
			case WEAK_IMMEDIATE_PAST:
			    writeOperator(bop.getOperatorName(), "weak_immediate_past", bop.getStateName(), bop.getLowBound());
			    break;
			case STRONG_ABSOLUTE_PAST:
			    writeOperator(bop.getOperatorName(), "strong_absolute_past", bop.getStateName(),
				    bop.getLowBound() + "," + bop.getUppBound() + ",time");
			    break;
			case WEAK_ABSOLUTE_PAST:
			    writeOperator(bop.getOperatorName(), "weak_absolute_past", bop.getStateName(),
				    bop.getLowBound() + "," + bop.getUppBound() + ",time");
			    break;
			default:
			    break;
		}
    }

    private void writeOperator(String operatorName, String operatorType, String stateName, String bound)
	    throws IOException {
    	writer.append("\t" + operatorName + " : " + operatorType + "(" + stateName + "," + bound + "); \n");
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
