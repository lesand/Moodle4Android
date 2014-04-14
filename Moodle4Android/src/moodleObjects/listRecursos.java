package moodleObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class listRecursos implements Serializable {
	
	ArrayList<infoRecurso> listRecursos;
	infoCourse currentCourse;
	
	public void addElement(infoRecurso object)
	{
		listRecursos.add(object);
	}
	
	public infoRecurso getElement(int id)
	{
		return listRecursos.get(id);
	}
	
	public int SizeList()
	{
		return listRecursos.size();
	}
	
	public listRecursos(infoCourse currentCourse)
	{
		listRecursos = new ArrayList<infoRecurso>();
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
