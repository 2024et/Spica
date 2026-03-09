package Beans;

public class approverBeans {
	private String id;	
	private String document_id;	
	private String role;
	private String status;	
	private String message;	
	
	public approverBeans() {
	}
	
	public approverBeans(String id, String document_id, String role, String status, String message) {
		this.id = id;
		this.document_id = document_id;
		this.role = role;
		this.status = status;
		this.message = message;
	}
	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}
	public String getDocument_id() {
	    return document_id;
	}

	public void setDocument_id(String document_id) {
	    this.document_id = document_id;
	}
	
	
	public String getRole() {
	    return role;
	}

	public void setRole(String role) {
	    this.role = role;
	}
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}
}
