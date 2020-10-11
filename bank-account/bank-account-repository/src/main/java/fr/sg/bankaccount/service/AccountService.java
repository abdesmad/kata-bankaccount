package fr.sg.bankaccount.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.sg.bankaccount.enumeration.AccountOperation;
import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.repository.AccountRepository;

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
		return accountRepository.findById(id).get();
	}

	public Account transactionAccount(BigInteger accountNumber, BigDecimal amout,
			String operation) {
		Account account = getById(accountNumber);
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
		Account debitedAccount = getById(debitedAccountNumber);

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
