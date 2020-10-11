package fr.sg.bankaccount.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSFER_HISTORY")
public class TransferHistory implements Serializable {

	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * The creation date
	 */
	@Column(name = "DT_CREATE")
	private Date dtCreate;

	@ManyToOne
	@JoinColumn(name = "ID_ACCOUNT")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "ID_ACCOUNT_INTERACTION")
	private Account accountInteraction;

	@Column(name = "OPERATION")
	private String operation;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "BALANCE_BEFORE")
	private BigDecimal balanceBefore;

	@Column(name = "BALANCE_AFTER")
	private BigDecimal balanceAfter;

	public TransferHistory() {
		super();
	}

	public TransferHistory(Account account, Account accountInteraction,
			String operation, BigDecimal amount, BigDecimal balanceBefore,
			BigDecimal balanceAfter) {
		super();
		this.account = account;
		this.operation = operation;
		this.amount = amount;
		this.dtCreate = new Date();
		this.balanceBefore = balanceBefore;
		this.balanceAfter = balanceAfter;
		this.accountInteraction = accountInteraction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
	}
	
	public Account getAccountInteraction() {
		return accountInteraction;
	}

	public void setAccountInteraction(Account accountInteraction) {
		this.accountInteraction = accountInteraction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferHistory other = (TransferHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
