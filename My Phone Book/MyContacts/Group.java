/**
 * 
 */
package MyContacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <nl><li>This class creates object Group and contains all methods that deals with this object.<nl></li>
 * <nl><li>Object Group represents a group of contacts.<nl></li>
 * <nl><li><b>Object Group attributes :<nl></li>
 * <nl><li>*User name : the user which this group of contacts belongs to.<nl></li>
 * <nl><li>*Group name : name of the group.<nl></li>
 * <nl><li>*Contact list : list of contacts at this group.<nl></li>
 * <nl><li>*Path : path of the file *.txt that contains all the contacts.<nl></li>
 * 
 * @author Thunder Bolt
 *
 */
public class Group {
	
	// Instances of object Group.
	private String userName;
	private String groupName;
	private Contact[] contactList;
	private String path; 
	private File file;
	
	/**
	 * 
	 */
	public Group() {
		
	}
	/**
	 * Creates new file named with the name of the group.
	 * @param groupName name of the group.
	 * @param userName name of the user.
	 * @param contactList array of contactList.
	 */
	public Group(String groupName,String userName ,Contact[] contactList) {
		
		this.userName = userName;
		
		this.groupName = groupName;
		this.contactList = contactList;
		this.path = "C:\\My PhoneBook\\"+userName+"\\"+groupName+".txt";
		this.file = new File(this.path);
		if(!file.exists())
			try {
				PrintWriter p = new PrintWriter(new FileWriter(this.path,true));
				p.println("0");
				p.close();
			} 
			catch (Exception e) {
				System.out.print("Error: in constructing a group.");
			}
	}
	//	------------------------------------------------------------------------------------------------------  \\
	//	----------------------------------------- Getters and Setters ----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * @return the array of contactList.
	 */
	public Contact[] getContactList() {
		return contactList;
	}
	/**
	 * @param contactList the array contactList to set.
	 */
	public void setContactList(Contact[] contactList) {
		this.contactList = contactList;
	}	
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * @return the object file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	-------------------------------------------  ** Methods **   -----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Creates new group
	 * @param user The user which this group belongs to.
	 * @return The new group.
	 * @see #getNewGroupData(User)
	 */
	public Group newGroup(User user){
		Group group = new Group();
		try 
		{
			group = getNewGroupData(user);
		} 
		catch (Exception e) 
		{
			System.out.println("Error: in making new group.");
			Method.loadDelay(4, true);
		}
		System.out.print("The group \""+group.getGroupName()+"\" has been created ");
		Method.loadDelay(4, true);
		return group;
	}
	
	/**
	 * Writes the contact list in a file.
	 * 
	 * @param save takes true if the process is save , and false if
	 * 	the process is export or save as.
	 * @param encrypted takes true if the contacts will be written in
	 * a certain code , false otherwise.
	 * @param path the path of file to be written in. 
	 * 
	 */
	public boolean saveGroup( boolean save ,boolean encrypted ,String path){				
		
		
		boolean saved = true;
		PrintWriter file = null;
		Contact[] a = this.getContactList() ;
		if ( a==null)
		{
			System.out.println("There aren't any contact in this group.");
			return false;
		}
			
		int length = a.length;
		
		try {			
			
			// writes every contact but in certain code.
			if(encrypted)
			{
				
				file = new PrintWriter(new FileWriter(this.path, false));
				// writes the number of contacts in this group in first line of
				// the file.
				file.println(length);
				for (int i = 0; i < length ; i++)					
					file.println(a[i].toEncryptedString());
				
			}
			// writes them but normally .
			else
			{
				
				file = new PrintWriter(new FileWriter(path, false));
				for (int i = 0; i < length ; i++)
					file.println(a[i].toString());
					
			}							
			
		} 
		
		catch (Exception e)
		{
			System.out.println("Error: in saving a group.");
			saved = false;
		}
		
		finally 
		{
			if(file != null)
			{
				file.close();
				// deletes the file if the exporting failed.
				if ( !saved && !encrypted)
				{
					File txt = new File(path);
					if ( txt.exists() )
						txt.delete();
				}
					
			}
				
		}
		
		if(save)
		{
			System.out.print("The group \"" + this.getGroupName() 
					+ "\" has been saved ");
		}
		return true;
	}
	
