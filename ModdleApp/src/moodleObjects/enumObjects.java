/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleObjects;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




/**
 *
 * @author josh
 */
public class enumObjects 
{

    //the enum
    public enum moodleEnum 
    {
        COURSE, USER,COURSE_CONTENT,PARTICIPANTS   
    }
    
    //constructor
    public enumObjects(moodleEnum _obj,String _str)
    {
        mood=_obj;
        str=_str;
    }
    
     //the enum
    public enum contentCoursesEnum 
    {
        descripciondelcurso,
        OBJETIVOS,
        POLITICAS,
        SILABO,
        EVALUACION,
        BIBLIOGRAGIA,
        BIBLIOGRAFIA_COMPLEMENTARIA
        
        
    }

    
    moodleEnum mood;
    contentCoursesEnum cont;
    String str;
    ArrayList<moodleObj> _list;
    
    
    
    
    public ArrayList<moodleObj> giveMetheObject() 
    {    
       // moodleObj obj=new moodleObj();
       // ArrayList<moodleObj> list=new ArrayList<moodleObj>();
        switch (mood) 
        {
            case COURSE:
            	//ArrayList<course> list=new ArrayList<course>();
            	            	            	            	
                return _list;                                    
            case USER:
                //user us=new user();                                
                return _list;
                
                         
                        
            default:            	
                return _list;
        }
    }
    
    
    
