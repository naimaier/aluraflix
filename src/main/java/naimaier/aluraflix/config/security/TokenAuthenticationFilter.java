package naimaier.aluraflix.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import naimaier.aluraflix.model.User;
import naimaier.aluraflix.repository.UserRepository;

public class TokenAuthenticationFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UserRepository userRepository;
	

	public TokenAuthenticationFilter(TokenService tokenService, 
			UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		boolean valid = tokenService.isTokenValid(token);
		
		if (valid) {
			authenticateClient(token);
		}
		
		filterChain.doFilter(request, response);
		
	}


	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;			
		}
		
		return token.substring(7, token.length());
	}
	
	
	private void authenticateClient(String token) {
		String username = tokenService.getUsername(token);
		Optional<User> userOptional = userRepository.findById(username);
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(userOptional.get(), 
						null, 
						userOptional.get().getAuthorities());
		
		SecurityContextHolder
			.getContext()
			.setAuthentication(authentication);
	}
}