	/**
	 * Save the group as a new group with different name.
	 *
	 * @param user The user which the group belongs to.
	 * @return the new group.
	 * 
	 * @see #saveGroup(boolean, boolean, String)
	 * @see #getNewGroupData(User) 
	 * 
	 */
	public Group saveAsGroup(User user){
		Group group = new Group();			
				
		try {
			// getting the data of the new group.
			group = getNewGroupData(user);
		
		
		group.setContactList(this.getContactList());
		boolean success = group.saveGroup(false,true,"");
		
		// if the saving process completed successfully.
		if(success)
			// that means that this process is part of importing a file.
			if (this.getGroupName()==null){
				System.out.print("The group has been saved as \""+group.getGroupName()+"\" ");
				Method.loadDelay(4,true);
			}
			
			else
				System.out.print("The group \""+this.getGroupName()
						+"\" has been saved as \""+group.getGroupName()+"\" ");
		
		return group;
		} 
		catch (Exception e)
		{
			System.out.println("Error: in saving the group as a new group.");
			return group;
		}
	}
	
	/**
	 * Exports the group as a txt file in "C:\" its name 
	 * provided from the user.	  
	 * 
	 * @see #saveGroup(boolean, boolean, String) 
	 */
	public void exportFile() {		
				
		String destination = "" ;
		String option = "" ;
		boolean success = true;
		boolean found;
		
		try {
			
			do{
				// get the path of the wanted destination .
				destination = Method.getInput("Please, enter the name " +
					"of the exported file:", 4, "file name", false);
				
				// if the user wants to go back.
				if(destination.equalsIgnoreCase("*back"))
					return ;
				
				destination = "C:\\" + destination + ".txt";
				found = new File(destination).exists();
				
				// makes sure not to overwrite an existing file.
				if(found)
				{
					System.out.println("This file already exists.");
					option = Method.getInput("Do you want to overwrite it?", 3, "", false);
					if(option.equalsIgnoreCase("n"))
						found = false;
				}
				else
					found = true;
				
			}while(!found);
			
			// printing to a file after collecting its data.
			success = this.saveGroup(false, false, destination);
			
		}

		catch (Exception e) 
		{
			if( !success)
				System.out.println("Error: in exporting the group.");							
			
			return ;
		}
		
		if( success )
		{
			if (new File(destination).exists())
			{
				System.out.println("The group \"" + this.getGroupName()
						+ "\" has been exported and its path :");
				System.out.println(destination);
			}
			
			else
				System.out.println("Error : Exporting failed ");
			
		}	
	}
	
	/**
	 * Loads the contactList saved in a file .
	 * @param user The user which the group belongs to.
	 * @return a group of contactList that was read from a file.
	 * 
	 * @see #getLoadDelGroupData(User, String) 
	 */
	public Group loadGroup(User user) {

		BufferedReader b;
		Group group;
				
		try {

			// getting the information about the file to be loaded.
			 group = this.getLoadDelGroupData(user ,"load");			

			if (group.getGroupName().equals("??"))
				return group;

			b = new BufferedReader( new FileReader( group.getPath() ) );
			
			// reading number of contacts from first line.
			int n = Integer.parseInt(b.readLine());			
						 
			Contact[] contact = new Contact[n];
			Contact temp;

			for (int i = 0; i < n; i++) 
			{													 
				temp = new Contact(b.readLine().split(","), true);
				contact[i] = temp;					
			}
			// closing the buffer.
			b.close();	
			// setting the contact list
			group.setContactList(contact);

			System.out.print("The group \"" + group.getGroupName()
					+ "\" has been loaded ");
			Method.loadDelay(4, true);
			return group;
		}

		catch (Exception e) 
		{			
			System.out.println("Error: in loading a group. ");
			setGroupName("??");
			return this;
		}

		
	}
	
