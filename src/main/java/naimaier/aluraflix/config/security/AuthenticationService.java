package naimaier.aluraflix.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import naimaier.aluraflix.model.User;
import naimaier.aluraflix.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> optional = userRepository.findById(username);
		
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return optional.get();
	}

}
