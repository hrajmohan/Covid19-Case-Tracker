/**
COVID-19 Case Tracker
 */

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.io.*;

public class CaseTracker {
	public static void main(String[] args) throws IOException {
		
		String[] states = new String[50];
		//String[] statesCopy = new String[50];
		int[] cases = new int[50];
		int size = 0;
		//int arrInd = 0;
		String userInput = "";
		String userInputState = "";
		//String userInputCases = "";
		int userInputCases;
		int userInputLocation;
		int statePresence;
		int maxCasesIndex;
		
		Scanner sc = null;
		File file = new File("cases_per_state.txt");
		sc = new Scanner(file);
		String state;
		String numberCase;
		while(sc.hasNextLine()) {
			state = sc.next();
			states[size] = state;
			numberCase = sc.next();
			cases[size] = Integer.parseInt(numberCase);
			//arrInd += 1;
			size+=1;
		}
		
		/*for(String str : states) {
			System.out.println(str);
		}
		for(int cas : cases) {
			System.out.println(cas);
		}*/
		//System.out.println("Size of the arrays: " + size);
		
		sc.close();
		
		//creating menu and taking user input
		System.out.println("Welcome to the Coronavirus Case Tracker!\n ");
		Scanner myObj = new Scanner(System.in);
		while(userInput.compareTo("X")!= 0) {
			bubbleSort(states, cases, size); //sorting based on states to use binary search
			System.out.println();
			//System.out.println("Size of the arrays: " + size);
			System.out.println("Please select from the following options:\n ");
			System.out.println("A. Add a state");
			System.out.println("U. Update a state's case");
			System.out.println("P. Print all states' information");
			System.out.println("L. Locate state with highest cases");
			System.out.println("W. Write to a file");
			System.out.println("X. Exit\n");
			System.out.print("Enter your choice (A-W) or X to exit: ");
			userInput = myObj.next();
			System.out.println();
			
			if(userInput.equals("X")) {
				System.out.println("Goodbye!");
				break;
			}
			//adding a state along with its associated cases
			if(userInput.equalsIgnoreCase("A")) {
				System.out.print("Enter the two letter abbreviation for the state: ");
				userInputState = myObj.next();
				System.out.println();
				//int statePresence = binarySearch(states, userInputState, states.length);
				//bubbleSort(statesCopy)
				//bubbleSort(states, cases, size); //sorting based on states to use binary search
				statePresence = binarySearch(states, userInputState, size); //checks if state is already present
				
				//System.out.println("statePresence: " + statePresence);
				if(statePresence == -1) {
					System.out.print("Enter the number of cases in the state: ");
					userInputCases = myObj.nextInt();
					System.out.println();
					
					System.out.print("Enter the position number to insert: ");
					userInputLocation = myObj.nextInt();
					System.out.println();
					
					if(userInputLocation > size + 1 || userInputLocation <= 0) {
						System.out.println("Error! Please enter a location from 1 to " + (size+1) );
						System.out.println();
					}
					else {
						insertState(states, size, userInputLocation -1  , userInputState); //inserting state
						insertCase(cases, size, userInputLocation -1 , userInputCases);//inserting cases
						System.out.println(userInputState + " was added");
						System.out.println();
						size += 1;
					}
				}
			
				else {
					System.out.println("Error! That state is already in the list.");
					System.out.println();
				}
			}
			//update menu option
			if(userInput.equalsIgnoreCase("U")) {
				//System.out.println();
				System.out.print("Enter the state's two letter abbreviation: ");
				userInputState = myObj.next();
				System.out.println();
				statePresence = binarySearch(states, userInputState, size); //checks if state is already present
				if(statePresence == -1) {
					System.out.println("Error! This state is not on list.");
					System.out.println();
				}
				else {
					System.out.print("Enter the updated number of cases in the state: ");
					userInputCases = myObj.nextInt();
					cases[statePresence] = userInputCases;
					System.out.println();
					System.out.print("The total number of cases has been updated!");
				}
				
			}
			if(userInput.equalsIgnoreCase("P")) {
				printArrays(states, cases, size, true, "" );
				
			}
			if(userInput.equalsIgnoreCase("L")) {
				maxCasesIndex = findMax(cases, size);
				System.out.println("The state with the highest number of cases is " + states[maxCasesIndex] + " with " + cases[maxCasesIndex]);
				
			}
			if(userInput.equalsIgnoreCase("W")) {
				System.out.print("Enter the name of the output file: " );
				String outputFileName = myObj.next();
				printArrays(states, cases, size, false, outputFileName );
				
			}
			
			/*for(int i = 0; i < size ; i++) {
				statesCopy[i] = states[i];
			}*/
		}
		myObj.close();
		/*
		for(String str : states) {
			System.out.println(str);
		}
		for(int cas : cases) {
			System.out.println(cas);
		}*/

	}// end main

	/**
	 * Inserts a String element into an array at a specified index - for the states array
	 * @param array the list of String values
	 * @param numElements the current number of elements stored
	 * @indexToInsert the location in the array to insert the new element
	 * @param stateName the new String value to insert in the array
	 */
	public static void insertState(String array[], int numElements, int indexToInsert, String stateName) {

        if (array.length == numElements) {
            System.out.println("Array is full. No room to insert.");
        }
        for(int i = numElements; i > indexToInsert; i--) {
        	array[i] = array[i - 1];
        }
        array[indexToInsert] = stateName;
        
	} // end insert

