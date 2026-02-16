package Beans;

public class chatBeans {
	private String id;	
	private String user_id;	
	private String request_id;
	private String created_at;	
	private String message;	
	
	public chatBeans() {
	}
	
	public chatBeans(String id, String user_id, String request_id, String created_at, String message) {
		this.id = id;
		this.user_id = user_id;
		this.request_id = request_id;
		this.created_at = created_at;
		this.message = message;
	}
	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}
	
}
