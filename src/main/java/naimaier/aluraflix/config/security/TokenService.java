package naimaier.aluraflix.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import naimaier.aluraflix.model.User;

@Service
@Profile("prod")
public class TokenService {

	@Value("${aluraflix.jwt.expiration}")
	private String expiration;
	
	@Value("${aluraflix.jwt.secret}")
	private String secret;
	
	
	public String gerarToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = 
				new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("Aluraflix")
				.setSubject(user.getUsername())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public String getUsername(String token) {
		Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return body.getSubject();
	}
}
