package edu.casetools.dcase.m2nusmv.data.elements;

public class BoundedOperator {

    public enum BOP_TYPE {
    	STRONG_IMMEDIATE_PAST, WEAK_IMMEDIATE_PAST, STRONG_ABSOLUTE_PAST, WEAK_ABSOLUTE_PAST
    };

    private String id;
    private String operatorName;
    private String status;
    private BOP_TYPE type;
    private String lowBound;
    private String uppBound;
    private String stateName;

    public BoundedOperator() {
		id = "";
		status = "";
		lowBound = "";
		uppBound = "";
		stateName = "";
    }

    public String getId() {
    	return id;
    }

    public void setId(String id) {
    	this.id = id;
    }

    public BOP_TYPE getType() {
    	return type;
    }

    public void setType(BOP_TYPE type) {
    	this.type = type;
    }

    public String getLowBound() {
    	return lowBound;
    }

    public void setLowBound(String lowBound) {
    	this.lowBound = lowBound;
    }

    public String getUppBound() {
    	return uppBound;
    }

    public void setUppBound(String uppBound) {
    	this.uppBound = uppBound;
    }

    public String getStatus() {
    	return status;
    }

    public void setStatus(String status) {
    	this.status = status;
    }

    public String getStateName() {
    	return stateName;
    }

    public void setStateName(String stateName) {
    	this.stateName = stateName;
    }
    
	public String getOperatorName() {
		return this.operatorName;	
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
