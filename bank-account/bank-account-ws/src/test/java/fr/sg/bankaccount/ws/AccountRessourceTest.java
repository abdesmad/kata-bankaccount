package fr.sg.bankaccount.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;

/**
 * Test Ressource REST for Account
 */

@Transactional
public class AccountRessourceTest extends AbstractTest {

	private final String URI_ACOOUNT_BY_ID = "/account/1";
	private final String URI_ACOOUNT_BY_ID_NOT_FOUND_EXCEPTION = "/account/10";

	private final String URI_TRANSACTION_ACCOUNT =
			"/transactionAccount?accountNumber=1&amout=100&operation=deposit";
	private final String URI_TRANSACTION_ACCOUNT_NOT_FOUND_EXCEPTION =
			"/transactionAccount?accountNumber=10&amout=100&operation=deposit";
	private final String URI_TRANSACTION_ACCOUNT_BAD_OPERATION_EXCEPTON =
			"/transactionAccount?accountNumber=1&amout=100&operation=anyOperation";
	private final String URI_TRANSACTION_ACCOUNT_BAD_PARAMETER_EXCEPTION =
			"/transactionAccount?accountNumber=1&amout=-100.12345&operation=deposit";

	private final String URI_TRANSACTION_ACCOUNT_TO_ACCOUNT =
			"/transactionAccountToAccount?creditedAccountNumber=1&debiteddAccountNumber=2&amout=50";
	private final String URI_TRANSACTION_ACCOUNT_TO_ACCOUNT_NOT_FOUND_EXCEPTION =
			"/transactionAccountToAccount?creditedAccountNumber=10&debiteddAccountNumber=2&amout=50";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void account() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(URI_ACOOUNT_BY_ID).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Account account = super.mapFromJson(content, Account.class);
		assertEquals(account.getAccountNumber(), new BigInteger("1"));
		assertEquals(account.getBalance(), new BigDecimal("100.12"));
	}

	@Test
	public void accountNotFoundException() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI_ACOOUNT_BY_ID_NOT_FOUND_EXCEPTION)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("The account whose accountNumber = 10 does not exist", content);
	}

	@Test
	@Rollback
	public void transactionAccount() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Account account = super.mapFromJson(content, Account.class);
		assertEquals(account.getAccountNumber(), new BigInteger("1"));
		assertEquals(account.getBalance(), new BigDecimal("200.12"));
	}

	@Test
	public void transactionAccountNotFoundException() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT_NOT_FOUND_EXCEPTION)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("The account whose accountNumber = 10 does not exist", content);
	}

	@Test
	public void transactionAccountBadOperationTypeException() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT_BAD_OPERATION_EXCEPTON)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("The operation type = anyOperation does not exist", content);
	}

	@Test
	public void transactionAccountBadParameterException() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT_BAD_PARAMETER_EXCEPTION)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("numeric value out of bounds (<9 digits>.<3 digits> expected)"));
		assertTrue(content.contains("amout: must be greater than or equal to 0"));
	}

	@Test
	@Rollback
	public void transactionAccountToAccount() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT_TO_ACCOUNT)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		TransferHistory transferHistory = super.mapFromJson(content, TransferHistory.class);
		assertEquals(transferHistory.getAccount().getAccountNumber(), new BigInteger("2"));
		assertEquals(transferHistory.getBalanceBefore(), new BigDecimal("200.00"));
		assertEquals(transferHistory.getBalanceAfter(), new BigDecimal("150.00"));
		assertEquals(transferHistory.getOperation(), "pull");
	}

	@Test
	public void transactionAccountToAccountNotFoundException() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
				.post(URI_TRANSACTION_ACCOUNT_TO_ACCOUNT_NOT_FOUND_EXCEPTION).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("The account whose creditedAccountNumber = 10 or debiteddAccountNumber = 2 does not exist",
				content);
	}

}
