package com.userprofile.model;

public class UserProfile extends BaseModel{
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String password;
	
	public UserProfile() {}
	public UserProfile(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public UserProfile(String userId, String username, String password, String firstname, String lastname, String email, String phone) {
		this.setId(userId);
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}
	
	public String getUserId() {
		return this.getId();
	}

	public void setUserId(String userId) {
		this.setId(userId);
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@Override
	public String toString() {
		return "userId= " + this.getId() + ", username " + this.username + ", firstname " + this.firstname + ", email " + this.email; 
	}

}
