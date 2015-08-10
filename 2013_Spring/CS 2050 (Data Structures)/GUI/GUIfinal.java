/* Alex Howard
 * CS-2050
 * 
 * This program creates a GUI interface where a user can select a date from three combo boxes.
 * The selected date is then displayed in a text field.
 * The interface also has three radio buttons that change the background  color to the selected radio buttons.
 * The interface also has an option to save the data to a text file in a directory of the user's choice.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class GUIfinal implements ActionListener {
	public JFrame frame; //Instance variable for the JFrame
	public JPanel northpanel; //Instance variable for the JPanel for the north frame
	public JPanel centerpanel; //Instance variable for the JPanel in the center frame
	public JPanel southpanel; //Instance variable for the JPanel in the south frame
	public JRadioButton red; //Red JRadioButton
	public JRadioButton green; //Green JRadioButton
	public JRadioButton blue; //Blue JRadioButton
	public ButtonGroup buttongroup; //Creates a grouping for the JRadioButtons
	public JLabel message = new JLabel("Select a date:"); //Selecting date message
	public JLabel choose = new JLabel("You chose:"); //Selected date message
	public JTextField datechosen; //Text Field that holds the selected date
	public JComboBox<String> month; //Combo Box that holds the months
	public JComboBox<Integer> day; //Combo Box that holds the days
	public JComboBox<Integer> year; //Combo Box that holds the years
	public JMenuBar menu; //Instance variable for the JMenuBar
	public JMenu file; //Instance variable for a file menu
	public JMenuItem save; //Instance variable for save menu item
	public JFileChooser filesave; //Dialog to save the data to a text file

	public static void main(String[] args) {
		GUIfinal g = new GUIfinal();
	} //End main

	public GUIfinal() {
		// ///////CREATE FRAME///////////////////////////////////
		frame = new JFrame("GUI"); //Creates the frame
		frame.setBounds(20, 20, 400, 400); //Sets the frame's boundaries
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// //////CREATE MENU BAR/////////////////////////////////
		menu = new JMenuBar(); //Creates the menu bar
		file = new JMenu("File"); //Creates the file menu
		save = new JMenuItem("Save"); //Creates the save menu item
		menu.add(file); //Adds the file menu to the menu bar
		file.add(save); //Adds the save item to the file menu
		frame.setJMenuBar(menu); //Sets the main menu bar for the frame

		save.addActionListener(this); //Adds an action listener to the save item
		filesave = new JFileChooser(); //Creates the file saving dialog
		// /////////COMBO BOXES/////////////////////////////////
		final String[] theMonths = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }; //Array for the months
		final Integer[] theDays = new Integer[31]; //Array for the days
		final Integer[] theYears = new Integer[43]; //Array for the years

		for (int x = 0; x <= 30; x++) {
			theDays[x] = x + 1;
		} //Populates the array for the days

		int y = 1970; //Starts the array for the years at 1970
		for (int x = 0; x <= 42; x++) {
			theYears[x] = y;
			y++;
		} //Populates the array for the years

		month = new JComboBox<String>(theMonths); //Creates the combo box for the months
		day = new JComboBox<Integer>(theDays); //Creates the combo box for the days
		year = new JComboBox<Integer>(theYears); //Creates the combo box for the years

		northpanel = new JPanel(); //Creates the northern JPanel

		frame.add(northpanel, BorderLayout.NORTH); //Adds the north panel to the frame

		northpanel.add(message); //Adds the message to the north panel
		northpanel.add(month); //Adds the month combo box to the north panel
		northpanel.add(day); //Adds the day combo box to the north panel
		northpanel.add(year); //Adds the year combo box to the north panel

		centerpanel = new JPanel(); //Creates the center panel

		centerpanel.add(choose); //Adds choice message to the center panel
		datechosen = new JTextField(10); //Creates the text field to hold the choice
		centerpanel.add(datechosen); //Adds the text field to the center panel
		frame.add(centerpanel, BorderLayout.CENTER); //Adds the center panel to the frame

		JButton select = new JButton("Select"); //Creates a button to add the selected date to the text field
		northpanel.add(select); //Adds the button to the north panel
		select.addActionListener(new ActionListener() { //Creates an Action Listener to the button
			public void actionPerformed(ActionEvent e) {
				datechosen.setText("" + month.getSelectedItem() + " "
						+ day.getSelectedItem() + ", " + year.getSelectedItem()); //Adds the selection to the text field
			}
		}); //End Action Listener

		// ////RADIO BUTTONS/////////////////////////////////////
		southpanel = new JPanel(); //Creates the southern panel
		red = new JRadioButton("Red"); //Creates the red radio button
		green = new JRadioButton("Green"); //Creates the green radio button
		blue = new JRadioButton("Blue"); //Creates the blue radio button
		buttongroup = new ButtonGroup(); //Creates the button group for the radio buttons
		buttongroup.add(red); //Adds the red radio button to the group
		buttongroup.add(green); //Adds the green radio button to the group
		buttongroup.add(blue); //Adds the blue radio button to the group
		southpanel.add(red); //Adds the red radio button to the south panel
		southpanel.add(green); //Adds the green radio button to the south panel
		southpanel.add(blue); //Adds the blue radio button to the south panel

		red.addActionListener(new ActionListener() { //Creates an Action Listener for the red radio button
			public void actionPerformed(ActionEvent e) {
				southpanel.setBackground(Color.RED); //Sets the south panel's background color to red
			}
		}); //End Action Listener
		green.addActionListener(new ActionListener() { //Creates an Action Listener for the green radio button
			public void actionPerformed(ActionEvent e) {
				southpanel.setBackground(Color.GREEN); //Sets the south panel's background color to green
			}
		}); //End Action Listener
		blue.addActionListener(new ActionListener() { //Creates the Action Listener for the blue radio button
			public void actionPerformed(ActionEvent e) {
				southpanel.setBackground(Color.BLUE); //Sets the south panel's background color to blue
			}
		}); //End Action Listener

		frame.add(southpanel, BorderLayout.SOUTH); //Adds the south panel to the frame

		frame.setVisible(true); //Makes the frame visible
	} //End GUIfinal

	public String buttonSelection() { //Method to get which button is selected
		if (red.isSelected())
			return "Red";
		else if (green.isSelected())
			return "Green";
		else if (blue.isSelected())
			return "Blue";
		else
			return null;
	} //End buttonSelection method

	public String dateSelection() { //Method to get the text of the date selection
		if (!(datechosen.getText().equals("")))
			return datechosen.getText();
		else
			return null;
	} //End dateSelection method

	public void actionPerformed(ActionEvent e) { //Creates a generic Action Listener
		int val = filesave.showSaveDialog(frame);
		if (val == JFileChooser.APPROVE_OPTION) { 
			if (filesave.getSelectedFile() != null) {
				File file = new File(filesave.getSelectedFile() + ".txt"); //Creates a file to save the data
				FileWriter writeToFile;
				try { //Tries to write the data to the file
					writeToFile = new FileWriter(file);
					writeToFile.write("Date: " + dateSelection()); //Writes the date to the file
					writeToFile.write("\r\n"); //Writes a new line
					writeToFile.write("Color: " + buttonSelection()); //Writes the selected radio button to the file
					JOptionPane.showMessageDialog(frame, "Saved to: " + filesave.getCurrentDirectory());
					writeToFile.close(); //Closes the File Writer
				} catch (IOException ioe) { //Error handling for the file
					System.out.println(ioe);
				}
			}
		}
	} //End actionPerformed method
} //End GUIfinal class