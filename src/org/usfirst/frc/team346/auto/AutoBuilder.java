package org.usfirst.frc.team346.auto;

public class AutoBuilder {

	private AutoPlan gg, bg, gb, bb;
	
	public AutoBuilder(AutoPlan _GSwitchGScale, AutoPlan _BSwitchGScale, AutoPlan _GSwitchBScale, AutoPlan _BSwitchBScale) {
		this.gg = _GSwitchGScale;
		this.bg = _BSwitchGScale;
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
	
	public String getGoals() {
		String ret = "";
		ret += "GG| " + this.gg.getGoal() + "  ";
		ret += "BG| " + this.bg.getGoal() + "  ";
		ret += "GB| " + this.gb.getGoal() + "  ";
		ret += "BB| " + this.bb.getGoal();
		
		return ret;
	}
	
}