package mat;

import java.io.Serializable;
import java.util.HashMap;

public class Person implements Serializable{
	
	String name; //name of user
	String email;//the same as userName
	String password;
	int timeZone; //relatively GMT, e.g. value for Israel 2
	HashMap<String, Group> groups;

	public Person(String name, String email, String password, int timeZone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.timeZone = timeZone;
		this.groups = new HashMap<String, Group>();
		groups.put("Default", new Group("Default"));
	}
	 
	public Person(String name, String email, String password, int timeZone, HashMap<String, Group> groups) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.timeZone = timeZone;
		this.groups = groups;
	}
	 
	public int getTimeZone() {return timeZone;}
	public void setTimeZone(int timeZone) {this.timeZone = timeZone;}
	 
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	 
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	 
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	 
	public HashMap<String, Group> getGroups() {return groups;}
	public void setGroups(HashMap<String, Group> groups) {this.groups = groups;}
	
	//email as UserName
	public String getUserName() {return email;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + timeZone;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (timeZone != other.timeZone)
			return false;
		return true;
	}
	
	
}
