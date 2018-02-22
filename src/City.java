import java.io.Serializable;

public class City implements Serializable {
	
	private float energy;
	private int population;
	private int houses;
	private int capacity;
	
	public City(float energy, int population, int houses, int capacity) {
		super();
		this.energy = energy;
		this.population = population;
		this.houses = houses;
		this.capacity = capacity;
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