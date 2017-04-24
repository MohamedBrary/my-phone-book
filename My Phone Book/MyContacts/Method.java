
package MyContacts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Containes all the static helping methods used in the project.
 * 
 * @author Mohamed Brary
 * 
 */
public class Method {

	
	
	
	
	
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	---------------------------------------  ** Validating input **   ------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Tests a string if it is a number .
	 * @param num The string to be tested
	 * @param phone if the string must be a phone number.
	 * @return true if the string is a valid number.
	 * @return false if the string isn't a valid number.
	 */
	private static boolean validNum(String num ,boolean talkToUser, boolean phone){
		
		if (num!=null&&num.length()!=0)
		{			
			if ( !phone )
				if ( num.length() >= 10)
				{
					if (talkToUser)
						System.out.println("Don't you think that " +
								"you may exceeded your limit ??!! ");
					return false;
				}
				
			// Takes every character from the string.
			for (int i=0;i<num.length();i++)
				if(!((num.charAt(i)>='0')&&(num.charAt(i)<='9')))
				{
					if (talkToUser)
						System.out.println("Please,enter a valid number.");
					return false;
				}
			return true;
		}
		else	
			return false;
	}

	/**
	 * Tests a string if it is a valid .
	 * @param entery The string to be tested
	 * @return true if the string isn't null or its lenght is zero.
	 * @return false otherwise.
	 */
	private static boolean validEntery(String entery){
		
		boolean valid= 	entery!=null && entery.length()!=0
			&& !entery.equals(" ")	&& !entery.contains("  ");	
		
		boolean comma = !entery.contains(",");
		
		if ( valid && comma )		
			return true;
		
			
		else
		{
			if(!comma)
				System.out.println("Sorry, but \",\" is a reserved character.");
			else
				System.out.println("Please,enter a valid input.");
			return false;	
		}
			
	}
	
	/**
	 * Tests a string if it is a valid file name .
	 * @param file The string to be tested
	 * @return true if and only if the string is valid for a file name.
	 * @return false otherwise.
	 */
	private static boolean validFile(String file,String userOrGroup){
		
		boolean notValidFile = file.contains("\\")||file.contains("/")||file.contains("*")||
			file.contains(":")||file.contains("?")||file.contains("\"")||file.contains("<")	||
			file.contains(".")||file.contains(">")||file.contains("|");
		
		boolean notValid = file==null ||file.length()==0 || notValidFile ||
			file.equals(" ") || file.contains("  ");
		
		if(userOrGroup.equals("group name"))
			if (file.equalsIgnoreCase("password")){
				System.out.println("Sorry but thats a reserved word.");
				return false;
			}
		
		if(notValidFile)
			System.out.println("Sorry but "+userOrGroup+" can't contain ( \\ / ? \" . | < > * : ) .");
		else if(notValid)
			System.out.println("Please,enter a valid input.");
		
		return(!notValid);
	}
	
	/**
	 * Tests a string if it is a valid for a contact .
	 * @param contactAsString The string to be tested
	 * @return true if and only if the string is valid for being a contact.
	 * @return false otherwise.
	 */
	public static boolean validContact(String contactAsString)
	{
		
		String[] a = contactAsString.split(",");
		boolean valid = a.length == 5 && a[0].trim().length() != 0 
			&& a[1].trim().length() != 0 && Method.validNum(a[4].trim(), false, true);
			
		return valid;
	}	
	
