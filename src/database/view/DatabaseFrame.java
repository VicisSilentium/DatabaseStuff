package database.view;

import javax.swing.JFrame;

import database.controller.AppController;

public class DatabaseFrame extends JFrame
{
	// ------ Declaration Section ------ \\
	private DatabasePanel basePanel;

	// --------- Constructor ---------- \\
	public DatabaseFrame(AppController baseController)
	{

		basePanel = new DatabasePanel(baseController);

		setupFrame();
	}

	// ----------- Methods ------------ \\
	public DatabasePanel getDatabasePanel()
	{
		return basePanel;
	}

	private void setupFrame()
	{
		this.setContentPane(basePanel);
		this.setSize(650, 400);
		this.setVisible(true);
	}

}
