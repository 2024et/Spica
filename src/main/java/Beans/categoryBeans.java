package Beans;

import java.io.Serializable;

public class categoryBeans implements Serializable {
	
	private String id;	
	private String group_id;	
	private String name;	
	private String type;	
	private String status;
	
	public categoryBeans() {
	}
	
	public categoryBeans(String id, String group_id, String name, String type, String status) {
		this.id = id;
		this.group_id = group_id;
		this.name = name;
		this.type = type;
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
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	public String getType() {
	    return type;
	}

	public void setType(String type) {
	    this.type = type;
	}
	
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
}
