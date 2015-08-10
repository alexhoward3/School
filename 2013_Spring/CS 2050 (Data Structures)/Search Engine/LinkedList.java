/**
 * 
 * @author Alex Howard
 * @author Ivan Gajic
 *
 * This is an implementation of a Linked List type data structure.
 * It is specifically designed to hold a LinkedList of LinkedLists.
 * 
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

public class LinkedList<E>
{
	private int length = 0; //The size of the list
	private int debugCount = 0; //debug counter
	Cell head; //Global head variable

	public LinkedList() {
		head = new Cell(null);
	} //end LinkedList

	public LinkedList(String s) {
		head = new Cell((E) s);
	} //end LinkedList

	/**Adds in an item on the head*/
	public void add(E item) {
		Cell c = new Cell(item);    //Create a new cell:
		c.setNext(head.getNext());  //Link it to the head:
		head.setNext(c);            //Link the head to it:
		length++; //Increment the length
	} //end add

	/**Adds an item in sorted (alphabetical) order*/
	public void addSorted(E item) {
		Cell c = new Cell(item); //Create a new cell
		Cell curr; //Current cell iterator
		Cell prv; //Previous cell iterator
		if(isEmpty()) {      //If the list is empty
			head.setNext(c); //Set the head's next to the cell
			length++; //Increment the length
		}
		else {
			//Iterate down the list and compare alphabetically
			for(prv = head, curr = head.getNext(); curr != null && curr.getData().toString()
					.compareTo(c.getData().toString()) < 0; prv = curr, curr = curr.getNext()){}
			//If it is a repeated term do nothing.
			if(curr != null && c.getData().toString().equals(curr.getData().toString())){ return;}
			//Insert between the current and the previous
			c.setNext(curr);
			prv.setNext(c);
			length++; //Increment length
		}
	} //end addSorted

	/**Allows for adding an item into a LinkedList of LinkedLists*/
	public void addInside(String h, String item) {
		Cell c = head.getNext();
		LinkedList<E> nextList = (LinkedList<E>)c.getData();
		while(!nextList.head.getData().equals(h) && c.getNext() != null) {
			c = c.getNext();
			nextList = (LinkedList<E>)c.getData();
		} //Iterate through the lists to the correct position
		if(!nextList.isEmpty() && item.equals(nextList.head.getNext().getData())){ return;}
		try {
			nextList.add((E)item); //Add the new item into the list
		} catch(NullPointerException np) {
			return;
		}
	} //end addInside

	/**Removes an item from the list (semi-arbitrary)*/
	public Cell remove() throws ListIsEmptyException {
		if(isEmpty())
			throw new ListIsEmptyException();
		Cell c = head.getNext();
		head.setNext(c.getNext());
		length--;
		return c;
	} //end remove

	/**Prints a LinkedList*/
	public void print() throws ListIsEmptyException {
		System.out.printf("\nThe list holds the following\n");
		Cell c = head;
		if(isEmpty())
			throw new ListIsEmptyException();

		while(c.getNext() != null) {
			c = c.getNext();
			System.out.println(">> " + c.getData().toString());
		}
	} //end print

	/**Prints a LinkedList of LinkedLists*/
	public void printList() throws ListIsEmptyException {
		Cell c = head;
		LinkedList<E> nextList = (LinkedList<E>)c.getData();

		while(c.getNext() != null) {
			c = c.getNext();
			nextList = (LinkedList<E>)c.getData();
			nextList.printl(nextList);
		}
		System.out.println("\n");
	} //end printList

	/**Method called by printWords() that prints through the list*/
	private void printl(LinkedList<E> ll) throws ListIsEmptyException {
		System.out.printf("\nThe list holds the following:\n");
		Cell c = ll.head;
		if(isEmpty()) {
			System.out.println("THE LIST <<" + c.getData() + ">> IS EMPTY!\n");
			return;
		}
		if(head.getData() != null) {
			System.out.print("<<" + c.getData() + ">> : ");
			c = c.getNext();
			while(c != null) {
				System.out.print(c.getData().toString() + " : ");
				c = c.getNext();
			}
		}
		System.out.println("<<END>>");
	} //end printl

	/**Searches the linked list for a matching query. Returns an array of sites in which that query appears.*/
	public ArrayList<String> listSearch(String h) {
		StringTokenizer st = new StringTokenizer(h);
		ArrayList<String> al = new ArrayList<String>(); //An array to hold the links
		String word;
		while(st.hasMoreTokens()) {
			word = st.nextToken();
			Cell c = head.getNext(); //Iterator cell
			LinkedList<E> nextList = (LinkedList<E>)c.getData(); //List iterator
			while(!nextList.head.getData().equals(word) && c.getNext() != null) {
				c = c.getNext(); //Increment cell iterator
				nextList = (LinkedList<E>)c.getData(); //Increment list iterator
			}
			Cell d = nextList.head; //A new cell iterator inside nextList

			while(!nextList.isEmpty() && d.getNext() != null) { //While list is not empty and the iterator hasn't hit the end
				if(nextList.head.getData().equals(word)) { //If the head of the inner list equals the searched term:
					al.add((String)d.getNext().getData()); //Add that data to the array
					d = d.getNext(); //Increment the iterator
				}
				else {
					break; //Break if the head does not equal the searched term
				}
			}
		}
		return al; //Return the array
	} //end search

	/**Method returns true when there is nothing in the list*/
	public boolean isEmpty() {
		return head.getNext() == null;
	} //end isEmpty

	/**The amount of cells inside of the LinkedList*/
	public int length() {
		return length;
	} //end length

	/**Debug method*/
	private void debug() {
		System.out.printf(">>>DEBUG IN LINKEDLIST>>> %d\n",debugCount);
		debugCount++;
	} //end debug

	/**toString override to print the head of each list (the words) as a String*/
	public String toString() {
		return head.getData().toString(); //Returns the head's data in a String format
	} //end toString

	//********************************************************************************//

	/**
	 * 
	 * @author Alex Howard
	 * 
	 * Cell Class
	 */
	private class Cell
	{
		E data;
		Cell next;
		Cell prev;

		public Cell(E type){
			data = type;
		} //end Cell

		public void setData(E type) {
			data = type;
		} //end setData

		public void setNext(Cell c) {
			next = c;
		} //end setNext

		public void setPrev(Cell c) {
			prev = c;
		} //end setPrev

		public E getData() {
			return data;
		} //end getData

		public Cell getNext() {
			return next;
		} //end getNext

		public Cell getPrev() {
			return prev;
		} //end getPrev
	} //End Cell
} //End LinkedList