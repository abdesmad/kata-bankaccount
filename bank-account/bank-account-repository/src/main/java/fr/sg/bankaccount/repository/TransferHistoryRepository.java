package fr.sg.bankaccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.sg.bankaccount.model.Account;
import fr.sg.bankaccount.model.TransferHistory;

@Repository
public interface TransferHistoryRepository extends
		JpaRepository<TransferHistory, Long> {

	List<TransferHistory> findByAccount(Account account);

}
