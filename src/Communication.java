import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Communication extends Observable implements Observer, Runnable {

	private static final int PORT = 5000;
	private boolean life = true;
	private ServerSocket sS;
	public ArrayList<Control> users;
	//private City city;
	private float energy = 700;
	private int population = 10;
	private int houses = 5;
	private int capacity = 10;
	

	public Communication() {

		life = true;
		users = new ArrayList<Control>();

		//city = new City(energy, population, houses, capacity);

		try {
			sS = new ServerSocket(PORT);
			System.out.println("Servidor iniciado");
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (Exception e) {
		    throw new RuntimeException("Could not create server socket.", e);
		}
 
	}

	@Override
	public void run() {
		while (life) {
			try {
				System.out.println("Esperando...");
				users.add(new Control(sS.accept(), this, users.size() + 1));
				//,city)
				System.out.println("Usuarios totales: " + users.size());
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendAll(int cmd) {
		try {
			for (Control control : users) {
				control.send(new Validation(true, cmd,0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTurn(boolean cmd, int who) {
		try {
			users.get(who).send(new Validation(cmd, 1,0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof String)) {
			setChanged();
			notifyObservers(o);
			clearChanged();
		} else if (arg instanceof String) {
			String not = (String) arg;
			if (not.contains("finConexion")) {
				users.remove(o);
				System.out.println("Clientes restantes: " + users.size());
			}
		}
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getHouses() {
		return houses;
	}

	public void setHouses(int houses) {
		this.houses = houses;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}