	/**
	 * Takes a valid input from the user.
	 * @param talkToUser the words which will be printed to user.
	 * @param typeOfInput Takes 1 if the input must be valid string.
	 *  ,Takes 2 if the input must be valid number
	 *  ,Takes 3 if the input must be yes or no 
	 *  ,Takes 4 if the input must be valid for a file name 
	 *  ,Takes 5 if the input can be "" and it returns it "??" .
	 * @param userOrGroup special input for case 4 determines
	 * 		the file will be for a group or a user.
	 * @param phone special input for case 2 determines 
	 * 		the number is a phone number or no.	
	 * @return the input from the user after validating it.
	 * 
	 */
	public static String getInput(String talkToUser, int typeOfInput ,
			String userOrGroup, boolean phone )
			{
		
		boolean failed = false ;
		String input = "";
		
		do {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				

				switch (typeOfInput) {

				// if the input must be just string.
				case 1:
					do {
						System.out.print(talkToUser);
						input = in.readLine().trim();
					} while (!Method.validEntery(input));
					break;

				// if the input must be a positive number.	
				case 2:
					do {
						System.out.print(talkToUser);
						input = in.readLine().trim();
						if (input.length() == 0)
							System.out.println("Sorry but you must " 
									+ "enter a number.");
					} while (!Method.validNum(input, true, phone));
					break;

				// if the input must be yes or no .
				case 3:
					boolean valid;
					System.out.print(talkToUser);

					do {
						input = Method.getInput("(Y\\N) : ", 1, "", phone);
						valid = input.equalsIgnoreCase("y")
								|| input.equalsIgnoreCase("n")
								|| input.length() == 0;
						if (!valid)
							System.out.print("Please, write \"Y\" or \"N\":");

					} while (!valid);
					break;

				// if the input must be valid for file name.
				case 4:
					do {
						System.out.print(talkToUser);
						input = in.readLine().trim();
					} while (!Method.validFile(input, userOrGroup));
					break;

				// if the input can be "" .
				case 5:
					boolean comma;
					do {
						System.out.print(talkToUser);
						input = in.readLine().trim();
						comma = input.contains(",");
						if (comma)
							System.out.println("Sorry, but \",\" " 
									+ "is a reserved character.");
					} while (comma);
					if(input.length()==0)
						input = "??";
					break;

				}
				return input;

			} 
			catch (Exception e) 
			{
				failed = true;
			}
		} while (failed);
		
