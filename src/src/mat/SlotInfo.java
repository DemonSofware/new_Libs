package mat;

import java.io.Serializable;

public class SlotInfo implements Serializable{
	private static final long serialVersionUID = 1L;//must be changed when changing data fields

	private String nameGuest;
	private String surnameGuest;
	private String emailGuest;
	private String phoneGuest;
	private String noteGuest;

	public SlotInfo(String nameGuest, String surnameGuest,
			String emailGuest, String phoneGuest, String noteGuest) {
		this.nameGuest = nameGuest;
		this.surnameGuest = surnameGuest;
		this.emailGuest = emailGuest;
		this.phoneGuest = phoneGuest;
		this.noteGuest = noteGuest;
	}

	public String getNameGuest() {
		return nameGuest;
	}

	public void setNameGuest(String nameGuest) {
		this.nameGuest = nameGuest;
	}

	public String getSurnameGuest() {
		return surnameGuest;
	}

	public void setSurnameGuest(String surnameGuest) {
		this.surnameGuest = surnameGuest;
	}

	public String getEmailGuest() {
		return emailGuest;
	}

	public void setEmailGuest(String emailGuest) {
		this.emailGuest = emailGuest;
	}

	public String getPhoneGuest() {
		return phoneGuest;
	}

	public void setPhoneGuest(String phoneGuest) {
		this.phoneGuest = phoneGuest;
	}

	public String getNoteGuest() {
		return noteGuest;
	}

	public void setNoteGuest(String noteGuest) {
		this.noteGuest = noteGuest;
	}

	
}