	/**
	 * Inserts a String element into an array at a specified index - for the cases array
	 * @param array the list of String values
	 * @param numElements the current number of elements stored
	 * @indexToInsert the location in the array to insert the new element
	 * @param numberCases the new int value to insert in the array
	 */
	public static void insertCase(int array[], int numElements, int indexToInsert, int numberCases) {
        if (array.length == numElements) {
            System.out.println("Array is full. No room to insert.");
        }
        for(int i = numElements; i > indexToInsert; i--) {
        	array[i] = array[i - 1];
        }
        array[indexToInsert] = numberCases;
	} // end insert

	/**
	 * Prints two arrays to a file or the console in the format #. STATE: CASES
	 * 
	 * @param states       the list of states
	 * @param cases        the list of cases corresponding to states
	 * @param numElements  the current number of elements stored
	 * @param printConsole a boolean value for whether
	 * @param file         the file name
	 * @throws IOException 
	 */
	public static void printArrays(String[] states, int[] cases, int numElements, boolean printConsole, String file) throws IOException {
				Scanner myObjPrint = new Scanner(System.in);
				if(printConsole == true) {
				System.out.println("Do you wish to order data by state or by cases?");
				System.out.println();
				System.out.println("S. By state");
				System.out.println("C. By total cases");
				System.out.println();
				System.out.print("Enter your choice: ");
				String userPrintChoice = myObjPrint.next();
				System.out.println();
				if(userPrintChoice.equals("C")) {
					bubbleSortInt(cases, states, numElements); //sorting based on states to use binary search
					System.out.println("Sorted By Case: ");
					System.out.println();
					for(int i = 0; i < numElements; i++) {
						System.out.println((i+1)+ ". " + states[i]+": " + cases[i] );
					}
					
				}
				
				else if(userPrintChoice.equals("S")) {
					bubbleSort(states, cases, numElements); //sorting based on states to use binary search
					System.out.println("Sorted Alphabetically: ");
					System.out.println();
					for(int i = 0; i < numElements; i++) {
						System.out.println((i+1)+ ". " + states[i]+": " + cases[i] );
					}
					
				}
				
				else {
					System.out.println("Invalid Entry");
					}
				}
				
				else {
					FileWriter outFile = new FileWriter(file);
					for(int i = 0; i < numElements; i++) {
						outFile.write(states[i]);
						outFile.write("\n");
						outFile.write(Integer.toString(cases[i]));
						outFile.write("\n");
					}
					outFile.close();
				}
			
			//	myObjPrint.close();
			return;
			//throws IOException {
		//}
	} // end printArrays

	/**
	 * Searches for a specified String in a list using the binary search algorithm
	 * 
	 * @param array       the array of Strings
	 * @param value       the String to search for
	 * @param numElements the number of elements in the list
	 * @return the index where value is located in the array
	 */
	public static int binarySearch(String array[], String value, int numElements) {
		
	   int low = 0;
	   int high = numElements - 1;
	   
	   while(low <= high) {
		  int mid = (low+high)/2;
		   if(array[mid].equals(value)) {
			   return mid;
		   }
		   
		   else if(value.compareTo(array[mid]) < 0){
			   high = mid - 1;
		   }
		   else {
			   low = mid+1;
		   }
	   }
		return -1;
	} // end binarySearch

	/**
	 * Searches an array for the location of the maximum value
	 * 
	 * @param array,      an array of doubles
	 * @param numElements the total elements currently stored in the array
	 * @return the index where the maximum value is located
	 */
	public static int findMax(int[] array, int numElements) {
		int max = Integer.MIN_VALUE; // smallest possible int stored as a variable
		int max_index = -1;
		//implement the rest of the method here
		for(int i = 1; i <= numElements; i++ ) {
			if(array[i] > max) {
				max = array[i];
				max_index = i;
			}
		}
		return max_index;
	} // end findMax

	/**
	 * Sorts two parallel arrays - alphabetically - according to the values in the
	 * first (String) array using the bubble sort algorithm - based on states
	 * 
	 * @param first       the array of Strings
	 * @param second      the array of ints
	 * @param numElements the total elements stored in the parallel arrays
	 */
	public static void bubbleSort(String first[], int second[], int numElements) {
		for(int i = 0; i <= numElements - 2; i++) {
			for(int j = 0; j <= (numElements - i - 2); j++) {
				if(first[j].compareTo(first[j+1]) > 0) {
					//array[j+1] = array[j];
					String temp = first[j];
					first[j] = first[j+1];
					first[j+1] = temp;
					//swapping cases array 
					int temp1 = second[j];
					second[j] = second[j+1];
					second[j+1] = temp1;
				}
			}
		}
		return;
	} // end bubbleSort

	/**
	 * Sorts two parallel arrays - numerically - according to the values in the
	 * first (int) array using the bubble sort algorithm - based on cases
	 * 
	 * @param first       the array of ints
	 * @param second      the array of Strings
	 * @param numElements the total elements stored in the parallel arrays
	 */
	public static void bubbleSortInt(int first[], String second[], int numElements) {
		for(int i = 0; i <= numElements - 2; i++) {
			for(int j = 0; j <= (numElements - i - 2); j++) {
				if(first[j] > first[j+1] ) {
					//array[j+1] = array[j];
					int temp = first[j];
					first[j] = first[j+1];
					first[j+1] = temp;
					//swapping cases array 
					String temp1 = second[j];
					second[j] = second[j+1];
					second[j+1] = temp1;
				}
			}
		}
		return;
	} // end bubbleSort
} // end class