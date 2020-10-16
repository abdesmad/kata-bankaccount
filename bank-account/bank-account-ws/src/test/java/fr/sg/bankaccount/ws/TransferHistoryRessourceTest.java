package fr.sg.bankaccount.ws;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.sg.bankaccount.model.TransferHistory;

/**
 * Test Ressource REST for Transfer History
 */

@Transactional
public class TransferHistoryRessourceTest extends AbstractTest {
	
	private final String URI_TRANSFER_HISTORY_BY_ACCOUNT = 
			"/transferHistoryByAccountNumber?accountNumber=2";
	private final String URI_TRANSFER_HISTORY_BY_ACCOUNT_NOT_FOUND_EXCEPTION = 
			"/transferHistoryByAccountNumber?accountNumber=22";
	private final String URI_TRANSACTION_ACCOUNT_TO_ACCOUNT = 
			"/transactionAccountToAccount?creditedAccountNumber=1&debiteddAccountNumber=2&amout=50";
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Rollback
	public void transferHistoryByAccountNumber() throws Exception {
		//Create some transaction
		mvc.perform(
				MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT_TO_ACCOUNT)
						.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		// Call transfer history ressource
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(URI_TRANSFER_HISTORY_BY_ACCOUNT).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		TransferHistory[] transferHistories = super.mapFromJson(content,
				TransferHistory[].class);
		assertEquals(transferHistories.length, 1);
		TransferHistory transferHistory=transferHistories[0];
		assertEquals(transferHistory.getAccount().getAccountNumber(),
				new BigInteger("2"));
		assertEquals(transferHistory.getBalanceBefore(), new BigDecimal(
				"200.00"));
		assertEquals(transferHistory.getBalanceAfter(),
				new BigDecimal("150.00"));
		assertEquals(transferHistory.getOperation(), "pull");
	}
	
	@Test
	public void transferHistoryByAccountNumberNotFoundException() throws Exception {
		// Call transfer history ressource
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(URI_TRANSFER_HISTORY_BY_ACCOUNT_NOT_FOUND_EXCEPTION).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("The account whose accountNumber = 22 does not exist", content);
	}
	
	public void createTransactionAccountToAccount() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post(URI_TRANSACTION_ACCOUNT_TO_ACCOUNT)
						.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	}

}
