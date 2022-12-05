package naimaier.aluraflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
	
	public LoginException() {
		super(HttpStatus.BAD_REQUEST, "Usuário e senha inválidos");
	}

}
