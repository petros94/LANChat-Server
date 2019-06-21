import java.util.ArrayList;

public abstract class Backend_Proxy {
	public String sender;
	public String receiver;
	public ArrayList<String> info;
	
	public Backend_Proxy() {
		sender = "";
		receiver = "";
		info = new ArrayList<String>();
	}
	
	public Backend_Proxy(String sender, String receiver) {
		this.sender = sender;
		this.receiver = receiver;
		info = new ArrayList<String>();
	}
	
	public Backend_Proxy(String sender, String receiver, ArrayList<String> info) {
		this.sender = sender;
		this.receiver = receiver;
		this.info = new ArrayList<String>(info);		
	}
	
	public String get_sender() {
		return sender;
	}
	public String get_receiver() {
		return receiver;
	}
	abstract String send(String message) throws Exception;
	abstract String receive() throws Exception;
	abstract String test_session(String[] test_parameters) throws Exception;
	abstract String create_session(String[] session_parameters) throws Exception;
	abstract String start_session() throws Exception;
}
