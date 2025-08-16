package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import gianluca.com.SeleniumWrapper;

public class PageContacts extends SeleniumWrapper {

	public PageContacts(WebDriver driver) {
		super(driver);
	}

	private By containerMexForm = By.cssSelector(".contact-form h2");

	private By inputName = By.name("name");
	private By inputEmail = By.name("email");
	private By inputSubJect = By.name("subject");
	private By inputMessage = By.id("message");
	private By upLoadFile = By.name("upload_file");
	private By subMitButton = By.name("submit");
	private By containerMessageAlertSuccess = By.xpath("//div[@class='status alert alert-success']");
    private By buttonHome=By.xpath("//div[@id='form-section']//a[@class='btn btn-success']");
	public String getTextContainerMexForm() {
		return getText(containerMexForm);
	}

	public void contactDataEntry(String name, String email, String subJect, String message) {

		type(inputName, name);
		type(inputEmail, email);
		type(inputSubJect, subJect);
		type(inputMessage, message);

	}

	public void insertPathFile(String path) {

		type(upLoadFile, path);

	}

	public void clickSubMitButton() {
		click(subMitButton);

	}

	public String textMessageSuccess() {

		return getText(containerMessageAlertSuccess);

	}

	public void acceptConfirmDialog() {
		acceptAlert();
	}
	public void clickHome() {
		click(buttonHome);
	}

}
