package gianluca.com.model.TestRegistrerDelete;

public class Preferences {

	private boolean subscribeNewsletter;
	private boolean receiveOffers;

	public boolean isSubscribeNewsletter() {
		return subscribeNewsletter;
	}

	public void setSubscribeNewsletter(boolean subscribeNewsletter) {
		this.subscribeNewsletter = subscribeNewsletter;
	}

	public boolean isReceiveOffers() {
		return receiveOffers;
	}

	public void setReceiveOffers(boolean receiveOffers) {
		this.receiveOffers = receiveOffers;
	}

}
