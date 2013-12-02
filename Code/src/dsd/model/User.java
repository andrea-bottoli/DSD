package dsd.model;

import dsd.calculations.CryptFunktions;
import dsd.model.enums.eUserRole;

public class User {

	private String username;
	private String surename;
	private String lastname;
	private String email;
	private String passwd;
	private eUserRole role;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSurename() {
		return surename;
	}

	public void setSurename(String surename) {
		this.surename = surename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = CryptFunktions.MD5(passwd);
	}

	public eUserRole getRole() {
		return role;
	}

	public void setRole(eUserRole role) {
		this.role = role;
	}

}
