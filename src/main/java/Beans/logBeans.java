package Beans;

public class logBeans {
	private String id;
	private String group_id;
	private String created_at;
	private String log;
	
	public logBeans() {
	}
	
	public logBeans(String id, String group_id, String created_at, String log) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.log = log;
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
	
	public String getLog() {
	    return log;
	}

	public void setLog(String log) {
	    this.log = log;
	}
}
