package fr.sg.bankaccount.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.sg.bankaccount.enumeration.AccountOperation;
import fr.sg.bankaccount.exception.InvalidOperationTypeAxception;
import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.repository.AccountRepository;

/**
 * Service Account
 */

@Service
public class AccountService {

	@Autowired
	private TransferHistorytService transferHistorytService;

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	public Account getById(BigInteger id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			throw new NoSuchElementException(String.format("The account whose accountNumber = %d does not exist", id));
		}
		return account;
	}

	public Account transactionAccount(BigInteger accountNumber, BigDecimal amout,
			String operation) {
		Account account = getById(accountNumber);
		if (account == null) {
			throw new NoSuchElementException(String.format("The account whose accountNumber = %d does not exist", accountNumber));
		}
		if (!EnumUtils.isValidEnum(AccountOperation.class, operation)) {
			throw new InvalidOperationTypeAxception(String.format("The operation type %s does not exist", operation));
		}
		 
		if (AccountOperation.deposit.toString().equals(operation)) {
			account.setBalance(account.getBalance().add(amout));
		} else if (AccountOperation.pull.toString().equals(operation)) {
			account.setBalance(account.getBalance().subtract(amout));
		}
		accountRepository.save(account);
		return account;
	}

	@Transactional
	public TransferHistory transactionAccountToAccount(BigInteger creditedAccountNumber,
			BigInteger debitedAccountNumber, BigDecimal amout) {
		Account creditedAccount = getById(creditedAccountNumber);
		if (creditedAccount == null) {
			throw new NoSuchElementException(String.format("The account whose creditedAccountNumber = %d does not exist", creditedAccountNumber));
		}
		
		Account debitedAccount = getById(debitedAccountNumber);
		if (debitedAccount == null) {
			throw new NoSuchElementException(String.format("The account whose debitedAccountNumber = %d does not exist", debitedAccountNumber));
		}

		TransferHistory transferHistoryCreditedAccount = new TransferHistory(
				creditedAccount, debitedAccount,
				AccountOperation.deposit.toString(), amout,
				creditedAccount.getBalance(), creditedAccount.getBalance().add(
						amout));
		TransferHistory transferHistorydebitedAccount = new TransferHistory(
				debitedAccount, creditedAccount,
				AccountOperation.pull.toString(), amout,
				debitedAccount.getBalance(), debitedAccount.getBalance()
						.subtract(amout));

		creditedAccount.setBalance(creditedAccount.getBalance().add(amout));
		debitedAccount.setBalance(debitedAccount.getBalance().subtract(amout));

		accountRepository.saveAll(Arrays
				.asList(creditedAccount, debitedAccount));
		transferHistorytService.createTransferHistory(Arrays.asList(
				transferHistoryCreditedAccount, transferHistorydebitedAccount));
		return transferHistorydebitedAccount;
	}

}
