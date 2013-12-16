// Anthony-Virgil Bermejo
// 0831360
// InvoiceSearch.java
package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Java Bean for an invoice search in the music store management
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class InvoiceSearch implements Serializable {

	private static final long serialVersionUID = 6770253893712194168L;
	private int saleId;
	private Date saleDate;
	private int clientId;
	private double netValue;
	private double pst;
	private double gst;
	private double hst;
	private double grossValue;
	private String email;
	private boolean removalStatus;

	/**
	 * Default constructor
	 */
	public InvoiceSearch() {
	}

	/**
	 * @param saleId
	 * @param saleDate
	 * @param clientId
	 * @param netValue
	 * @param pst
	 * @param gst
	 * @param hst
	 * @param grossValue
	 * @param email
	 * @param removalStatus
	 */
	public InvoiceSearch(int saleId, Date saleDate, int clientId,
			double netValue, double pst, double gst, double hst,
			double grossValue, String email, boolean removalStatus) {
		super();
		this.saleId = saleId;
		this.saleDate = saleDate;
		this.clientId = clientId;
		this.netValue = netValue;
		this.pst = pst;
		this.gst = gst;
		this.hst = hst;
		this.grossValue = grossValue;
		this.email = email;
		this.removalStatus = removalStatus;
	}

	/**
	 * @return the removalStatus
	 */
	public boolean isRemovalStatus() {
		return removalStatus;
	}

	/**
	 * @param removalStatus
	 *            the removalStatus to set
	 */
	public void setRemovalStatus(boolean removalStatus) {
		this.removalStatus = removalStatus;
	}

	/**
	 * @return the saleId
	 */
	public int getSaleId() {
		return saleId;
	}

	/**
	 * @param saleId
	 *            the saleId to set
	 */
	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}

	/**
	 * @param saleDate
	 *            the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
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
	 * @return the netValue
	 */
	public double getNetValue() {
		return netValue;
	}

	/**
	 * @param netValue
	 *            the netValue to set
	 */
	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}

	/**
	 * @return the pst
	 */
	public double getPst() {
		return pst;
	}

	/**
	 * @param pst
	 *            the pst to set
	 */
	public void setPst(double pst) {
		this.pst = pst;
	}

	/**
	 * @return the gst
	 */
	public double getGst() {
		return gst;
	}

	/**
	 * @param gst
	 *            the gst to set
	 */
	public void setGst(double gst) {
		this.gst = gst;
	}

	/**
	 * @return the hst
	 */
	public double getHst() {
		return hst;
	}

	/**
	 * @param hst
	 *            the hst to set
	 */
	public void setHst(double hst) {
		this.hst = hst;
	}

	/**
	 * @return the grossValue
	 */
	public double getGrossValue() {
		return grossValue;
	}

	/**
	 * @param grossValue
	 *            the grossValue to set
	 */
	public void setGrossValue(double grossValue) {
		this.grossValue = grossValue;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvoiceSearch [saleId=" + saleId + ", saleDate=" + saleDate
				+ ", clientId=" + clientId + ", netValue=" + netValue
				+ ", pst=" + pst + ", gst=" + gst + ", hst=" + hst
				+ ", grossValue=" + grossValue + ", email=" + email + "]";
	}
}
