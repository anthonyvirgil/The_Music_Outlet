// Anthony-Virgil Bermejo
// 0831360
// ClientReport.java
package beans;

import java.io.Serializable;

/**
 * Java bean that represents a detailed report about clients
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class ClientReport implements Serializable {

	private static final long serialVersionUID = 4132182437759685567L;
	private int clientId;
	private String firstName;
	private String lastName;
	private String email;
	private double totalSales;

	/**
	 * No param constructor, setting all instance variables to default values
	 */
	public ClientReport() {
		clientId = 0;
		firstName = "";
		lastName = "";
		email = "";
		totalSales = 0.0;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param clientId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param totalSales
	 */
	public ClientReport(int clientId, String firstName, String lastName,
			String email, double totalSales) {
		super();
		this.clientId = clientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.totalSales = totalSales;
	}

	/**
	 * Returns the client id
	 * 
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * Sets the client id
	 * 
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * Returns client's first name
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets client's first name
	 * 
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns client's last name
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets client's last name
	 * 
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns client's email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets client's email
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns client's total sales
	 * 
	 * @return the totalSales
	 */
	public double getTotalSales() {
		return totalSales;
	}

	/**
	 * Sets client's total sales
	 * 
	 * @param totalSales
	 *            the totalSales to set
	 */
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClientReport [clientId=" + clientId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", totalSales=" + totalSales + "]";
	}

}
