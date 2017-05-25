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
    	this.stateName = stateName.replaceAll("\\s+","_");
    }
    
	public String getOperatorName() {
		return this.operatorName;	
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
