package client.app.logic;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import client.server.connection.*;

/**
 * 
 * @author Martin
 *
 *The Email class is currently developed for use only on sending 
 *emails to registered clients (clients who have registered an 
 *email address with the restaurant). The main function is to send 
 *out special offers to these clients, using the Special class. The 
 *manager has the options of choosing the header of the email and 
 *some text inside the email on the user interface. To send an email, 
 *the user (manager) must input the restaurant email address, the 
 *restaurant email password, and a header and text if they wish to.
 */

public class Email{
	
	private static ServerClient scli = new ServerClient();
	private static StringBuffer currResponse = new StringBuffer();
	private static Account accounts = new Account();
	private static List<String> emails = new ArrayList<>();
	private static List<MenuItem> items = new ArrayList<>();
	private static List<Special> specialOffers = new ArrayList<>();
	static Scanner secCheck = new Scanner(System.in); //create scanner
	public static String email="";
	public static String password="";
	public static String emailContent="";
	static String confirm = "";
	static String header = "";
	
	/**
	 * Constructor
	 */
	public Email(String email, String password) {
		this.email = email;
		this.password = password;
	}
	/**
	 * Constructor
	 * @param email
	 * @param password
	 * @param message
	 */
	public Email(String email, String password, String header, String emailContent) {
		this.email = email;
		this.password = password;
		this.emailContent = emailContent;
		this.header = header;
	}
	/**
	 * calls send email method
	 * @param args
	 * @throws AddressException
	 */
	/*public static void main(String[] args) throws AddressException {
		sendEmail();
	}*/
	
	/**
	 * sends email to addresses listed
	 * @throws AddressException
	 */
	public static void sendEmail() throws AddressException {
		String senderEmail = "tpgroup10@gmail.com"; //Email of restaurant.
		List<String> adEmails = new ArrayList<String>(); //creates arraylist for emails
		String subject = header; //email subject
		String text = emailContent + "// " + getText(); //Text of menu items with special offers.
		InternetAddress address = new InternetAddress("fahy_martin@yahoo.co.uk"); //example email
		String username = email;
		String password1 = password;


		Properties props = new Properties(); //Creates new properties
		props.put("mail.smtp.auth", "true"); //Sets authentication to be true
	    props.put("mail.smtp.starttls.enable", "true"); //Safe for sending mail set to true
	    props.put("mail.smtp.host", "smtp.gmail.com"); //Sets host
	    props.put("mail.smtp.port", "587"); //Sets port number
	    props.put("mail.transport.protocol", "smtps");
	    props.put("mail.password", "carlosmatostp"); //Sets password for account

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() { //Creates new session
			@Override
			protected PasswordAuthentication getPasswordAuthentication() { //authenticates password for email account
					return new PasswordAuthentication(username, password1);
			}
		}); //Gets default session object.
		
		try {
			MimeMessage message = new MimeMessage(session); //Creates a MimeMessage object using session.
			message.setFrom(new InternetAddress(senderEmail)); //Setting where the email says it is from.
			adEmails = getRegisteredEmails(); //gets list of emails
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getAddresses(adEmails))); //sets emails sent to

			message.setSubject(subject); //Set email subject.
			message.setText(text); //Set contents of email.
			
			Transport.send(message); //sends email
			System.out.println("Message ready to send.");
			System.out.println("Message sending.");
			System.out.println("Message sending..");
			System.out.println("Message sending...");
			System.out.println("Message sent!");
		} catch(MessagingException e) {
			System.out.println("Messaging exception caught.");
			e.printStackTrace();
		}
	}
	
	/*public String checkEmail(String email) {
		System.out.println("Email: ");
		String username = secCheck.nextLine(); //enter username
		return email;
	}
	
	public String checkPassword(String password) {
		System.out.println("Password: ");
		String password = secCheck.nextLine(); //enter password for authentication
		return password;
	}*/
	
	/**
	 * 
	 * @return registered emails of customers to be sent special offers
	 */
	public static List<String> getRegisteredEmails() {
		Accounts accounts = new Accounts();
		emails = new ArrayList<>(); //create arraylist for emails
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/accounts");
		accounts = (Accounts) scli.convertXML(currResponse, Accounts.class);
		for(Account acc : accounts.getItems()) {
		  //System.out.println(acc.getEmail());
			if(acc.getAccountType() == 0) {
				emails.add(acc.getEmail()); //add client emails to arraylist
			} else {
				//System.out.println("Guest "+ acc.getName() +" has no Email");
				//return null;
			}
		}
		return emails; //return emails arraylist
	}
	
	/**
	 * 
	 * @param emailAddresses
	 * @return registered emails of customers to be sent special offers
	 * @throws AddressException
	 */
	public static String getAddresses(List<String> emailAddresses) throws AddressException {
		InternetAddress[] to = new InternetAddress[emailAddresses.size()];
		StringBuffer stringemails = new StringBuffer();
		for(int i = 0; i < emailAddresses.size(); i++) {
		  try{
		    //InternetAddress newAdd = new InternetAddress(emailAddresses.get(i));
			if(!emailAddresses.get(i).contains("@")) {} else {
				to[i] = new InternetAddress(emailAddresses.get(i).trim());
			    stringemails.append(emailAddresses.get(i).trim());
			    stringemails.append(',');
			}
		  }catch(AddressException e) {
		    System.out.println("Email "+ emailAddresses.get(i) +" could not be found.. skipping.");
		  }
		  
		}
		
		return stringemails.toString();
	}
	
	/**
	 * 
	 * @return text that will be included in email, special offers
	 */
	public static String getText() {
		List<MenuItem> specialOffers = new ArrayList<MenuItem>();
		specialOffers = Special.listSpecialOffers();
		String[] nametext = new String[specialOffers.size()];
		double[] pricetext = new double[specialOffers.size()];
		StringBuffer stringtext = new StringBuffer(); //creating a sentence of special offers to return
		if(specialOffers.size() > 0) {
			for(int i = 0; i < specialOffers.size(); i++) {
			  try{
			    //InternetAddress newAdd = new InternetAddress(emailAddresses.get(i));
			    nametext[i] = specialOffers.get(i).getName();
			    pricetext[i] = specialOffers.get(i).getPrice();
			    stringtext.append(nametext[i]);
			    stringtext.append(" �");
			    stringtext.append(pricetext[i]);
			    stringtext.append(", ");
			  }catch(Exception e) {
			    System.out.println("Item "+ specialOffers.get(i).getName() +" could not be found.. skipping.");
			  }
			  
			}
		} else {
			System.out.println("No menu items have special offers.");
		}
		stringtext.append(".");
		return stringtext.toString();
	}
	
	/**
	 * 
	 * @return custom text entered by user to be sent to customers
	 */
	/*public static String getCustomText() {
		Scanner scan1 = new Scanner(System.in);
		System.out.println("Enter contents of proposed email: (Followed by enter) ");
		
		String contents = scan1.nextLine();
		scan1.close();
		return contents;
	}*/
	
	public static String confirmEmail() {
		confirm = "Email sent.";
		return confirm;
	}
}