	/**
	 * Loads the contactList saved in a file .
	 * 
	 * @param user The user which the group belongs to. 
	 * @return a group of contactList that was read from a file.
	 * 
	 * @see #saveAsGroup(User)
	 * @see Contact insertContact(Contact[], int)
	 * @see Method validContact(String)
	 */
	public Group importFile(User user) {

		
		Group group = new Group();
		BufferedReader b ;
		String pathOf = null;
		boolean flag;
		
		
		try {
			
			// getting the full path from the user with an option to go back.
			System.out.println("Please, enter the full " 
					+ "path of your file:"
					+ "\n\tFor example : \"C:\\Folder name\\File name.txt\"");
			pathOf = Method.getInput("The full " +
					"path of your file:", 1,"", false);
			if(pathOf.equalsIgnoreCase("*back"))
			{
				group.setGroupName("??");
				return group;
			}
				
			b = new BufferedReader(new FileReader(pathOf));
			int k = 0 ; 	// number of contacts.
			int n = 10000;	// the begining lenght of the array. 		
			String line;
			Contact[] contact = new Contact[n];
			Contact temp;
			boolean duplicated = false;
			boolean notValid = false;
			int dupl = 0;
			int inValid = 0;
			contact[0] = new Contact("zzzzzz", "zz", "zz",
						"zz", "zz");
			
			flag = true;
			while(flag)
			{
				line = b.readLine() ;
				if (line != null && !line.equals("")) 
				{
					k++;
					// makes sure that we didn't out bound the array
					// and doubles the array otherwise.
					
					if( k >= n)
					{
						n = 2*n;
						contact = Method.expandArray(contact, n);
						k--;
						
					}
					
					// make sure that this line can be a valid contact.					
					if(Method.validContact(line))
					{
						temp = new Contact(line.split(","), false);						
						contact = temp.insertContact(contact, k);	
						// that means that there is duplicated contact.
						if ( contact[k] == null)
						{
							duplicated = true;
							dupl++;
							k--;
						}	
					}
					
					else
					{
						notValid = true;
						inValid++;
						k--;
					}
															
				}
				// end of file break the loop.
				else if(line == null)
					flag = false;
			}
			b.close();

			group.importCheck(k, n, contact, duplicated, notValid, dupl, inValid);
			group = group.saveAsGroup(user);
		}

		catch (Exception e) 
		{			
			System.out.println("Error: in importing the group " 
					+",Please make sure that you entered a valid data.");	
			group.setGroupName("??");
			return group;
		}

		System.out.print("The group \"" + group.getGroupName()
				+ "\" has been loaded ");
		
		return group;
	}
	/**
	 * 
	 * Get the user option whether to fix the file imported if it
	 * is corrupted or no.
	 * 
	 * @param numberOfContacts number of contacts.
	 * @param lengthOfContactList length of the array.
	 * @param contactList the array of contacts.
	 * @param duplicated true if there is duplicated contact(s).
	 * @param notValid true if there is corrupted contact(s).
	 * @param duplicatedContacts number of duplicated contacts.
	 * @param inValidContacts number of corrupted contacts.
	 * @throws IOException
	 */
	private void importCheck(int numberOfContacts, int lengthOfContactList, Contact[] contactList,
			boolean duplicated, boolean notValid, int duplicatedContacts,
			int inValidContacts) throws IOException {
		// if the file is empty.
		if (numberOfContacts == 0)
		{				
			
			System.out.println("Sorry, but the file is empty.");
			setGroupName("??");
			return ;
			
		}
		// that means there was corrupted and duplicated contacts in this file.
		else if (duplicated && notValid)
			System.out.println("Import report:\n" 
					+ "**************\n\t **There are "+duplicatedContacts+" duplicated contacts ." 
					+ "\n\t **There are "+inValidContacts+" invalid contacts .");
					
		// that means that there are duplicated contacts in the file.
		else if (duplicated)
			System.out.println("There are "+duplicatedContacts+" duplicated contacts in this file.");
			
		
		// that means there was corrupted contacts in this file.
		else if (notValid)
			System.out.println("There are "+inValidContacts+" invalid contacts in this file.");
			
		
		if(notValid||duplicated)
		{
			String option = Method.getInput("Do you want me to fix it ? ", 3, "", false);
			
			if ( option.equalsIgnoreCase("n") )
			{
				setGroupName("??");
				return ;
			}
			
		}
		// removing the null elements.	
		if (numberOfContacts != lengthOfContactList)
			contactList = Method.shrinkArray(contactList, numberOfContacts);
		
		/*
		 *  setting the list and printing it to a new file
		 *  in the ordinary place in the certain code.
		 */ 
		setContactList(contactList);
	}
	
