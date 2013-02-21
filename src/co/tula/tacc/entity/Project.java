package co.tula.tacc.entity;

public class Project {
	private String mName;
	private long mId;
	private long mTaccId;
	
	public Project(long id, long taccId, final String name)
	{
		mName = name;
		mId = id;
		mTaccId =taccId; 
	}
	
	public Project()
	{
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

	public long getTaccId() {
		return mTaccId;
	}

	public void setTaccId(long taccId) {
		mTaccId = taccId;
	}
	
	@Override
	public String toString() {
		return mName;
	}
}
