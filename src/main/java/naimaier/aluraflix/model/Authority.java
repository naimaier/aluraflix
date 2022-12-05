package naimaier.aluraflix.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String authority;
	
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
	@Override
	public String getAuthority() {
		return this.authority;
	}

}
