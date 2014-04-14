package moodleObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class listPerfilUserInfo implements Serializable {

	ArrayList<perfilUserInfo> listUser;
	
	public void addElement(perfilUserInfo object)
	{
		listUser.add(object);
	}
	
	public perfilUserInfo getElement(int id)
	{
		return listUser.get(id);
	}
	
	public int SizeList()
	{
		return listUser.size();
	}
	
	public listPerfilUserInfo()
	{
		listUser = new ArrayList<perfilUserInfo>();
	}
}
