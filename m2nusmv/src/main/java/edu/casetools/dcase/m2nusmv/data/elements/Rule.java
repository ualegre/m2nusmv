package edu.casetools.dcase.m2nusmv.data.elements;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String id;
    private List<RuleElement> antecedents;
    private RuleElement consequent;

    public Rule() {
	id = "";
	antecedents = new ArrayList<>();
	consequent = new RuleElement();
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

}
