package edu.casetools.dcase.m2nusmv.data.elements;

public class RuleElement {

    private String name;
    private String status;

    public RuleElement() {
		name = "";
		status = "";
    }

    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public String getStatus() {
    	return status;
    }

    public void setStatus(String status) {
    	this.status = status;
    }

}