	/**
	 * Deletes a group .
	 * 
	 * @param user The user which the group belongs to.
	 * @return the group after deleting its data.
	 * 
	 * @see #getLoadDelGroupData(User, String)
	 *  
	 */
	public Group deleteGroup(User user) {	
		
		// printing all groups available to user to choose from them.
		Group group = new Group();
		try 
		{
			group = group.getLoadDelGroupData(user, "delete");
		} 
		catch (Exception e) 
		{
			System.out.print("Error occured in acquiring deleting data. ");
			return group;
		}
		
		if( group.getGroupName().equals("??") )					
			return group ;
		
		
		else
		{	
			// to make sure that the file is going to be deleted.
			System.gc();
			boolean deleted = group.getFile().delete();			
			if(deleted)
				System.out.print("The group \"" + group.getGroupName() 
						+ "\" has been deleted ");
			else
				System.out.print("An error occured while deleting the group \""
						+ group.getGroupName() + "\"  ");
		}		
			group.setGroupName("??");
			return group;
		}	
	
	
	/**
	 * Add contacts to this group.
	 * Expand the ContactList and puts new Contacts 
	 * into it provided from the user.
	 * 
	 * @see  #search(String, Contact[], int, boolean)
	 * @see  Contact insertContact(Contact[], int)
	 * @see Contact getContactData(boolean)
	 * 
	 */
	public void addContact(){
		
		try 
		{			
			int rank;		
			Contact[] a = getContactList();
			int oldLength = 0;
			if( a != null)
				oldLength = a.length;
			
			// getting number of contacts to be added.
			String s = Method.getInputBackOption("Please, enter number of " +
						"contacts you want to add to the group :", 1, 300);
			if ( s.equalsIgnoreCase("*back"))
				return ;
			
			// increasing the Contact list by the number user entered.
			int k = Integer.parseInt(s);
			if ( !(a==null) && !(a.length==0) )
			{
				rank = a.length;
				a = Method.expandArray(a, rank + k);						
			}			
			else{
				a = new Contact[k];
				rank = 0;
			}
			
				
			System.out.println("If you want to go back enter \"*back\" in \"Last name\" field. "
					+ "\n\t\" * fields are optional \"");
			boolean flag = true;
			int counter = 0;
			
			for( int i=1 ; i<=k && flag ; i++)
			{
				Contact temp = new Contact();
				System.out.println("Please, enter the following for contact "+i+" :");
				
				// getting contact details.
				temp = temp.getContactData(false);
				
				/*
				 * if the user had enough adding we delete the extra
				 * elements of the list and break the loop.
				 */  
				if ( temp.getLastName().equalsIgnoreCase("*back"))
				{
					a = Method.shrinkArray(a, rank+counter);				
					flag = false;				
				}				
				
				else
				{	
					// setting the limit of search to avoid null exception.
					int limitOfSearch = oldLength + counter ;
					int search = Method.search( temp.toSortString(), 1, a, limitOfSearch);
					
					if (search>=0)
					{
						System.out.println("A contact with this last name " +
								",first name and phone number already exists.");
						i--;				
					}
					else
					{
						// inserting the contact in the right place.
						a = temp.insertContact(a, limitOfSearch);
						counter++;
					}
				}
				
			}
			
			// setting the contact list after modifying.
			setContactList( a );
			System.out.print(counter+" contacts have been added to the group \""
					+ getGroupName()
					+ "\" successfully but they aren't saved yet  ");
		
		} 
		catch (Exception e) 
		{
			System.out.println("Error: in adding contacts to the group.");
		}
	}
	
