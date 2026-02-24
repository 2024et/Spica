package Beans;

import java.io.Serializable;

public class account_report_summaryBeans implements Serializable {
	private String name;
	private int budget;
	private int amount;
	private int fluctuation;
	
	public account_report_summaryBeans() {
		
	}
	public account_report_summaryBeans(String name, int budget, int amount, int fluctuation) {
		this.name = name;
		this.budget = budget;
		this.amount = amount;
		this.fluctuation = fluctuation;		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getFluctuation() {
		return fluctuation;
	}
	public void setFluctuation(int fluctuation) {
		this.fluctuation = fluctuation;
	}
}
