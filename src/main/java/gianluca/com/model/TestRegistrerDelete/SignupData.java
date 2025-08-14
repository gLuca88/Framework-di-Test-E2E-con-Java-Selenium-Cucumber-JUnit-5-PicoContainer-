package gianluca.com.model.TestRegistrerDelete;

public class SignupData {
	private String name;
	private String email;
	private String expectedError;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpectedError() {
		return expectedError;
	}

	public void setExpectedError(String expectedError) {
		this.expectedError = expectedError;
	}

}
