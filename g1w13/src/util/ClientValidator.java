// Venelin Koulaxazov
// 1032744
// ClientValidator.java
package util;

import beans.Client;

/**
 * Validates whether the fields of a Client bean are valid
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class ClientValidator {

	/**
	 * Validates whether the fields of a Client bean are correct
	 * 
	 * @param client
	 * @return a message if some of the fields are incorrect
	 */
	public String validateClient(Client client, String password2) {
		String error = "";

		error = validateFields(client);
		if (!error.equals(""))
			return error;
		if (!validatePhoneNumber(client.getHomePhone())) {
			error = "Invalid home phone";
			return error;
		}
		if (!validatePostalCode(client.getPostalCode())) {
			error = "Invalid postal code";
			return error;
		}
		if (!validatePasswords(client.getPassword(), password2)) {
			error = "Passwords do not match";
			return error;
		}
		return error;
	}

	/**
	 * Validates if the email is correct using a regular expression
	 * 
	 * @param email
	 * @return whether or not the email is valid
	 */
	public boolean validateEmail(String email) {
		boolean valid = false;

		if (email != null)
			if (email
					.matches("^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$"))
				valid = true;
		return valid;
	}

	/*
	 * Checks if some of the fields are null or above the required length
	 * 
	 * @param client
	 * 
	 * @return a message with which field is invalid
	 */
	public String validateFields(Client client) {
		if (client.getTitle() == null || client.getTitle().length() > 4)
			return "Invalid title";
		if (client.getLastName() == null || client.getLastName().length() > 100)
			return "Invalid last name";
		if (client.getFirstName() == null
				|| client.getFirstName().length() > 100)
			return "Invalid first name";
		if (client.getCompanyName() != null)
			if (client.getCompanyName().length() > 100)
				return "Invalid company name";
		if (client.getAddress1() == null || client.getAddress1().length() > 300)
			return "Invalid primary address";
		if (client.getAddress2() != null)
			if (client.getAddress2().length() > 300)
				return "Invalid secondary address";
		if (client.getCity() == null || client.getCity().length() > 100)
			return "Invalid city";
		if (client.getProvince() == null || client.getProvince().length() > 45)
			return "Invalid province";
		if (client.getCountry() == null || client.getCountry().length() > 45)
			return "Invalid country";
		if (client.getPostalCode() == null
				|| client.getPostalCode().length() > 6)
			return "Invalid postal code";
		if (client.getHomePhone() == null
				|| client.getHomePhone().length() > 13)
			return "Invalid home phone";
		if (client.getCellPhone() != null)
			if (client.getCellPhone().length() > 13)
				return "Invalid cell phone";
		if (client.getEmail() == null || client.getEmail().length() > 200)
			return "Invalid email";
		if (client.getPassword() == null || client.getPassword().length() > 50)
			return "Invalid password";
		if (client.getGenreLastSearch() != null)
			if (client.getGenreLastSearch().length() > 45)
				return "Invalid genre";
		return "";
	}

	/*
	 * Validates if a phone number is correct using a regular expression
	 * 
	 * @param phoneNumber
	 * 
	 * @return whether or not the phone number is valid
	 */
	private boolean validatePhoneNumber(String phoneNumber) {
		boolean valid = false;

		if (phoneNumber != null)
			if (phoneNumber.matches("^\\((\\d{3})\\)(\\d{3})[-](\\d{4})$"))
				valid = true;
		return valid;
	}

	/*
	 * Validates if a postal code is correct using a regular expression
	 * 
	 * @param postalCode
	 * 
	 * @return whether or not the postal code is valid
	 */
	private boolean validatePostalCode(String postalCode) {
		boolean valid = false;

		if (postalCode != null)
			if (postalCode.matches("^(\\D\\d\\D)(\\d\\D\\d)$"))
				valid = true;
		return valid;
	}

	/*
	 * Compares if two passwords are the same
	 * 
	 * @param password1
	 * 
	 * @param password2
	 * 
	 * @return whether the passwords are identical
	 */
	private boolean validatePasswords(String password1, String password2) {
		boolean identical = false;

		if (password1.equals(password2))
			identical = true;

		return identical;
	}
}
