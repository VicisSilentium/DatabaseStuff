package database.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
		catch (Exception currentExeption)
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
	
	/**
	 * A method to create a specified database. Uses a try/catch to prevent
	 * crashing.
	 * 
	 * @param databaseName
	 *            The name for the database to connect to.
	 */
	public void createDatabase(String databaseName)
	{
		clearConnection();
		try
		{
			Statement createDatabaseStatement = databaseConnection.createStatement();
			
			int result = createDatabaseStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName + ";");
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * Helper method to parse a 2D array of String to a single string for importing into a SQL CREATE TABLE statement.
	 * @param Parameters a nX3 array of String with the following format[0] field name , [1] field type, [2] field information. the third field, [2] may be left blank as needed by the data type in SQL database.
	 * 
	 * @return The array parsed out with separationns of each parameter by commas as a String/
	 */
	private String parsePerameterArray(String[][] parameters)
	{
		String parameterInfo = "";
		
		for(int row = 0; row < parameters.length; row++)
		{
			for(int col = 0; col < parameters[0].length;col++)
			{
			parameterInfo += "`" + parameters[0] + "` " + parameters[1] + " " + parameters [2];	
			}
			if(row < parameters.length - 1)
			{
				parameterInfo += ",";
			}
		}
		
		return parameterInfo;
	}
	
	/**
	 * A method to create a new table in the database specified.
	 * If there is a problem it will call the displaySQLErrors method.
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
	 * Creates a table on a specified database with the supply
	 * @param database The database to add the table to.
	 * @param tableName The name of the new table.
	 * @param parameters a nX3 2D array of String with the following Structure: [0] field_name, [1] field type, field information(if needed)
	 */
	public void createTable(String database, String tableName, String [][] parameters)
	{

		try
		{
			Statement createTableStatement = databaseConnection.createStatement();
			String parameterInfo = parsePerameterArray(parameters);
			
			String createString = "CREATE TABLE IF NOT EXISTS `" + database + "`.`" + tableName + "`" + "(" + parameterInfo + ") ENGINE + INNODB;";
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
	 * death, children, martial status, and age. If there is a problem it will
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
	
	public Vector<String> getTableHeaders(String database, String table)
	{
		Vector<String> tableHeaders = new Vector<String>();
		String query = "SELECT * from `" + database + "`,`" + table + "`;";
		try
		{
			Statement headerStatement = databaseConnection.createStatement();
			ResultSet results = headerStatement.executeQuery(query);
			ResultSetMetaData resultsData = results.getMetaData();
			
			for (int column = 1; column <= resultsData.getColumnCount(); column++)
			{
				tableHeaders.add(resultsData.getColumnName(column));
			}
			
			results.close();
			headerStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
		
		return tableHeaders;
	}
	
	/**
	 * A method to delete a database; should NOT be in use.
	 * 
	 * @param databaseName
	 *            The name of the database to be deleted.
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
			String insertString = "INSERT INTO `graveyard`.`people` ( `person_name`,`person_death_date`,`person_birth_date`,`person_is_married`,`person_has_children`,`person_age`)"
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
					+ currentPerson.getAge() + "' );";
			
			int result = insertPersonStatement.executeUpdate(insertString);
			JOptionPane.showMessageDialog(null, "successfully inserted " + result + " rows.");
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
	}
	
	/**
	 * Updates the people table to reflect a new name based on the supplied
	 * name. Pops a window with the number of changes reflected in the SQL
	 * statement.
	 * 
	 * @param oldName
	 *            The old name to be changed.
	 * @param newName
	 *            The new name to be inserted.
	 */
	public void updatePersonNameInTable(String oldName, String newName)
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
	 * Updates the person selected from the people table to reflect new data.
	 * 
	 * @param Name
	 *            Name of the person whose data will be changed.
	 * @param birthDate
	 *            The birth date of the selected person.
	 * @param deathDate
	 *            The death date of the selected person.
	 * @param age
	 *            The age of the selected person.
	 */
	public void updatePersonInfoInTable(String Name, String birthDate, String deathDate, String age)
	{
		try
		{
			Statement updateStatement = databaseConnection.createStatement();
			String updateString = "UPDATE `graveyard`.`people`"
					+ "SET `person_birth_date` = '" + birthDate + "'"
					+ "SET `person_death_date` = '" + deathDate + "'"
					+ "SET `person_age` = '" + age + "'"
					+ "WHERE `person_name = '" + Name + "' ;";
			
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
	 * A method to select data from the specified table.
	 * 
	 * @param tableName
	 *            The name of the table selected.
	 * @return
	 */
	public Vector<Person> selectDataFromTable(String tableName)
	{
		Vector<Person> personVector = new Vector<Person>();
		ResultSet seeDeadPeopleResults;
		String selectQuery = "SELECT person_name, person_birth_date, person_death_date, person_age,"
				+ " person_is_married, person_has_children FROM `" + tableName + "`;";
		
		try
		{
			PreparedStatement selectStatement = databaseConnection.prepareStatement(selectQuery);
			seeDeadPeopleResults = selectStatement.executeQuery();
			
			while (seeDeadPeopleResults.next())
			{
				Person tempPerson = new Person();
				String tempName = seeDeadPeopleResults.getString(1);
				String tempBirth = seeDeadPeopleResults.getString(2);
				String tempDeath = seeDeadPeopleResults.getString(3);
				int tempAge = seeDeadPeopleResults.getInt(4);
				boolean tempMarried = seeDeadPeopleResults.getBoolean(5);
				boolean tempChildren = seeDeadPeopleResults.getBoolean(6);
				
				tempPerson.setName(tempName);
				tempPerson.setBirthDate(tempBirth);
				tempPerson.setDeathDate(tempDeath);
				tempPerson.setAge(tempAge);
				tempPerson.setMarried(tempMarried);
				tempPerson.setChildren(tempChildren);
				
				personVector.add(tempPerson);
			}
			seeDeadPeopleResults.close();
			selectStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displaySQLErrors(currentSQLError);
		}
		return personVector;
	}
	
	/**
	 * Method that creates a connection with an external server.
	 */
	public void connectToExternalServer()
	{
		buildConnectionString("10.228.6.204", "", "ctec", "student");
		setupConnection();
		// createDatabase("Kyler");
	}
	
}
