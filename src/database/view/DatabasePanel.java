package database.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import database.controller.AppController;
import database.model.Person;

public class DatabasePanel extends JPanel
{
	// ------ Declaration Section ------ \\
	private AppController baseController;
	private SpringLayout baseLayout;
	private JTextArea resultsArea;
	private JTextField nameField;
	private JTextField birthDateField;
	private JTextField deathDateField;
	private JTextField ageField;
	private JButton selectButton;
	private JButton createPeopleTableButton;
	private JButton insertPersonButton;
	private JButton updateNameButton;
	private JButton externalServerButton;
	private JButton	 updatePersonInfoButton;
	private JLabel nameLabel;
	private JLabel birthLabel;
	private JLabel deathLabel;
	private JLabel ageLabel;
	
	// --------- Constructor ---------- \\
	public DatabasePanel(AppController baseController)
	{
		this.baseController = baseController;
		
		// --------- SpringLayout --------- \\
		baseLayout = new SpringLayout();
		// ----------- TextArea ----------- \\
		resultsArea = new JTextArea(9, 20);
		
		// ---------- TextFeilds ---------- \\
		nameField = new JTextField(50);
		birthDateField = new JTextField(30);
		deathDateField = new JTextField(30);
		ageField = new JTextField(5);
		// ----------- JButtons ----------- \\
		selectButton = new JButton("Select from database");
		createPeopleTableButton = new JButton("Create Table");
		insertPersonButton = new JButton("Insert Person");
		updateNameButton = new JButton("Update Person Name");
		updatePersonInfoButton = new JButton("Update Person Info");
		externalServerButton = new JButton("Connect to External Server");
		// ----------- JLabels ------------ \\
		nameLabel = new JLabel("Name");
		birthLabel = new JLabel("Birth Date");
		deathLabel = new JLabel("Death Date");
		ageLabel = new JLabel("Age");
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	// ----------- Methods ------------ \\
	private void setupPanel()
	{
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(baseLayout);
		resultsArea.setWrapStyleWord(true);
		resultsArea.setLineWrap(true);
		this.add(selectButton);
		this.add(createPeopleTableButton);
		this.add(insertPersonButton);
		this.add(updateNameButton);
		this.add(updatePersonInfoButton);
		this.add(externalServerButton);
		this.add(resultsArea);
		this.add(nameLabel);
		this.add(nameField);
		this.add(birthLabel);
		this.add(birthDateField);
		this.add(deathLabel);
		this.add(deathDateField);
		this.add(ageLabel);
		this.add(ageField);
		
		resultsArea.setWrapStyleWord(true);
		resultsArea.setLineWrap(true);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.WEST, birthDateField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, deathDateField, 22, SpringLayout.SOUTH, birthDateField);
		baseLayout.putConstraint(SpringLayout.WEST, deathDateField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, nameField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, birthDateField, 84, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, ageField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, ageField, 29, SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.SOUTH, nameField, -6, SpringLayout.NORTH, birthLabel);
		baseLayout.putConstraint(SpringLayout.SOUTH, birthLabel, -2, SpringLayout.NORTH, birthDateField);
		baseLayout.putConstraint(SpringLayout.WEST, birthLabel, 8, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, deathLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, deathLabel, -6, SpringLayout.NORTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, resultsArea, 23, SpringLayout.EAST, ageField);
		baseLayout.putConstraint(SpringLayout.EAST, nameField, 7, SpringLayout.EAST, resultsArea);
		baseLayout.putConstraint(SpringLayout.SOUTH, nameLabel, -6, SpringLayout.NORTH, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, resultsArea, 27, SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.WEST, selectButton, 56, SpringLayout.EAST, birthDateField);
		baseLayout.putConstraint(SpringLayout.NORTH, createPeopleTableButton, 14, SpringLayout.SOUTH, selectButton);
		baseLayout.putConstraint(SpringLayout.WEST, createPeopleTableButton, 0, SpringLayout.WEST, selectButton);
		baseLayout.putConstraint(SpringLayout.NORTH, insertPersonButton, 1, SpringLayout.NORTH, resultsArea);
		baseLayout.putConstraint(SpringLayout.WEST, insertPersonButton, 0, SpringLayout.WEST, selectButton);
		baseLayout.putConstraint(SpringLayout.NORTH, updateNameButton, 13, SpringLayout.SOUTH, insertPersonButton);
		baseLayout.putConstraint(SpringLayout.WEST, updateNameButton, 0, SpringLayout.WEST, selectButton);
		baseLayout.putConstraint(SpringLayout.NORTH, updatePersonInfoButton, 14, SpringLayout.SOUTH, updateNameButton);
		baseLayout.putConstraint(SpringLayout.WEST, updatePersonInfoButton, 0, SpringLayout.WEST, selectButton);
		baseLayout.putConstraint(SpringLayout.NORTH, externalServerButton, -4, SpringLayout.NORTH, birthLabel);
		baseLayout.putConstraint(SpringLayout.WEST, externalServerButton, 54, SpringLayout.EAST, birthDateField);
		baseLayout.putConstraint(SpringLayout.NORTH, selectButton, -4, SpringLayout.NORTH, deathLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, ageLabel, 6, SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.WEST, ageLabel, 0, SpringLayout.WEST, nameLabel);

	}
	
	private void fillTextArea(Vector<Person> people)
	{
		resultsArea.setText("");
		for (Person currentPerson : people)
		{
			resultsArea.append(currentPerson.toString() + "\n");
		}
	}
	
	private void clearFields()
	{
		nameField.setText("");
		birthDateField.setText("");
		deathDateField.setText("");
		ageField.setText("");
		resultsArea.setText("");
	}
	
	private void setupListeners()
	{
		selectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Vector<Person> currentPeople = baseController.getMyDataController().selectDataFromTable("people");
				fillTextArea(currentPeople);
				
				// baseController.getMyDataController().createDatabase("graveyard");
			}
		});
		createPeopleTableButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().createPeopleTable("graveyard");
				clearFields();
			}
		});
		insertPersonButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Person currentPerson = new Person();
				currentPerson.setName(nameField.getText());
				currentPerson.setDeathDate(deathDateField.getText());
				currentPerson.setBirthDate(birthDateField.getText());
				currentPerson.setAge(Integer.parseInt(ageField.getText()));
				
				baseController.getMyGravePeople().add(currentPerson);
				baseController.getMyDataController().insertPersonIntoDatabase(currentPerson);
				clearFields();
			}
		});
		updateNameButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().updatePersonNameInTable(nameField.getName(), deathDateField.getText());
			}
			
		});
		updatePersonInfoButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().updatePersonInfoInTable(nameField.getName(), birthDateField.getText(), deathDateField.getText(), ageField.getText());
			}
			
		});
		externalServerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().connectToExternalServer();
			}
		});
	}
	
	private boolean checkInteger(String current)
	{
		boolean checkInteger = false;
		
		try
		{
			Integer.parseInt(current);
			checkInteger = true;
		}
		catch (NumberFormatException currentExeption)
		{
			JOptionPane.showMessageDialog(this,
					"Make sure you typed in a number for the age.");
		}
		return checkInteger;
	}
	
	private Person createPersonFromInput()
	{
		Person deadPerson = null;
		int deadPersonage = 0;
		
		if (checkInteger(ageField.getText()))
		{
			int newAge = Integer.parseInt(ageField.getText());
		}
		String name = nameField.getText();
		
		return deadPerson;
	}
	
}
