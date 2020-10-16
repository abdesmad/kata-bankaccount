package fr.sg.bankaccount.exception;

public class InvalidOperationTypeAxception extends RuntimeException {

	/**
	 * Exception thrown when operation type not exist in AccountOperation
	 */
	private static final long serialVersionUID = 1L;

	public InvalidOperationTypeAxception(String s) {
		super(s);
	}

}
