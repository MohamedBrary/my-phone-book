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
 * <nl><li>This class creates object User and contains all methods that deals with this object.<nl></li>
 * <nl><li>Object User represents a group of groups of contacts<nl></li> 
 * <nl><li>belongs to one person.<nl></li>
 * <nl><li><b>Object User attributes :<nl></li>
 * <nl><li>*User name : the user name which these groups of contacts belongs to.<nl></li>
 * <nl><li>*Pass word : user's password to keep his privacy.<nl></li>
 * <nl><li>*Path : path of the folder that contains the user groups.<nl></li>
 * 
 * @author Thunder Bolt
 *
 */
public class User {
	
	// Creats the folder that keeps all the files .
	private static String fullPath = "C:\\My PhoneBook\\";
	private static File dir = new File(fullPath);
	static 
	{
		dir.mkdir();
	}	
	private static String[] paths = dir.list();
	
	// Instances of User.
	private String name;
	private String path;
	private File folder;
	
	/**
	 * 
	 */
	public User() {
		
	}
	
	/**
	 * Creates a folder named after user name 
	 * and creates a file txt named "Password" where the
	 * pass word of user will be written in certain code.
	 * @param name User name.
	 * @param password User's password.	 
	 */
	public User(String name, String password) {
		this.path="C:\\My PhoneBook\\"+name+"\\";
		this.name = name;				
		PrintWriter file = null;
		try 
		{
			// creating folders.
			this.folder = new File(this.path);			
			folder.mkdirs();
			file = new PrintWriter(new FileWriter(path+"\\Password.txt"));
			file.println(Method.encode(password));
		}
		catch (Exception e) 
		{
			System.out.print("Error: in constructing a user.");
		}
		finally 
		{			
			if(file != null)
				file.close();
		}
		
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	----------------------------------------- Getters and Setters ----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param folder the folder to set
	 */
	public void setFolder(File folder) {
		this.folder = folder;
	}
	/**
	 * @return the folder
	 */
	public File getFolder() {
		return folder;
	}
	
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	-------------------------------------------  ** Methods **   -----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Creates new user.
	 * 
	 * @return the new user
	 * @see #getNewUserData()
	 *
	 */
	public User newUser(){
		User user = new User();
		
		try 
		{			
			user = user.getNewUserData();
		} 
		
		catch (Exception e) 
		{
			System.out.print("Error: creating new user.");
			user.setName("?");	
			return user;
		}
		
		System.out.print("The user \"" + user.getName() 
				+ "\" has been created ");
		Method.loadDelay(4,true);
		
		return user;
	}
	
	/**
	 * Attemp the user for his info and load his profile.
	 * 
	 * @see #getLoadUserData()
	 * 
	 */
	public void loadUser(){		
			
		
		try 
		{
			getLoadUserData();
		} 
		
		catch ( Exception e) 
		{
			System.out.print("Error: loading user.");
			setName("?");
			Method.loadDelay(4,true);
			return ;
		}
		
		Method.loadDelay(4,true);
		return ;
	}
	//	--------------------------------------------------------------------------------------------------  \\
	//	------------------------------------- Input for user options -------------------------------------  \\
	//	--------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Prints all the users created before.
	 *
	 */
	public void getAllUsers() {
		
		// makes sure if there is no user.
		if (paths == null || paths.length == 0)
			System.out.println("There isn't any user .");
		
		// prints all users on console.
		else {
			System.out.println("There are " + paths.length + " Users:");
			for (int i = 0; i < paths.length; i++)
				System.out.println("\tUser " + (i + 1) + " :"+ paths[i]);
		}
	}
	
	/**
	 * Prints all the groups created by the User.
	 *
	 */
	public void getAllGroups() {
		
		String[] paths = this.getFolder().list();		
		
		// makes sure if the user is new or have no groups.
		if (paths == null || paths.length == 1)
			System.out.println("Sorry, you don't have any group .");
		
		else 
		{
			// getting all files in the user folder.
			System.out.println("There are " + (paths.length-1) + " groups:");
			
			for (int i = 0; i < paths.length; i++)
				
				/*
				 * remove the file that contain the password from
				 * the list of groups.
				 */ 
				if(!paths[i].equalsIgnoreCase("password.txt"))
					System.out.println("\tGroup " + (i + 1) + " :"
							+ paths[i].substring(0, paths[i].indexOf(".")));					
				else
				{
					paths=Method.deleteRow(paths, i);
					i--;
				}						
		}
	}
	
	/**
	 * Prompts the user for his name and password
	 * and checks if they are right, then loads it.
	 * 
	 * @throws IOException
	 */
	private void getLoadUserData() throws IOException{
		
		// prints all users created on the console.
		getAllUsers();		 		
		
		if(paths.length==0)
		{
			this.setName("?");
			return ;
		}
		
		// Prompting the user to choose one user to load it.
		System.out.println("If you want to go back to menu write \"*back\" .");
		String s = Method.getInputBackOption("Please, write the number of user:",
				1, paths.length);		
		
		if(s.equalsIgnoreCase("*back"))
		{
			this.setName("?");
			return ;
		}
		
		// getting the name of folder presenting the user.
		s = paths[(Integer.parseInt(s))-1];				
		String path = fullPath + s+"\\" ;
		
		boolean bassPass = this.getUserPassWord(path);

		if ( !bassPass ) 
		{
			this.setName("?");
			return ;
		}		
		
		System.out.print("The user \""+s+"\" has been loaded ");
		
		// setting the new data of user .
		this.setName(s);
		this.setPath(path);		
		this.setFolder(new File(path));
		
		return ;
	}
	
	/**
	 * Prompts the new user for his name and password
	 * and creates new profile. 
	 * @return User after setting its name and password.
	 * @throws IOException
	 */
	private User getNewUserData() throws IOException{
		
		// Prompting user for the name.
		String userName = "";		
		boolean flag;
		do {
			flag = true;
			userName = Method.getInput("Please, enter the " +
					"name of the user :", 4, "user name", false);
			
			// make sure that the user name isn't duplicated.
			if (paths != null) {	        
				for (int i = 0; i < paths.length; i++) 
					if (paths[i].equalsIgnoreCase(userName)) {
						System.out.print("This user is already exsited .");
						flag=false;
					}
			}
		} while (!flag);		
		
		// Prompting the user for the password.
		String pass = Method.getInput("Please, enter password for your account : ", 1,"", false);
		
		// Creating the new user and putting its data.
		User newOne = new User(userName, pass);
		return newOne;
	}
	
	/**
	 * Prompting the user for the password and compares it
	 * to the saved one.
	 * 
	 * @param pathOfUser path of the folder representing the user.
	 * @return true if the password match ,false otherwise .
	 */
	private boolean getUserPassWord( String pathOfUser ){
		
		String pass;		
		boolean loop;
		BufferedReader b1;
		try {
			 
					
			// reading the saved password from the file.
			b1 = new BufferedReader(new FileReader( pathOfUser + "Password.txt"));
			String passCoded= b1.readLine();
			b1.close();
			
			System.out.println("\nIf you dont know the password write \"*back\" .");			
			do{
				
				// read the password of this user.
				pass = Method.getInput("Password : ", 1, "", false);
				
				// comparing it with the saved one.
				loop = (! pass.equalsIgnoreCase("*back")
						&&(! (Method.encode(pass)).equalsIgnoreCase(passCoded)) );
				if(loop)
					System.out.println("Invalid password try again .");
				
			}while(loop);
			
			if ( pass.equalsIgnoreCase("*back") )			
				return false;			
			
			else
				return true;
		} 
		
		catch (Exception e) 
		{
			System.out.print("Error: there is no password.");
			return false;
		}	
		
	}
	
}
