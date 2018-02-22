import java.io.Serializable;

public class Validation implements Serializable {

    /**
	 * Types -> 1:enable - 2:disable - 3:askEnergy - 4:sendEnergy - 5:endTurn - 6:ImDead - 7:SomeOneDied - 8:start
	 */
	private static final long serialVersionUID = 1L;
	private boolean check;
    private int type;
    private int energy;
    
	public Validation(boolean check, int type, int _energy) {
		super();
		this.check = check;
		this.type = type;
		energy = _energy;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setEnergy(int _energy) {
		energy = _energy;
	}
	public int getEnergy() {
		return energy;
	}
	
	
}