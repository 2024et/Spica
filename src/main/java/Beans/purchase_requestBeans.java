package Beans;

import java.io.Serializable;

public class purchase_requestBeans implements Serializable {
	private String id;	
	private String group_id;	
	private String created_at;	
	private String name;	
	private String item;	
	private int amount;	
	private String user_id;
	private String store_link;	
	private String purpose;	
	private String status;
	
	public purchase_requestBeans() {
		
	}
	
	public purchase_requestBeans(String id, String group_id, String created_at, String name, String item, int amount, String user_id, String store_link, String purpose, String status) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.name = name;
		this.item = item;
		this.amount = amount;
		this.user_id = user_id;
		this.store_link = store_link;
		this.purpose = purpose;
		this.status = status;
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
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getStore_link() {
		return store_link;
	}
	public void setStore_link(String store_link) {
		this.store_link = store_link;
	}
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
