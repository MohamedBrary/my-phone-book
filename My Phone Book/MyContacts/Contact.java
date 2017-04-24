/**
 * 
 */
package MyContacts;


import java.io.IOException;


/** 
 * <nl><li>This class creates object Contact and contains all methods that deals with this object.<nl></li>
 * <nl><li>Object Contact represents a simple phone contact.<nl></li>
 * <nl><li><b>Object Contact attributes :<nl></li>
 * <nl><li>*Last name of the contact.<nl></li>
 * <nl><li>*First name of the contact.<nl></li>
 * <nl><li>*Address of the contact.<nl></li>
 * <nl><li>*City of the contact.<nl></li>
 * <nl><li>*Phone number of the contact.<nl></li>
 * 
 * @author Thunder Bolt
 *
 */
public class Contact {		
	
	// Instances of object Contact.
	private String lastName;
	private String firstName;
	private String address;
	private String city;
	private String phoneNumber;
		
	
	/**
	 * 
	 */
	public Contact() {
		
	}

	/**
	 * Construct a contact and taking its data from the parameters.
	 * 
	 * @param lastName last Name of contact.
	 * @param firstName first Name of contact.
	 * @param address address of contact.
	 * @param city city of contact.
	 * @param phoneNumber phoneNumber of contact.
	 */
	
	public Contact(String lastName, String firstName, 
			String address, String city, String phoneNumber) {
		
		this.lastName    =  lastName.trim();
		this.firstName   =  firstName.trim();
		this.address     =  address.trim();
		this.city        =  city.trim();
		this.phoneNumber =  phoneNumber.trim();
		
	}
	
	/**
	 * Construct a contact and taking its data from 
	 * an array of strings.
	 * @param a details of contact as an array of strings.
	 * @param encrypted true if the details are encrypted,
	 * false otherwise.
	 */
	public Contact(String[] a , boolean encrypted){
		if (encrypted)
		{
			this.lastName    = 	Method.encrypt(a[0].trim() ,false);
			this.firstName 	 = 	Method.encrypt(a[1].trim() ,false);
			this.address 	 =	Method.encrypt(a[2].trim() ,false);
			this.city 		 = 	Method.encrypt(a[3].trim() ,false);
			this.phoneNumber =  Method.encrypt(a[4].trim() ,false);
		}
		else 
		{
			this.lastName    = 	a[0].trim();
			this.firstName 	 = 	a[1].trim();
			
			this.address 	 =	a[2].trim();
			if(address.length()==0)
				this.address = "??";
			
			this.city 		 = 	a[3].trim();
			if(city.length()==0)
				this.city = "??";
			
			this.phoneNumber =  a[4].trim();
		}
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	----------------------------------------- Getters and Setters ----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	

	//	------------------------------------------------------------------------------------------------------  \\
	//	----------------------------------------  ** String Methods **   -------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\
	
	/**
	 * Transform the contact to this form :
	 * Last name, First name, Address, City, Phone number
	 *  
	 * @return The contact as a string every attribute seperated by "," .
	 * 
	 */
	
	public String toString() {
		
		String line = this.getLastName()+","
			+this.getFirstName()+","
			+this.getAddress()+","
			+this.getCity()+","
			+this.getPhoneNumber();
		
		return line;
	}
	
	/**
	 * Transform the contact to a special form to be printed.
	 * 
	 * @return The contact as a string organised in a good way .
	 * 
	 */
	public String toFancyString(){
		
		String line = "Last name : " + this.getLastName()
			+ "\tFirst name : " + this.getFirstName()
			+ "\tPhone number : " + this.getPhoneNumber()
			+ "\n\t\t  Address : " + this.getAddress()
			+ "\t City : " + this.getCity() ;
		
		return line ;
	}
	

	/**
	 * Transform the contact to a special form to be printed.
	 * 
	 * @return The contact as a string organised in a good way .
	 * 
	 */
	public String toBigFancyString(){
		
		String line = 
			    "\tLast name    : " + this.getLastName()     + "\t"
			+ "\n\tFirst name   : " + this.getFirstName()    + "\t"
			+ "\n\tPhone number : " + this.getPhoneNumber()  + "\t"
			+ "\n\tAddress      : " + this.getAddress()      + "\t"
			+ "\n\tCity         : " + this.getCity()         + "\t" ;
		
		return line ;
	}
	
	/**
	 * Encrypts the contact data to keep its privacy when 
	 * written in an external *.txt file.
	 * 
	 * @return The contact as an encrypted string every attribute seperated by "," .
	 * @see  Method encrypt(String, boolean)	
	 */
	
	public String toEncryptedString() {
		
		// puts the optional fields as "??" if they were blanks.
		if(this.getAddress().length()==0)
			this.setAddress("??");
		
		if(this.getCity().length()==0)
			this.setCity("??");		
		
		// encrypt contact info.
		String line = Method.encrypt(this.getLastName(), true)+","
			+Method.encrypt(this.getFirstName(),true)+","
			+Method.encrypt(this.getAddress(),true)+","
			+Method.encrypt(this.getCity(),true)+","
			+Method.encrypt(this.getPhoneNumber(),true);
		
		return line;
	}
		
	/**
	 * it is used when sorting the contact list by last name then first name 
	 * then phone number.
	 * @return The last name,first name and phone number of the contact as a string .
	 * 
	 */
	
	public String toSortString() {
		
		// to seperate between each attribute.
		String separator = (char) 0 + ""; 
		
		String line = this.getLastName() + separator
			+this.getFirstName() + separator			
			+this.getPhoneNumber();
		
		return line;
	}
	
	//	------------------------------------------------------------------------------------------------------  \\
	//	-------------------------------------------  ** Methods **   -----------------------------------------  \\
	//	------------------------------------------------------------------------------------------------------  \\	
	
	/**
	 * Prompt the user to fill the contact details.
	 * @return Contact 
	 * @throws IOException
	 */
	public Contact getContactData(boolean modify) {		
		
		/*
		 * gettin the first name and giving the user the chance to go back
		 * in adding new contacts.
		 */ 
		String input = Method.getInput("\tLast name : ",1,"", false);
		this.setLastName(input);
		if(!modify)
			if ( input.equalsIgnoreCase("*back"))
				return this;
				
		
		// getting first name.	
		this.setFirstName(Method.getInput("\tFirst name : ",1,"", false));
		
		// gettin the address and city setting them as "??" if they were blanks .
		this.setAddress(Method.getInput("\t*Address : ", 5, "", false).trim()) ;		
		this.setCity(Method.getInput("\t*City : ", 5, "", false).trim());	
		
		// getting the phone number.		
		this.setPhoneNumber(Method.getInput("\tPhone Number : ",2,"", true));	
		
		return this;
	}
	

	/**
	 * Takes a contact list and puts in it the contact placed in its sorted place.
	 * @param a The contact list to insert the contact in it.
	 * @param limitOfInsert limit of range to insert the contact in.
	 * @return The contact list after inserting the contact.
	 */
	public Contact[] insertContact(Contact[] a, int limitOfInsert){
		
		// do binary search on the contact list and getting its place as it was there.
		int rank = Method.search(this.toSortString() , 1 , a ,limitOfInsert)*-1;		
		
		// that means that this contact is duplicated.
		if (rank <= 0)
			return a;
		
		else if (rank == 99999)
			rank = 0 ;		
		
		// shifting the array of contacts.
		for (int i = (a.length)-1 ; i>=rank+1 ; i-- )
			a[i]=a[i-1];
		
		a[rank] = this ;
		return a;
	}
}
