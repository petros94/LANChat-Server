
public class app {
	
	static String OS = "";
	
	public static void main(String[] args) {
		
		String path = "";
		if (args.length>0) {
			path = args[0];
		}
		else {
			path = "config/LANChat_settings_server.json";
		}
		
		try {
			OS = System.getProperty("os.name");
			if (isWindows()) {
				System.setProperty("java.library.path", args[1]);
		    	System.load(args[1] +"/swt-win32-3139.dll");
		    	System.out.println(System.getProperty("java.library.path"));
				new GepChat_ChatWindow(path);
			}
			else {
				new GepChat_ChatWindow(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}


}