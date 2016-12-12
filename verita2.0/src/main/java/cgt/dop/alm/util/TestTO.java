package cgt.dop.alm.util;

public class TestTO {
	
	private String id;
	
	private String name;
	
	private String applicationName;

	private String status;
	
	public TestTO() {		
	}

	public TestTO(String id, String name, String applicationName,
		String status) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.applicationName = applicationName;
	    this.status = status;
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

	public String getApplicationName() {
	    return applicationName;
	}

	public void setApplicationName(String applicationName) {
	    this.applicationName = applicationName;
	}

	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("TestTO [id=");
	    builder.append(id);
	    builder.append(", name=");
	    builder.append(name);
	    builder.append(", applicationName=");
	    builder.append(applicationName);
	    builder.append(", status=");
	    builder.append(status);
	    builder.append("]");
	    return builder.toString();
	}
	
	
}
