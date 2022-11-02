package naimaier.aluraflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotFoundException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Categoria não encontrada");
	}
}
