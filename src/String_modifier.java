public class String_modifier {
	
	private String delimeter;
	public String_modifier(String delimeter) {
		this.delimeter = delimeter;
	}
	
	public String[] string_to_messages(String string){
		return string.split(delimeter);
	}
}
