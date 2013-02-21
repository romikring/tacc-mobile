package co.tula.tacc.entity;

public class Task
{
	private long id;
	private long projectId;
	private String description;
	private int state;
	
	public Task(long id, long projectId, final String description, int state)
	{
		this.id = id;
		this.projectId = projectId;
		this.description = description;
		this.state = state;
	}
	
	public Task()
	{
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
