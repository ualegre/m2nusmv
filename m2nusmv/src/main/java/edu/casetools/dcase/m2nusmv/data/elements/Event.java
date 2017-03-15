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
		this.id = id;
		this.stateId = stateId;
		this.time = time;
		this.stateValue = stateValue;
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
