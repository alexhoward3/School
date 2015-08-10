/**
 * 
 * @author Alex Howard
 * @author Ivan Gajic
 * 
 * This search engine class creates a LinkedList of LinkedLists to hold an index of words taken
 * from websites using an implementation of the Page class.
 * 
 * The Page class creates and index of websites. The search engine then builds a LinkedList that takes
 * words from sites in that index. Each word has a list of websites attached to it. When the user enters
 * a query, the engine looks through this list, analyzing the query to match terms that are found in the list.
 * The engine then returns an array of websites in which the query appears. 
 *
 */

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SearchEngine
{	
	private int debugCount = 0; //Debug counter
	private ArrayList<String> links; //Holds the links acquired by building the index
	private LinkedList<LinkedList<String>> words; //Holds the words acquired by creating the list of words
	private boolean indexDone = false; //Building the index is done
	private boolean listsDone = false; //Creating the lists is done
	private int MAXSITES = 5; //Maximum site value for each site
	
	/**Constructor. Initialized the words LinkedList of LinkedLists**/
	public SearchEngine() throws ListIsEmptyException {
		words = new LinkedList<LinkedList<String>>(); //Create a new LinkedList of LinkedLists
	}
	
	/**Builds and index of websites**/
	public void buildIndex(String s) {
		Page p; //Make an instance of the page class
		links = new ArrayList<String>(); //Create the array of links
		try {
			p = new Page(s);
			while(p != null && !p.pageDone()) {
				String line = p.getLine(); //Get each line from the website called by page
			}
			links = p.getLinks();
			for(int i = 0; i < MAXSITES; i++) {
				try {
					p = new Page(links.get(i)); //Add the links acquired by the page
					while(p != null && !p.pageDone()) {
						String line = p.getLine(); //Get the lines from the page
					}
					for(int k = 0; k < MAXSITES; k++) {
						links.add(p.getLinks().get(k)); //Add more links to the array
					}
				} catch(Exception ee) {
					continue; //If there is an error, just keep going
				}
			}
		} catch(Exception ex) {
			System.out.println("ERROR WITH PAGE! " + ex);
		}
		links.add(0,SearchEngineGUI.DEFAULT_URL); //Add the default index to the array
		indexDone = true; //Building the index is done
	}
	
	/**Creates the list of words from the indexed websites**/
	public void createLists() {
		int prog = 0; //Counter
		StringTokenizer st; //StringTokenizer for the words in the sites
		for(int arrayNum = 0; arrayNum < links.size(); arrayNum++) {
			try {
				Page p = new Page(links.get(arrayNum)); //Go to each site in the index
				while(p != null && !p.pageDone()) {
					String theLine = p.getLine().toLowerCase(); //Get each line in lower case
					st = new StringTokenizer(theLine); //Tokenize each line
					while(st.hasMoreTokens()) {
						String token = st.nextToken(); //Grab each individual token
						if(token.charAt(0) != '<' && token.charAt(token.length()-1) != '>') {
							token = token.replaceAll("\\W", ""); //Take out all non-word characters
							token = token.replaceAll("\\d", ""); //Take out all digits
							if(!token.equals("") && token.length() >= 3 && token.length() <= 9) {
								words.addSorted(new LinkedList<String>(token)); //Add the word to the list
								words.addInside(token, p.getName()); //Add the url to the list in the word
							}
						}
						prog++; //Increment the progress
						SearchEngineGUI.runSearchEngine.progress(Math.min(prog, 100)); //Update the searchbar
					}
				}
			} catch(Exception ex) {
				System.out.println("ERROR IN createLists(): " + ex);
				continue;
			}
		}
		listsDone = true; //Creating the lists is done
	}
	
	/**Searches through the LinkedList words for the userQuery**/
	public ArrayList<String> search(String userQuery) {
		String uq = userQuery.toLowerCase().replaceAll("\\d", ""); //Get rid of digit characters
		uq = uq.replaceAll("\\W", " "); //Set all non-word characters to whitespace
		ArrayList<String> al = new ArrayList<String>(); //Create the array
		al = words.listSearch(uq); //Search through the lists
		return al;
	}
	
	/**Returns true if both the buildIndex() and createLists() methods are done**/
	public boolean isDone() {
		return (buildIndexDone() && createListsDone());
	}
	
	/**Returns true if buildIndex() is done**/
	public boolean buildIndexDone() {
		return indexDone;
	}
	
	/**Returns true if createLists() is done**/
	public boolean createListsDone() {
		return listsDone;
	}
	
	/**Prints an array**/
	private void printArray(ArrayList<String> al) {
		for(int i = 0; i < al.size(); i++) {
			System.out.println(i + " " + al.get(i));
		}
	}
	
	/**Debug**/
	private void debug() {
		System.out.printf(">>>DEBUG IN SEARCHENGINE>>> %d\n",debugCount);
		debugCount++;
	}
	
	int tableSize = 21;
	/**NOT IMPLEMENTED YET**/
	public HashMap<Integer,String> ignoreWords() {
		HashMap<Integer,String> ignoreTable = new HashMap<Integer,String>();
		File f = new File("src/ignorewordslist.txt");
		Scanner scan;
		try {
			scan = new Scanner(f);
			while(scan.hasNextLine()) {
				String word = scan.next();
				ignoreTable.put(computeHash(word),word);
			}
			scan.close();
		} catch(FileNotFoundException fnf) {
			System.out.println("IGNORE WORDS FILE NOT FOUND!");
		}
		return ignoreTable;
	}
	
	public Integer computeHash(String s) {
		int value = 0;
		for(int i = 0; i < s.length(); i++) {
			value = (int)(s.charAt(i));
			value %= tableSize;
		}
		System.out.println(value);
		return 0;
	}
} //End SearchEngine