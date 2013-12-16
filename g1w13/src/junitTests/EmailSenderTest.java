// Venelin Koulaxazov
// 1032744
// EmailSenderTest.java
package junitTests;

import util.EmailSender;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class EmailSenderTest {

	private EmailSender emailSender;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		emailSender = new EmailSender();
	}

	@Test
	public void testSendMail() {
		assertTrue(emailSender.sendMail("venkokoul@hotmail.com", "Test",
				"This is a test"));
	}

}
