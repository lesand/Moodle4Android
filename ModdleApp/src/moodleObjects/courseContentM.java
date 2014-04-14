/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleObjects;

import java.util.ArrayList;

/**
 *
 * @author josh
 */
public class courseContentM extends moodleObj
{

    private ArrayList<sectionCourse> sections;
    private String courseId;
    
    
    
    

    /**
     * @return the sections
     */
    public ArrayList<sectionCourse> getSections() {
        return sections;
    }

    /**
     * @param sections the sections to set
     */
    public void setSections(ArrayList<sectionCourse> sections) {
        this.sections = sections;
    }

    /**
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
        
    
    
    
}