	/**
	 * Deletes a contact from this group or
	 * group of contacts .
	 * 
	 * @see #getModDelData(String)
	 * 
	 */
	public void deleteContact()  {

		int[] rank ;
		try {
			rank = this.getModDelData("delete");
		
		
			/* 
			 * make sure that if the contact isn't found 
			 * or the user don't want to delete anything.
			 * or there isn't any contact in the group.
			 */
			if (rank.length != 0)
			{
				Contact[] a = this.getContactList();
				
				if( rank.length == 1)
				{	
					System.out.println("This contact has been deleted:\n\t"
							+a[rank[0]].toString());
					// remove the wanted contact from the list.
					a = Method.deleteRow( a, rank[0] );
					this.setContactList(a);
					return ;
				}
				
				else
				{
					System.out.println("These contacts have been deleted:" 
							+ "\n*********************************");
					for ( int i = rank[0] ; i <= rank[1] ; i++)
						System.out.println("\t" + a[i].toString());
					
					a = Method.deleteRows(a, rank[0], rank[1]);
					this.setContactList(a);
					return ;
					
				}
			}
		} 
		catch (Exception e) 
		{
			
			System.out.print("Error occured in acquiring deleting data. ");
			return ;
		}
	}
	
	/**
	 * Modifies a contact from this group.
	 * 
	 * @see #getModDelData(String)
	 */
	public void modifyContact()  {		
			
		try 
		{
			Contact temp = new Contact();
			int[] rank = getModDelData("modify");

			/*
			 * make sure that if the contact isn't found 
			 * or the user don't want to delete anything. or
			 * there isn't any contact in the group.
			 */
			if (rank.length != 0)
			{
				// keeping the old contact before modifying it.
				String old = this.getContactList()[rank[0]].toString();
				Contact[] cont = this.getContactList();

				// deleting the contact from the list.
				cont = Method.deleteRow(cont, rank[0]);
				cont = Method.expandArray(cont, cont.length + 1);

				System.out.println("\nPlease, enter the new data for the contact.");

				// inserting the contact after modifying it in its sorted place.
				if (cont.length == 1)
					cont[0] = temp.getContactData(true);
				else 
				{
					
					int limitOfInsert = cont.length - 1;
					int search;
					
					do {
						
						// to check that the entered contact isn't duplicated.
						temp = temp.getContactData(true);
						search = Method.search(temp.toSortString() , 1 , cont ,limitOfInsert);
						if ( search >= 0 )
							System.out.println("A contact with this last name " +
								",first name and phone number already exists."
									+ "\nPlease, enter the data again :\n");
						
					} while ( search >= 0 );
					
					cont = temp.insertContact( cont, limitOfInsert );
				}

				this.setContactList(cont);
				System.out.println("This contact has been modified:"
						+"\n\tFrom : " + old
						+"\n\tTo   : " + temp.toString());
				
			}
		}
		catch (Exception e) 
		{

			System.out.print("Error occured in acquiring modifying data. ");
			return ;
		}

	}
	
	/**
	 * Prints all contacts in the group on console,
	 * giving the user 3 forms to choose from them.
	 *  
	 * 
	 */
	public void print() {		
		
		try {
			if (this.getContactList()==null || this.getContactList().length==0)
			{
				System.out.println("There aren't any contact in this group.");
			}
			
			else
			{	
				// giving the user 3 options and getting the user's choice.
				System.out.println("\nChoose from the following forms :"
						+"\n*********************************\n");
				Thread.sleep(300);
				System.out.println("\t1- Every detail in one line.\n");
				Thread.sleep(300);
				System.out.println("\t2- Contact (n) : Last name, First name, Address, City, Phone number.\n");
				Thread.sleep(300);
				System.out.println("\t3- Contact (n) :\n\t\tLast name \tFirst name \tPhone number"
						+ "\n\t\t\tAddress \tCity\n");
				Thread.sleep(300);
				
				String num = Method.getInputBackOption("Choose from those commands" +
							" (Write its number):", 1, 3);
				
				System.out.println();
				
				if(num.equalsIgnoreCase("*back"))
					return;
				
				
				switch( Integer.parseInt(num) )
				{
				case 1 :
					for (int i = 0; i < getContactList().length; i++)
						System.out.println("Contact (" + (i+1) + ") :\n" 
								+ getContactList()[i].toBigFancyString()+"\n");					
					break;
					
				case 2 :
					for (int i = 0; i < getContactList().length; i++) 
						System.out.println("Contact (" + (i+1) + ") : " 
								+ getContactList()[i].toString());				
					break;
					
				case 3 :
					for (int i = 0; i < getContactList().length; i++)
						System.out.println("Contact (" + (i+1) + ") :\n\t" 
								+ getContactList()[i].toFancyString()+"\n");					
					break;

				
				}
					
				System.out.println();
			}
		} catch (Exception e) {

			System.out.print("Error occured in acquiring printing data. ");
			
		}
	}
	
