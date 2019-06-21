import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class json_parser {
		
	public json_parser() {
	}
	
	private class Session {
		private String from;
	    private String to;
	    private String message;
	    private String messageCounter;
	    private String id;

	    public Session(){}
	    
	    public Session(String from, String to, String message, String id) {
	        this.from = from;
	        this.to = to;
	        this.message = message;
	        this.id = id;
	        this.messageCounter = messageCounter;
	    }

	    public String getFrom() {
	        return from;
	    }
	    
	    public String getID() {
	        return id;
	    }

	    public void setFrom(String from) {
	        this.from = from;
	    }

	    public String getTo() {
	        return to;
	    }

	    public void setTo(String to) {
	        this.to = to;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
	    
	    public String getmessageCounter() {
	        return messageCounter;
	    }	    
	    
	}
	
	public String parse_message(String json_string) {
		return parse(json_string).getMessage();
	}
	
	public String parse_id(String json_string) {
		return parse(json_string).getID();
	}
	
	public String parse_from(String json_string) {
		return parse(json_string).getFrom();
	}
	
	public String parse_counter(String json_string) {
		return parse(json_string).getmessageCounter();
	}
	
	public Session parse(String json_string) {
		System.out.println(json_string);
		json_string = "["+json_string+"]";
//		String replace = json_string.replace("{ "+"\"items\":" , "");
//        replace = replace.substring(0, replace.length()-1);
//        System.out.println("sss: " + replace);
//        String lastlatter = replace.substring(replace.length() - 1);

        Gson gson = new Gson();

        List<Session> fromJson = gson.fromJson(json_string, new TypeToken<List<Session>>() {
        }.getType());
        
        return fromJson.get(0);
	}
}
