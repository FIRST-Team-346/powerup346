package com.team346.frc2018.auto.step;

public interface Step {
	public interface Action {

	    public abstract boolean isComplete();

	    public abstract void update();

	    public abstract void complete();

	    public abstract void begin();
	}
}
