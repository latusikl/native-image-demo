public class Hello{

	private final static String messageValue;

	static{
		System.out.println("Static initialization called");
		final String value = System.getenv("TEST_ENV");
		messageValue = value != null ? value : "Not defined";
	}

	public static void main(String[] args){
		System.out.println(messageValue);
	}
}
