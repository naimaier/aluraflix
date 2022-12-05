package naimaier.aluraflix.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import naimaier.aluraflix.config.security.TokenService;
import naimaier.aluraflix.controller.dto.TokenDto;
import naimaier.aluraflix.controller.form.LoginForm;
import naimaier.aluraflix.exception.LoginException;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	
	@PostMapping
	public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid LoginForm loginForm) {
		
		UsernamePasswordAuthenticationToken loginData = loginForm.convert();
		
		try {
			Authentication authentication = authManager.authenticate(loginData);
			String token = tokenService.gerarToken(authentication);
			
			return ResponseEntity.ok(new TokenDto("Bearer", token));
		} catch (AuthenticationException e) {
			throw new LoginException();
		}
	}
}
