package fr.sg.bankaccount.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.Digits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.service.AccountService;

/**
 * Ressource REST for Account
 */
@RestController
@Validated
public class AccountRessource {

	@Autowired
	AccountService accountService;

	/**
     * Get all Accounts
     *
     * @return List<Account>
     */
	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountService.getAll();
	}
	
	/**
     * Get Account by accountNumber
     *
     * @return Account
     */
	@GetMapping("/account/{accountNumber}")
	public Account getAccountById(@PathVariable BigInteger accountNumber) {
		return accountService.getById(accountNumber);
	}

	/**
     * Post transaction account
     *
     * @return Account after transaction
     */
	@PostMapping(path = "/transactionAccount")
	public Account transactionAccount(@RequestParam BigInteger accountNumber,
			@RequestParam @Digits(integer=9, fraction=3) BigDecimal amout, @RequestParam String operation) {
		return accountService.transactionAccount(accountNumber, amout, operation);
	}

	/**
     * Post transaction from an account to another
     *
     * @return TransferHistory
     */
	@PostMapping(path = "/transactionAccountToAccount")
	public TransferHistory transactionAccount(
			@RequestParam BigInteger creditedAccountNumber,
			@RequestParam BigInteger debiteddAccountNumber,
			@RequestParam @Digits(integer=9, fraction=3) BigDecimal amout) {
		return accountService.transactionAccountToAccount(creditedAccountNumber,
				debiteddAccountNumber, amout);
	}

}
