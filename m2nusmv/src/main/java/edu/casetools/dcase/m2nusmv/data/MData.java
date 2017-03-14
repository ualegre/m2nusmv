package edu.casetools.dcase.m2nusmv.data;

import java.util.ArrayList;
import java.util.List;

import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator;
import edu.casetools.dcase.m2nusmv.data.elements.Event;
import edu.casetools.dcase.m2nusmv.data.elements.Rule;
import edu.casetools.dcase.m2nusmv.data.elements.Specification;
import edu.casetools.dcase.m2nusmv.data.elements.State;

public class MData {

    private int maxIteration;
    private String filePath;
    private List<State> states;
    private List<State> independentStates;
    private List<Event> events;
    private List<Rule> strs;
    private List<Rule> ntrs;
    private List<BoundedOperator> bops;
    private List<Specification> ctlspecs;
    private List<Specification> ltlspecs;
    private List<Specification> pslspecs;
    
    public MData() {
    	initialiseLists();
    }

    private void initialiseLists() {
		states = new ArrayList<>();
		events = new ArrayList<>();
		strs = new ArrayList<>();
		ntrs = new ArrayList<>();
		bops = new ArrayList<>();
		ctlspecs = new ArrayList<>();
		ltlspecs = new ArrayList<>();
		pslspecs = new ArrayList<>();

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

    public List<Event> getEvents() {
	return events;
    }

    public void setEvents(List<Event> events) {
	this.events = events;
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

    public void initialiseTestCase(String filepath) {
	events = new ArrayList<>();
	getIndependentStates();
	this.filePath = filepath;

    }

	public List<Specification> getCtlSpecifications() {
		return this.ctlspecs;
	}

}
