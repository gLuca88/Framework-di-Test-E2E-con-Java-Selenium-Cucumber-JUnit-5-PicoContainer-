package gianluca.com.model.products;

public class DetailsProductTest {

	private String urlPage;
	private String titleInPage;
	private String urlDetailProduct;

	public DetailsProductTest(String urlPage, String titleInPage, String urlDetailProduct) {
		super();
		this.urlPage = urlPage;
		this.titleInPage = titleInPage;
		this.urlDetailProduct = urlDetailProduct;
	}

	public DetailsProductTest() {
		super();
	}

	public String getUrlPage() {
		return urlPage;
	}

	public void setUrlPage(String urlPage) {
		this.urlPage = urlPage;
	}

	public String getTitleInPage() {
		return titleInPage;
	}

	public void setTitleInPage(String titleInPage) {
		this.titleInPage = titleInPage;
	}

	public String getUrlDetailProduct() {
		return urlDetailProduct;
	}

	public void setUrlDetailProduct(String urlDetailProduct) {
		this.urlDetailProduct = urlDetailProduct;
	}

}
