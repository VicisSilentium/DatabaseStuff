package database.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	private JButton firstButton;
	private JButton secondButton;

	// --------- Constructor ---------- \\
	public DatabasePanel(AppController baseController)
	{
		this.baseController = baseController;

		// --------- SpringLayout --------- \\
		baseLayout = new SpringLayout();
		// ----------- TextArea ----------- \\
		textArea = new JTextArea(10, 20);
		// ---------- TextFeilds ---------- \\
		nameField = new JTextField(30);
		birthDateField = new JTextField(30);
		deathDateField = new JTextField(30);
		ageField = new JTextField(30);
		// ----------- JButtons ----------- \\
		firstButton = new JButton("Create Database");
		secondButton = new JButton("second");

		setupPanel();
		setupLayout();
		setupListeners();
	}

	// ----------- Methods ------------ \\
	private void setupPanel()
	{
		this.setLayout(baseLayout);
		this.setBackground(Color.DARK_GRAY);
		this.add(firstButton);
		this.add(secondButton);
		this.add(textArea);
		this.add(nameField);
		this.add(birthDateField);
		this.add(deathDateField);
		this.add(ageField);

		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
	}

	private void setupListeners()
	{
		firstButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				baseController.getMyDataController().createDatabase();
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

	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.WEST, textArea, 9,
				SpringLayout.EAST, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, nameField, 70,
				SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, nameField, 10,
				SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, birthDateField, 17,
				SpringLayout.SOUTH, nameField);
		baseLayout.putConstraint(SpringLayout.WEST, birthDateField, 0,
				SpringLayout.WEST, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, deathDateField, 17,
				SpringLayout.SOUTH, birthDateField);
		baseLayout.putConstraint(SpringLayout.EAST, deathDateField, 0,
				SpringLayout.EAST, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, ageField, 17,
				SpringLayout.SOUTH, deathDateField);
		baseLayout.putConstraint(SpringLayout.EAST, ageField, 0,
				SpringLayout.EAST, nameField);
		baseLayout.putConstraint(SpringLayout.WEST, firstButton, 79,
				SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, firstButton, -27,
				SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, secondButton, 0,
				SpringLayout.NORTH, firstButton);
		baseLayout.putConstraint(SpringLayout.EAST, secondButton, -106,
				SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, textArea, -28,
				SpringLayout.NORTH, secondButton);
	}

}
