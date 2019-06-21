import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;


import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.List;

public class GepChat_ChatWindow {

	protected Shell shell;
	private Text message;
	private Main_controller controller;
	private String title_var;
	public Text chat;
	public volatile String temp_text;
	private Label icon;
	public volatile boolean update_text_flag;
	private List list;
	private String[] original_list_names;
	private String[] temp_list_names;

	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public GepChat_ChatWindow(String settings_path) {
		title_var = "";
		temp_text = "";
		update_text_flag = false;
		createContents();
		controller = new Main_controller(this, settings_path);
		String icon_path = controller.get_icon_path(settings_path);
		icon.setImage(SWTResourceManager.getImage(icon_path));
		open();
	}
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		shell.open();
		shell.layout();
		controller.start_updater();	
		display.timerExec(1000, controller.updater);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			if (update_text_flag == true) {
				display.wake();
				update_Text();
				update_text_flag = false;
			}
		}				
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		String s = "Gep Chat - " + title_var;
		shell.setText(s);
		
		icon = new Label(shell, SWT.NONE);
		icon.setImage(SWTResourceManager.getImage("/Users/petros/eclipse-workspace/GEPchat_v0.01/src/earth8bit.png"));
		icon.setBounds(274, 0, 70, 53);
		
		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(17, 223, 430, 2);
		
		message = new Text(shell, SWT.BORDER);
		message.addKeyListener(new MessageKeyListener());
		message.setBounds(18, 238, 328, 29);
		
		Button btnSend = new Button(shell, SWT.NONE);
		btnSend.addSelectionListener(new BtnSendSelectionListener());
		btnSend.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 14, SWT.NORMAL));
		btnSend.setBounds(363, 235, 64, 35);
		btnSend.setText("Send");
		
		chat = new Text(shell, SWT.READ_ONLY | SWT.V_SCROLL);
		chat.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		chat.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		chat.addModifyListener(new ChatModifyListener());
		chat.setBounds(0, 0, 361, 211);
		chat.setTopIndex(chat.getLineCount()-1);
		
		list = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		list.addSelectionListener(new ListSelectionListener());
		list.setItems(new String[] {});
		list.setBounds(362, 0, 88, 212);
	

	}
	
	public void update_Text() {
		chat.setText(temp_text);
		chat.setTopIndex(chat.getLineCount() - 1);
		list.setItems(temp_list_names);
	}
	
	private class BtnSendSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			controller.send_message(message.getText());
			message.setText("");
		}
	}
	private class MessageKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.character == 13) {
				controller.send_message(message.getText());
				message.setText("");
			}
		}
	}
	private class ChatModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent arg0) {
		}
	}
	private class ListSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			int idx = list.getSelectionIndex();
			if (idx>=0){
				controller.set_active_comm(idx);
				controller.clear_chat_text();
				reset_chat_list(idx);
			}
			
		}
	}
	
	public void set_title_var(String title) {
		title_var = title;
		String s = "Gep Chat - " + title_var;
		shell.setText(s);
	}
	
	public void add_to_chat_list(String name) {
		// TODO Auto-generated method stub
		list.add(name);
	}
	
	public void update_chat_list (int idx, String new_name){
		temp_list_names[idx] = new_name;
	}
	
	public void reset_chat_list () {
		temp_list_names = original_list_names.clone();
	}
	
	public void reset_chat_list (int idx) {
		temp_list_names[idx] = original_list_names[idx];
	}
	
	public String[] get_chat_list() {
		return list.getItems();
	}
	
	public void set_original_list_names() {
		original_list_names = list.getItems().clone();
		temp_list_names = original_list_names.clone();
	}
	
}
