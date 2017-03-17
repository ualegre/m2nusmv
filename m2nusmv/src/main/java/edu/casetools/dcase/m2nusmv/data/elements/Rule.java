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

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String id;
    private List<RuleElement> antecedents;
    private List<BoundedOperator> bops;
    private List<Rule> similarRules;
    private RuleElement consequent;

    public Rule() {
		id = "";
		antecedents  = new ArrayList<>();
		bops  = new ArrayList<>();
		consequent   = new RuleElement();
		similarRules = new ArrayList<>();
    }

    public String getId() {
    	return id;
    }

    public void setId(String id) {
    	this.id = id;
    }

    public List<RuleElement> getAntecedents() {
    	return antecedents;
    }

    public void setAntecedents(List<RuleElement> antecedents) {
    	this.antecedents = antecedents;
    }

    public RuleElement getConsequent() {
    	return consequent;
    }

    public void setConsequent(RuleElement consequent) {
    	this.consequent = consequent;
    }

	public List<Rule> getSimilarRules() {
		return similarRules;
	}

	public void setSimilarRules(List<Rule> similarRules) {
		this.similarRules = similarRules;
	}

	public List<BoundedOperator> getBops() {
		return bops;
	}

	public void setBops(List<BoundedOperator> bop) {
		this.bops = bop;
	}

	
    
}
