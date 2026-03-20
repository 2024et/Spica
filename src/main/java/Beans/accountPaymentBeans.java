package Beans;

public class accountPaymentBeans {
	private String id;
	private String name;
	private String date;
	private String status;
	private String role;
	
	public accountPaymentBeans() {
	}
	
	public accountPaymentBeans(String id, String name, String date, String status, String role) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.status = status;
		this.role = role;
	}
	
	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	public String getDate() {
	    return date;
	}

	public void setDate(String date) {
	    this.date = date;
	}
	
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
	
	public String getRole() {
	    return role;
	}

	public void setRole(String role) {
	    this.role = role;
	}

}
