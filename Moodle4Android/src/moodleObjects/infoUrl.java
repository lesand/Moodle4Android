package moodleObjects;

public class infoUrl {

	String idItem;
	String Section;
	String Name;
	String Content;
	
	public void setidItem(String idiTem)
	{
		this.idItem = idiTem;
	}
	
	public void setSection(String Section)
	{
		this.Section = Section;
	}
	
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public void setContent(String Content)
	{
		this.Content = Content;
	}
	
	public String getIdItem()
	{
		return this.idItem;
	}
	
	public String getSection()
	{
		return this.Section;
	}
	
	public String getName()
	{
		return this.Name;
	}
	
	public String getContent()
	{
		return this.Content;
	}
	
	public infoUrl(String idItem, String Section, String Name, String Content)
	{
		this.idItem = idItem;
		this.Section = Section;
		this.Name = Name;
		this.Content = Content;
	}
}
