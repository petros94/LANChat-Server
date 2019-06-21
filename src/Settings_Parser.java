
public abstract class Settings_Parser {
	public abstract void import_settings(String file_path) throws Exception;
	public abstract String get_sender(String file_path) throws Exception;
	public abstract String[] get_clients(String file_path) throws Exception;
	public abstract String get_server(String file_path)throws Exception;
	public abstract String get_icon_path(String file_path)throws Exception;
}
