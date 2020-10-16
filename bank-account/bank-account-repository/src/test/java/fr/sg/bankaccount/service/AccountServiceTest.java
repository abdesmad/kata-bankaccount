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
import fr.sg.bankaccount.repository.AccountRepository;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private AccountService accountService;

	@Test
	public void getAllAccountsTest() {
		Mockito.when(accountService.getAll()).thenReturn(createSomeAccounts());
		List<Account> accounts = accountService.getAll();
		assertEquals(accounts.size(), 3);
		assertEquals(accounts.get(0).getAccountNumber(), new BigInteger("111"));
		assertEquals(accounts.get(0).getBalance(), new BigDecimal("111"));
	}

	@Test
	public void getByIdTest() {
		Mockito.when(accountService.getById(Mockito.any(BigInteger.class))).thenReturn(createAccount());
		Account account = accountService.getById(new BigInteger("111"));
		assertEquals(account.getAccountNumber(), new BigInteger("111"));
		assertEquals(account.getBalance(), new BigDecimal("111"));
	}

	@Test
	public void transactionAccountTest() {
		Mockito.when(accountService.transactionAccount(Mockito.any(BigInteger.class), Mockito.any(BigDecimal.class),
				Mockito.anyString())).thenReturn(createAccount());
		Account account = accountService.transactionAccount(new BigInteger("1234"), new BigDecimal("1234"),
				AccountOperation.pull.toString());
		assertEquals(account.getAccountNumber(), new BigInteger("111"));
		assertEquals(account.getBalance(), new BigDecimal("111"));
	}

	@Test
	public void transactionAccountToAccountTest() {
		Mockito.when(accountService.transactionAccountToAccount(Mockito.any(BigInteger.class),
				Mockito.any(BigInteger.class), Mockito.any(BigDecimal.class))).thenReturn(createTransferHistory());
		TransferHistory transferHistory = accountService.transactionAccountToAccount(new BigInteger("111"),
				new BigInteger("222"), new BigDecimal("10"));
		assertEquals(transferHistory.getAccount().getAccountNumber(),
				createTransferHistory().getAccount().getAccountNumber());
		assertEquals(transferHistory.getAccount().getBalance(), createTransferHistory().getAccount().getBalance());

		assertEquals(transferHistory.getAccountInteraction().getAccountNumber(),
				createTransferHistory().getAccountInteraction().getAccountNumber());
		assertEquals(transferHistory.getAccountInteraction().getBalance(),
				createTransferHistory().getAccountInteraction().getBalance());

		assertEquals(transferHistory.getAmount(), createTransferHistory().getAmount());
		assertEquals(transferHistory.getBalanceBefore(), createTransferHistory().getBalanceBefore());
		assertEquals(transferHistory.getBalanceAfter(), createTransferHistory().getBalanceAfter());
	}

	@Test
	public void createAccountTest() {
		Account account = createAccount();
		Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
		Account createdAccount = accountRepository.save(account);
		assertEquals(createdAccount.getAccountNumber(), new BigInteger("111"));
		assertEquals(createdAccount.getBalance(), new BigDecimal("111"));
		accountRepository.save(account);
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

	private List<Account> createSomeAccounts() {
		Account account1 = new Account();
		account1.setAccountNumber(new BigInteger("111"));
		account1.setBalance(new BigDecimal("111"));

		Account account2 = new Account();
		account2.setAccountNumber(new BigInteger("222"));
		account2.setBalance(new BigDecimal("222"));

		Account account3 = new Account();
		account3.setAccountNumber(new BigInteger("333"));
		account3.setBalance(new BigDecimal("333"));
		return Arrays.asList(account1, account2, account3);
	}

	private TransferHistory createTransferHistory() {
		return new TransferHistory(createAccount(), createAccountInteraction(), AccountOperation.pull.toString(),
				new BigDecimal("10"), new BigDecimal("111"), new BigDecimal("101"));
	}

}
