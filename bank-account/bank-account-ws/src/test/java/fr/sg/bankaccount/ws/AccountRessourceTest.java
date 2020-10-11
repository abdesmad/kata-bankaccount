package fr.sg.bankaccount.ws;

import static org.junit.Assert.assertEquals;

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

@Transactional
public class AccountRessourceTest extends AbstractTest {
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void account() throws Exception {
		String uri = "/account/1";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Account account = super.mapFromJson(content, Account.class);
		assertEquals(account.getAccountNumber(), new BigInteger("1"));
		assertEquals(account.getBalance(), new BigDecimal("100.12"));
	}

	@Test
	@Rollback
	public void transactionAccount() throws Exception {
		String uri = "/transactionAccount?accountNumber=1&amout=100&operation=deposit";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Account account = super.mapFromJson(content, Account.class);
		assertEquals(account.getAccountNumber(), new BigInteger("1"));
		assertEquals(account.getBalance(), new BigDecimal("200.12"));
	}

	@Test
	@Rollback
	public void transactionAccountToAccount() throws Exception {
		String uri = "/transactionAccountToAccount?creditedAccountNumber=1&debiteddAccountNumber=2&amout=50";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		TransferHistory transferHistory = super.mapFromJson(content,
				TransferHistory.class);
		assertEquals(transferHistory.getAccount().getAccountNumber(),
				new BigInteger("2"));
		assertEquals(transferHistory.getBalanceBefore(), new BigDecimal(
				"200.00"));
		assertEquals(transferHistory.getBalanceAfter(),
				new BigDecimal("150.00"));
		assertEquals(transferHistory.getOperation(), "pull");
	}

}
