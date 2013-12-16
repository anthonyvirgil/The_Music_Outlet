// Venelin Koulaxazov
// 1032744
// CreditCard.java
package beans;

/**
 * Java Bean for a credit card in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class CreditCard {

	private String cardHolder;
	private String creditCardNum;
	private String expirationMonth;
	private String expirationYear;
	private String cardSecurityCode;

	/**
	 * Default constructor
	 */
	public CreditCard() {
		cardHolder = "";
		creditCardNum = "";
		expirationMonth = "";
		expirationYear = "";
		cardSecurityCode = "";
	}

	/**
	 * @param cardHolder
	 * @param creditCardNum
	 * @param expirationMonth
	 * @param expirationYear
	 * @param cardSecurityCode
	 */
	public CreditCard(String cardHolder, String creditCardNum,
			String expirationMonth, String expirationYear,
			String cardSecurityCode) {
		super();
		this.cardHolder = cardHolder;
		this.creditCardNum = creditCardNum;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cardSecurityCode = cardSecurityCode;
	}

	/**
	 * @return the cardHolder
	 */
	public String getCardHolder() {
		return cardHolder;
	}

	/**
	 * @param cardHolder
	 *            the cardHolder to set
	 */
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	/**
	 * @return the creditCardNum
	 */
	public String getCreditCardNum() {
		return creditCardNum;
	}

	/**
	 * @param creditCardNum
	 *            the creditCardNum to set
	 */
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	/**
	 * @return the expirationMonth
	 */
	public String getExpirationMonth() {
		return expirationMonth;
	}

	/**
	 * @param expirationMonth
	 *            the expirationMonth to set
	 */
	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	/**
	 * @return the expirationYear
	 */
	public String getExpirationYear() {
		return expirationYear;
	}

	/**
	 * @param expirationYear
	 *            the expirationYear to set
	 */
	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	/**
	 * @return the cardSecurityCode
	 */
	public String getCardSecurityCode() {
		return cardSecurityCode;
	}

	/**
	 * @param cardSecurityCode
	 *            the cardSecurityCode to set
	 */
	public void setCardSecurityCode(String cardSecurityCode) {
		this.cardSecurityCode = cardSecurityCode;
	}

	public String getLastFourDigits() {
		return creditCardNum.substring(12);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreditCard [cardHolder=" + cardHolder + ", creditCardNum="
				+ creditCardNum + ", expirationMonth=" + expirationMonth
				+ ", expirationYear=" + expirationYear + ", cardSecurityCode="
				+ cardSecurityCode + "]";
	}

}