package moodleObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListHomeWorks implements Serializable {

	ArrayList<infoHomeWork> listHomeWorks;
	infoCourse currentCourse;
	
	public void addElement(infoHomeWork object)
	{
		listHomeWorks.add(object);
	}
	
	public infoHomeWork getElement(int id)
	{
		return listHomeWorks.get(id);
	}
	
	public void deleteElement(int id)
	{
		listHomeWorks.remove(id);
	}
	
	public int SizeList()
	{
		return listHomeWorks.size();
	}
	
	public ListHomeWorks(infoCourse currentCourse)
	{
		listHomeWorks = new ArrayList<infoHomeWork>();
		this.currentCourse = currentCourse;
	}
	
	public void setCourse(infoCourse course)
	{
		this.currentCourse = course;
	}
	
	public infoCourse getCourse()
	{
		return this.currentCourse;
	}
}
