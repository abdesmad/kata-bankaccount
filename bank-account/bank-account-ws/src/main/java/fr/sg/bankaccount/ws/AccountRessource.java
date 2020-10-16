package fr.sg.bankaccount.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sg.bankaccount.exception.InvalidOperationTypeAxception;
import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.service.AccountService;
import io.swagger.annotations.ApiParam;

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
	public ResponseEntity<Account> getAccountById(
			@ApiParam(value = "The account number") @PathVariable BigInteger accountNumber) {
		try {
			return new ResponseEntity<Account>(accountService.getById(accountNumber), HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity(
					String.format("The account whose accountNumber = %d does not exist", accountNumber),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Post transaction account
	 *
	 * @return Account after transaction
	 */
	@PostMapping(path = "/transactionAccount")
	public ResponseEntity<Account> transactionAccount(
			@ApiParam(value = "The account number") @RequestParam BigInteger accountNumber,
			@RequestParam @DecimalMin(value = "0", message = "amout: must be greater than or equal to 0") @Digits(integer = 9, fraction = 3) BigDecimal amout,
			@ApiParam(value = "Possible values are : pull or deposit") @RequestParam String operation) {
		try {
			return new ResponseEntity<Account>(accountService.transactionAccount(accountNumber, amout, operation),
					HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity(
					String.format("The account whose accountNumber = %d does not exist", accountNumber),
					HttpStatus.NOT_FOUND);
		} catch (InvalidOperationTypeAxception e) {
			return new ResponseEntity(String.format("The operation type = %s does not exist", operation),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Post transaction from an account to another
	 *
	 * @return TransferHistory
	 */
	@PostMapping(path = "/transactionAccountToAccount")
	public ResponseEntity<TransferHistory> transactionAccount(
			@ApiParam(value = "The account number to be credited") @RequestParam BigInteger creditedAccountNumber,
			@ApiParam(value = "The account number to be debited") @RequestParam BigInteger debiteddAccountNumber,
			@RequestParam @DecimalMin(value = "0") @Digits(integer = 9, fraction = 3) BigDecimal amout) {
		try {
			return new ResponseEntity<TransferHistory>(
					accountService.transactionAccountToAccount(creditedAccountNumber, debiteddAccountNumber, amout),
					HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity(String.format(
					"The account whose creditedAccountNumber = %d or debiteddAccountNumber = %d does not exist",
					creditedAccountNumber, debiteddAccountNumber), HttpStatus.NOT_FOUND);
		}

	}

}
