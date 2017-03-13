package edu.casetools.dcase.m2nusmv.data.elements;

public class Event {

    private String id;
    private String stateId;
    private String time;
    private String stateValue;

    public Event() {
	id = "";
	stateId = "";
	time = "";
	stateValue = "";
    }

    public Event(String id, String stateId, String time, String stateValue) {
	id = "";
	stateId = "";
	time = "";
	stateValue = "";
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getStateId() {
	return stateId;
    }

    public void setStateId(String stateId) {
	this.stateId = stateId;
    }

    public String getTime() {
	return time;
    }

    public void setTime(String time) {
	this.time = time;
    }

    public String getStateValue() {
	return stateValue;
    }

    public void setStateValue(String status) {
	this.stateValue = status;
    }

}
