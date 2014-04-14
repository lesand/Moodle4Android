package moodleObjects;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class InfoUser {

	
	private static InfoUser INSTANCE = null;
	private synchronized static void createInstance() {
	        if (INSTANCE == null) { 
	            INSTANCE = new InfoUser();
	        }
	}
	 
	public static InfoUser getInstance() {
	        createInstance();
	        return INSTANCE;
	}
	
	
	
	public int userId;
	public ArrayList<moodleObj> listClasesInfo;
	public course CurrentCourse;
	public int currentWeek;
	public int currentHomeWork;
	public int currentParticipant;
	public ArrayList<weekObject> listWeeks;
	public ArrayList<HomeWork> tareas;
	public courseContentM contenidoDeCurso;
	public  ArrayList<String> categories = new ArrayList<String>();
	public courseContentM global;
	public ArrayList<moodleObj> Participants;
	public int currentIdChat;
	
	private  void chargecategories()
	{	  
		categories = new ArrayList<String>();
		categories.add("Descripcion del Curso");
		categories.add("OBJETIVOS DEL CURSO");
		categories.add("POLITICAS DE LA CLASE");
		categories.add("Bibliografia");
		categories.add("Bibliografía Complementaria");	
	}
	
	
	public  void Update() throws ParserConfigurationException, SAXException, IOException
	{
		chargecategories();
		ArrayList<moodleObj> list=new ArrayList<moodleObj>();
		try {
			String idCourse = CurrentCourse.getCourseIDNumber();
			connecFunc.getInstance().tryConnect("courseid="+idCourse,"core_course_get_contents");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		String str=connecFunc.getInstance().xml;
		enumObjects obj=new enumObjects(enumObjects.moodleEnum.COURSE_CONTENT,str); 
    	try {
    		obj.parseString();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	list=obj.giveMetheObject();
    	global = (courseContentM) list.get(0); 
    	fillListSemanas();
	}
	
	
	public void UpdateParticipants() throws ParserConfigurationException, SAXException, IOException{
		ArrayList<moodleObj> list=new ArrayList<moodleObj>();
		try {
			String idCourse = CurrentCourse.getCourseIDNumber();
			connecFunc.getInstance().tryConnect("courseid="+idCourse,"core_enrol_get_enrolled_users");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		String str=connecFunc.getInstance().xml;
		enumObjects obj=new enumObjects(enumObjects.moodleEnum.PARTICIPANTS,str); 
    	try {
    		obj.parseString();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	list=obj.giveMetheObject();
    	Participants = list;
	}
	public sectionCourse findsection(String Name)
	{
		for(int i=0; i<global.getSections().size(); i++)
		{
			if(global.getSections().get(i).getSectionName().compareTo(Name)==0)
			{
				return global.getSections().get(i);
			}
		}
		 return null;
	}
	
	public itemCourse findItem(String Name)	{
		for(int i=0; i<global.getSections().size(); i++)
		{
			for(int e=0; e<global.getSections().get(i).getItems().size(); e++)
			{
				itemCourse temp = global.getSections().get(i).getItems().get(e);
				if(temp.getName().compareTo(Name)==0)
				{
					return global.getSections().get(i).getItems().get(e+1);
				}
			}
		}
		return null;
	} 

	public void fillListSemanas()
	{
		ArrayList<weekObject> listWeeks = new ArrayList<weekObject>();
		
		for(int i=4; i<global.getSections().size(); i++)
		{
			weekObject temp = new weekObject();
			temp.id = i-4;
			temp.idSeccion = global.getSections().get(i).getSectionId();
			itemCourse findtema = findItemsWeekByTile(global.getSections().get(i), "TEMA");
			itemCourse findObjetivos = findItemsWeekByTile(global.getSections().get(i), "OBJETIVOS DEL TEMA");
			ArrayList<itemCourse> tareas = findItemsWeekByType(global.getSections().get(i), "assign");
			
			
			if(findtema == null)
			{
				temp.tema = "Sin Tema";
			}else
			{
				temp.tema = findtema.getLabel();
			}
			
			if(findObjetivos == null)
			{
				temp.objetivos = "Sin Objetivos";
			}else
			{
				temp.objetivos = findObjetivos.getLabel();
			}
			
			if(tareas.size() ==0)
			{
				temp.tarea = null;
			}else
			{
				temp.tarea = tareas;
			}
			listWeeks.add(temp);
			
		
		}
		this.listWeeks = listWeeks;
		
	}
	
   public ArrayList<itemCourse> findItemsWeekByType(sectionCourse sc, String type)
   {
	   
	   ArrayList<itemCourse> listaItems = new ArrayList<itemCourse>();
	   
	   for(int i=0; i<sc.getItems().size(); i++)
	   {
		   if(sc.getItems().get(i).getItemType().compareTo(type)==0)
		   {
			   listaItems.add(sc.getItems().get(i));
		   }
			   
	   }
	   return listaItems;
	   
   }
   
   public itemCourse findItemsWeekByTile(sectionCourse sc, String Name)
   {	   
	   for(int i=0; i<sc.getItems().size(); i++)
	   {
		   if(sc.getItems().get(i).getName().compareTo(Name)==0)
		   {
			   return sc.getItems().get(i+1);
		   }
			   
	   }
	   return null;
   }
	   
	
	
	
	
	
	
	
}
