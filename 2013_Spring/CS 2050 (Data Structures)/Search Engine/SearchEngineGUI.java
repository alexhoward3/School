/**
 * 
 * @author Alex Howard
 * @author Ivan Gajic
 * 
 * @version 1.0
 * 
 * This program creates a GUI that allows a user to search through an index of websites.
 * If a word exists inside of a particular website the url will be shown to the user.
 * 
 * The SearchEngine class creates a LinkedList of LinkedLists (and extension of Java's proprietary LinkedList class)
 * and builds an index of websites from a default hard coded site. Words from the website are then created as the the heads
 * of each new LinkedList and the sites in which that word appears are appended to that LinkedList as a new set of LinkedLists.
 * 
 * The GUI first builds the visual components and then calls for a new Task to be run. The Task executes the SearchEngine and
 * runs its buildIndex() and createLists() methods to initialize the application. The Task can then update the JProgressBar
 * to indicate that the program is loading the index and lists.
 * 
 * Finally, when the index and lists are loaded, the user can search through the lists to find websites in which their query appears.
 * 
 * The SearchEngine can handle multiple word queries, but returns the sites for every word of the query.
 * 
 * The GUI was in-part build with the WindowBuilder Eclipse plugin for Juno 4.2.
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

public class SearchEngineGUI implements PropertyChangeListener {

	private JFrame frame; //Frame
	private JTextField searchField; //Search
	private JProgressBar progressBar; //The loading bar
	private JTextArea results; //The results area
	private JButton goButton; //The go button for the search
	public static Task runSearchEngine; //New task to run the search on a new thread (for updating the JProgressBar loading bar
	public static final String DEFAULT_URL = "http://rowdy.msudenver.edu/~gordona/index.html"; //Default index url
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { //Run the GUI
				try {
					SearchEngineGUI window = new SearchEngineGUI();
					window.frame.pack(); //Pack the window
					window.frame.setVisible(true); //Set it visible
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchEngineGUI() {
		initialize(); //Create the GUI
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(); //Create the frame
		frame.setBounds(200, 400, 1000, 600); //Set the frame's bounds
		frame.setLocationRelativeTo(null); //Center the frame to the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Terminate the thread when the window is closed

		progressBar = new JProgressBar(); //Create the loading bar
		progressBar.setString("Loading...");
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.GREEN);

		searchField = new JTextField(); //Create the search field
		searchField.setColumns(10);

		JLabel searchLabel = new JLabel("Search:"); //Label for the search field
		searchLabel.setLabelFor(searchField);

		goButton = new JButton("Go"); //Create the button
		goButton.setEnabled(false); //Disable the button until the loading is done

		results = new JTextArea();
		JScrollPane resultsPane = new JScrollPane(results);
		
		
		//Add all of the elements into a group within the frame
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(resultsPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
										.addGap(94)
										.addComponent(searchLabel)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(goButton)))
										.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(goButton)
								.addComponent(searchLabel))
								.addGap(30)
								.addComponent(resultsPane, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				);
		frame.getContentPane().setLayout(groupLayout);
		
		runSearchEngine = new Task(); //Create a new Task to run the search engine
		runSearchEngine.addPropertyChangeListener(this); //Add a PropertyChangeListener
		runSearchEngine.execute(); //Execute the Task
	}
	
	/**
	 * 
	 * This class creates a task for the SearchEngine so it can run in a different thread than the GUI.
	 * This allows the JProgressBar inside of the GUI to be updated while it is running. 
	 *
	 */
	class Task extends SwingWorker<Void, Void> {

		private SearchEngine se; 
		public boolean done = false;
		@Override
		protected Void doInBackground() throws Exception {
			se = new SearchEngine(); //Create a new SearchEngine
			se.computeHash("w");
			se.buildIndex(DEFAULT_URL); //Build the index starting with the default url
			setProgress(0); //Set the progress to 0
			se.createLists(); //Create the lists (the highest time consuming operation)
			return null;
		}
		
		/**Method that sets the progress**/
		public void progress(int i) {
			setProgress(i);
		}

		@Override
		protected void done() {
			if(se.isDone()) { //If both operations are done
				done = true; //Set done to true
				progressBar.setIndeterminate(false); //Reset the loading bar
				goButton.setEnabled(true); //Enable the button
				progressBar.setString("Done!"); //Set the loading bar's String to "Done!"
				progressBar.setValue(100); //Set the loading bar's value to 100%
			}
			goButton.addActionListener(new ActionListener() { //Add and ActionListener to the goButton
				@Override
				public void actionPerformed(ActionEvent e) {
					String userQuery = searchField.getText(); //Get the user's query
					ArrayList<String> urls = new ArrayList<String>(); //Make a new array to hold the sites
					if(!userQuery.equals("")) { //As long as the query is not null
						urls = se.search(userQuery); //Search for the query
						results.setText("You searched for: " + userQuery + "\n\n");
						if(urls.size() == 0) results.append("No results :("); //If there are no results
						for(int i = 0; i < urls.size(); i++) results.append(urls.get(i) + "\n"); //Print the results to the GUI's JTextArea
					}
				}
			});	
		}
	}
	
	/**This Method updates the progressBar as the Task is loading the index and lists**/
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			progressBar.setIndeterminate(true); //Sets the progressBar to Indeterminate mode
		} 
	}
} //End SearchEngineGUI