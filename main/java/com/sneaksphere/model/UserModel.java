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
	
	/**
	 * Empty constructor.
	 */
	public UserModel() {}

	/**
	 * Constructor to create a full UserModel object with all details.
	 * 
	 * @param firstName       user's first name
	 * @param lastName        user's last name
	 * @param email           user's email address
	 * @param phone           user's phone number
	 * @param password        user's password
	 * @param address         user's address
	 * @param role            user's role (e.g., admin, customer)
	 * @param totalPurchases  total number of purchases made by the user
	 * @param totalSpent      total amount spent by the user
	 * @param userImageURL    URL of the user's profile image
	 */
	public UserModel(String firstName, String lastName, String email,
	            String phone, String password, String address, String role,
	            int totalPurchases, float totalSpent, String userImageURL) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
		this.role = role;
		this.totalPurchases = totalPurchases;
		this.totalSpent = totalSpent;
		this.userImageURL = userImageURL;
	}
	
	/**
	 * Constructor for user signup with necessary user details.
	 * 
	 * @param firstName     user's first name
	 * @param lastName      user's last name
	 * @param email         user's email address
	 * @param phone         user's phone number
	 * @param password      user's password
	 * @param address       user's address
	 * @param userImageURL  URL of the user's profile image
	 */
	public UserModel(String firstName, String lastName, String email,
            String phone, String password, String address, String userImageURL) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
		this.userImageURL = userImageURL;
	}
	
	/**
	 * Constructor for user login with email and password.
	 * 
	 * @param email     user's email address
	 * @param password  user's password
	 */
	public UserModel(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/**
	 * Gets the user's database ID.
	 * 
	 * @return userId the unique user ID from the database
	 */
	public int getUserID() {
		return userId;
	}

	/**
	 * Sets the user's database ID.
	 * 
	 * @param userId the unique user ID to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user's first name.
	 * 
	 * @return firstName user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the user's first name.
	 * 
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the user's last name.
	 * 
	 * @return lastName user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the user's last name.
	 * 
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the user's email.
	 * 
	 * @return email user's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email.
	 * 
	 * @param email the email address to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user's phone number.
	 * 
	 * @return phone user's phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the user's phone number.
	 * 
	 * @param phone the phone number to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the user's password.
	 * 
	 * @return password user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user's address.
	 * 
	 * @return address user's address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the user's address.
	 * 
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the user's role.
	 * 
	 * @return role user's role in the system
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the user's role.
	 * 
	 * @param role the role to set for the user
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Gets the URL of the user's profile image.
	 * 
	 * @return userImageURL the profile image URL
	 */
	public String getUserImageURL() {
	    return userImageURL;
	}
	
	/**
	 * Sets the URL of the user's profile image.
	 * 
	 * @param userImageURL the profile image URL to set
	 */
	public void setUserImageURL(String userImageURL) {
	    this.userImageURL = userImageURL;
	}

	/**
	 * Gets the total number of purchases made by the user.
	 * 
	 * @return totalPurchases the total purchases count
	 */
	public int getTotalPurchases() {
	    return totalPurchases;
	}

	/**
	 * Sets the total number of purchases made by the user.
	 * 
	 * @param totalPurchases the purchases count to set
	 */
	public void setTotalPurchases(int totalPurchases) {
	    this.totalPurchases = totalPurchases;
	}

	/**
	 * Gets the total amount spent by the user.
	 * 
	 * @return totalSpent the total spent amount
	 */
	public float getTotalSpent() {
	    return totalSpent;
	}

	/**
	 * Sets the total amount spent by the user.
	 * 
	 * @param totalSpent the total amount spent to set
	 */
	public void setTotalSpent(float totalSpent) {
	    this.totalSpent = totalSpent;
	}

	/**
	 * Returns a string representation of the user object.
	 * 
	 * @return string describing the user
	 */
	@Override
    public String toString() {
        return "UserModel [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName +
                ", email=" + email + ", phone=" + phone + ", address=" + address + "]";
	}

}
