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
public class sectionCourse extends courseContentM
{
    
    private ArrayList<itemCourse> items;
    private String sectionName;
    private String sectionId;
    
    
    

    /**
     * @return the items
     */
    public ArrayList<itemCourse> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<itemCourse> items) {
        this.items = items;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the sectionId
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    
    
    
}
