package fr.sg.bankaccount.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {
	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ACCOUNT_NUMBER")
	/**
	 * The account number
	 */
	private BigInteger accountNumber;

	/**
	 * The bank balance
	 */
	@Column(name = "BALANCE")
	private BigDecimal balance;

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
