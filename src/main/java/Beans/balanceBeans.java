package Beans;

import java.io.Serializable;

public class balanceBeans implements Serializable  {
	private String id;	
	private String group_id;	
	private String created_at;	
	private String name;	
	private String item;	
	private int amount;	
	private String project;
	private String category;	
	private String memo;	
	private String type;	//このカラムは収入/支出であって、購入申請/収支の話ではない。
	
	public balanceBeans() {
		
	}
	
	public balanceBeans(String id, String group_id, String created_at, String name, String item, int amount, String project, String category, String memo, String type) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.name = name;
		this.item = item;
		this.amount = amount;
		this.project = project;
		this.category = category;
		this.memo = memo;
		this.type = type;
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
	public void setName(String name) {
		this.name = name;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