    public void parseString() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException
    {        
     	XPathFactory xpathFactory;
     	XPath xpath;
        InputSource source;
        InputSource source1;
        DocumentBuilderFactory builderfactory;
        XPathExpression xPathExpression;
        NodeList nodeListCourses;
        NodeList nodeListCoursesLocal;
        NodeList nodeListCoursesLocalSilabo;
    	switch (mood) 
        {
            case COURSE:            	            	            	               
                xpathFactory = XPathFactory.newInstance();
                xpath = xpathFactory.newXPath();               
                builderfactory = DocumentBuilderFactory.newInstance();
                builderfactory.setNamespaceAware(true);                
                XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
                XPath xPath = factory.newXPath();                
                String id,key;                                              
                id= "fullname";
                source= new InputSource(new StringReader(str));
                source1= new InputSource(new StringReader(str));
                xPathExpression= xPath.compile("//RESPONSE//MULTIPLE//SINGLE//KEY[@name='" +id + "']//VALUE");                
                key = xPathExpression.evaluate(source,XPathConstants.STRING).toString();               
                nodeListCourses=  (NodeList) xPathExpression.evaluate(source1,XPathConstants.NODESET);
                _list=new ArrayList<moodleObj>();                                                       
                //get the total of classes to create and add it their fullname
                for (int index = 0; index < nodeListCourses.getLength(); index++) 
                {
                	course cour=new course();
                	String coursename = nodeListCourses.item(index).getTextContent();
                	cour.setCourseFullName(coursename);
                        _list.add(cour);		                	                                        
                }                			
                id = "id";
                source= new InputSource(new StringReader(str));
                source1= new InputSource(new StringReader(str));
                xPathExpression = xPath.compile("//RESPONSE//MULTIPLE//SINGLE//KEY[@name='" +id + "']//VALUE");
                key = xPathExpression.evaluate(source,XPathConstants.STRING).toString();               
                nodeListCourses =  (NodeList) xPathExpression.evaluate(source1,XPathConstants.NODESET);
                //get the ids value of the classes
                for (int index = 0; index < nodeListCourses.getLength(); index++) 
                {                	
                	String courseid = nodeListCourses.item(index).getTextContent();
                	course cour=(course) _list.get(index);
                	cour.setCourseIDNumber(courseid);                    		                	                    
                    //Log.d("Course Name ",coursename);
                }
                id = "shortname";
                source= new InputSource(new StringReader(str));
                source1= new InputSource(new StringReader(str));                
                xPathExpression = xPath.compile("//RESPONSE//MULTIPLE//SINGLE//KEY[@name='" +id + "']//VALUE");
                key = xPathExpression.evaluate(source,XPathConstants.STRING).toString();               
                nodeListCourses =  (NodeList) xPathExpression.evaluate(source1,XPathConstants.NODESET);
                //get the shortname value of the classes
                for (int index = 0; index < nodeListCourses.getLength(); index++) 
                {
                	String courseShortName = nodeListCourses.item(index).getTextContent();
                	course cour=(course) _list.get(index);
                	cour.setCourseShortName(courseShortName);                    		                	                    
                    //Log.d("Course Name ",coursename);
                }                                                  
               break;                                     
            case USER:                                            	
                xpathFactory = XPathFactory.newInstance();
                xpath = xpathFactory.newXPath();               
                builderfactory = DocumentBuilderFactory.newInstance();
                builderfactory.setNamespaceAware(true);                
                factory = javax.xml.xpath.XPathFactory.newInstance();
                xPath = factory.newXPath();
                id= "username";
                source= new InputSource(new StringReader(str));
                source1= new InputSource(new StringReader(str));
                xPathExpression= xPath.compile("//RESPONSE//MULTIPLE//SINGLE//KEY[@name='" +id + "']//VALUE");                
                key = xPathExpression.evaluate(source,XPathConstants.STRING).toString();               
                nodeListCourses=  (NodeList) xPathExpression.evaluate(source1,XPathConstants.NODESET);
                _list=new ArrayList<moodleObj>();                                                       
                int lenTotal= nodeListCourses.getLength();
                //get the total of classes to create and add it their fullname
                for (int index = 0; index < nodeListCourses.getLength(); index++) 
                {
                	user us=new user();
                	String username = nodeListCourses.item(index).getTextContent();
                	us.setSurname(username);
                        _list.add(us);		                	                                        
                }                			
                id = "id";
                source= new InputSource(new StringReader(str));
                source1= new InputSource(new StringReader(str));
                xPathExpression = xPath.compile("//RESPONSE//MULTIPLE//SINGLE//KEY[@name='" +id + "']//VALUE");
                key = xPathExpression.evaluate(source,XPathConstants.STRING).toString();               
                nodeListCourses =  (NodeList) xPathExpression.evaluate(source1,XPathConstants.NODESET);
                //get the ids value of the classes
                for (int index = 0; index < lenTotal; index++) 
                {                	
                	String userid = nodeListCourses.item(index).getTextContent();
                	user us=(user) _list.get(index);
                	us.setUserid(userid);                    		                	                    
                    //Log.d("Course Name ",coursename);
                }
                id = "firstname";
                source= new InputSource(new StringReader(str));
                source1= new InputSource(new StringReader(str));                
                xPathExpression = xPath.compile("//RESPONSE//MULTIPLE//SINGLE//KEY[@name='" +id + "']//VALUE");
                key = xPathExpression.evaluate(source,XPathConstants.STRING).toString();               
                nodeListCourses =  (NodeList) xPathExpression.evaluate(source1,XPathConstants.NODESET);
                //get the shortname value of the classes
                for (int index = 0; index < lenTotal; index++) 
                {
                	String firstName = nodeListCourses.item(index).getTextContent();
                	user us=(user) _list.get(index);
                	us.setFirstName(firstName);               		                	                    
                    //Log.d("Course Name ",coursename);
                }                			                                                                                                                        
               break;  
            case COURSE_CONTENT:                                                         	
            	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                InputSource source2= new InputSource(new StringReader(str));
                org.w3c.dom.Document doc;                
                 doc = dBuilder.parse(source2);
                 _list=new ArrayList<moodleObj>();
                 //optional, but recommended	
                 doc.getDocumentElement().normalize();
                 System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                 //NodeList nList = doc.getElementsByTagName("SINGLE");
                 NodeList nList = doc.getLastChild().getChildNodes();                 
                 NodeList unoList=doc.getDocumentElement().getChildNodes();                 
                 for(int i=0;i<unoList.getLength();i++)
                 {
                     Node ses=unoList.item(i);
                     if(ses.getNodeName().contentEquals("MULTIPLE"))
                     {
                         nList =ses.getChildNodes();;
                         break;
                     }
                 } 
                 System.out.println("----------------------------");            
                 //nueva clase
                 courseContentM course=new courseContentM();
                 ArrayList<sectionCourse> listaSecs=new ArrayList<sectionCourse>();
                 //nList.getLength()
                 //un single representa a un seccion
                 sectionCourse section;
                 ArrayList<itemCourse> lista=new ArrayList<itemCourse>();            
                 for (int temp = 0; temp <nList.getLength() ; temp++) //ITERACION DE LAS SINGLES
                 {
                     Node nNode = nList.item(temp);
                     section=new sectionCourse();                
                     if (nNode.getNodeType() == Node.ELEMENT_NODE&&nNode.getNodeName().contentEquals("SINGLE")) 
                     {
                         System.out.println("\nCurrent Element :" + nNode.getNodeName());
                         NodeList keys=nNode.getChildNodes();
                         //Element eElement = (Element) nNode;                                             
                         for(int x=0;x<keys.getLength();x++)//ITERACION DE LAS KEYS
                         {
                            Node value = keys.item(x);
                           // Element eElement = (Element) value;                                       
                            //System.out.println("\nCurrent Key :" + value.getNodeName());
                            if(value.getNodeName().contentEquals("KEY"))
                            {
                                Element eElement = (Element) value;                                       
                               // System.out.println("name : " + eElement.getAttribute("name"));
                                if(eElement.getAttribute("name").contentEquals("id"))//SETIAMOS EL ID DE LA SECCION
                                {
                                    Node nod=value.getFirstChild();                                                              
                                    section.setSectionId(nod.getTextContent());
                                }
                                if(eElement.getAttribute("name").contentEquals("name"))//SETIAMOS EL NAME DE LA SECCION
                                {
                                    Node nod=value.getFirstChild();                                                              
                                    section.setSectionName(nod.getTextContent());
                                }                                                                                 
                                if(eElement.getAttribute("name").contentEquals("modules"))
                                {
                                    //NodeList modules=value.getChildNodes();                                                                                                                                                           
                                      Node multiple = eElement.getFirstChild();
                                      lista=new ArrayList<itemCourse>();
                                     //aqui sacaremos el contenido de una seccion
                                      NodeList items=multiple.getChildNodes();                                                          

                                      
                                      for(int y=0;y<items.getLength();y++)//ITERACION DE SINGLES INSIDE MODULES
                                      { 
                                          itemCourse itm=new itemCourse();            
                                          Node singleInside = items.item(y);                                     
                                          //System.out.println("Singles inside the Module :" + singleInside.getNodeName());                                     
                                          //SINGLES INSIDE THE MODULES
                                          if(singleInside.getNodeName().contentEquals("SINGLE"))
                                          {                                         
                                             NodeList insideKeys= singleInside.getChildNodes();                                        
                                             String descrip,type,url,visible;
                                             for(int m=0;m<insideKeys.getLength();m++) //ITERACION DE KEYS INSIDE SINGLE
                                             {
                                                  Node insideKey = insideKeys.item(m);                                          
                                                  
                                                  if(insideKey.getNodeName().contentEquals("KEY"))
                                                  {                                                
                                                     Element elemInside = (Element) insideKey; 
                                                     
                                                      if(elemInside.getAttribute("name").contentEquals("url"))
                                                     {
                                                          String c=insideKey.getTextContent().trim();  
                                                          //if(insideKey.getTextContent().length()>1)                                                      
                                                          if(c.length()>1)
                                                          {
                                                              int y1=0;
                                                              itm.setLabel(c);
                                                          }                                                       
                                                     }else if(elemInside.getAttribute("name").contentEquals("name")) 
                                                     {
                                                       itm.setName(insideKey.getTextContent().trim());
                                                     }else if(elemInside.getAttribute("name").contentEquals("id")) 
                                                     {
                                                         itm.setIdItemCourse(insideKey.getTextContent().trim());
                                                     }else if(elemInside.getAttribute("name").contentEquals("description"))
                                                     {                                                                                                        
                                                            String c22=insideKey.getTextContent().trim();                                                       
                                                            if(c22.length()>1)
                                                             itm.setLabel(c22);                                                                                                                                                              
                                                             //itm.setLabel(c);
                                                             lista.add(itm);                                                    
                                                          //System.out.println(insideKey.getTextContent());
                                                          
                                                     } else if(elemInside.getAttribute("name").contentEquals("modname"))
                                                     {
                                                         //lista.add(itm);
                                                         itm.setItemType(insideKey.getTextContent().trim());
                                                     }
                                                     else if(elemInside.getAttribute("name").contentEquals("visible"))
                                                     {
                                                          //itm.setLabel(insideKey.getTextContent());
                                                          //System.out.println(insideKey.getTextContent().trim());
                                                         if(insideKey.getTextContent().trim().contentEquals("0"))
                                                          lista.remove(lista.size()-1);
                                                          //lista.add(itm);
                                                     }                                                 
                                                  }                                             
                                             }  
                                             int n3=0;                                                                                
                                          }                                     
                                      }                                  
                                      section.setItems(lista);                                 
                                }                           
                            }                                              
                         }
                     }                                
                     if(section.getSectionName()!=null)
                     {
                      
                         System.out.println(section.getSectionName());
                         listaSecs.add(section);
                     }
                     int n=0;
                 }
                 
                 course.setSections(listaSecs);
                 System.out.println("sdsd");
                                              
                _list.add(course);		                	                                                                        
                break;
            case PARTICIPANTS:
            	 _list=new ArrayList<moodleObj>();
                  dbFactory = DocumentBuilderFactory.newInstance();
                  dBuilder = dbFactory.newDocumentBuilder();
                 source2= new InputSource(new StringReader(str));
                               
                 doc = dBuilder.parse(source2);
                 //optional, but recommended	
                 doc.getDocumentElement().normalize();
                 System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                 //NodeList nList = doc.getElementsByTagName("SINGLE");
                  nList = doc.getLastChild().getChildNodes();
             
                  unoList=doc.getDocumentElement().getChildNodes();
                 for(int i=0;i<unoList.getLength();i++)
                 {
                     Node ses=unoList.item(i);
                     if(ses.getNodeName().contentEquals("MULTIPLE"))
                     {
                         nList =ses.getChildNodes();;
                         break;
                     }
                 }            
             System.out.println("----------------------------");                                                         
             //un single representa a un user                        
             for (int temp = 0; temp <nList.getLength() ; temp++) //ITERACION DE LAS SINGLES
             {
                 
                 Node nNode = nList.item(temp);
                 user us=new user();  
                 //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                               
                 if (nNode.getNodeType() == Node.ELEMENT_NODE&&nNode.getNodeName().contentEquals("SINGLE")) 
                 {
                     System.out.println("\nCurrent Element :" + nNode.getNodeName());
                     NodeList keys=nNode.getChildNodes();
                     //Element eElement = (Element) nNode;                                             
                     for(int x=0;x<keys.getLength();x++)//ITERACION DE LAS KEYS
                     {
                        Node value = keys.item(x);
                        //Element eElement = (Element) value;                                       
                        //System.out.println("\nCurrent Key :" + value.getNodeName());
                        if(value.getNodeName().contentEquals("KEY"))
                        {
                            Element eElement = (Element) value;                                       
                           // System.out.println("name : " + eElement.getAttribute("name"));    
                            if(eElement.getAttribute("name").contentEquals("id"))//SETIAMOS EL ID DE LA SECCION
                            {                               
                                Node nod=value.getFirstChild(); 
                                us.setUserid(nod.getTextContent());
                            }                                                                                                                                                                  
                            if(eElement.getAttribute("name").contentEquals("username"))//SETIAMOS EL NAME DE LA SECCION
                            {
                                Node nod=value.getFirstChild(); 
                                us.setUsername(nod.getTextContent());
                            }                                                                                 
                            if(eElement.getAttribute("name").contentEquals("firstname"))//SETIAMOS EL NAME DE LA SECCION
                            {
                                Node nod=value.getFirstChild(); 
                                us.setFirstName(nod.getTextContent());
                            }                                                                                 
                            if(eElement.getAttribute("name").contentEquals("lastname"))//SETIAMOS EL NAME DE LA SECCION
                            {
                                Node nod=value.getFirstChild(); 
                                us.setLastName(nod.getTextContent());
                            }                                                                                 
                            if(eElement.getAttribute("name").contentEquals("fullname"))//SETIAMOS EL NAME DE LA SECCION
                            {
                                Node nod=value.getFirstChild(); 
                                us.setFullName(nod.getTextContent());
                            }                                                                                 
                            if(eElement.getAttribute("name").contentEquals("email"))//SETIAMOS EL NAME DE LA SECCION
                            {
                                Node nod=value.getFirstChild(); 
                                us.setEmailAddress(nod.getTextContent());
                            }                                                                                 
                            if(eElement.getAttribute("name").contentEquals("profileimageurl"))//SETIAMOS EL NAME DE LA SECCION
                            {
                                Node nod=value.getFirstChild(); 
                                us.setPicture(nod.getTextContent());
                            }                                                                                 
                                                                                                            
                        }                       
                     }
                 }                 
                 if(us.getFullName()!=null)
                   _list.add(us);
             }                        
             System.out.println("sdsd");                                                
             int n=0;
             break;
            default:
                System.out.println("Midweek days are so-so.");
                
        }                         
    }
    
}
    
    

