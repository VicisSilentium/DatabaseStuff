package database.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JTextArea textArea;
	private JTextField nameField;
	private JTextField birthDateField;
	private JTextField deathDateField;
	private JTextField ageField;
	private JButton createDatabaseButton;
	private JButton createPeopleTableButton;
	private JButton insertPersonButton;
	private JButton updateButton;
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
		textArea = new JTextArea(10, 20);
		
		// ---------- TextFeilds ---------- \\
		nameField = new JTextField(50);
		birthDateField = new JTextField(30);
		deathDateField = new JTextField(30);
		ageField = new JTextField(5);
		// ----------- JButtons ----------- \\
		createDatabaseButton = new JButton("Create Database");
		createPeopleTableButton = new JButton("Create Table");
		insertPersonButton = new JButton("insert Person");
		updateButton = new JButton("Update Person");
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
		this.setLayout(baseLayout);
		this.setBackground(Color.DARK_GRAY);
		this.add(createDatabaseButton);
		this.add(createPeopleTableButton);
		this.add(insertPersonButton);
		this.add(updateButton);
		this.add(textArea);
		this.add(nameLabel);
		this.add(nameField);
		this.add(birthLabel);
		this.add(birthDateField);
		this.add(deathLabel);
		this.add(deathDateField);
		this.add(ageLabel);
		this.add(ageField);
		
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.WEST, birthDateField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, deathDateField, 22, SpringLayout.SOUTH, birthDateField);
		baseLayout.putConstraint(SpringLayout.WEST, deathDateField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, nameField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, birthDateField, 84, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, textArea, 6, SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.WEST, textArea, 38, SpringLayout.EAST, ageField);
		baseLayout.putConstraint(SpringLayout.WEST, ageField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, ageField, 29, SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.NORTH, createDatabaseButton, 0, SpringLayout.NORTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.WEST, createDatabaseButton, 54, SpringLayout.EAST, deathDateField);
		baseLayout.putConstraint(SpringLayout.SOUTH, nameField, -6, SpringLayout.NORTH, birthLabel);
		baseLayout.putConstraint(SpringLayout.SOUTH, birthLabel, -2, SpringLayout.NORTH, birthDateField);
		baseLayout.putConstraint(SpringLayout.WEST, birthLabel, 8, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, deathLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, deathLabel, -6, SpringLayout.NORTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.NORTH, ageLabel, 6, SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.WEST, ageLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, nameField, 256, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, createPeopleTableButton, -1, SpringLayout.NORTH, ageField);
		baseLayout.putConstraint(SpringLayout.WEST, createPeopleTableButton, 0, SpringLayout.WEST, createDatabaseButton);
		baseLayout.putConstraint(SpringLayout.NORTH, insertPersonButton, 27, SpringLayout.SOUTH, createPeopleTableButton);
		baseLayout.putConstraint(SpringLayout.WEST, insertPersonButton, 0, SpringLayout.WEST, createDatabaseButton);
		baseLayout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, nameLabel, -6, SpringLayout.NORTH, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, updateButton, 24, SpringLayout.SOUTH, insertPersonButton);
		baseLayout.putConstraint(SpringLayout.WEST, updateButton, 0, SpringLayout.WEST, createDatabaseButton);

	}
	
	private void setupListeners()
	{
		createDatabaseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().createDatabase();
			}
		});
		createPeopleTableButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().createPeopleTable("graveyard");
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
			}
		});
		updateButton.addActionListener(new ActionListener()
	{
			public void actionPerformed(ActionEvent click)
			{
//				baseController.getMyDataController().updatePersonInTable(getName(), );
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
