package naimaier.aluraflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotEmptyException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
	
	public CategoryNotEmptyException() {
		super(HttpStatus.BAD_REQUEST, "A categoria não pode ser apagada pois possui vídeos associados");
	}

}
