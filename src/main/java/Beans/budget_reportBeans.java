package Beans;

public class budget_reportBeans {
	private String id;
	private String group_id;
	private String created_at;
	private String name;
	private String category_name;
	private int amount;
	
	public budget_reportBeans() {
		
	}
	public budget_reportBeans(String id,String name) {
		this.id = id;
		this.name = name;
	}
	public budget_reportBeans(String id,String group_id, String name, String created_at, String category_name, int amount) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.name = name;
		this.category_name = category_name;
		this.amount = amount;
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
	
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_name() {
		return category_name;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
