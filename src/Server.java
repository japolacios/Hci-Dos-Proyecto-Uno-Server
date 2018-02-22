import processing.core.PApplet;

public class Server extends PApplet {
	
	private Logica log;
	
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		PApplet.main("Server");
	}
	
	@Override
	public void settings() {
		size(1280, 1024);
	}
	
	@Override
	public void setup() {
		log = new Logica(this);
		imageMode(CENTER);
		textAlign(CENTER);
	}
	
	@Override
	public void draw() {
		background(0);
		log.ejecutar();
	}
	
	@Override
	public void keyPressed() {
		log.key();
	}
}