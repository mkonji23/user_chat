package chat;

public class ChatDTO {
	int chatID;
	String fromID;
	String toID;
	String chatContent;
	String chatTimes;

	public int getChatID() {
		return this.chatID;
	}

	public void setChatID(int chatID) {
		this.chatID = chatID;
	}

	public String getFromID() {
		return this.fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getToID() {
		return this.toID;
	}

	public void setToID(String toID) {
		this.toID = toID;
	}

	public String getChatContent() {
		return this.chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public String getChatTimes() {
		return this.chatTimes;
	}

	public void setChatTimes(String chatTimes) {
		this.chatTimes = chatTimes;
	}
}