package br.co.progresso.concurso.request;

public class LoginRequest {
	private Long id;
	private String username;
	private String password;

	public LoginRequest() {
		
	}
	
	public LoginRequest(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	// Getters e Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

}
