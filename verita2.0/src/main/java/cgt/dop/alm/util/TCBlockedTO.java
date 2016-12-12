package cgt.dop.alm.util;

public class TCBlockedTO {
	
	private String id;
	
	private String name;	
	
	private String status;
	
	private String createdBy;
	
	private String createdDate;
		
	public TCBlockedTO() {		
	}

	public TCBlockedTO(String id, String name, String status, String createdBy, String createdDate) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
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

	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	@Override
	public String toString() {
		return "TCBlockedTO [ id=" + id + ", name=" + name + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + "]";
	}

	
	
	
	
}
