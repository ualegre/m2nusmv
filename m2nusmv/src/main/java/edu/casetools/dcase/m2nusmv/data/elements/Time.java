package edu.casetools.dcase.m2nusmv.data.elements;

public class Time {

	public final static int EMPTY = -1;
	private long lowBound;
	private long highBound;
	
	public Time(){
		setLowBound(0);
		setHighBound(EMPTY);
	}
	
	public Time(long lowBound,long highBound){
		this.setLowBound(lowBound);
		this.setHighBound(highBound);
	}

	public long getHighBound() {
		return highBound;
	}

	public void setHighBound(long highBound) {
		this.highBound = highBound;
	}

	public long getLowBound() {
		return lowBound;
	}

	public void setLowBound(long l) {
		this.lowBound = l;
	}
}
