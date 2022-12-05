package naimaier.aluraflix.controller.dto;

public class TokenDto {

	private String prefix;
	private String token;

	public TokenDto(String prefix, String token) {
		this.prefix = prefix;
		this.token = token;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getToken() {
		return token;
	}
	
}
