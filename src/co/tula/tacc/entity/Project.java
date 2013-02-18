package co.tula.tacc.entity;

public class Project {
	private String mName;
	private long mId;
	
	public Project(long id, final String name)
	{
		mName = name;
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public Project setName(final String mName) {
		this.mName = mName;
		
		return this;
	}

	public long getId() {
		return mId;
	}

	public Project setId(long id) {
		this.mId = id;
		
		return this;
	}
}
