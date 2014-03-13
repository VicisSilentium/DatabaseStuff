package database.model;

import java.util.ArrayList;

public class Person
{
	// ------ Declaration Section ------ \\
	private String name;
	private String birthDate;
	private String deathDate;
	private boolean married;
	private boolean children;
	private int age;

	// --------- Constructor ---------- \\
	public Person()
	{
		name = "";
		birthDate = "unknown";
		deathDate = "unknown";
		married = false;
		children = false;
		age = -500;
	}

	public Person(String name, String deathDate)
	{
		this.name = name;
		this.deathDate = deathDate;
	}
	// ----------- Getters ------------ \\
	public String getName()
	{
		return name;
	}

	public boolean isMarried()
	{
		return married;
	}

	public boolean isHasChildren()
	{
		return children;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public int getAge()
	{
		return age;
	}

	public String getDeathDate()
	{
		return deathDate;
	}

	// ----------- Setters ------------ \\
	public void setName(String name)
	{
		this.name = name;
	}

	public void setDeathDate(String deathDate)
	{
		this.deathDate = deathDate;
	}

	public void setMarried(boolean married)
	{
		this.married = married;
	}

	public void setChildren(boolean children)
	{
		this.children = children;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	// ----------- Methods ------------ \\
	public String toString()
	{
		String personInfo = "";

		personInfo += "this is: " + name;
		personInfo += "; Died around: " + deathDate;
		personInfo += " at age :" + age;

		return personInfo;
	}
}
