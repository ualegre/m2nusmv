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

public class State {

    private String id;
    private String name;
    private String initialValue;
    private boolean isIndependent;
    private boolean value;

    public State() {
		id = "";
		name = "";
		initialValue = "FALSE";
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
    	if (("false").equalsIgnoreCase(initialValue)) {
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
		if (("true").equalsIgnoreCase(isIndependent))
		    this.isIndependent = true;
		else
		    this.isIndependent = false;
    }

}
