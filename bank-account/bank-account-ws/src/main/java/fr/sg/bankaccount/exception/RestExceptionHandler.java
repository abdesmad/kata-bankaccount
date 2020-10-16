package fr.sg.bankaccount.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Exception thrown when org.springframework.validation.annotation.Validated is
	 * used in controller.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex,
			HttpServletRequest request) {
		try {
			List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(new ErrorResponse<>(messages), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse<>(Arrays.asList(ex.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
