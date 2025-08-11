package gianluca.com.model;

public class LoginData {
	private String email;
	private String password;
	private String urlAtteso;

	public String getUrlAtteso() {
		return urlAtteso;
	}

	public void setUrlAtteso(String urlAtteso) {
		this.urlAtteso = urlAtteso;
	}

	// Getters e Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
