package Beans;

public class account_reportBeans {
	private String id;	
	private String group_id;	
	private String budget_id;
	private String created_at;	
	private String name;
	private String start_period;
	private String end_period;
	
	public account_reportBeans() {
		
	}
	public account_reportBeans(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public account_reportBeans(String id, String group_id, String budget_id, String created_at, String name, String start_period, String end_period) {
		this.id = id;
		this.group_id = group_id;
		this.budget_id = budget_id;
		this.created_at = created_at;
		this.name = name;
		this.start_period = start_period;
		this.end_period = end_period;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	public String getBudget_id() {
		return budget_id;
	}
	public void setBudget_id(String budget_id) {
		this.budget_id = budget_id;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStart_period() {
		return start_period;
	}
	public void setStart_period(String start_period) {
		this.start_period = start_period;
	}
	
	public String getEnd_period() {
		return end_period;
	}
	public void setEnd_period(String end_period) {
		this.end_period = end_period;
	}
	
	
	
	
}