		return input;		
	}
	
	/**
	 * Takes a valid number from the user with an option to go back.
	 * @param talkToUser the words which will be printed to user.
	 * @param lowerLimit lower limit of the number given by user.
	 * @param upperLimit higher limit of the number given by user.
	 * @return the input from the user after validating it.
	 * @throws IOException
	 */
	public static String getInputBackOption(String talkToUser, int lowerLimit , int upperLimit)
			 {
		
		boolean failed = false ;
		String input = "";
		
		do {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				
				boolean loop;

				do {

					do {
						System.out.print(talkToUser);
						input = in.readLine().trim();
						if (input == null || input.length() == 0)
							System.out
									.println("Sorry but you must enter a number.");
					} while (!input.equalsIgnoreCase("*back")
							&& !Method.validNum(input, true, false));

					loop = !input.equalsIgnoreCase("*back")
							&& (Integer.parseInt(input) > upperLimit || Integer
									.parseInt(input) < lowerLimit);

					if (loop)
						System.out.println("Sorry but that's not an option.");

				} while (loop);

				return input;
			
			} 
			catch (Exception e) 
			{
				failed = true;
			}
		} while (failed);
		
		return input;		
	}
	

	//	------------------------------------------------------------------------------------------------------  \\
	//	---------------------------------------  ** Array Modifying **   -------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Method to copy array of Contacts and decreases its lenght.
	 * 
	 * @param a  The array to be copied and shrinked.
	 * @param numRows  Number of rows of the new array.
	 * @return A new shrinked array.
	 */	
	public static Contact[] shrinkArray(Contact[] a,int numRows){
		
		Contact[] aNew = new Contact[numRows];
		
		for(int i=0;i<numRows;i++)
			aNew[i]=a[i];
		
		return aNew;
	}
	
	/**
	 *  Method to copy array of Strings and decreases its lenght.	 
	 *  @param a The array to be copied and shrinked.
	 *  @param numRows Number of rows of the new array.
	 *  @return A new shrinked array.
	 */
	public static String[] shrinkArray(String[] a,int numRows){
		
		String[] aNew = new String[numRows];
		
		for(int i=0;i<numRows;i++)
			aNew[i]=a[i];
		
		return aNew;
	}
	
	/**
	 *  Method to copy array and increases its lenght.	 
	 *  @param a The array to be copied and expanded.
	 *  @param numRows Number of rows the new array.
	 *  @return A new expanded array.
	 */
	public static Contact[] expandArray(Contact[] a,int numRows){
		
		Contact[] aNew = new Contact[numRows];
		
		for(int i=0;i<a.length;i++)
			aNew[i]=a[i];
		
		return aNew;
	}
	
	/**
	 * Deletes a row from an array of Strings.
	 * @param a The array to be modified.  
	 * @param row Rank of row to be deleted.
	 * @return The array after modifying.
	 */	
	public static String[] deleteRow(String[] a,int row){
		
		int numRows = a.length-1;
		String[] aNew = new String[numRows];	
		
		// to copy elements before the row to be deleted.
		for(int i=0; i<row ;i++)
			aNew[i]=a[i];
		// to overwrite the row and copy the rest of elements.
		for(int i=row; i<numRows ;i++)
			aNew[i]=a[i+1];			
		
		return aNew;		
	}
	
	/**
	 * Deletes a row from an array of Contacts.
	 * @param a The array to be modified.  
	 * @param row Rank of row to be deleted.
	 * @return The array after modifying.
	 */	
	public static Contact[] deleteRow(Contact[] a,int row){
		
		int numRows = a.length-1;
		Contact[] aNew = new Contact[numRows];	
		
		// to copy elements before the row to be deleted.
		for(int i=0; i<row ;i++)
			aNew[i]=a[i];
		// to overwrite the row and copy the rest of elements.
		for(int i=row; i<numRows ;i++)
			aNew[i]=a[i+1];			
		
		return aNew;	
	}	
	
	/**
	 * Deletes a row from an array of Objects.
	 * @param a The array to be modified.  
	 * @param startingRow Rank of row to start deleting from (included in deletion).
	 * @param lastRowToDelete Rank of row to start deleting from (included in deletion).
	 * @return The array after modifying.
	 */
	public static Contact[] deleteRows(Contact[] a, int startingRow,
			 int lastRowToDelete){
		
		int numRows = a.length - (lastRowToDelete - startingRow + 1);
		Contact[] aNew = new Contact[numRows];	
		
		// to copy elements before the row to be deleted.
		for(int i = 0; i < startingRow ;i++)
			aNew[i]=a[i];
		// to overwrite the row and copy the rest of elements.
		for(int i = lastRowToDelete ,j = startingRow ; j < numRows ; i++ , j++)
			aNew[j]=a[i+1];			
		
		return aNew;	
	}	
	

	/**
	 * Deletes a row from an array of Objects.
	 * @param a The array to be modified.  
	 * @param startingRow Rank of row to start deleting from (included in deletion).
	 * @param lastRowToDelete Rank of row to start deleting from (included in deletion).
	 * @return The array after modifying.
	 */
	public static Object[] deleteRow(Object[] a, int startingRow,
			 int lastRowToDelete){
		
		int numRows = a.length-1;
		Object[] aNew = new Object[numRows];	
		
		// to copy elements before the row to be deleted.
		for(int i = 0; i < startingRow ;i++)
			aNew[i]=a[i];
		// to overwrite the row and copy the rest of elements.
		for(int i = lastRowToDelete; i < numRows ;i++)
			aNew[i]=a[i+1];			
		
		return aNew;	
	}	
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	------------------------------------------  ** Encoding **   -----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Puts the string into a certain code.
	 * @param pass The string to be coded.
	 * @return The string after coding it.
	 */
	public static String encode(String pass){
		
		String passCoded="";
		int uni=0;
		
		for (int i = 0 ;i<pass.length();i++)
		{
			uni = pass.codePointAt(i);
			passCoded=passCoded+uni;
		}
		
		return passCoded;
	}
	
	/**
	 * Encrypts or decryptes a string.
	 * @param s The string to be encrypted.
	 * @param encrypt if true it encrypts the String ,and decrypts it otherwise.
	 * @return The string after encrepting.
	 */
	public static String encrypt(String s, boolean encrypt){
		
		String enc="";
		char c;
		int uni;
		
		if(s.contains("* & ]WMEN"))
			return s.substring(9);
		
		// Takes every character and change its unicode by adding 2 or 		
		// sub. 2 from it .
		for(int i=0;i<s.length();i++)
		{
			if(encrypt)
				uni = s.codePointAt(i) + 2;
			else
				uni = s.codePointAt(i) - 2;
			
			c=(char) uni;
			
			if (c == ',')
				return "* & ]WMEN" + s ;
			enc=enc+c;
		}
		return enc;
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	-------------------------------------------  ** Search **   ------------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Searches for a contact in the contact list.
	 * @param s The string to be searched for.
	 * @param byWhat 1 to search for whole contact and 2 to search by last name. 
	 *  
	 * @return The rank of the contact if it exists.
	 * @return Negative number if the contact doexn't exists but return the rank of it if it was exists.
	 * @return -99999 if the contact supposed to be the first contact in the list.
	 */
	public static int search(String s , int byWhat , Contact[] a ,int limitOfSearch)
	{
		
		int low = 0;
		int high = limitOfSearch - 1;
		int mid = 0;
		
		while (low <= high) 
		{
			mid = (low + high) / 2;
			String temp;

			switch (byWhat) 
			{
			case 1:
				temp = a[mid].toSortString();
				break;

			case 2:
				temp = a[mid].getLastName();
				break;
				
			case 3 :
				temp = a[mid].getLastName() + (char) 0 + a[mid].getFirstName();
				break;

			default:
				temp = "";
			}

			if (temp.equalsIgnoreCase(s)) // a[mid]=s
				return mid;
			else if (temp.compareToIgnoreCase(s) < 0) // a[mid] < s
				low = mid + 1;
			else
				// a[mid] > s
				high = mid - 1;
		}
		if (low == 0)
			return -99999;
		return -low;
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	---------------------------------------  ** Helping Methods **   -------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Delays the execution of next line by specific amount of time.
	 * @param times numbers of loops to be done.
	 * @param dots it prints a dot after every loop if it is true.
	 */
	
	public static void loadDelay(int times, boolean dots){
		
		for ( int i = 0 ; i <= times ; i++ ){
			try {
				// delay for 100 milli second.
				Thread.sleep(100);
			} catch (Exception e) {
				
			}
			if(dots)				
				System.out.print(".");
		}
		if(dots)
			System.out.println();	
	}
	
	/**
	 * Confirm from the user that he really wants to close the program.
	 * 
	 * @param talkToUser name of user who wants to close the program.
	 * @throws IOException
	 */
	public static void exitProgram(String talkToUser)  {
		
		
		String option;
		try {
			
			option = Method.getInput("Are you sure you want to exit?",3,"", false);
			if(option.equalsIgnoreCase("y"))
			{
				String bye = "Thanks for using my program ," +
						" good bye "+ talkToUser +" ";
				// writes every character one by one.
				for(int i = 0 ; i < bye.length() ; i++)
				{
					System.out.print( bye.charAt(i) + "");
					Thread.sleep(50);
				}
				Method.loadDelay( 4, true );
				Thread.sleep(500);
				System.exit(0);
			}	
		} 
		catch (Exception e) 
		{
			System.out.print("Error in closing the program");
		}
						
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	---------------------------------------  ** Printers **   -------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Prints the User options menu in the console.
	 * @param delayTimes the amount of delaying between every line .
	 * @see  #loadDelay(int, boolean)
	 */
	public static void printUserMenu( int delayTimes )
	{
		
		System.out.println("\n******************\n"
				+"User options menu:" 
				+ "\n******************");

		Method.loadDelay(delayTimes, false);
		System.out.println("\t1- New  : Creates a new user.");

		Method.loadDelay(delayTimes, false);
		System.out.println("\t2- Open : if you already have a user.");

		Method.loadDelay(delayTimes, false);
		System.out.println("\t3- Dir  : To see all users.");

		Method.loadDelay(delayTimes, false);
		System.out.println("\t4- Quit : Exiting program.");

		Method.loadDelay(delayTimes, false);
		System.out.println("\n******************************************\n");
			
	}
	
	/**
	 * Prints the Group options menu in the console.
	 * @param delayTimes the amount of delaying between every line .
	 * @see #loadDelay(int, boolean)
	 */
	public static void printGroupMenu( int delayTimes )
	{
		

		System.out.println(
			   "\n*******************\n"
				+"Group options menu:"
			  +"\n*******************");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t1- New    : Creates a new group of contacts.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t2- Load   : Opens an existed group of contacts.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t3- Import : Imports a group of contacts made by other program.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t4- Delete : Deletes an existed group of contacts.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t5- Dir    : To see all groups.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t6- Quit   : Exiting program.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\n******************************************\n");
		
	}
	
	/**
	 * Prints the Contact options menu in the console.
	 * @param delayTimes the amount of delaying between every line .
	 * @see #loadDelay(int, boolean)
	 */
	public static void printContactMenu(int delayTimes) {
		
		System.out.println(
				"\n*********************\n"
				+ "Contact options menu:"
				+ "\n*********************");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 1- Add    : To add contacts.");

		Method.loadDelay(delayTimes,false);
		System.out.println("\t 2- Query  : To search for an existing contact.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 3- Delete : To delete an existing contact.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 4- Modify : To modify an existing contact.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 5- Print  : To print all contacts sorted in the group.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 6- Save   : Saves changes made in the group.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 7- Save as: Saves the group with other name.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 8- Export : Saves the group to be ready for print.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t 9- Back   : To go back to the Group options menu.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\t10- Quit   : Exiting without saving the changes.");
		
		Method.loadDelay(delayTimes,false);
		System.out.println("\n******************************************\n");
		
	}

	/**
	 * Prints first menu in ModDel method.
	 *
	 */
	public static void printModDelMenu1(){
		
		try {
			System.out.println("\nThere are too much contacts with this last name :");
			Thread.sleep(200);
			System.out.println("\t1- View all.");
			Thread.sleep(200);
			System.out.println("\t2- Search on these contacts by first name.");
			Thread.sleep(200);
		} 
		catch (Exception e) 
		{
			
		}
		
	}
	
	/**
	 * Prints second menu in ModDel method.
	 *
	 */
	public static void printModDelMenu2(){
		
		try {
			System.out.println("\nDo you want to:\n***************");
			Thread.sleep(200);
			System.out.println("\t1- Delete all.");
			Thread.sleep(200);
			System.out.println("\t2- Choose one of them.");
			Thread.sleep(200);
		} 
		catch (Exception e) {
			
		}
		
	}
	//	------------------------------------------------------------------------------------------------------  \\
	//	------------------------------------------  ** Main Menu **   ----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	

	/**
	 * Handeles all the connecting with user and connect between all methods 
	 * and classes, contain all the menus and offer the user alot of options 
	 * to choose from them.
	 * 
	 * @throws IOException
	 * @see #userMenu(User)
	 * @see #groupMenu(User)
	 * @see #contactMenu(User, Group)
	 */
	public static void getMenus()  
	{
		boolean restart = false;
		String option;
		
		do {
			
			try {
				System.out.println("\n******************************************"
								+ "\nWelcome to my Phone Book !"
								+ "\nI hope you enjoy using it .. \nFrom author: Mohamed Brary .. "
								+ "\n******************************************");

				User user = new User();
				user = userMenu(user);

				Group group = new Group();
				// infinte loop.
				do {
					group = groupMenu(user);

					contactMenu(user, group);

				} while (true);
			} 
			
			// to catch any exception happenes in the project.
			catch (Exception e) 
			{
				try {					
					System.out.println("\nSystem Failure !");
					option = Method.getInput("Do you want to restart? ", 3, "", false);
					if( option.equalsIgnoreCase("y"))
						restart = true;
					else
					{
						System.out.print("Good bye ! ");
						Thread.sleep(500);
						System.exit(1);
					}
						
				} 
				catch (Exception e1) {
					System.out.print("\nGood bye ! ");
					Method.loadDelay(2, false);
					System.exit(1);
				}
			}
			
		} while (restart);		
	}

	/**
	 * Handels the dealing with the user option menu.
	 * 
	 * @param user the object user to be passed and modified in the methods.
	 * @throws IOException
	 * @see #printUserMenu(int)
	 */
	public static User userMenu( User user )  
	{
		
		boolean flag1 = true;
		int n = 2 ;
		
		do 
		{
			flag1 = true;
			printUserMenu(n);
			n--;
			String inA = Method.getInput("Choose from those " +
					"commands (Write its number 1 ,2 ,3 or 4):", 2,"", false);		
			
			switch(Integer.parseInt(inA))
			{
			
			case 1 :
				user = user.newUser();				
				break;
			
			case 2 :
				user.loadUser();				
				break;
			
			case 3 :			
				user.getAllUsers();
				Method.loadDelay(4,true);
				flag1 = false;
				break;
			
			case 4 :
				Method.exitProgram("!");
				flag1 = false;
				break;	
							
			default:
				{
				System.out.println("\nSorry, but thats not an option.\n");
				Method.loadDelay(1,false);
				flag1 = false;
				}
			}
						
		} while (!flag1|| (user.getName().equalsIgnoreCase("?")));
		
		return user;
	}

	/**
	 * Handels the dealing with the Group option menu.
	 * 
	 * @param user the loaded user from the previous menu.
	 * @return group after creating ,importing or loading it.
	 * @see #printGroupMenu(int)
	 */
	public static Group groupMenu( User user) {
		
		int n = 1;
		String in1 = "";
		boolean flag2 = true;
		Group group = new Group();
		
		do 
		{				
			flag2 = true;
			printGroupMenu(n);
			n--;
			
			in1 = Method.getInput("Choose from those commands " +
					"(Write its number):", 2,"", false);			
			
			switch (Integer.parseInt(in1)) {

			case 1:
				group = group.newGroup(user);				
				break;

			case 2:
				group = group.loadGroup(user);				
				break;

			case 3:
				group = group.importFile(user);
				Method.loadDelay(4, true);
				break;

			case 4:
				group = group.deleteGroup(user);
				Method.loadDelay(4, true);
				break;

			case 5:
				user.getAllGroups();
				Method.loadDelay(4, true);
				flag2 = false;
				break;
			
			case 6 :
				Method.exitProgram("\"" +user.getName() +"\"");	
				flag2 = false;
				break;	

			default: {
				System.out.println("\nSorry, but thats not an option.\n");
				flag2 = false;
				Method.loadDelay(1, false);
			}
			}				
		} while (!flag2 || (user.getName().equalsIgnoreCase("?")) 
				|| group.getGroupName() == null || group.getGroupName().equals("??"));	

		return group;
		
	}
	
	/**
	 * Handels the dealing with the Contact option menu.
	 * 
	 * @param user the loaded user from the previous menu.
	 * @param group after creating ,importing or loading it from the previous menu.
	 * @see #printContactMenu(int)
	 */
	public static void contactMenu( User user, Group group ) {
		
		int n = 1;
		String in2 = ""; 	
		boolean flag3 = true;
								
		
		do {
			
			flag3 = true;
			printContactMenu(n);
			n--;
			
			in2 = Method.getInput("Choose from those " +
					"commands (Write its number):", 2,"", false);				
			
			switch(Integer.parseInt(in2))
			{
				case 1 :
					group.addContact();
					Method.loadDelay( 4, true );
					break;
				case 2 :
					group.query();
					Method.loadDelay( 6, true );
					break;
				case 3 :
					group.deleteContact();
					Method.loadDelay( 4, true );
					break;
				case 4 :
					group.modifyContact();
					Method.loadDelay( 4, true );
					break;
				case 5 :
					group.print();
					Method.loadDelay( 4, true );
					break;
				
				case 6 :
					group.saveGroup( true, true, "" );
					Method.loadDelay( 4, true );
					break;
				
				case 7 :
					group.saveAsGroup(user);
					Method.loadDelay( 4, true );
					break;						
				
				case 8 :
					group.exportFile();
					Method.loadDelay( 4, true );
					break;
				
				case 9 :
					System.out.println("All unsaved changes will be lost.");
					in2 = Method.getInput("Are you sure you want to go back?", 3, "", false);
					if(in2.equalsIgnoreCase("y"))
						return ;					
					break;
					
				case 10 :
					Method.exitProgram("\"" +user.getName() +"\"");		
					break;						
				
				default:
					{
					System.out.println("\nSorry, but thats not an option.\n");
					Method.loadDelay(1,false);
					
					}
				
			}				
		} while ( flag3 );

	}


}
