package com.sneaksphere.model;

/**
 * UserModel represents the user entity for SneakSphere's user system.
 */
public class UserModel {
	private int userId; // DB reference
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private String address;
	private String role;
	private int totalPurchases;
	private float totalSpent;
	private String userImageURL;
	// Empty constructor
	public UserModel() {}

	
	public UserModel(String firstName, String lastName, String email,
            String phone, String password, String address, String role, String userImageURL) {
this.firstName = firstName;
this.lastName = lastName;
this.email = email;
this.phone = phone;
this.password = password;
this.address = address;
this.role = role;
this.userImageURL = userImageURL;
}

	// Constructor for login 
	public UserModel(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	
	public UserModel(String firstName, String lastName, String email,
            String phone, String password, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
}

	// Getters and Setters
	public int getUserID() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
	// Getters and Setters
	public int getTotalPurchases() {
	    return totalPurchases;
	}
	public void setTotalPurchases(int totalPurchases) {
	    this.totalPurchases = totalPurchases;
	}

	public float getTotalSpent() {
	    return totalSpent;
	}
	public void setTotalSpent(float totalSpent) {
	    this.totalSpent = totalSpent;
	}
	
	// Getter
		public String getUserImageURL() {
		    return userImageURL;
		}

		// Setter
		public void setUserImageURL(String userImageURL) {
		    this.userImageURL = userImageURL;
		}
	
	@Override
    public String toString() {
        return "UserModel [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName +
                ", email=" + email + ", phone=" + phone + ", address=" + address + "]";
	}


}