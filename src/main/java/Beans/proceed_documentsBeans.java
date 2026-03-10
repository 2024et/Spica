package Beans;

public class proceed_documentsBeans {
	private String id;	
	private String group_id;	
	private String created_at;
	private String name;	
	private String pdf_path;
	private String status;
	private String comment;
	
	public proceed_documentsBeans() {
	}
	
	public proceed_documentsBeans(String id, String group_id, String created_at, String name, String pdf_path, String status, String comment) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.name = name;
		this.pdf_path = pdf_path;
		this.status = status;
		this.comment = comment;
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
	
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	public String getPath() {
	    return pdf_path;
	}

	public void setPath(String pdf_path) {
	    this.pdf_path = pdf_path;
	}
	
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
	
	public String getComment() {
	    return comment;
	}

	public void setComment(String comment) {
	    this.comment = comment;
	}
	
}
