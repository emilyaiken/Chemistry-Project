import java.util.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class Game extends JPanel {
	final static int FRAME_HEIGHT = 600;
	final static int FRAME_WIDTH = 600;
	static final int RECT_HEIGHT = 500;
	static final int RECT_WIDTH = 500;
	static final int CATION_RADIUS = 40;
	static final int ANION_RADIUS = 30;
	static final int POLYATOMIC_RADIUS = 50;
	Ion willBeCenter1 = null;
	Ion willBeOuter1 = null;
	Ion willBeCenter2 = null;
	Ion willBeOuter2 = null;
	static Compound reactant1;
	static Compound reactant2;
	Reaction myReaction;
	ArrayList<Integer> precipitateCenters1 = new ArrayList<Integer>();
	ArrayList<Integer> precipitateOuters1 = new ArrayList<Integer>();
	ArrayList<Integer> extras1 = new ArrayList<Integer>();
	ArrayList<Integer> precipitateCenters2 = new ArrayList<Integer>();
	ArrayList<Integer> precipitateOuters2 = new ArrayList<Integer>();
	ArrayList<Integer> extras2 = new ArrayList<Integer>();
	int counter1;
	int counter2;
	static boolean playSimulation = false;
	public int repeats = 0;
	static boolean shouldExit = false;
	static boolean newIons = false;
	public static ArrayList<Ion> ions = new ArrayList<Ion>();
	public static ArrayList<Compound> precipitates = new ArrayList<Compound>();
	
	// 3LiFl + AlBr3
	public Game() {
		myReaction =  new Reaction(reactant1, reactant2);
		// balances reaction
		myReaction.toString();
		if (!myReaction.product1.isSoluble()) {
			precipitates.add(myReaction.product1);
		}
		if (!myReaction.product2.isSoluble()) {
			precipitates.add(myReaction.product2);
		}
		getIons();
		if (!myReaction.product1.isSoluble() && !myReaction.product2.isSoluble()) {
			assignFollowers(myReaction.product1, precipitateCenters1, precipitateOuters1, extras1, willBeCenter1, willBeOuter1);
			assignFollowers(myReaction.product2, precipitateCenters2, precipitateOuters2, extras2, willBeCenter2, willBeOuter2);
		}
		else if (myReaction.getPrecipitate() != null) {
			assignFollowers(myReaction.product1, precipitateCenters1, precipitateOuters1, extras1, willBeCenter1, willBeOuter1);
		}
		

		
	}
	public void getIons() {
		for (int i=0; i<myReaction.getIons().size(); i++) {
			Ion currentIon = myReaction.getIons().get(i);
			ions.add(new Ion(currentIon.getName(), currentIon.getCharge()));
			if (!myReaction.product1.isSoluble() && !myReaction.product2.isSoluble()) {
				setUpCenterOuterLists(myReaction.product1, currentIon, i, precipitateCenters1, precipitateOuters1, extras1, willBeCenter1, willBeOuter1, true);
				setUpCenterOuterLists(myReaction.product2, currentIon,  i, precipitateCenters2, precipitateOuters2, extras2, willBeCenter2, willBeOuter2, false);
			}
			else if (myReaction.getPrecipitate() != null) {
				setUpCenterOuterLists(myReaction.getPrecipitate(), currentIon,  i, precipitateCenters1, precipitateOuters1, extras1, willBeCenter1, willBeOuter1, true);
			}
		}
		
	}
	public void setUpCenterOuterLists(Compound currentPrecipitate, Ion currentIon, int i, ArrayList<Integer> precipitateCenters, ArrayList<Integer> precipitateOuters, ArrayList<Integer> extras, Ion willBeCenter, Ion willBeOuter, boolean first) {
		
		//for (int j=0; j<precipitates.size(); j++) {
			//Compound currentPrecipitate = precipitates.get(j);
			// everything except 3 of one ion, two of another
			if(!((currentPrecipitate.cation.getChargeAsInt() == 3 && currentPrecipitate.anion.getChargeAsInt() == -2) || (currentPrecipitate.cation.getChargeAsInt() == 2 && currentPrecipitate.anion.getChargeAsInt() == -3))) {
				if (Math.abs(currentPrecipitate.cation.getChargeAsInt()) > Math.abs(currentPrecipitate.anion.getChargeAsInt())) {
					willBeCenter = currentPrecipitate.cation;
					willBeOuter = currentPrecipitate.anion;
				}
				else {
					willBeCenter = currentPrecipitate.anion;
					willBeOuter = currentPrecipitate.cation;
				}
				if (currentIon == willBeCenter) {
					precipitateCenters.add(i);
				}
				if (currentIon == willBeOuter) {
					precipitateOuters.add(i);
				}
			}
			else {
				int counter;
				if (first) {
					counter = counter1;
				}
				else {
					counter = counter2;
				}
				if (Math.abs(currentPrecipitate.cation.getChargeAsInt()) > Math.abs(currentPrecipitate.anion.getChargeAsInt())) {
					willBeCenter = currentPrecipitate.anion;
					willBeOuter = currentPrecipitate.cation;
				}
				else {
					willBeCenter = currentPrecipitate.cation;
					willBeOuter = currentPrecipitate.anion;
				}
				if (currentIon == willBeCenter && counter % 3 == 0) {
					precipitateCenters.add(i);
					counter++;
				}
				else if (currentIon == willBeCenter && counter % 3 != 0){
					extras.add(i);
					counter++;
				}
				else if (currentIon == willBeOuter) {
					precipitateOuters.add(i);
				}
				if (first) {
					counter1 = counter;
				}
				else {
					counter2 = counter;
				}
			}
			
			if (first) {
				willBeCenter1 = willBeCenter;
				willBeOuter1 = willBeOuter;
				precipitateCenters1 = precipitateCenters;
				precipitateOuters1 = precipitateOuters;
			}
			else {
				willBeCenter2 = willBeCenter;
				willBeOuter2 = willBeOuter;
				precipitateCenters2 = precipitateCenters;
				precipitateOuters2 = precipitateOuters;
			}

		}

	
	public void assignFollowers(Compound currentPrecipitate, ArrayList<Integer> precipitateCenters, ArrayList<Integer> precipitateOuters, ArrayList<Integer> extras, Ion willBeCenter, Ion willBeOuter) {
		if (myReaction.getPrecipitate() != null) {
			// not three of one ion, two of another
			if(!((currentPrecipitate.cation.getChargeAsInt() == 3 && currentPrecipitate.anion.getChargeAsInt() == -2) || (myReaction.getPrecipitate().cation.getChargeAsInt() == 2 && myReaction.getPrecipitate().anion.getChargeAsInt() == -3))) {
				for (int i=0; i<precipitateOuters.size(); i++) {
					ions.get(precipitateOuters.get(i)).setLeader(ions.get(precipitateCenters.get(Math.abs(i/(willBeCenter.getChargeAsInt()/willBeOuter.getChargeAsInt())))));
				}
				for (int i=0; i<precipitateCenters.size(); i++) {
					ions.get(precipitateCenters.get(i)).prepCenter();
				}
			}
			// three of one ion, two of another
			else {
				// outer ions (groups of 3)
				for (int i=0; i<precipitateOuters.size(); i++) {
					ions.get(precipitateOuters.get(i)).setLeader(ions.get(precipitateCenters.get((int) (.25*i))));
				}
				// central ions that will be on the outside
				for (int i =0; i<extras.size(); i++) {
					ions.get(extras.get(i)).setLeader(ions.get(precipitateCenters.get((int) (i*.5))));
				}
				
				// central ions
				for (int i=0; i<precipitateCenters.size(); i++) {
					if((myReaction.getPrecipitate().cation.getChargeAsInt() == 3 && myReaction.getPrecipitate().anion.getChargeAsInt() == -2) || (myReaction.getPrecipitate().cation.getChargeAsInt() == 2 && myReaction.getPrecipitate().anion.getChargeAsInt() == -3)) {
						ions.get(precipitateCenters.get(i)).orderEventualFollowers();
					}
					ions.get(precipitateCenters.get(i)).prepCenter();
				}
			}
		}
	}

	public void move() {
		for (int i=0; i<ions.size(); i++)  {
				ions.get(i).moveIon();	
		}
	}
	


	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Font ionNames = new Font("Courier", Font.BOLD, 9);
		Font forEquation = new Font("Courier", Font.BOLD, 15);
		g2d.setFont(ionNames);
		for (int i=0; i<ions.size(); i++) {
			Ion myIon = ions.get(i);
			String chargeForName;
			if (myIon.charge == "+1") {
				chargeForName = "+";
			}
			else if (myIon.charge == "-1") {
				chargeForName = "-";
			}
			else {
				chargeForName = myIon.charge;
			}
			String fullName = myIon.name + chargeForName;
			g2d.setColor(myIon.color);
			g2d.fillOval(myIon.locX, myIon.locY, myIon.diameter, myIon.diameter);
			g2d.setColor(Color.BLACK);
			g2d.drawString(fullName, myIon.locX + myIon.diameter/2 + fullName.length()*-2, myIon.locY + myIon.diameter/2 + 4);
		}
		// draw rectangle
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, RECT_WIDTH, RECT_HEIGHT);
		
		// draw eqation
		g2d.setFont(forEquation);
		g2d.drawString(myReaction.toString(), 5, RECT_HEIGHT + 30);
		}


	


	public static void main(String[] args) throws InterruptedException {
		MyFrame frame = new MyFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Popup();
		while (playSimulation == false) {
			if (shouldExit == true) {
				System.exit(0);
			}
			Thread.sleep(10);
		}
			Game game = new Game();
			frame.add(game);
			frame.setVisible(true);
			newIons = false;
			while (true) {
				
				game.move();
				game.repaint();
				if (newIons == true) {
					ions = new ArrayList<Ion>();
					precipitates = new ArrayList<Compound>();
					Game newGame = new Game();
					frame.add(newGame);
					newIons = false;
				}
				Thread.sleep(30);
				if (shouldExit == true) {
					System.exit(0);
				
			}
		}
	}
}







