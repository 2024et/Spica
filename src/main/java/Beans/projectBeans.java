package Beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class projectBeans implements Serializable {
	private String id;	
	private String group_id;
	private LocalDateTime created_at;
	private String name;		
	private String status;
	
	public projectBeans() {
	}
	
	public projectBeans(String id, String group_id, LocalDateTime created_at, String name, String status) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.name = name;
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

	public LocalDateTime getCreated_at() {
	    return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
	    this.created_at = created_at;
	}
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
}
