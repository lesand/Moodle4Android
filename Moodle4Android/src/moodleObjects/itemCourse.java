/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moodleObjects;

/**
 *
 * @author josh
 */
public class itemCourse extends sectionCourse
{
    
    private String label;
    private String itemType; 
    private String name;
    private String idItemCourse;
    
    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIdItemCourse(String id)
    {
    	this.idItemCourse = id;
    }

    public String getIDItemCourse()
    {
    	return this.idItemCourse; 
    }

    
    
    
    
    
    
}
