package Beans;

public class documentApproverlDTOBeans {
	private String id;	
	private String group_id;	
	private String created_at;
	private String name;	
	private String pdf_path;
	private String accountant;
	private String vice_president;
	private String president;
	private String advisor;
	
	public documentApproverlDTOBeans() {
	}
	
	public documentApproverlDTOBeans(String id, String group_id, String created_at, String name, String pdf_path, String status, String accountant,String vice_president, String president, String advisor) {
		this.id = id;
		this.group_id = group_id;
		this.created_at = created_at;
		this.name = name;
		this.pdf_path = pdf_path;
		this.accountant = accountant;
		this.vice_president = vice_president;
		this.president = president;
		this.advisor = advisor;
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
	
	public String getAccountant() {
	    return accountant;
	}

	public void setAccountant(String accountant) {
	    this.accountant = accountant;
	}
	
	public String getVice_President() {
	    return vice_president;
	}

	public void setVice_President(String vice_president) {
	    this.vice_president = vice_president;
	}
	
	public String getPresident() {
	    return president;
	}

	public void setPresident(String president) {
	    this.president = president;
	}
	
	public String getAdvisor() {
	    return advisor;
	}

	public void setAdvisor(String advisor) {
	    this.advisor = advisor;
	}
}
