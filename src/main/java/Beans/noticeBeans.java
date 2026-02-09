package Beans;

import java.io.Serializable;

public class noticeBeans implements Serializable {
	private String id;
	private String user_id;
	private String created_at;
	private String message;
	
	public noticeBeans() {
	}
	
	public noticeBeans(String id, String user_id, String created_at, String message) {
		this.id = id;
		this.user_id = user_id;
		this.created_at = created_at;
		this.message = message;
	}
	
	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}
	
	public String getUserId() {
	    return user_id;
	}

	public void setUserId(String user_id) {
	    this.user_id = user_id;
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
