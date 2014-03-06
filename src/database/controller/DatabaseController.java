package database.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseController
{
	// ------ Declaration Section ------ \\
	private String connectionString;
	private Connection databaseConnection;

	// --------- Constructor ---------- \\
	public DatabaseController()
	{
		connectionString = "jdbc:mysql://localhost/?user=root";

		setupConnection();
	}

	// ----------- Getters ------------ \\
	public String getConnectionString()
	{
		return connectionString;
	}

	// ----------- Setters ------------ \\
	public void setConnectionString(String connectionString)
	{
		this.connectionString = connectionString;
	}

	// ----------- Methods ------------ \\
	public void displaySQLErrors(SQLException currentException)
	{
		JOptionPane.showMessageDialog(null, "The SQL error is: "
				+ currentException.getMessage());
		JOptionPane.showMessageDialog(null, "The MySQL server state is: "
				+ currentException.getSQLState());
		JOptionPane.showMessageDialog(null, "TheMySQL error code is: "
				+ currentException.getErrorCode());
	}

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

	public void clearConnection()
	{
		closeConnection();
		connectionString = "jdbc:mysql://localhost/?user=root";
		setupConnection();
	}

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

}
