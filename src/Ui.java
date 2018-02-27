
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;

public class Ui{
	private ArrayList<PImage> fondos, baterias, headers, usuarios;
	int temporada = 1;
	private ArrayList<Control> users;

	private PApplet app;

	public Ui(PApplet app) {
		this.app = app;
		inicializarArrayList();
		loadImages();
	}

	public void loadImages() {
		cargarImgBateria();
		cargarImgHeader();
		cargarImgTemporadas();
		cargarImgUsuario();
	}

	public void inicializarArrayList() {
		fondos = new ArrayList();
		baterias = new ArrayList();
		headers = new ArrayList();
		usuarios = new ArrayList();
		users = new ArrayList<Control>();
		System.out.println("Se inicializan arraylist de img");
	}

	public void pintarUi() {
		app.image(fondos.get(temporada), app.width / 2, app.height / 2);
		app.image(headers.get(temporada), app.width / 2, 100);
		//System.out.println(users.size());
		app.fill(0);
		app.textSize(20);
		if (users.size() > 0 && !users.isEmpty()) {
			//System.out.println("LALA: " + users.size());
			for (int i = 0; i < users.size(); i++) {
				//System.out.println(i);
				if (users.get(i).getCity()!=null) {
					if (i < 5) {
						//System.out.println(users.get(i).getCity().getEnergy());
						//app.image(usuarios.get(temporada), app.width / 4, 250 + (i * 150));
						app.text(users.get(i).getCity().getEnergy(), (app.width / 4 + 60), (i * 150)+ 300);
						app.text(users.get(i).getCity().getHouses(), (app.width / 4 - 60), (i * 150)+300);
						app.text(users.get(i).getCity().getPopulation(), (app.width / 4 + 200), (i * 150)+300);
					} else {
						// i = 0;
						//app.image(usuarios.get(temporada), (app.width) * 3 / 4, 250 + ((i * 150) - 750));
						app.text(users.get(i).getCity().getEnergy(), ((app.width) * 3 / 4 + 60), (250 + (i * 150) - 700));
						app.text(users.get(i).getCity().getHouses(), ((app.width) * 3 / 4 - 60), (250 + (i * 150) - 700));
						app.text(users.get(i).getCity().getPopulation(), ((app.width) * 3 / 4 + 200), (250 + (i * 150) - 700));
					}	
				}
		
				/*
				app.text(users.get(i).getCity().getEnergy(), 380, 300);
				app.text(users.get(i).getCity().getHouses(), 260, 300);
				app.text(users.get(i).getCity().getPopulation(), 525, 300);
				*/
			}
		}
		for (int i = 0; i < 10; i++) {
			
			if (i < 5) {
			app.image(usuarios.get(temporada), app.width / 4, 250 + (i * 150));}
			
			else {
				app.image(usuarios.get(temporada), (app.width) * 3 / 4, 250 + ((i * 150) - 750));
			}
		}

	}

	public void cargarImgTemporadas() {

		for (int i = 1; i < 5; i++) {
			PImage temporadaTemp = new PImage();
			temporadaTemp = app.loadImage("fondo/fondo_" + i + ".png");
			fondos.add(temporadaTemp);
		}
		System.out.println("Se cargan las img de fondo de temporadas");
	}

	public void cargarImgUsuario() {

		for (int i = 1; i < 5; i++) {
			PImage usuarioTemp = new PImage();
			usuarioTemp = app.loadImage("usuario/usuario_" + i + ".png");
			usuarios.add(usuarioTemp);
		}

		System.out.println("Se cargan las img de usuarios");
	}

	public void cargarImgHeader() {
		for (int i = 1; i < 5; i++) {
			PImage headerTemp = new PImage();
			headerTemp = app.loadImage("header/header_" + i + ".png");
			headers.add(headerTemp);
		}
		System.out.println("Se cargan las img de fondo de headers");
	}

	public void cargarImgBateria() {

		for (int i = 0; i < 6; i++) {
			PImage bateriaTemp = new PImage();
			bateriaTemp = app.loadImage("bateria/b_" + i + ".png");
			baterias.add(bateriaTemp);
		}
		System.out.println("Se cargan las img de fondo de baterias");
	}

	public void setUsers(ArrayList<Control> _users) {
		users = _users;
	}

	/*
	 * @Override public void update(Observable o, Object arg) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
}