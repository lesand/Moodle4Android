package moodleObjects;

import java.io.Serializable;

public class infoRecurso implements Serializable {
	
	  String name;
	  String idItemCourse;
	  String idSection;
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
	  
	  
	  
	  public String getDescription()
	  {
		  return this.Description;
	  }
	  
	  public infoRecurso(String Name, String IdItemCourse, String IdSection, String Description)
	  {
		  this.name = Name;
		  this.idItemCourse = IdItemCourse;
		  this.idSection = IdSection;
		  this.Description = Description;
	  }
	    
}
