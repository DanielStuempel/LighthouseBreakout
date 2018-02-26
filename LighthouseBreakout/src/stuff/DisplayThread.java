package stuff;

public class DisplayThread extends Thread {
	Display display;
	String[] args;
	
	public DisplayThread(Display display) {
		super("Display Thread");
		this.display = display;
	}
	
	public void start(String args) {
		start(args.split("\\s"));
	}
	
	public void start(String... args) {
		this.args = args;
		start();
	}
	
	@Override
	public void run() {
		display.start(args);
	}
}