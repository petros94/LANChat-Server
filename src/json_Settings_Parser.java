import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.google.gson.Gson;


public class json_Settings_Parser extends Settings_Parser{
	
	private Gson gson;
	
	public class Settings_Class{
		private String sender;
	    private String server;
	    private String[] clients;
	    private String icon;
	    private String sound_notification_filepath;

	    
	    public String get_sender() {
	    	return sender;
	    }
	    
	    public String get_server() {
	    	return server;
	    }

		public String[] get_clients() {
			// TODO Auto-generated method stub
			return clients;
		}
		public String get_icon() {
			return icon;
		}
		public String get_sound() {
			return sound_notification_filepath;
		}

	}
	
	public json_Settings_Parser() {
		gson = new Gson();
	}
	
	public Settings_Class json_to_Settings_Class(String file_path) throws Exception {
		Settings_Class fromJson;

		try {
			Scanner in = new Scanner(new FileReader(file_path));
			StringBuilder sb = new StringBuilder();
			while(in.hasNext()) {
			    sb.append(in.next());
			}
			in.close();
			String json_string = sb.toString();
			

	        fromJson = gson.fromJson(json_string, Settings_Class.class);
	        return fromJson;
	        

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void import_settings(String file_path) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String get_sender (String file_path) throws Exception {
		return json_to_Settings_Class(file_path).get_sender();
	}

	@Override
	public String get_server(String file_path) throws Exception {
		// TODO Auto-generated method stub
		return json_to_Settings_Class(file_path).get_server();
	}

	@Override
	public String[] get_clients(String file_path) throws Exception {
		// TODO Auto-generated method stub
		return json_to_Settings_Class(file_path).get_clients();
	}
	
	public String get_icon_path(String file_path) throws Exception{
		// TODO Auto-generated method stub
		return json_to_Settings_Class(file_path).get_icon();
	}
	
	public String get_sound_path(String file_path) throws Exception {
		return json_to_Settings_Class(file_path).get_sound();
	}


}
