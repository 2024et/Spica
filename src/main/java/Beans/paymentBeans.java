package Beans;

public class paymentBeans {
	private String id;
	private String user_id;
	private String membership_fee_id;
	private String status;
	
	public paymentBeans() {
	}
	
	public paymentBeans(String id, String user_id, String membership_fee_id, String status) {
		this.id = id;
		this.user_id = user_id;
		this.membership_fee_id = membership_fee_id;
		this.status = status;
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
	
	public String getMembership_id() {
		return membership_fee_id;
	}
	public void setMembership_id(String membership_fee_id) {
		this.membership_fee_id = membership_fee_id;
	}
	
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
}
