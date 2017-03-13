package edu.casetools.dcase.m2nusmv.data.elements;

public class State {

    private String id;
    private String name;
    private String initialValue;
    private boolean isIndependent;
    private boolean value;

    public State() {
	id = "";
	name = "";
	initialValue = "";
	this.setValue(false);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getInitialValue() {
	return initialValue.toUpperCase();
    }

    public void setInitialValue(String initialValue) {
	this.initialValue = initialValue;
	if (initialValue.equalsIgnoreCase("false")) {
	    value = false;
	} else {
	    value = true;
	}
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public boolean getValue() {
	return value;
    }

    public void setValue(boolean value) {
	this.value = value;
    }

    public boolean isIndependent() {
	return isIndependent;
    }

    public void setIndepedence(boolean isIndependent) {
	this.isIndependent = isIndependent;
    }

    public void setIndepedence(String isIndependent) {
	if (isIndependent.equalsIgnoreCase("true"))
	    this.isIndependent = true;
	else
	    this.isIndependent = false;
    }

}
