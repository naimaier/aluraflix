package naimaier.aluraflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotEditableException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
	
	public CategoryNotEditableException() {
		super(HttpStatus.BAD_REQUEST, "Esta categoria não pode ser alterada");
	}
}
