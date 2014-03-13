package database.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import database.model.Person;

/**
 * A public class to create and connect to the database.
 * 
 * @author Kyler Jensen 13/3/2014
 * @version 2.1 Added documentation
 * 
 */
public class DatabaseController
{
	// ------ Declaration Section ------ \\
	private String connectionString;
	private Connection databaseConnection;
	
	// --------- Constructor ---------- \\
	/**
	 * Constructor for the DatabaseController class. Sets up a connection with
	 * the database.
	 */
	public DatabaseController()
	{
		connectionString = "jdbc:mysql://localhost/?user=root";
		
		setupConnection();
	}
	
	// ----------- Getters ------------ \\
	/**
	 * Gets the connectionString for the database.
	 * 
	 * @return connectionString - String for connecting to the database.
	 */
	public String getConnectionString()
	{
		return connectionString;
	}
	
	// ----------- Setters ------------ \\
	/**
	 * Sets the connection String for the database.
	 * 
	 * @param connectionString
	 *            - String for connecting to the database.
	 */
	public void setConnectionString(String connectionString)
	{
		this.connectionString = connectionString;
	}
	
	// ----------- Methods ------------ \\
	/**
	 * A method to show what the current SQL error is.
	 * 
	 * @param currentException
	 *            - The current error.
	 */
	public void displaySQLErrors(SQLException currentException)
	{
		JOptionPane.showMessageDialog(null, "The SQL error is: "
				+ currentException.getMessage());
		JOptionPane.showMessageDialog(null, "The MySQL server state is: "
				+ currentException.getSQLState());
		JOptionPane.showMessageDialog(null, "TheMySQL error code is: "
				+ currentException.getErrorCode());
	}
	
	/**
	 * A method to create a connection with the database. Uses a try/catch to
	 * prevent crashing.
	 */
	public void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch (SQLException currentException)
		{
			displaySQLErrors(currentException);
		}
	}
	
	/**
	 * A method to close the connection to the database to prevent data
	 * corruption. Uses a Try/catch to prevent crashing.
	 */
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * A method to clear the connection to the database to prevent data
	 * corruption.
	 * 
	 */
	public void clearConnection()
	{
		closeConnection();
		connectionString = "jdbc:mysql://localhost/?user=root";
		setupConnection();
	}
	
	/*
	 * A method to create a database labeled 'graveyard'. Uses a try/catch to
	 * prevent crashing.
	 */
	public void createDatabase()
	{
		clearConnection();
		try
		{
			Statement createDatabaseStatement = databaseConnection
					.createStatement();
			
			int result = createDatabaseStatement
					.executeUpdate("CREATE DATABASE graveyard;");
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * A method to create the tables in the database.
	 * 
	 * @param database
	 *            - the database the data is being added to.
	 * @param tableName
	 *            - the name of the table being created.
	 */
	public void createTable(String database, String tableName)
	{
		int queryIndex = connectionString.indexOf("?");
		String connectionStart = connectionString.substring(0, queryIndex);
		String connectionEnd = connectionString.substring(queryIndex);
		connectionString = connectionStart + database + connectionEnd;
		closeConnection();
		setupConnection();
		
		try
		{
			Statement createTableStatement = databaseConnection
					.createStatement();
			String mySQLStatement = "CREATE TABLE	'" + database + "'.'"
					+ tableName
					+ "' ('test_id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "'test_name' VARCHAR( 50) NOT NULL) " + "ENGINE = INNOB";
			
			int result = createTableStatement.executeUpdate(mySQLStatement);
			createTableStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
		
	}
	
	/**
	 * A method to delete the database labeled 'graveyard'.should NOT be called.
	 * Uses a try/catch to prevent crashing.
	 */
	public void deleteDatabase()
	{
		clearConnection();
		try
		{
			Statement deleteDatabaseStatement = (databaseConnection)
					.createStatement();
			
			int result = deleteDatabaseStatement
					.executeUpdate("DROP DATABASE graveyard;");
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * A method to insert a person into the database.
	 * 
	 * @param currentPerson
	 */
	public void insertPersonIntoDatabase(Person currentPerson)
	{
		try
		{
			Statement insertPersonStatement = databaseConnection
					.createStatement();
			
			String insertString = "INSERT INTO 'graveyard'.'people' ( 'person_name','death_date','birth_date','is_married','has_children','age')"
					+ "VALUES "
					+ "('"
					+ currentPerson.getName()
					+ "', '"
					+ currentPerson.getDeathDate()
					+ "', '"
					+ currentPerson.getBirthDate()
					+ "', '"
					+ currentPerson.isMarried()
					+ "', '"
					+ currentPerson.isHasChildren()
					+ "', '"
					+ currentPerson.getAge() + ");";
			
			int result = insertPersonStatement.executeUpdate(insertString);
			JOptionPane.showMessageDialog(null, "successfully inserted "
					+ result + " rows.");
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
}
