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
package edu.casetools.dcase.m2nusmv.data;

import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator;
import edu.casetools.dcase.m2nusmv.data.elements.Rule;
import edu.casetools.dcase.m2nusmv.data.elements.Specification;
import edu.casetools.dcase.m2nusmv.data.elements.State;

public class MData {

    private int maxIteration;
    private String filePath;
    private List<State> states;
    private List<State> independentStates;
    private List<Rule> strs;
    private List<Rule> ntrs;
    private List<BoundedOperator> bops;
    private List<Specification> specs;

    public MData() {
	initialiseLists();
    }

    private void initialiseLists() {
	states = new ArrayList<>();
	strs = new ArrayList<>();
	ntrs = new ArrayList<>();
	bops = new ArrayList<>();
	specs = new ArrayList<>();
    }

    public int getMaxIteration() {
	return maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
	this.maxIteration = maxIteration;
    }

    public void setIndependentStates(List<State> independentStates) {
	this.independentStates = independentStates;
    }

    public List<State> getIndependentStates() {
	independentStates = new ArrayList<>();
	for (int i = 0; i < states.size(); i++) {
	    if (states.get(i).isIndependent()) {
		independentStates.add(states.get(i));
	    }
	}
	return independentStates;
    }

    public List<State> getStates() {
	return states;
    }

    public void setStates(List<State> states) {
	this.states = states;
    }

    public List<Rule> getStrs() {
	return strs;
    }

    public void setStrs(List<Rule> strs) {
	this.strs = strs;
    }

    public List<Rule> getNtrs() {
	return ntrs;
    }

    public void setNtrs(List<Rule> ntrs) {
	this.ntrs = ntrs;
    }

    public List<BoundedOperator> getBops() {
	return bops;
    }

    public List<BoundedOperator> getBops(BoundedOperator.BOP_TYPE type) {
	List<BoundedOperator> list = new ArrayList<>();
	for (int i = 0; i < bops.size(); i++) {
	    if (bops.get(i).getType() == type)
		list.add(bops.get(i));
	}
	return list;
    }

    public void setBops(List<BoundedOperator> bops) {
	this.bops = bops;
    }

    public int getBopNumber(BoundedOperator.BOP_TYPE type) {
	int result = 0;
	for (int i = 0; i < bops.size(); i++) {
	    if (bops.get(i).getType() == type)
		result++;
	}
	return result;
    }

    public String getFilePath() {
	return filePath;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    public List<Specification> getSpecifications() {
	return this.specs;
    }

    public void groupStrs() {
	List<Rule> toRemove = new ArrayList<>();
	int limit = 0;
	if (!strs.isEmpty())
	    limit = 1;

	for (Rule rule : strs) {
	    for (int j = limit; j < strs.size(); j++) {
		if (rule.getConsequent().getName().equals(strs.get(j).getConsequent().getName())) {
		    rule.getSimilarRules().add(strs.get(j));
		    toRemove.add(strs.get(j));
		}
	    }
	    limit++;
	}

	for (Rule rule : toRemove) {
	    strs.remove(rule);
	}
    }

    public void groupNtrs() {
	List<Rule> toRemove = new ArrayList<>();
	int limit = 0;
	if (!ntrs.isEmpty())
	    limit = 1;

	for (Rule rule : ntrs) {
	    for (int j = limit; j < ntrs.size(); j++) {
		if (rule.getConsequent().getName().equals(ntrs.get(j).getConsequent().getName())) {
		    rule.getSimilarRules().add(ntrs.get(j));
		    toRemove.add(ntrs.get(j));
		}
	    }
	    limit++;
	}

	for (Rule rule : toRemove) {
	    ntrs.remove(rule);
	}
    }

}
