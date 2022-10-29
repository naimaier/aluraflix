package naimaier.aluraflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VideoNotFoundException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public VideoNotFoundException() {
		super(HttpStatus.NOT_FOUND, "Video não encontrado");
	}
}
