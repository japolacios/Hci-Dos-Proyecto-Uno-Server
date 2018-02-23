import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Logica implements Observer {

	private PApplet app;
	private Communication com;
	private Ui ui;
	private final static int TOTAL_USERS = 2;
	private final static int TIMEKEEPER = 15;

	private int time;
	private int offsetTime;
	private int turn;
	private int subturn;
	private boolean init, change = false;
	private boolean rondaDone;
	
	private int mes;
	private int cargaBateria;
	private int cargaMes;
	private double restarTemporada;
	private boolean actualPidio;
	private ArrayList<Control> usuarios;
	
	
	private PFont fuente;
	private int temporada;

	/* primavera 0, verano 1, otono 2, invierno 3 */

	/*
	 * ID's para las pruebas
	 * 
	 * 1 Vergara 2 Jairo 3 Ana 3 Jaime 4 Alitza 5 Jhonatan 6 Julio 7 Jaime 8 Cuadros
	 * 9 Camilo
	 */

	public Logica(PApplet app) {
		super();
		this.app = app;

		ui = new Ui(app);
		com = new Communication();
		new Thread(com).start();
		com.addObserver(this);
		usuarios = new ArrayList<Control>();
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}


	public void init() {
		mes = 1;
		temporada = 1;
		rondaDone = false;
		cargaMes = 6000;
		actualPidio = false;
		
	}
	
	public void ejecutar() {
		ui.pintarUi();
		/*
		if (init == true) {
			time = ((app.millis() - offsetTime));
			System.out.println("Time " + time / 10);

			if ((time / 10) % (TIMEKEEPER * 100) == 0) {
				change = true;
			} else {
				change = false;
			}

			if (change && subturn <= TOTAL_USERS - 1) {
				com.sendTurn(false, subturn);
				subturn++;
				com.sendTurn(true, subturn);
			} else if (change && subturn > TOTAL_USERS - 1) {
				com.sendTurn(false, subturn);
				turn++;
				subturn = 0;
				com.sendTurn(true, subturn);
			}
		}*/


	}

	public void key() {
		if (app.keyPressed) {
			System.out.println("Simulation started!");
			if (app.key == PApplet.ENTER && !init) {
				com.sendAll(8);
				//turn = 1;
				//subturn = 0;
				init = true;
				cloneList();
				cambiarTurno();
			}
		}
	}
	
	public void cloneList() {
		
		if(!usuarios.isEmpty() || usuarios != null) {
			usuarios = new ArrayList<Control>();	
		}
		
	    for (Control control : com.users) {
	        usuarios.add(control);
	    }
	}
	
	//Cambio de mes
	public void cambioMes() {
		//Cambio Numero de mes
			if(mes < 12) {
				mes++;
				
			} else {
				mes = 1;
		}
		//Vuelve y le da a los turnos
		usuarios = com.users;
		//Revisa el cambio de esgtacion
		cambioEstacion();
		generarEnergia();
		cambiarTurno();
	}
	
	//Cambio de estacion
	public void cambioEstacion() {
		if(mes >= 1 && mes<=3) {
			temporada = 0;
		}
		if(mes > 3 && mes<=6) {
			temporada = 1;
		}
		if(mes > 6 && mes<=9) {
			temporada = 2;
		}
		if(mes > 9 && mes<=12) {
			temporada = 3;
		}
	}
	//Random de turnos
	
	//terminar turno
	public Validation terminarTurno() {
		Validation tempValidation = new Validation(true,2,0);
		return tempValidation;
	}
	//generar energia
	public void generarEnergia() {
		if(temporada == 0 || temporada == 2) {
			restarTemporada = 0.6;
		}
		if(temporada == 1) {
			restarTemporada = 0.7;
		}
		if(temporada == 3) {
			restarTemporada = 0.4;
		}
		cargaBateria = cargaBateria + (int) (cargaMes*restarTemporada);
	}
	
	public void cambiarTurno() {
		if(!usuarios.isEmpty() && usuarios != null) {
		actualPidio = false;
		int randomTurno = (int) app.random(0, usuarios.size());
		Control user = usuarios.get(randomTurno);
		Validation val = new Validation(true,1,0);
		try {
			user.send(val);
			usuarios.remove(randomTurno);
			System.out.println("Mande el turno al man:" + randomTurno );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(usuarios.size() <= 0 || usuarios.isEmpty()){
			//Se acabaron los turnos
			cloneList();
			cambioMes();
		}
	}
	//enviar energia
	public Validation enviarEnergia(int _energia) {
		int energiaD = 0;
		if (_energia>= cargaBateria) {
			energiaD = cargaBateria;
			cargaBateria = 0;
		} else {
			energiaD = _energia;
			cargaBateria = cargaBateria - _energia;
		}
		
		Validation tempValidation = new Validation(true, 4, energiaD);
		return tempValidation;
	}
	//alguien murio
	
	@Override
	public void update(Observable o, Object arg) {
		ui.setUsers(com.users);
		System.out.println("usuariesitos"+ com.users.size());
		if(arg instanceof Control) {
			Control usuarioActual = (Control) arg;
			//Recibe Mensajes de los Clientes
			
			//Recibe Ciudades
			if(usuarioActual.getCity() != null) {
				ui.setUsers(com.users);
			}
				
			//Recibe Logica de Control
				if(usuarioActual.getValidation() != null) {
					System.out.println("Got Validation");
					Validation tempValidation = usuarioActual.getValidation();
					if(tempValidation.getType() == 3 && actualPidio == false) {
						//Pide Energia
						int energiaPedida = tempValidation.getEnergy();
						try {
							usuarioActual.send(enviarEnergia(energiaPedida));
							actualPidio = true;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(tempValidation.getType() == 5) {
						//Termino su turno
						try {
							System.out.println(usuarioActual+ " Terminio turno");
							usuarioActual.send(terminarTurno());
							cambiarTurno();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cambiarTurno();
					}
					if(tempValidation.getType() == 6) {
						//Se murio
						com.sendAll(7);
					}
				}
			
		}
	}
}