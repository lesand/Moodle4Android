package moodleObjects;

import java.io.Serializable;


public class userInfo implements Serializable{
	
	String Name;
	String PassWord;
	String IdMoodle;
	String UrlMoodle;
	String contextID;
	String RoleId;
	
	
	public void SetIdRole(String RoleID)
	{
		this.RoleId = RoleID;
	}
	
	public String getIdRole()
	{
		return this.RoleId;
	}
	
	
	public void setUrlMoodle (String Url)
	{
		this.UrlMoodle = Url;
	}
	
	public void setContextId(String Context)
	{
		this.contextID = Context;
	}
	
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public void setPassWord(String PassWord)
	{
		this.PassWord = PassWord;
	}
	
	public void setIdMoodle(String IdMoodle)
	{
		this.IdMoodle = IdMoodle;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public String getPassWord()
	{
		return PassWord;
	}
	
	public String getIdMoodle()
	{
		return IdMoodle;
	}
	
	public String getUrl()
	{
		return this.UrlMoodle;
	}
	
	public String getContextID()
	{
		return this.contextID;
	}
	
	public userInfo(String Name, String PassWord, String IdMoodle, String Url, String Context, String RoleID)
	{
		this.Name = Name;
		this.PassWord = PassWord;
		this.IdMoodle = IdMoodle;
		this.UrlMoodle = Url;
		this.contextID = Context;
		this.RoleId = RoleID;
	}

}
