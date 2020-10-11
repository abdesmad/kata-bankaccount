package fr.sg.bankaccount.ws;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.service.TransferHistorytService;

@RestController
public class TransferHistoryRessource {

	@Autowired
	TransferHistorytService transferHistorytService;

	@GetMapping(path = "/transferHistoryByAccountNumber")
	public List<TransferHistory> transferHistoryByAccountNumber(
			@RequestParam BigInteger accountNumber) {
		return transferHistorytService
				.getTransferHistoryByAccountNumber(accountNumber);
	}

}
