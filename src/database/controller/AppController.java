package database.controller;

import java.util.ArrayList;

import database.model.GraveMarker;
import database.model.Person;
import database.view.DatabaseFrame;

public class AppController
{
	// ------ Declaration Section ------ \\
	private DatabaseController myDataController;
	private DatabaseFrame myAppFrame;
	private ArrayList<GraveMarker> myGraveMarkers;
	private ArrayList<Person> myGravePeople;

	// --------- Constructor ---------- \\
	public AppController()
	{

		myDataController = new DatabaseController();
		myGraveMarkers = new ArrayList<GraveMarker>();
		myGravePeople = new ArrayList<Person>();
	}


	// ----------- Getters ------------ \\	
	public DatabaseFrame getMyAppFrame()
	{
		return myAppFrame;
	}

	public ArrayList<GraveMarker> getMyGraveMarkers()
	{
		return myGraveMarkers;
	}

	public ArrayList<Person> getMyGravePeople()
	{
		return myGravePeople;
	}
	
	public DatabaseController getMyDataController()
	{
		return myDataController;
	}
	
	// ----------- Methods ------------ \\

	public void start()
	{
		myAppFrame = new DatabaseFrame(this);
	}

}
