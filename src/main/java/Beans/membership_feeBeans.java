package Beans;

public class membership_feeBeans {
	private String id;
	private String group_id;
	private String start_date;
	private String end_date;
	private int fee;
	
	public membership_feeBeans() {
	}
	
	public membership_feeBeans(String id, String group_id, String start_date, String end_date, int fee) {
		this.id = id;
		this.group_id = group_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.fee = fee;
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
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public int getFee() {
	    return fee;
	}

	public void setFee(int fee) {
	    this.fee = fee;
	}
	

}
