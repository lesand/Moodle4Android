package moodleObjects;

import java.io.Serializable;

public class infoCourse implements Serializable{

	String NameShortCourse;
	String NameLargeCourse;
	String IdMoodleCourse;
	userInfo currentUser;
	
	
	public void SetcurrentUser(userInfo user)
	{
		this.currentUser = user;
	}
	
	public userInfo getCurrentUser()
	{
		return this.currentUser;
	}
	public  void setNameLargeCourse(String NameLarge)
	{
		this.NameLargeCourse = NameLarge;
	}
	
	public  void setNameShortCourse(String NameShort)
	{
		this.NameShortCourse = NameShort;
	}
	
	public  void SetIdMoodleCourse(String IdCourse)
	{
		this.IdMoodleCourse = IdCourse;
	}
	
	public String getNameLargeCourse()
	{
		return this.NameLargeCourse;
	}
	
	public String getNameShortCourse()
	{
		return this.NameShortCourse;
	}
	
	public String getIdMoodleCourse()
	{
		return this.IdMoodleCourse;
	}
	
	public infoCourse(String IdCourse, String NameShort, String NameLarge, userInfo user)
	{
		this.IdMoodleCourse = IdCourse;
		this.NameShortCourse = NameShort;
		this.NameLargeCourse = NameLarge;
		this.currentUser = user;
	}
	
}
