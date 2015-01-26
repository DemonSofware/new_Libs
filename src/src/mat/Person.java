package mat;

import java.io.Serializable;
import java.util.TimeZone;

@SuppressWarnings("serial")
public class Person implements Serializable{
	 String name;
	 //String userName;
	 String email;//the same as username
	 String password;
//	 int timeZone; //relatively GMT, e.g. value for Israel 2
	 TimeZone timeZone;

	 public Person(String name/*, String[] snNames*/, String email, String password, TimeZone timeZone) {
	  super();
	  this.name = name;
	  this.email = email;
	  this.password = password;
	  this.timeZone = timeZone;
	 }
	 
	 public TimeZone getTimeZone() {return timeZone;}
	 public void setTimeZone(TimeZone timeZone) {this.timeZone = timeZone;}
	 
	 public String getName() {return name;}
	 public void setName(String name) {this.name = name;}
	 
	 public String getEmail() {return email;}
	 public void setEmail(String email) {this.email = email;}
	 
	 public String getPassword() {return password;}
	 public void setPassword(String password) {this.password = password;}
	 
	 
	 // 
	 public String getUserName() {return email;}
}
