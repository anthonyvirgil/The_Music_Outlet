// Venelin Koulaxazov
// 1032744
// CreditCardValidator.java
package util;

import beans.CreditCard;

/**
 * Validates whether the fields of a CreditCard bean are valid
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class CreditCardValidator {

	/**
	 * Validates whether the fields of a CreditCard bean are correct
	 * 
	 * @param creditCard
	 * @return
	 */
	public boolean validateCreditCard(CreditCard creditCard) {

		if (!validateFields(creditCard))
			return false;
		if (!validateCreditCardNumber(creditCard.getCreditCardNum()))
			return false;
		if (!validateExpirationMonthYear(creditCard.getExpirationMonth(),
				creditCard.getExpirationYear()))
			return false;
		if (!validateCsc(creditCard.getCardSecurityCode()))
			return false;
		return true;

	}

	/*
	 * Checks if some of the fields are null or above the required length
	 * 
	 * @param creditCard
	 */
	private boolean validateFields(CreditCard creditCard) {
		if (creditCard.getCardHolder() == null
				|| creditCard.getCardHolder().length() > 100)
			return false;
		if (creditCard.getCreditCardNum() == null)
			return false;
		if (creditCard.getExpirationMonth() == null)
			return false;
		if (creditCard.getExpirationYear() == null)
			return false;
		if (creditCard.getCardSecurityCode() == null)
			return false;
		return true;
	}

	/*
	 * Validates if a credit card number is correct using a regular expression
	 * 
	 * @param creditCardNumber
	 * 
	 * @return whether or not the credit card number is valid
	 */
	private boolean validateCreditCardNumber(String creditCardNumber) {
		if (creditCardNumber != null) {
			if (creditCardNumber.matches("^\\d{16}$")) {
				int[] finalArray = new int[16];
				String[] array = creditCardNumber.split("");
				for (int i = 0; i < finalArray.length; i++)
					finalArray[i] = Integer.parseInt(array[i + 1]);

				for (int i = 0; i < finalArray.length; i += 2)
					finalArray[i] = finalArray[i] * 2;

				int sum = 0;

				for (int i = 0; i < finalArray.length; i++)
					if (finalArray[i] >= 10)
						sum += (finalArray[i] - 9);
					else
						sum += finalArray[i];

				if ((sum % 10) == 0)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	/*
	 * Validates whether the month and the year are valid integer and above zero
	 * 
	 * @param month
	 * 
	 * @param year
	 * 
	 * @return
	 */
	private boolean validateExpirationMonthYear(String month, String year) {
		if (month.matches("^\\d{2}$") || year.matches("^\\d{2}$")) {
			if ((Integer.parseInt(month) < 0) || Integer.parseInt(year) < 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * Validates whether the csc is a valid integer
	 * 
	 * @param csc
	 * @return
	 */
	private boolean validateCsc(String csc) {
		if (csc.matches("^\\d{3}$"))
			return true;
		else
			return false;

	}
}
