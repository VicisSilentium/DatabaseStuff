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
	private ArrayList<Person> myGraveyardPeople;

	// --------- Constructor ---------- \\
	public AppController()
	{

		myDataController = new DatabaseController();
		myGraveMarkers = new ArrayList<GraveMarker>();
		myGraveyardPeople = new ArrayList<Person>();
	}

	// ----------- Methods ------------ \\
	public DatabaseController getMyDataController()
	{
		return myDataController;
	}

	public void start()
	{
		myAppFrame = new DatabaseFrame(this);
	}

}
