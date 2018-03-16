package org.usfirst.frc.team346.auto;

import org.usfirst.frc.team346.auto.plans.AutoPlan;

public class AutoBuilder {

	private AutoPlan gg, bg, gb, bb;
	
	public AutoBuilder(AutoPlan _GSwitchGScale, AutoPlan _BSwitchGScale, AutoPlan _GSwitchBScale, AutoPlan _BSwitchBScale) {
		this.gg = _GSwitchGScale;
		this.bg = _BSwitchBScale;
		this.gb = _GSwitchBScale;
		this.bb = _BSwitchBScale;
	}
	
	public AutoBuilder(AutoPlan _singleOption) {
		this.gg = _singleOption;
		this.bg = _singleOption;
		this.gb = _singleOption;
		this.bb = _singleOption;
	}
	
	public AutoPlan getGG() {
		return this.gg;
	}
	
	public AutoPlan getBG() {
		return this.bg;
	}
	
	public AutoPlan getGB() {
		return this.gb;
	}
	
	public AutoPlan getBB() {
		return this.bb;
	}
	
}