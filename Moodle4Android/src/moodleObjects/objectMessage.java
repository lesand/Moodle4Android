package moodleObjects;

public class objectMessage {
	
	private int user;
	private int id;
	private String Message;
	private long TimeCreate;
	
	public int getId()
	{
		return id;
	}
	
	
	public int getUser()
	{
		return user;
	}
	
	public String getMessage()
	{
		return Message;
		
	}
	
	public long TimeCreate()
	{
		return TimeCreate;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setUser(int user)
	{
		this.user = user;
	}
	
	public void setMessage(String Message)
	{
		this.Message = Message;
	}
	
	public void setTimeCreate(long TimeCreate)
	{
		this.TimeCreate = TimeCreate;
	}
	
	public objectMessage(int user, String Message, long TimeCreate, int id)
	{
		this.user = user;
		this.Message = Message;
		this.TimeCreate = TimeCreate;
		this.id= id;
		
	}
	

}
