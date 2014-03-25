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
	private AppController baseController;
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
		checkDriver();
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
	
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentExeption)
		{
			System.err.println("Unable to load driver.");
			System.exit(1);
		}
	}
	
	
	/**
	 * Builds a Java connectionString for a MySQL database with the supplied
	 * fields for serverPath, database, userName, and password to access the
	 * database.
	 * 
	 * @param serverPath
	 *            The path to the server. This can be an IP address or a URL
	 *            string.
	 * @param database
	 *            The name of the database your are connecting to. Remember that
	 *            it is case sensitive.
	 * @param userName
	 *            The username for the database access. If that user dot not
	 *            have permission the connection will fail.
	 * @param password
	 *            The password in the cleartext for the connection. If it does
	 *            not hash to match the password of the user, the connection
	 *            will fail.
	 */
	public void buildConnectionString(String serverPath, String database, String userName, String password)
	{
		connectionString = "jdbc:mysql://" + serverPath + "/" + database + "?user=" + userName + "&password="
				+ password;
	}
	
	/**
	 * A method to show what the current SQL error is.
	 * 
	 * @param currentException
	 *            - The current error.
	 */
	public void displaySQLErrors(SQLException currentException)
	{
		JOptionPane.showMessageDialog(null, "The SQL error is: " + currentException.getMessage());
		JOptionPane.showMessageDialog(null, "The MySQL server state is: " + currentException.getSQLState());
		JOptionPane.showMessageDialog(null, "TheMySQL error code is: " + currentException.getErrorCode());
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
		setupConnection();
	}
	
	/*
	 * A method to create a specified database. Uses a try/catch to prevent
	 * crashing.
	 * 
	 * @param databaseName The name for the database to connect to.
	 */
	public void createDatabase(String databaseName)
	{
		clearConnection();
		try
		{
			Statement createDatabaseStatement = databaseConnection.createStatement();
			
			int result = createDatabaseStatement.executeUpdate("CREATE DATABASE " + databaseName + ";");
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
			Statement createTableStatement = databaseConnection.createStatement();
			String mySQLStatement = "CREATE TABLE	'" + database + "'.'" + tableName
					+ "' ('test_id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY," + "'test_name' VARCHAR( 50) NOT NULL) "
					+ "ENGINE = INNOB";
			
			int result = createTableStatement.executeUpdate(mySQLStatement);
			createTableStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
		
	}
	
	/**
	 * Creates a person table on the supplied database. Only creates the table
	 * if it does not exist. Defines table structure to have an ID, name, birth,
	 * death, children, marital status, and age. If there is a problem it will
	 * call the displaySQLErrors method.
	 * 
	 * @param database
	 *            The database to build the table on.
	 */
	public void createPeopleTable(String database)
	{
		closeConnection();
		int queryIndex = connectionString.indexOf("?");
		String connectionStart = connectionString.substring(0, queryIndex);
		String connectionEnd = connectionString.substring(queryIndex);
		connectionString = connectionStart + database + connectionEnd;
		
		setupConnection();
		try
		{
			Statement createTableStatement = databaseConnection.createStatement();
			String createMyPersonTabe = "CREATE TABLE IF NOT EXISTS " + database + ".`people`"
					+ "("
					+ "`person_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "`person_name` VARCHAR(50) NOT NULL,"
					+ "`person_birth_date` VARCHAR(30),"
					+ "`person_death_date` VARCHAR(30),"
					+ "`person_is_married` BOOL,"
					+ "`person_has_children` BOOL,"
					+ "`person_age` INT"
					+ ") ENGINE = INNODB;";
			
			int result = createTableStatement.executeUpdate(createMyPersonTabe);
			createTableStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * A method to delete the database specified. should NOT be called. Uses a
	 * try/catch to prevent crashing.
	 */
	public void deleteDatabase(String databaseName)
	{
		clearConnection();
		try
		{
			Statement deleteDatabaseStatement = (databaseConnection).createStatement();
			
			int result = deleteDatabaseStatement.executeUpdate("DROP DATABASE " + databaseName + ";");
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
			Statement insertPersonStatement = databaseConnection.createStatement();
			int databaseIsMarried, databaseHasChildren;
			if (currentPerson.isMarried())
			{
				databaseIsMarried = 1;
			}
			else
			{
				databaseIsMarried = 0;
			}
			if (currentPerson.isHasChildren())
			{
				databaseHasChildren = 1;
			}
			else
			{
				databaseHasChildren = 0;
			}
			String insertString = "INSERT INTO `graveyard`.`people` ( `person_name`,`death_date`,`birth_date`,`is_married`,`has_children`,`age`)"
					+ "VALUES "
					+ "('"
					+ currentPerson.getName()
					+ "', '"
					+ currentPerson.getDeathDate()
					+ "', '"
					+ currentPerson.getBirthDate()
					+ "', '"
					+ databaseIsMarried
					+ "', '"
					+ databaseHasChildren
					+ "', '"
					+ currentPerson.getAge() + ");";
			
			int result = insertPersonStatement.executeUpdate(insertString);
			JOptionPane.showMessageDialog(null, "successfully inserted " + result + " rows.");
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * Updated the people table to reflect a new name based on the supplied
	 * name. Pops a window with the number of changes reflected in the SQL
	 * statement.
	 * 
	 * @param oldName
	 *            The old name to be changed.
	 * @param newName
	 *            The new name to be inserted.
	 */
	public void updatePersonInTable(String oldName, String newName)
	{
		try
		{
			Statement updateStatement = databaseConnection.createStatement();
			String updateString = "UPDATE `graveyard`.`people`"
					+ "SET `person_name` = '" + newName + "'"
					+ "WHERE `person_name = '" + oldName + "' ;";
			
			int result = updateStatement.executeUpdate(updateString);
			JOptionPane.showMessageDialog(baseController.getMyAppFrame(), "successfully updated " + result + " row(s)");
			updateStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * Method that creates a connection with an external server.
	 */
	public void connectToExternalServer()
	{
		buildConnectionString("10.228.6.204", "", "ctec", "student");
		setupConnection();
		createDatabase("Kyler");
	}
	
}
