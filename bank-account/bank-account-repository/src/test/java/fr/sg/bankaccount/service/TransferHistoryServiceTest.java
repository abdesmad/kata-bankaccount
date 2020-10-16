package fr.sg.bankaccount.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sg.bankaccount.enumeration.AccountOperation;
import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.repository.TransferHistoryRepository;

@RunWith(SpringRunner.class)
public class TransferHistoryServiceTest {

	@Mock
	private TransferHistoryRepository transferHistoryRepository;

	@Mock
	private TransferHistorytService transferHistorytService;

	@Test
	public void getTransferHistoryByAccountNumberTest() {
		Mockito.when(transferHistorytService.getTransferHistoryByAccountNumber(Mockito.any(BigInteger.class)))
				.thenReturn(getTransferHistorys());

		List<TransferHistory> transferHistories = transferHistorytService
				.getTransferHistoryByAccountNumber(new BigInteger("111"));
		assertEquals(transferHistories.size(), 2);
		assertEquals(transferHistories.get(0).getAccount().getAccountNumber(),
				getTransferHistorys().get(0).getAccount().getAccountNumber());
		assertEquals(transferHistories.get(0).getAccount().getBalance(),
				getTransferHistorys().get(0).getAccount().getBalance());

		assertEquals(transferHistories.get(0).getAccountInteraction().getAccountNumber(),
				getTransferHistorys().get(0).getAccountInteraction().getAccountNumber());
		assertEquals(transferHistories.get(0).getAccountInteraction().getBalance(),
				getTransferHistorys().get(0).getAccountInteraction().getBalance());

		assertEquals(transferHistories.get(0).getAmount(), getTransferHistorys().get(0).getAmount());
		assertEquals(transferHistories.get(0).getBalanceBefore(), getTransferHistorys().get(0).getBalanceBefore());
		assertEquals(transferHistories.get(0).getBalanceAfter(), getTransferHistorys().get(0).getBalanceAfter());
	}

	@Test
	public void createTransferHistoryTest() {
		Mockito.when(transferHistorytService.createTransferHistory(getTransferHistorys()))
				.thenReturn(getTransferHistorys());

		List<TransferHistory> transferHistories = transferHistorytService.createTransferHistory(getTransferHistorys());
		assertEquals(transferHistories.size(), 2);
		assertEquals(transferHistories.get(0).getAccount().getAccountNumber(),
				getTransferHistorys().get(0).getAccount().getAccountNumber());
		assertEquals(transferHistories.get(0).getAccount().getBalance(),
				getTransferHistorys().get(0).getAccount().getBalance());

		assertEquals(transferHistories.get(0).getAccountInteraction().getAccountNumber(),
				getTransferHistorys().get(0).getAccountInteraction().getAccountNumber());
		assertEquals(transferHistories.get(0).getAccountInteraction().getBalance(),
				getTransferHistorys().get(0).getAccountInteraction().getBalance());

		assertEquals(transferHistories.get(0).getAmount(), getTransferHistorys().get(0).getAmount());
		assertEquals(transferHistories.get(0).getBalanceBefore(), getTransferHistorys().get(0).getBalanceBefore());
		assertEquals(transferHistories.get(0).getBalanceAfter(), getTransferHistorys().get(0).getBalanceAfter());
	}

	private List<TransferHistory> getTransferHistorys() {
		TransferHistory transferHistory1 = new TransferHistory(createAccount(), createAccountInteraction(),
				AccountOperation.pull.toString(), new BigDecimal("10"), new BigDecimal("111"), new BigDecimal("101"));

		TransferHistory transferHistory2 = new TransferHistory(createAccount(), createAccountInteraction(),
				AccountOperation.pull.toString(), new BigDecimal("10"), new BigDecimal("101"), new BigDecimal("91"));

		return Arrays.asList(transferHistory1, transferHistory2);
	}

	private Account createAccount() {
		Account account = new Account();
		account.setAccountNumber(new BigInteger("111"));
		account.setBalance(new BigDecimal("111"));
		return account;
	}

	private Account createAccountInteraction() {
		Account account = new Account();
		account.setAccountNumber(new BigInteger("1234"));
		account.setBalance(new BigDecimal("1234"));
		return account;
	}

}
