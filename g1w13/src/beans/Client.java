// Venelin Koulaxazov
// 1032744
// Client.java
package beans;

import java.io.Serializable;

/**
 * Java Bean for a client in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.3
 */
public class Client implements Serializable {

	private static final long serialVersionUID = -3573315595536305118L;
	private int clientId;
	private String title;
	private String lastName;
	private String firstName;
	private String companyName;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String country;
	private String postalCode;
	private String homePhone;
	private String cellPhone;
	private String email;
	private String password;
	private boolean status;
	private String genreLastSearch;

	/**
	 * Default constructor
	 */
	public Client() {
		title = "";
		lastName = "";
		firstName = "";
		companyName = "";
		address1 = "";
		address2 = "";
		city = "";
		province = "";
		country = "";
		postalCode = "";
		homePhone = "";
		cellPhone = "";
		email = "";
		password = "";
		genreLastSearch = "";
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param title
	 * @param lastName
	 * @param firstName
	 * @param companyName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param province
	 * @param country
	 * @param postalCode
	 * @param homePhone
	 * @param cellPhone
	 * @param email
	 * @param password
	 * @param status
	 * @param genreLastSearch
	 */
	public Client(String title, String lastName, String firstName,
			String companyName, String address1, String address2, String city,
			String province, String country, String postalCode,
			String homePhone, String cellPhone, String email, String password,
			boolean status, String genreLastSearch) {
		super();
		this.title = title;
		this.lastName = lastName;
		this.firstName = firstName;
		this.companyName = companyName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.password = password;
		this.status = status;
		this.genreLastSearch = genreLastSearch;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param clientId
	 * @param title
	 * @param lastName
	 * @param firstName
	 * @param companyName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param province
	 * @param country
	 * @param postalCode
	 * @param homePhone
	 * @param cellPhone
	 * @param email
	 * @param password
	 * @param status
	 * @param genreLastSearch
	 */
	public Client(int clientId, String title, String lastName,
			String firstName, String companyName, String address1,
			String address2, String city, String province, String country,
			String postalCode, String homePhone, String cellPhone,
			String email, String password, boolean status,
			String genreLastSearch) {
		super();
		this.clientId = clientId;
		this.title = title;
		this.lastName = lastName;
		this.firstName = firstName;
		this.companyName = companyName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.password = password;
		this.status = status;
		this.genreLastSearch = genreLastSearch;
	}

	/**
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone
	 *            the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone
	 *            the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the genreLastSearch
	 */
	public String getGenreLastSearch() {
		return genreLastSearch;
	}

	/**
	 * @param genreLastSearch
	 *            the genreLastSearch to set
	 */
	public void setGenreLastSearch(String genreLastSearch) {
		this.genreLastSearch = genreLastSearch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", title=" + title
				+ ", lastName=" + lastName + ", firstName=" + firstName
				+ ", companyName=" + companyName + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", province="
				+ province + ", country=" + country + ", postalCode="
				+ postalCode + ", homePhone=" + homePhone + ", cellPhone="
				+ cellPhone + ", email=" + email + ", password=" + password
				+ ", status=" + status + ", genreLastSearch=" + genreLastSearch
				+ "]";
	}
}
