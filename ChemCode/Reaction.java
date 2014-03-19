import java.util.*;
public class Reaction {
	public static Compound reactant1;
	public static Compound reactant2;
	public static Compound product1;
	public static Compound product2;
	public static int reactant1Balance = 1;
	public static int reactant2Balance = 1;
	public static int product1Balance = 1;
	public static int product2Balance = 1;
	public static ArrayList<Ion> product1cations;
	public static ArrayList<Ion> product2cations;
	public static ArrayList<Ion> product1anions;
	public static ArrayList<Ion> product2anions;
	public int repeats;

	
	
	public Reaction(Compound reactant1, Compound reactant2) {
		this.reactant1 = reactant1;
		this.reactant2 = reactant2;
		this.product1 = new Compound(reactant1.getCation(), reactant2.getAnion());
		this.product2 = new Compound(reactant2.getCation(), reactant1.getAnion());
		
		
	}
	
	public String toString() {
		String reactant1String;
		String reactant2String;
		String product1String;
		String product2String; 
		int reactant1Co = Balance()[0];
		if (reactant1Co == 1) {
			reactant1String = reactant1.getName();
		}
		else {
			reactant1String = reactant1Co + reactant1.getName();
		}
		int reactant2Co = Balance()[1];
		if (reactant2Co == 1) {
			reactant2String = reactant2.getName();
		}
		else {
			reactant2String = reactant2Co + reactant2.getName();
		}
		int product1Co = Balance()[2];
		if (product1Co == 1) {
			product1String = product1.getName();
		}
		else {
			product1String = product1Co + product1.getName();
		}
		int product2Co = Balance()[3];
		if (product2Co == 1) {
			product2String = product2.getName();
		}
		else {
			product2String = product2Co + product2.getName();
		}

		return reactant1String + reactant1.getState() + " + " + reactant2String + reactant2.getState() + " --> " + product1String + product1.getState() + " + " + product2String + product2.getState();
	} 
	
	public ArrayList<Ion> getIons() {
		configureRepeats();
		System.out.println("repeats:" + repeats);
		ArrayList<Ion> ionList = new ArrayList<Ion>();
		for (int i = 0; i<reactant1Balance; i++) {
			for (int n = 0; n<repeats; n++) {
				for (int a=0; a<reactant1.getCationSubscript(); a++) {
					ionList.add(reactant1.cation);
				}
			}
			for (int n = 0; n<repeats; n++) {
				for (int b=0; b<reactant1.getAnionSubscript(); b++) {
					ionList.add(reactant1.anion);
				}
			}
		}
		for (int j=0; j< reactant2Balance; j++) {
			for (int n = 0; n<repeats; n++) {
				for (int a=0; a<reactant2.getCationSubscript(); a++) {
					ionList.add(reactant2.cation);
				}
			}
			for (int n = 0; n<repeats; n++) {
				for (int b=0; b<reactant2.getAnionSubscript(); b++) {
					ionList.add(reactant2.anion);
				}
			}
		}
		return ionList;
		
	}
	
	public void configureRepeats() {
		int numberIons = reactant1Balance*reactant1.cationSubscript + reactant1Balance*reactant1.anionSubscript + reactant2Balance*reactant2.cationSubscript + reactant2Balance*reactant2.anionSubscript;
		if(numberIons > 10) {
			repeats = 1;
		}
		else if(numberIons <= 5) {
			repeats = 3;
		}
		else if(numberIons <= 10) {
			repeats = 2;
		}
	}
	
	public static int[] Balance() {
		int[] coefficients = new int[4];
		int reactant1co = 0;
		int reactant2co = 0;
		int product1co = 0;
		int product2co = 0;
	
		for (int i=1; i<=6; i++) {
			for (int j=1; j<= 6; j++) {
				for (int k=1; k<=6; k++) { {
					for (int l=1; l<=6; l++) {
						reactant1co = i;
						reactant2co = j;
						product1co = k;
						product2co = l;
						if (
							coefficients[0] == 0 &&
							reactant1co * reactant1.getCationSubscript() == product1co * product1.getCationSubscript() &&
							reactant1co * reactant1.getAnionSubscript() == product2co * product2.getAnionSubscript() &&
							reactant2co * reactant2.getCationSubscript() == product2co * product2.getCationSubscript() &&
							reactant2co * reactant2.getAnionSubscript() == product1co * product1.getAnionSubscript()) {
								coefficients[0] = reactant1co;
								coefficients[1] = reactant2co;
								coefficients[2] = product1co;
								coefficients[3] = product2co;
 						}
						else {}
					}
				}
			}
		}
	}
	reactant1Balance = coefficients[0];
	reactant2Balance = coefficients[1];
	product1Balance = coefficients[2];
	product2Balance = coefficients[3];
	return coefficients;
	
	}

	
	public Compound getPrecipitate() {
		if (product1.isSoluble() && product2.isSoluble()) {
			return null;
		}
		else if (product1.isSoluble() && !product2.isSoluble()) {
			return product2;
		}
		else if (!product1.isSoluble() && product2.isSoluble()) {
			return product1;
		}
		else {
			return product1;
		}
	}
}



