import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.commons.mail.EmailException;;

public class EmailConcreteTest {

	private static final String[] VALID_EMAIL_ARRAY = {
		"arafat@gmail.com", "muath@gmail.com"
	};
	private static final String[] EMPTY_EMAIL_ARRAY = {};
	private EmailConcrete email;
	private static final String VALID_EMAIL = "arafat@gmail.com";
	private static final String VALID_NON_EMPTY_STRING = "sfbdfb@gmail.com";
	private static final String EMPTY_STRING = "";
	@Before
	public void setUp() throws Exception {
		 email = new EmailConcrete();
	}

	@Rule
	  public final ExpectedException exception = ExpectedException.none();

	@After
	public void tearDown() throws Exception {

	}


	@Test
	public void testAddBccValid() throws Exception {
		email.addBcc(VALID_EMAIL_ARRAY);
		assertEquals(2,email.getBccAddresses().size());
	}
	@Test
	public void testAddBccEmpty() throws Exception {
		exception.expect(EmailException.class);
		email.addBcc(EMPTY_EMAIL_ARRAY);
	}



	@Test
	public void testAddCcValid() throws Exception {
		email.addCc(VALID_EMAIL);
		assertEquals(1,email.getCcAddresses().size());
	}
	@Test
	public void testAddCcEmpty() throws Exception {
		exception.expect(Exception.class);
		email.addCc(EMPTY_STRING);
	}



	@Test
	public void testAddHeaderInvalidNameAndValue() throws Exception {
		exception.expect(IllegalArgumentException.class);
		email.addHeader(EMPTY_STRING, EMPTY_STRING);
	}
	@Test
	public void testAddHeaderValidNameInvalidValue() throws Exception {
		exception.expect(IllegalArgumentException.class);
		email.addHeader(VALID_NON_EMPTY_STRING, EMPTY_STRING);
	}
	@Test
	public void testAddHeaderInvalidNameValidValue() throws Exception {
		exception.expect(IllegalArgumentException.class);
		email.addHeader(EMPTY_STRING, VALID_NON_EMPTY_STRING);
	}



	@Test
	public void testAddReplytoInValidAndName() throws Exception {
		exception.expect(EmailException.class);
		email.addReplyTo(EMPTY_STRING, EMPTY_STRING);
	}

	@Test
	public void testAddReplytoValidEmailandName() throws Exception {
		email.addReplyTo(VALID_NON_EMPTY_STRING, VALID_NON_EMPTY_STRING);
		assertEquals(1,email.getReplyToAddresses().size());
	}





	@Test
	public void testBuildMimeMessageNoFromAddress() throws Exception{

		email.setHostName(VALID_NON_EMPTY_STRING);
		email.addTo(VALID_NON_EMPTY_STRING);
		email.addCc(VALID_NON_EMPTY_STRING);
		email.addHeader(VALID_NON_EMPTY_STRING, VALID_NON_EMPTY_STRING);
		email.setSubject(VALID_NON_EMPTY_STRING);


		exception.expect(EmailException.class);;
		email.buildMimeMessage();
	}
	@Test
	public void testBuildMimeMessageNoToAddress() throws Exception{

		email.setHostName(VALID_NON_EMPTY_STRING);
		email.setFrom(VALID_NON_EMPTY_STRING);
		email.addHeader(VALID_NON_EMPTY_STRING, VALID_NON_EMPTY_STRING);
		email.setSubject(VALID_NON_EMPTY_STRING);

		exception.expect(EmailException.class);;
		email.buildMimeMessage();
	}

	@Test
	public void testGetHostNameValid() throws Exception{
		email.setHostName(VALID_NON_EMPTY_STRING);
		assertEquals(VALID_NON_EMPTY_STRING, email.getHostName());
	}


	@Test
	public void testGetMailSessionWhenNull() throws Exception{
		exception.expect(EmailException.class);;
		email.getMailSession();
		//because no host name
	}


	@Test
	public void testGetSentDate() throws Exception{
		//can't really mock sent date being null, because it'll return current date
		Date someDate = new Date();
		email.setSentDate(someDate);
		assertEquals(someDate, email.getSentDate());
	}


	@Test
	public void testSocketConnectionTimeoutValid() throws Exception{
		//can't test it being null, int datatype is primitive, 0 by default
		final int tempSocketConnectionTimeout = 1234;
		email.setSocketConnectionTimeout(tempSocketConnectionTimeout);;
		assertEquals(tempSocketConnectionTimeout, email.getSocketConnectionTimeout());
	}

	@Test
	public void testSetFrom() throws Exception{
		email.setFrom(VALID_NON_EMPTY_STRING);
		assertEquals(VALID_NON_EMPTY_STRING,email.getFromAddress().toString());
	}

}