	/**
	 * Searches on contact list by last name, first name or phone number
	 * 
	 * 
	 */	
	public void query()  {
		
		try {
			Contact[] a = this.getContactList();
			if (a == null || a.length == 0)
				System.out.println("There aren't any contact in this group.");

			else 
			{
				
				System.out.println("Please, choose the way you want to search by :");
				Thread.sleep(300);
				System.out.println("\t1- Last name    : gets all contacts with this last name .");
				Thread.sleep(300);
				System.out.println("\t2- First name   : gets all contacts with this first name .");
				Thread.sleep(300);
				System.out.println("\t3- Phone number : gets all contacts with this phone number .");
				Thread.sleep(300);
				System.out.println("If you want to go back to menu write \"*back\" .");
				
				// getting user option.
				String option = Method.getInputBackOption("Number  of your choice : ", 0, 4);		
				if(option.equalsIgnoreCase("*back"))
					return ;
				
				int k = 1;			
				String s ;

				
				switch (Integer.parseInt(option)) 
				{
				case (1):

					// getting the last name.
					System.out.println("Enter the last name of the required contact ,please :");
					s = Method.getInput("\tLast name : ", 1, "", false);
					if (s.equalsIgnoreCase("*back"))
						return;

					int search = Method.search(s, 2, a, a.length);
					
					// searching binary search on this last name and printing the matching contacts.
					if (search < 0)
						System.out.println("There isn't any contact with this last name.");
					else 
					{
						int[] aInt = this.search(s, a, search, true);
						if ( aInt[2] == 1)
							System.out.println("The Contact is : \n\t"
									+ a[search].toString());
						else
							for (int i = aInt[0]; i <= aInt[1]; i++, k++) 
							{
								System.out.println("Contact (" + k + ") : \n\t"
										+ a[i].toString());
							}
					}
					System.out.println();
					break;

				case (2):
					// getting first name.
					System.out.println("Enter the first name of the required contact ,please :");
					s = Method.getInput("\tFirst name : ", 1, "", false);
					if (s.equalsIgnoreCase("*back"))
						return;

					System.out.println();
					// Sequential search on this first name, and print what it
					// finds at once.
					for (int i = 0; i < a.length; i++)
						if (s.equalsIgnoreCase(a[i].getFirstName())) 
						{
							System.out.println("Contact (" + k + ") : \n\t"
									+ a[i].toString());
							k++;
						}
					if (k == 1)
						System.out.println("There isn't any contact with this first name.");
					System.out.println();
					break;

				case (3):
					// getting phone number.
					System.out.println("Enter the phone number of the required contact ,please :");
					s = Method.getInput("\tPhone number : ", 2, "", true);
					if (s.equalsIgnoreCase("*back"))
						return;
					System.out.println();

					// Sequential search on this phone number, and print what it
					// finds at once.
					for (int i = 0; i < a.length; i++)
						if (s.equalsIgnoreCase(a[i].getPhoneNumber())) 
						{
							System.out.println("Contact (" + k + ") : \n\t"
									+ a[i].toString());
							k++;
						}
					if (k == 1)
						System.out.println("There isn't any contact with this phone number.");
					System.out.println();			
				}
				
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.print("Error occured in acquiring quering data. ");
			
		} 
	}
	
	// ----------------------------------------------------------------------------------------------------  \\
	//	------------------------------------- Input for group options -------------------------------------  \\
	//	---------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Prompts the user for group name
	 * and checks if it is existed.
	 * @return Group after setting user and its name.
	 * @throws IOException
	 */
	private Group getNewGroupData(User user) throws IOException {
		
		String[] paths = user.getFolder().list();		  
		boolean flag = true;
		String name;
		
		do {
			
			flag = true;
			// getting name of the group.
			name = Method.getInput("Please, enter the name " +
					"of the group :", 4, "group name", false);			

			// make sure that this group doesn't exist.
			if (paths != null || paths.length != 1) {
				for (int i = 0; i < paths.length; i++)
					if (paths[i].equalsIgnoreCase(name + ".txt")) 
					{
						String option = Method.getInput("This group is already exsited, "
								+ "Are you sure you want to replace it? ",3,"", false);
						if (option.equalsIgnoreCase("n"))
							flag = false;
					}
			}
			
		} while (!flag);
		
		Group group = new Group(name, user.getName(), null);
		
		return group;
	}
	
	/**
	 * Prompts the user for group name and checks if it is existed.
	 * 
	 * @return Group after setting user and its name.
	 * @throws IOException
	 */
	private Group getLoadDelGroupData(User user, String loadDelete)
			throws IOException {
		
		// prints all groups in this user.
		user.getAllGroups();
		
		// array of strings holds names of groups.
		String[] paths = user.getFolder().list();		

		if ( paths.length <= 1 ) 
		{
			this.setGroupName("??");
			return this;
		}
		
		// getting user option.
		System.out.println("If you want to go back to menu write \"*back\" .");
		String s = Method.getInputBackOption("Please, write the number of group "
				+ "that you want to " + loadDelete + " :", 1, paths.length - 1);

		if (s.equalsIgnoreCase("*back")) {
			this.setGroupName("??");
			return this;
		}

		boolean flag = true;
		int passwordRank = 0;
		for (int i = 0; i < paths.length && flag; i++)
			if (paths[i].equalsIgnoreCase("password.txt")) {
				passwordRank = i;
				flag = false;
			}

		// removes password.txt from paths array .
		paths = Method.deleteRow(paths, passwordRank);
		s = paths[(Integer.parseInt(s)) - 1];
		s = s.substring(0, s.indexOf("."));

		Group group = new Group(s, user.getName(), null);
		return group;
	}		
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	---------------------------------------- Sorting and Searching ---------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Prompts the user for information about the contact 
	 * to be modified or deleted.
	 * @param delMod Kind of operation the user gonna perform on 
	 * the contact (delete \ modify).
	 * @return rank of the wanted contact.
	 * @return Negative number if there is no contact was chosen by the user
	 * 	Or the contact doesn't exist.
	 * @return array of integers containing info about
	 * a group of contacts to be deleted.
	 * @throws IOException
	 */
	
	private int[] getModDelData(String delMod) throws IOException{
		
		
		String last, first, num;
		Contact[] contactList = this.getContactList();
		int[] retF = new int[0]; // the returned array when the proccess fail for any reason.

		// if there is no contacts
		if (contactList == null || contactList.length == 0) 
		{
			System.out.println("There aren't any contact in this group.");
			 
			return retF;
		}

		
		else 
		{
			// getting the last name of the wanted contact.
			System.out.println("Please, enter the name of contact " +
					"you want to "+ delMod + " :");
			last = Method.getInput("\tLast name : ", 1,"", false);
			if(last.equalsIgnoreCase("*back"))
				return retF ;
			
			// if the contact doesnt exist.
			int search = Method.search( last, 2, contactList, contactList.length );			
			if (search < 0) 
			{
				System.out.println("Sorry, you are trying to " + delMod
						+ " a contact that doesn't exist.");
				return retF;
			}

			
			else 
			{
				// getting more details about contacts with this last name.
				int[] a = this.search(last, contactList, search ,true);
											
				
				// number of contacts that match is more than 10.
				if (a[2] >= 10) 
				{
					// giving the user 2 options and getting the user's choice.
					Method.printModDelMenu1();
					num = Method.getInputBackOption("Choose from those commands" +
								" (Write its number):", 1, 2);
					if ( num.equalsIgnoreCase("*back"))
						return retF;
					
					// searching on the matching list by first name.
					if( Integer.parseInt(num)==2 )
					{
						first = Method.getInput("\tFirst name : ", 1,"", false);
						if ( first.equalsIgnoreCase("*back"))
							return retF;
						
						// searching binary search on last & first name.
						String toBeSearched = last + (char) 0 + first ;						
						search = Method.search(toBeSearched, 3, contactList ,contactList.length);
						
						if(search<0)
						{
							System.out.println("Sorry but there is no " 
									+ "contact with this first name from them.");
							return retF;
						}							
						
						// getting info about the matching contacts.
						a = this.search(toBeSearched, contactList, search, false);
						
					}	
				}			

				// if there is just one contact that matches.
				if (a[2] == 1) 
				{				
					
					System.out.println("\nThe contact  : "
							+ contactList[search].toString());					
					
					String option = Method.getInput("Are you sure you want to " 
							+ delMod + " this contact?",3,"", false);
										
					if (option.equalsIgnoreCase("y"))
					{
						/*
						 * returns one element array containing the 
						 * wanted contact's rank.
						 */ 
						int[] retS = { search } ;
						return retS;
					}
						
					else
						return retF;
					
				}
				
				else {
					int k = 0 ;
					
					// printing all matching contacts in console.
					System.out.println();
					for (int i = a[0]; i <= a[1]; i++)
					{
						System.out.println("Contact " + (k + 1) + " : "
								+ contactList[i].toString());
						k++;
					}
					
					if( delMod.equals("delete") && a[2] > 1)
					{
						//	giving the user 2 options and getting the user's choice.
						Method.printModDelMenu2();
						num = Method.getInputBackOption("Choose from those commands" +
									" (Write its number):", 1, 2);
						System.out.println();
						
						if ( num.equalsIgnoreCase("*back"))
							return retF;
						/*
						 * returns all details about the matching contacts.
						 */
						else if(num.equalsIgnoreCase("1"))
							return a;
					}
					
					// getting user option .
					System.out.println("\nIf you want to go back to menu write \"*back\" .");
					num = Method.getInputBackOption("Please, enter the key of entry " +
							"that you want to "	+ delMod + " :", 1, k);
					System.out.println();
					
					if (num.equalsIgnoreCase("*back"))
						return retF;
					else
					{
						/*
						 * returns one element array containing the 
						 * wanted contact's rank.
						 */ 
						int[] retS = {Integer.parseInt(num) + a[0] - 1} ;
						return retS;
					}
						
				}
					
			}
		}
	}	

	/**
	 * 
	 * Search for a group of contacts that have something in common in an arrray of contacts.
	 * 
	 * @param wanted The last name to be searched for.
	 * @param contactList The list to be searched in.
	 * @param search the result of binary search on this last name or last&first.
	 * @param lastName true if it is used to search on just last name.
	 * @return array of 3 integers first element is rank of first contact with that last name,
	 * second element is rank of last contact, third element is number of contacts with this last name.
	 * 
	 */
	private int[] search(String wanted ,Contact[] contactList 
			,int search ,boolean lastName){
		
		int number ;
		int from = -1 ;
		int to = -1 ;
		boolean flag = true;		
		
		if(lastName)
		{
			// Sequential search for this last name before the caught contact by the binary search.
			for ( int i = search -1 ; i>=0 && flag ; i --)
				if (contactList[i].getLastName().equalsIgnoreCase(wanted))					
					from = i;						
				else
					flag=false;				

			// Sequential search for this last name after the caught contact by the binary search.
			flag=true;
			for ( int i = search +1 ; i<contactList.length && flag ; i ++)
				if (contactList[i].getLastName().equalsIgnoreCase(wanted))						
					to = i;
				else
					flag=false;
		}
		
		else
		{
			// Sequential search for last and first name before the caught contact by the binary search.
			for ( int i = search -1 ; i>=0 && flag ; i --)
				if ((contactList[i].getLastName()+ (char) 0 +
						contactList[i].getFirstName()).equalsIgnoreCase(wanted))					
					from = i;						
				else
					flag=false;				

			// Sequential search for last and first name before the caught contact by the binary search.
			flag=true;
			for ( int i = search +1 ; i<contactList.length && flag ; i ++)
				if ((contactList[i].getLastName()+ (char) 0 +
						contactList[i].getFirstName()).equalsIgnoreCase(wanted))						
					to = i;
				else
					flag=false;
		}
		
		// adjusting first and last matching elements.
		if(to==-1&&from==-1)		
			number = 1;
		
		else if(to==-1)
		{
			to=search;
			number = to-from+1;
			
		}					
		
		else if(from==-1)
		{
			from = search;
			number = to-from+1;
		}				
		else
			number = to-from+1;	
		
		int[] result= { from, to, number};
		
		return result;
	}
}
