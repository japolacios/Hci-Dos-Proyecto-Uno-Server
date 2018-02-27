import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.ReverbType;

public class Control extends Observable implements Runnable {

	private Socket s;
	private Observer boss;
	private boolean life = true;
	private int id;
	private City myCity = null;
	private Validation validation = null;

	public Control(Socket s, Observer boss, int id) {
		super();
		this.s = s;
		this.boss = boss;
		this.id = id;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (life) {
			try {
				recibir();
				Thread.sleep(100);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Problema con el cliente " + id);
				setChanged();
				boss.update(this, "finConexion");
				life = false;
				clearChanged();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void send(Object o) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
		out.writeObject(o);
		System.out.println("Servidor: Se envio " + o.getClass());
	}

	private void recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object receive = in.readObject();
		System.out.println("Cliente: Llego " + receive.getClass());
		
		if (receive instanceof City) {
			myCity = (City) receive;
		} else {
			if (receive instanceof Validation) {
				validation = (Validation) receive;
			}
		}
		System.out.println(".................."+this);
		boss.update(this, receive);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public City getCity() {
		return myCity;
	}
	
	public Validation getValidation() {
		return validation;
	}
	
	public void setValidation(Validation vali) {
		validation = vali;
	}
}