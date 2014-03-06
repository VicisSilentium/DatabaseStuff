package database.model;

import java.util.ArrayList;

public class GraveMarker
{
	// ------ Declaration Section ------ \\
	private ArrayList<Person> gravePersonList;
	private boolean isReadable;
	private String typeOfGrave;
	private String graveInformation;
	private String location;

	// picture - image

	// -------- Constructors ---------- \\
	public GraveMarker()
	{
		gravePersonList = new ArrayList<Person>();
		isReadable = false;
		location = "not anywhere";
		graveInformation = "blank";
		typeOfGrave = "n/a";
	}

	public GraveMarker(boolean isReadable, String typeOfGrave)
	{
		this.isReadable = isReadable;
		this.typeOfGrave = typeOfGrave;
		gravePersonList = new ArrayList<Person>();
		isReadable = false;
		location = "not anywhere";
		graveInformation = "blank";
		typeOfGrave = "n/a";
	}

	// ----------- Getters ------------ \\
	public ArrayList<Person> getGravePersonList()
	{
		return gravePersonList;
	}

	public String getTypeOfGrave()
	{
		return typeOfGrave;
	}

	public String getGraveInformation()
	{
		return graveInformation;
	}

	public String getLocation()
	{
		return location;
	}

	// ----------- Setters ------------ \\
	public void setGravePersonList(ArrayList<Person> gravePersonList)
	{
		this.gravePersonList = gravePersonList;
	}

	public boolean isReadable()
	{
		return isReadable;
	}

	public void setReadable(boolean isReadable)
	{
		this.isReadable = isReadable;
	}

	public void setTypeOfGrave(String typeOfGrave)
	{
		this.typeOfGrave = typeOfGrave;
	}

	public void setGraveInformation(String graveInformation)
	{
		this.graveInformation = graveInformation;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	// ----------- Methods ------------ \\
	public String toString()
	{
		String graveInfo = "";

		for (Person current : gravePersonList)
		{
			graveInfo += current + " is buried here. \n";
		}
		if (isReadable)
		{
			graveInfo += "This grave is readable";
		}
		else
		{
			graveInfo += "this grave is NOT readable";
		}
		return graveInfo;
	}
}
