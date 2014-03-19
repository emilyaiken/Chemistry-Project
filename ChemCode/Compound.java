public class Compound {
	
	public Ion cation;
	public Ion anion;
	public String name;
	public boolean soluble;
	public int cationSubscript;
	public int anionSubscript;
	public String state;
	
	public Compound(Ion cation, Ion anion) {
		this.cation = cation;
		this.anion = anion;
		
		// Sets up the name
		if (cation.getCharge() == "+1" && anion.getCharge() == "-1") {
			this.name = cation.getName() + anion.getName();
			this.cationSubscript = 1;
			this.anionSubscript = 1;
		}
		else if (cation.getCharge() == "+2" && anion.getCharge() == "-1") {
			if (anion.isPolyatomic()) {
				this.name = cation.getName() + "(" + anion.getName() + ")2";
			}
			else {
				this.name = cation.getName() + anion.getName() + "2";
			}
			this.cationSubscript = 1;
			this.anionSubscript = 2;
		}
		
		else if (cation.getCharge() == "+3" && anion.getCharge() == "-1") {
			if (anion.isPolyatomic()) {
				this.name = cation.getName() + "(" + anion.getName() + ")3";
			}
			else {
				this.name = cation.getName() + anion.getName() + "3";
			}
			this.cationSubscript = 1;
			this.anionSubscript = 3;
		}
		
		else if (cation.getCharge() == "+1" && anion.getCharge() == "-2") {
			if (cation.isPolyatomic()) {
				this.name =  "(" + cation.getName() + ")2"  + anion.getName();
			}
			else {
				this.name =  cation.getName() + "2"  + anion.getName();
			}
			this.cationSubscript = 2;
			this.anionSubscript = 1;
		}
		
		else if (cation.getCharge() == "+2" && anion.getCharge() == "-2") {
			this.name =  cation.getName() + anion.getName();
			this.cationSubscript = 1;
			this.anionSubscript = 1;
		}
		
		else if (cation.getCharge() == "+3" && anion.getCharge() == "-2") {
			if (cation.isPolyatomic() && anion.isPolyatomic()) {
				this.name =  "(" + cation.getName() + ")2" + "(" + anion.getName() + ")3";
			}
			else if (cation.isPolyatomic() && !anion.isPolyatomic()) {
				this.name =  "(" + cation.getName() + ")2" + anion.getName() + "3";
			}
			else if (!cation.isPolyatomic() && anion.isPolyatomic()) {
				this.name =  cation.getName() + "2(" + anion.getName() + ")3";
			}
			else {
				this.name =  cation.getName() + "2" + anion.getName() + "3";
			}
			this.cationSubscript = 2;
			this.anionSubscript = 3;
		}
		
		else if (cation.getCharge() == "+1" && anion.getCharge() == "-3") {
			if (cation.isPolyatomic()) {
				this.name = "(" + cation.getName() + ")3" + anion.getName();
			}
			else {
				this.name = cation.getName() + "3" + anion.getName();
			}
			this.cationSubscript = 3;
			this.anionSubscript = 1;
		}
		
		else if (cation.getCharge() == "+2" && anion.getCharge() == "-3") {
			if (cation.isPolyatomic() && anion.isPolyatomic()) {
				this.name =  "(" + cation.getName() + ")3" + "(" + anion.getName() + ")2";
			}
			else if (cation.isPolyatomic() && !anion.isPolyatomic()) {
				this.name =  "(" + cation.getName() + ")3" + anion.getName() + "2";
			}
			else if (!cation.isPolyatomic() && anion.isPolyatomic()) {
				this.name =  cation.getName() + "3(" + anion.getName() + ")2";
			}
			else {
				this.name =  cation.getName() + "3" + anion.getName() + "2";
			}
			this.cationSubscript = 3;
			this.anionSubscript = 2;
		}
		
		else if (cation.getCharge() == "+3" && anion.getCharge() == "-3") {
			this.name =  cation.getName() + anion.getName();
			this.cationSubscript = 1;
			this.anionSubscript = 1;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Ion getCation() {
		return cation;
	}
	
	public Ion getAnion() {
		return anion;
	}
	
	public int getCationSubscript() {
		return cationSubscript;
	}
	
	public int getAnionSubscript() {
		return anionSubscript;
	}
	
	public boolean isSoluble() {
		// According to Mr. O'C's solubility rules!
		
		// Always Soluble (rule 1) cations
		if (cation.getName() == "Li" || cation.getName() == "Na" || cation.getName() == "K" || cation.getName() == "Cs" ||  cation.getName() == "Rb" || cation.getName() == "NH4") {
			soluble = true;
		}
		// Always Soluble (rule 1) anions
		else if (anion.getName() == "ClO3" || anion.getName() == "NO3" || anion.getName() == "C2H3O2") {
			soluble = true;
		}
		// Insoluble (rule 2) cations
		else if (cation.getName() =="Ag" || cation.getName() == "Pb" || cation.getName() == "Hg") {
			soluble = false;
		}
		// Insoluble (rule 2) anions
		else if (anion.getName() == "PO4" || anion.getName() == "CO3" || anion.getName() == "S" || anion.getName() == "O") {
			soluble = false;
		}
		// Hydroxides (rule 2 exception)
		else if (anion.getName() == "OH") {
			if (cation.getName() == "Mg") {
				soluble = false;
			}
			else if (cation.getName() == "Be" || cation.getName() == "Ca" || cation.getName() == "Sr" || cation.getName() == "Ba") {
				soluble = true;
			}
			else {
				soluble = false;
			}
		}
		
		// Chromates (rule 2 exception)
		else if (anion.getName() == "CrO4") {
			if (cation.getName() == "Ba") {
				soluble = false;
			}
			else if (cation.getName() == "Be" || cation.getName() == "Mg" || cation.getName() == "Ca" || cation.getName() == "Sr") {
				soluble = true;
			}
			else {
				soluble = false;
			}
		}
		
		// Soluble (rule 3) anions
		else if (anion.getName() == "Cl" || anion.getName() == "Br" || anion.getName() == "I") {
			soluble = true;
		}
		
		// Sulfates (rule 3 exception)
		else if (anion.getName() == "SO4") {
			if (cation.getName() == "Ca" || cation.getName() == "Sr" || cation.getName() == "Ba") {
				soluble = false;
			}
			else {
				soluble = true;
			}
		}
		
		else {
			soluble = false;
		}
		
		return soluble;
	}
	
	public String getState() {
		if (!isSoluble()) {
			return "(s)";
		}
		else {
			return "(aq)";
		}
	}
	


}


