package mat;

import java.io.Serializable;
import java.util.HashMap;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	String groupName;
	HashMap<String, Contact> groupContacts;
	
	public Group() {}
	
	public Group(String groupName) {
		this.groupName = groupName;
		this.groupContacts = new HashMap<String, Contact>();
	}

	public Group(String groupName, HashMap<String, Contact> groupContacts) {
		this.groupName = groupName;
		this.groupContacts = groupContacts;
	}

	public boolean addContact(Contact cont){
		if(cont==null || cont.getContactName()==null) return false;
		if(!groupContacts.containsKey(cont.getContactName()))
			groupContacts.put(cont.getContactName(), cont);
		else{
			if(groupContacts.get(cont.getContactName()).equals(cont)) return false;
			int i;
			for(i=1;groupContacts.containsKey(cont.getContactName()+i);i++){
				if(groupContacts.get(cont.getContactName()+i).equals(cont)) return false;
			}
			groupContacts.put(cont.getContactName()+i, cont);
		}
		return true;
	}
	
	public Contact removeContact(String key){
		return key!=null ? groupContacts.remove(key): null;
	}
	
	public boolean clearGroup(){
		if(groupContacts==null) return false;
		groupContacts.clear();
		return true;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
		if(this.groupContacts==null) this.groupContacts = new HashMap<String, Contact>();
	}

	public HashMap<String, Contact> getGroupContacts() {
		return groupContacts;
	}

	public void setGroupContacts(HashMap<String, Contact> groupContacts) {
		this.groupContacts = groupContacts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
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
		Group other = (Group) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}
	
	
}
