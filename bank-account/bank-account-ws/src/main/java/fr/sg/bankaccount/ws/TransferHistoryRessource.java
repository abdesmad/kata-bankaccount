package fr.sg.bankaccount.ws;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.service.TransferHistorytService;

/**
 * Ressource REST for Transfer History
 */

@RestController
public class TransferHistoryRessource {

	@Autowired
	TransferHistorytService transferHistorytService;

	/**
	 * Get all transfer history by accountNumber
	 *
	 * @return List<TransferHistory>
	 */
	@GetMapping(path = "/transferHistoryByAccountNumber")
	public ResponseEntity<List<TransferHistory>> transferHistoryByAccountNumber(
			@RequestParam BigInteger accountNumber) {
		try {
			return new ResponseEntity<List<TransferHistory>>(
					transferHistorytService.getTransferHistoryByAccountNumber(accountNumber), HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity(
					String.format("The account whose accountNumber = %d does not exist", accountNumber),
					HttpStatus.NOT_FOUND);
		}
	}

}
