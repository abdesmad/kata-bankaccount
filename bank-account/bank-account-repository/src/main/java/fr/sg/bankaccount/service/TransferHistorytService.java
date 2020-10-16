package fr.sg.bankaccount.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sg.bankaccount.model.TransferHistory;
import fr.sg.bankaccount.repository.TransferHistoryRepository;

/**
 * Service Transfert history
 */

@Service
public class TransferHistorytService {

	@Autowired
	private TransferHistoryRepository transferHistoryRepository;
	
	@Autowired
	private AccountService accountService;

	public List<TransferHistory> getTransferHistoryByAccountNumber(
			BigInteger accountNumber) {
		return transferHistoryRepository.findByAccount(accountService.getById(accountNumber));
	}

	public List<TransferHistory> createTransferHistory(List<TransferHistory> transferHistorys) {
		return transferHistoryRepository.saveAll(transferHistorys);
	}
}
