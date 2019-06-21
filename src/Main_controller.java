import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.widgets.Text;


public class Main_controller {
	private ArrayList<Backend_Proxy> comms;
	private Backend_Proxy active_comm;
	private GepChat_ChatWindow chatWindow_GUI;
	public Chat_Updater updater;
	private String settings_path;
	
	public Main_controller(GepChat_ChatWindow chatWindow_GUI, String settings_path) {
		this.settings_path = settings_path;
		configure_server(settings_path);
		init_gui(chatWindow_GUI, settings_path);
	}
	
	public void configure_server(String settings_path) {
		/* Import settings file - using json parser*/
		Settings_Parser settings_parser = new json_Settings_Parser();
		comms = new ArrayList<Backend_Proxy>();
		try {
			ArrayList<String> client_address = new ArrayList<String>(Arrays.asList(settings_parser.get_clients(settings_path)));
			String server = settings_parser.get_server(settings_path);
		
		/* Initialize communication*/
		for (int i = 0; i< client_address.size(); i++) {
			comms.add(new Http_Proxy("gep", client_address.get(i), server));
		}
		active_comm = comms.get(0);
		
		
		} catch (Exception e) {
			
		}
		
		
	}
	
	public void init_gui(GepChat_ChatWindow chatWindow_GUI, String settings_path) {
		/* Initialize chat window*/
		this.chatWindow_GUI = chatWindow_GUI;
		
		/*Modify clients chat list*/
		for (int i = 0; i<comms.size(); i++) {
			chatWindow_GUI.add_to_chat_list(comms.get(i).get_receiver());
		}
		
		/* set titles and text*/
		chatWindow_GUI.set_original_list_names();
		String title = "";
		try {
			title = (new json_Settings_Parser()).get_sender(settings_path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatWindow_GUI.set_title_var(title);
		
	}
	
	
	public void send_message(String message) {
		try {
			/* Call backend_proxy*/
			active_comm.send("GEP: " + message);
			
			/*Update chat text*/
			updater.update_chat();
			updater.increment_counter(comms.indexOf(active_comm));

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			set_chat_text("Message failed to send!\n");
		}
	}
	
	
	public ArrayList<Backend_Proxy> get_comms() {
		return comms;
	}
	
	public Backend_Proxy get_active_comm() {
		return active_comm;
	}
	
	public void set_active_comm(int idx) {
		active_comm = comms.get(idx);
	}
	
	
	public void start_updater() {
		updater = new Chat_Updater(chatWindow_GUI, this);
		try {
			updater.set_player(settings_path);
		} catch (Exception e) {
		}	
	}
	
	public void update_chat_text() {
		try {
			updater.update_chat();
		} catch (Exception e) {
			System.out.println("Failed to update chat");
		}
	}
	
	public void set_chat_text(String new_text) {
		chatWindow_GUI.temp_text += new_text;
	}
	
	public Text get_chat_text() {
		return chatWindow_GUI.chat;
	}


	public void set_update_text_flag(boolean value) {
		// TODO Auto-generated method stub
		chatWindow_GUI.update_text_flag = value;
		
	}
	
	public boolean get_window_status() {
		return !chatWindow_GUI.shell.isDisposed();
	}

	public void clear_chat_text() {
		// TODO Auto-generated method stub
		chatWindow_GUI.temp_text = "";
		
	}
	
	public String get_icon_path(String settings_path) {
		try {
			return new json_Settings_Parser().get_icon_path(settings_path);
		} catch (Exception e) {
			return "";
		}
		
	}

	public void highlight_chat_list_name(int client_idx) {
		// TODO Auto-generated method stub
		String name = chatWindow_GUI.get_chat_list()[client_idx];
		name = "-> " + name;
		chatWindow_GUI.update_chat_list(client_idx, name);
	}
	
	public String get_sound_path(String settings_path) {
		try {
			return new json_Settings_Parser().get_sound_path(settings_path);
		} catch (Exception e) {
			return "";
		}
		
	}

}
