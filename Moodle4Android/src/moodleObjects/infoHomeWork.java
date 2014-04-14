package moodleObjects;

import java.io.Serializable;

public class infoHomeWork implements Serializable {
	
	 
	  String name;
	  String idItemCourse;
	  String idSection;
	  String DateInit;
	  String DateEnd;
	  String Description;
	  
	  public void setName(String name)
	  {
		  this.name = name;
	  }
	  
	  public void setidItemCourse(String IditemCourse)
	  {
		  this.idItemCourse = IditemCourse;
	  }
	  
	  public void setidSection(String idSection)
	  {
		  this.idSection = idSection;
	  }
	  
	  public void setDateInit(String DateInit)
	  {
		  this.DateInit = DateInit;
	  }
	  
	  public void setDateEnd(String DateEnd)
	  {
		  this.DateEnd = DateEnd;
	  }
	  
	  public void setDescription(String Description)
	  {
		  this.Description = Description;
	  }
	  
	  public String getName()
	  {
		  return this.name;
	  }
	  
	  public String getidItemCourse()
	  {
		  return this.idItemCourse;
	  }
	  
	  public String getidSection()
	  {
		  return this.idSection;
	  }
	  
	  public String getDateInit()
	  {
		  return this.DateInit;
	  }
	  
	  public String getDateEnd()
	  {
		  return this.DateEnd;
	  }
	  
	  public String getDescription()
	  {
		  return this.Description;
	  }
	  
	  public infoHomeWork(String Name, String IdItemCourse, String IdSection, String Description)
	  {
		  this.name = Name;
		  this.idItemCourse = IdItemCourse;
		  this.idSection = IdSection;
		  this.Description = Description;
	  }
	    
}
