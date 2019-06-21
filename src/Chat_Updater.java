import org.eclipse.swt.widgets.Display;

public class Chat_Updater implements Runnable {
	private Main_controller controller;
	private int[] messageCounter;
	json_parser parser;
	String_modifier modifier;
	AudioPlayer player;

	
	public Chat_Updater(GepChat_ChatWindow chatWindow_GUI, Main_controller controller) {
		this.controller = controller;
		this.messageCounter = new int[controller.get_comms().size()];
		parser = new json_parser();
		modifier = new String_modifier("&");

	}
	
	public void run() {
		int client_idx = 0;
		for (int i  = 0; i<controller.get_comms().size(); i++) {
			client_idx = client_idx % controller.get_comms().size();
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				System.out.println("Fatal error");
			}
			try {
				if (check_for_new_messages(client_idx)) {
					//play sound
					if (player != null) {
						//If a sound file is available
						//play sound notification
						player.restart();
					}
					controller.highlight_chat_list_name(client_idx);
				}
				update_chat();

			} catch (Exception e) {
				System.out.println(e);
				controller.set_chat_text("Connection Timeout or Backend Error.\n");
				controller.set_update_text_flag(true);
			}
			client_idx++;
		}
		Display.getCurrent().timerExec(1000, this);
	}
	
	public void update_chat() throws Exception {
		controller.clear_chat_text();
		String ret = controller.get_active_comm().receive();
		String ret_msg = parser.parse_message(ret);

		String[] messages = modifier.string_to_messages(ret_msg);
		
		
		for (int i = 0; i<messages.length; i++) {
			controller.set_chat_text(messages[i] + "\n");
			System.out.println( messages[i]);
		}

		
		controller.set_update_text_flag(true);		
	}
	
	
	public boolean check_for_new_messages(int client_idx) throws Exception{
		String ret = controller.get_comms().get(client_idx).receive();
		String ret_msg_counter = parser.parse_counter(ret);
		if (Integer.parseInt(ret_msg_counter) <= messageCounter[client_idx]) {
			return false;
		}
		else {
			messageCounter[client_idx] = Integer.parseInt(ret_msg_counter);
			return true;
		}
	}
	
	public void set_player(String settings) {
		try {
			//Init new player
			this.player = new AudioPlayer(controller.get_sound_path(settings));
		} catch (Exception e){
			//if the sound path doesn't exist player is null
		}
	}
	
//	public void init_counter() throws Exception{
//		String ret = controller.get_coms().receive();
//		String ret_msg_counter = parser.parse_counter(ret);
//		messageCounter = Integer.parseInt(ret_msg_counter);
//	}
	
	public void increment_counter(int idx) {
		messageCounter[idx] ++;
	}


}
	
