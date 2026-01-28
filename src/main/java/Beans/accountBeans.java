package Beans;

import java.io.Serializable;

public class accountBeans implements Serializable {
	private String id;	
	private String group_id;	
	private String group_name;	
	private String name;
	private String pass;	
	private String email;	
	private String role;	
	
	public accountBeans() {
	}
	
	public accountBeans(String id,String group_id,String group_name, String name, String pass, String email,String role) {
		this.id = id;
		this.group_id = group_id;
		this.group_name = group_name;
		this.name = name;
		this.pass = pass;
		this.email = email;
		this.role = role;
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
	public String getGroup_name() {
	    return group_name;
	}

	public void setGroup_name(String group_name) {
	    this.group_name = group_name;
	}
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getPass() {
	    return pass;
	}

	public void setPass(String pass) {
	    this.pass = pass;
	}
	
	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}
	public String getRole() {
	    return role;
	}

	public void setRole(String role) {
	    this.role = role;
	}
}
