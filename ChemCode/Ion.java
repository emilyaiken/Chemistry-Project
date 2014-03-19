/* GOALS
 * 1. Followers do not bounce off outer ions of the molecule they are supposed to bind to
 * 2. Molecules sink when they are full --> DONE :D
 * 3. Ions self-identify who they should follow (and bind to)
 */

import java.util.*;
import java.awt.*;
public class Ion {
	public int diameter;
	public String name;
	public String charge;
	public Color color;
	public int locX = (int) (Math.random() * (Game.RECT_WIDTH - 50));
	public int currentChangeX = (int) (Math.random() * 2) + 1;
	public int locY = (int) (Math.random() * (Game.RECT_HEIGHT - 50));
	public int currentChangeY = (int) (Math.random() * 2) + 1;
	int xChange;
	int yChange;
	boolean bound = false; //get rid of if possible
	public String BINDING_SITE;
	public Ion closestFollower;
	public Ion leader;
	public Ion centralIon;
	public ArrayList<Ion> eventualFollowers = new ArrayList<Ion>(0);
	Ion follower = null;
	public ArrayList<Ion> outerIons = new ArrayList<Ion>(0);
	int depth = 0;
	public Ion rightIon;
	public Ion leftIon;
	public boolean almostSunk = false;
	public int possibleOuterIons = 0;
	public int chargeInt = this.getChargeAsInt();
	
	public int leftX() {
		return locX;
	}
	
	public int rightX() {
		return locX + diameter;
	}
	
	public int topY() {
		return locY;
	}
	
	public int bottomY() {
		return locY + diameter;
	}
	
	public int centerX() {
		return locX + diameter/2;
	}
	
	public int centerY() {
		return locY + diameter/2;
	}
	
	public int mySize(ArrayList<Ion> me) {
		int counter = 0;
		for (int i=0; i<me.size(); i++) {
			if (me.get(i) != null ) {
				counter++;
			}
		}
		return counter;
	}

	
	// Bounds of rectangle
		int left = 4;
		int top = 4;
		int right = Game.RECT_WIDTH-4;
		int bottom = Game.RECT_HEIGHT-4;
	
	
	public Ion(String name, String charge) {
		this.name = name;
		this.charge = charge;
		// diameter
		if (this.isPolyatomic()) {
			diameter = 30;
		}
		else {
			if (this.charge == "-1" || this.charge == "-2" || this.charge == "-3") {
				diameter = 30;
			}
			else if (this.charge == "+1" || this.charge == "+2" || this.charge == "+3") {
				diameter = 30;
			}
		}
		// color
		
		// cations
		if (name == "H") {
			color = Color.pink;
		}
		else if (name == "Li") {
			color = Color.red;
		}
		else if (name == "Na") {
			color = Color.orange;
		}
		else if (name == "K") {
			color = Color.yellow;
		}
		else if (name == "Be") {
			color = Color.magenta;
		}
		else if (name == "Mg") {
			color = new Color(168, 0, 0);
		}
		else if (name == "Ca") {
			color = new Color(255, 215, 0);
		}
		else if (name == "Ba") {
			color = new Color(238, 154, 0);
		}
		else if (name == "Sr") {
			color = new Color(255, 231, 186);
		}
		else if (name == "Sn") {
			color = new Color(238, 220, 130);
		}
		else if (name == "Zn") {
			color = new Color(255, 193, 193);
		}
		else if (name == "Cu") {
			color = new Color(198, 133, 133);
		}
		else if (name == "Fe") {
			color = new Color(255, 236, 139);
		}
		else if (name == "Co") {
			color = new Color(205, 96, 144);
		}
		else if (name == "Ag") {
			color = new Color(255, 20, 147);
		}
		else if (name == "Al") {
			color = new Color(255, 181, 197);
		}
		// anions
		else if (name == "Fl") {
			color = Color.green;
		}
		else if (name == "Cl") {
			color = Color.cyan;
		}
		else if (name == "Br") {
			color = Color.blue;
		}
		else if (name == "I") {
			color = new Color(159, 121, 238);
		}
		else if (name == "OH") {
			color = new Color(127, 255, 212);
		} 
		else if (name == "SO4") {
			color = new Color(135, 206, 250);
		}
		else if (name == "BrO3") {
			color = new Color(193, 255, 193);
		}
		else if (name == "NO3") {
			color = new Color(162, 205, 90);
		}
		else if (name == "CrO4") {
			color = new Color(255, 187, 255);
		}
	
	}
	
	public int getChargeAsInt() {
				if (this.charge == "-1") {return -1;}
				else if (this.charge == "-2") {return -2;}
				else if (this.charge == "-3") {return -3;}
				else if (this.charge == "+1") {return 1;}
				else if (this.charge == "+2") {return 2;}
				else if (this.charge == "+3") {return 3;}
				else {
					return (1000);
				}
	}
	
	public void setBindingSites() {
		this.possibleOuterIons = eventualFollowers.size();
		if (eventualFollowers.size() > 3) {
			eventualFollowers.get(0).BINDING_SITE = "left";
			eventualFollowers.get(1).BINDING_SITE = "right";
			eventualFollowers.get(2).BINDING_SITE = "leftLeft";
			eventualFollowers.get(3).BINDING_SITE = "rightRight";
		}
		else {
			for (int i=0; i<eventualFollowers.size(); i++) {
				if (i==0) {
					eventualFollowers.get(i).BINDING_SITE = "left";
				}
				else if (i==1) {
					eventualFollowers.get(i).BINDING_SITE = "right";
				}
				else if (i==2) {
					eventualFollowers.get(i).BINDING_SITE = "top";
				}
			}
		}
	}
	public void setFollower() {
		if (eventualFollowers.size() > 0) {
			follower = eventualFollowers.get(0);
			eventualFollowers.get(0).leader = this;
			eventualFollowers.remove(0);
		}
		else {
			follower = null;
		}
	}
	
	public void setLeader(Ion other) {
		other.eventualFollowers.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public String getCharge() {
		return charge;
	}
	
	public String getIon() {
		return name + charge;
	}
	
	public boolean isPolyatomic() {
		if (name.length() >= 3 || name == "OH") {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isFull() {
		return (outerIons.size()>0 && outerIons.size() == possibleOuterIons);
	}
	
	public void orderEventualFollowers() {
		ArrayList<Integer> likeThis = new ArrayList<Integer>();
		ArrayList<Integer> notLikeThis = new ArrayList<Integer>();
		ArrayList<Ion> orderedEventualFollowers = new ArrayList<Ion>();
		for (int i=0; i<eventualFollowers.size(); i++) {
			if (eventualFollowers.get(i).getName() == this.getName()) {
				likeThis.add(i);
			}
			else {
				notLikeThis.add(i);
			}
		}
		for (int i=0; i<likeThis.size(); i+=2) {
			orderedEventualFollowers.add(eventualFollowers.get(notLikeThis.get(i)));
			orderedEventualFollowers.add(eventualFollowers.get(notLikeThis.get(i+1)));
			orderedEventualFollowers.add(eventualFollowers.get(likeThis.get(i)));
			orderedEventualFollowers.add(eventualFollowers.get(likeThis.get(i+1)));
		}
		eventualFollowers = orderedEventualFollowers;
	}
	
	public void prepCenter() {
		setBindingSites();
		setFollower();
	}
	
	// location abstractions
	public boolean outToLeft() {
		int temp = this.locX;
		for (int i=0; i<outerIons.size(); i++) {
			Ion me = outerIons.get(i);
			if (me.locX < temp) {
				temp = me.locX;
			}
			for (int j=0; j<me.outerIons.size(); j++) {
				if (me.outerIons.get(j).locX < temp) {
					temp = me.outerIons.get(j).locX;
				}
			}
		}
		return (temp < left);
	}
	
	public boolean outToRight() {
		int temp = this.locX + this.diameter;
		for (int i=0; i<outerIons.size(); i++) {
			Ion me = outerIons.get(i);
			if (me.locX + me.diameter > temp) {
				temp = me.locX + me.diameter;
			}
			for (int j=0; j<me.outerIons.size(); j++) {
				if (me.outerIons.get(j).locX + me.outerIons.get(j).diameter < temp) {
					temp = me.outerIons.get(j).locX + me.outerIons.get(j).diameter;
				}
			}
			
		}
		return (temp > right);
	}
	
	public boolean outToTop() {
		int temp = this.locY;
		for (int i=0; i<outerIons.size(); i++) {
			if (outerIons.get(i).locY < temp) {
				temp = outerIons.get(i).locY;
			}
		}
		return (temp < top);
	}
	
	public boolean outToBottom() {
		int temp = this.locY + this.diameter;
		for (int i=0; i<outerIons.size(); i++) {
			if (outerIons.get(i).locY + outerIons.get(i).diameter > temp) {
				temp = outerIons.get(i).locY + outerIons.get(i).diameter;
			}
		}
		return (temp > bottom);
	}

	public boolean touching(Ion other) {
		return (this.distanceTo(other) < (this.diameter/2 + other.diameter/2)); 
	}
	
	public boolean leaderWithinDistance() {
		if (this.leader != null) {
			if (Math.abs(leader.locX - this.locX) < Game.POLYATOMIC_RADIUS) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public Ion touchingIon() {
		Ion other = null;
		for (int i=0; i<Game.ions.size(); i++) {
			if (this.touching(Game.ions.get(i))) {
				ArrayList<Ion> canPass = new ArrayList<Ion>();
				// add this
				canPass.add(this);
				// add this's leader and its outer ions
				if (this.leader != null) {
					canPass.add(this.leader);
					for (int j=0; j<leader.outerIons.size(); j++) {
						canPass.add(this.leader.outerIons.get(j));
					}
				}
				// add this's follower
				if (this.follower != null) {
					canPass.add(follower);
				}
				// if it's close to its leader
				if (this.leaderWithinDistance() || Game.ions.get(i).leaderWithinDistance()) {
					canPass.add(Game.ions.get(i));
				}
				// if this is almost sunk
				if (this.almostSunk) {
					canPass.add(Game.ions.get(i));
				}
				// if the other is almost sunk
				if (Game.ions.get(i).almostSunk) {
					canPass.add(Game.ions.get(i));
					for (int k=0; k<Game.ions.get(i).outerIons.size(); k++) {
						canPass.add(Game.ions.get(i).outerIons.get(k));
					}
				}
				if (!(canPass.contains(Game.ions.get(i)))) {
					other = Game.ions.get(i);
				}
			}
		}
		return other;
	}
	
	
	
	// movement abstractions
	public void bounceOffTop() {
		yChange = Math.max(-currentChangeY, 1);
		xChange = currentChangeX;
	}
	
	public void bounceOffBottom() {
		yChange = Math.min(-currentChangeY, -1);
		xChange = currentChangeX;
	}
	
	public void bounceOffLeft() {
		xChange =  Math.max(-currentChangeX, 1);
		yChange = currentChangeY;
	}
	
	public void bounceOffRight() {
		xChange = Math.min(-currentChangeX, -1);
		yChange = currentChangeY;
	}
	
	public void bounceOffIonInX(Ion other) {
		// if this is to the left of the other
		if ((this.locX + diameter/2) < (other.locX + other.diameter/2)) {
			xChange = -1 - (int) (Math.random()*2);
		}
		// if this is to the right of the other
		else {
			xChange = 1 + (int) (Math.random()*2);
		}
	}
	
	public void bounceOffIonInY(Ion other) {
		// if this is above the other
		if ((this.locY + diameter/2) < (other.locY + other.diameter/2)) {
			yChange = -1 - (int) (Math.random()*2);
		}
		// if this is below the other
		else {
			yChange = 1 + (int) (Math.random()*2);
		}
	}
	
	public void moveAsNormalInX() {
		xChange = currentChangeX;
	}
	
	public void moveAsNormalInY() {
		yChange = currentChangeY;
	}
	
	public void moveTowardInX(Ion other) {
		int me;
		int them;
		if (BINDING_SITE == "left") {
			me = this.rightX();
			them = other.leftX();
		}
		else if (BINDING_SITE == "right") {
			me = this.leftX();
			them = other.rightX();
		}
		else if (BINDING_SITE == "top" || BINDING_SITE == "bottom") {
			me = this.centerX();
			them = other.centerX();
		}
		else {
			me = this.leftX();
			them = other.leftX();
		}
		int xDif = them - me;
		int signX;
		if (xDif == 0) {
			signX = 0;
		}
		else {
			signX = xDif/(Math.abs(xDif));
		}
		double helper = (1.0 /  (distanceTo(other)));
		xChange = (int) (signX * Math.max(( xDif * helper) / 100, 1));
		//xChange = signX * Math.max(Math.abs((xDif)/30), 1);
	}
	
	public void moveTowardInY(Ion other) {
		int me;
		int them;
		if (BINDING_SITE == "top") {
			me = this.bottomY();
			them = other.topY();
		}
		else if (BINDING_SITE == "bottom") {
			me = this.topY();
			them = other.bottomY();
		}
		else if (BINDING_SITE == "left" || BINDING_SITE == "right") {
			me = this.centerY();
			them = other.centerY();
		}
		else {
			me = this.topY();
			them = other.topY();
		}
		int yDif = them - me;
		int signY;
		if (yDif == 0) {
			signY = 0;
		}
		else {
			signY = yDif/(Math.abs(yDif));
		}
		double helper = 1.0 / distanceTo(other);
		yChange = (int) (signY *  (Math.max((yDif * helper) / 100, 1)));
		//yChange = signY * Math.max((Math.abs(yDif)/30), 1);
	}
	
	public void sinkInX() {
		xChange = 0;
	}
	
	public void sinkInY() {
		if (locY + diameter >= Game.RECT_HEIGHT) {
			yChange = 0;
		}
		else {
			yChange = 1;
		}
	}
	
	//binding abstractions
	
	public boolean shouldBind(Ion other) {
		int avgDiameter = (this.diameter + other.diameter) / 2 + 15;
		return (
			// binding site is left
			 (BINDING_SITE == "left" &&  Math.abs(this.rightX() - other.leftX()) < avgDiameter && Math.abs(this.centerY() - other.centerY()) < 2*avgDiameter)
			||
			//binding site is right
			(BINDING_SITE == "right" &&  Math.abs(other.rightX() - this.leftX()) < avgDiameter && Math.abs(this.centerY() - other.centerY()) < 2*avgDiameter) 
			||
			//binding site is top
			(BINDING_SITE == "top" &&  Math.abs(this.bottomY() - other.topY()) < avgDiameter && Math.abs(this.rightX() - other.leftIon.leftX()) < 2*avgDiameter && Math.abs(this.centerX() - other.centerX()) < 2*avgDiameter)
			||
			//binding site is bottom
			(BINDING_SITE == "bottom" &&  Math.abs(this.topY() - other.bottomY()) < avgDiameter) 
			||
			//binding site is rightRight
			(BINDING_SITE == "rightRight" && other.rightIon!= null && Math.abs(this.leftX() - other.rightIon.rightX()) < 2*avgDiameter && Math.abs(this.centerY() - other.centerY()) < 2*avgDiameter)
			||
			//binding site is leftLeft
			(BINDING_SITE == "leftLeft" && other.leftIon != null && Math.abs(this.rightX() - other.leftIon.leftX()) < 2*avgDiameter && Math.abs(this.centerY() - other.centerY()) < 2*avgDiameter)
		);
	}

	public void bind(Ion other) { 
		other.outerIons.add(this);
		other.setFollower();
		this.centralIon = other;
		this.bound = true;
		this.leader = null;
		if (other.centralIon != null || other.leader != null) {
			depth += 2;
		}
		else {
			depth++;
		}
	}

	// other abstractions
	
	// Updates class variables for movement
	public void update() {
		locX = locX + xChange;
		currentChangeX = xChange;
		locY = locY + yChange;
		currentChangeY = yChange;
	}
	
	public int distanceTo(Ion other) {
		return (int) Math.abs(Math.sqrt((other.centerX()-this.centerX())*(other.centerX()-this.centerX()) + (other.centerY()-this.centerY())*(other.centerY()-this.centerY())));
	}
	
	public void moveIon() {
		// leaders
		if (this.follower != null && outerIons.isEmpty()) {
			// calculates change in x
			
			if(outToLeft()) {
				bounceOffLeft();
			}
			else if (outToRight()) {
				bounceOffRight();
			}
			else if (touchingIon() != null) {
				bounceOffIonInX(touchingIon());
			}
			else {
				moveAsNormalInX();
				//this.moveTowardInX(follower);
				//eclipse is the best
				/*
				 * Hihihihihihihihih
				 * ihateeclipse.com
				 */
			}
			// calculates change in y
		
			if (outToTop()) {
				bounceOffTop();
			}
			else if (outToBottom()) {
				bounceOffBottom();
			}
			else if (touchingIon() != null) {
				bounceOffIonInY(touchingIon());
			}
			else {
				moveAsNormalInY();
				//this.moveTowardInY(follower);
			}
		}
		// followers
		else if (this.leader != null) {
			if (this.shouldBind(leader)) {
				this.bind(leader);
			}
			else {
				// calculates change in x
				if (outToLeft()) {
					bounceOffLeft();
				}
				else if (outToRight()) {
					bounceOffRight();
				}
				else if (touchingIon() != null) {
					bounceOffIonInX(touchingIon());
				}
				else {
					this.moveAsNormalInX();
					//this.moveTowardInX(leader);
				}
				// calculates change in y
				if (outToTop()) {
					bounceOffTop();
				}
				else if (outToBottom()) {
					bounceOffBottom();
				}
				else if (touchingIon() != null) {
					bounceOffIonInY(touchingIon());
				}
				else {
					this.moveAsNormalInY();
					//this.moveTowardInY(leader);
				}
			}
		}
		// outer ions
		else if (this.bound == true) {
			xChange = 0;
			yChange = 0;
			if (!centralIon.outerIons.contains(this)) {
				centralIon.outerIons.add(this);
			}
			else if (centralIon.centralIon != null) {
				if (!centralIon.centralIon.outerIons.contains(this)) {
					centralIon.centralIon.outerIons.add(this);
				}
			}
			if (this.BINDING_SITE == "bottom") {
				this.locX = centralIon.locX + centralIon.diameter/2 - this.diameter/2;
				this.locY = centralIon.locY+ centralIon.diameter;
			}
			else if (this.BINDING_SITE == "top") {
				this.locX = centralIon.locX + centralIon.diameter/2 - this.diameter/2;
				this.locY = centralIon.locY - this.diameter;
			}
			else if (this.BINDING_SITE == "right") {
				this.locX = centralIon.locX + centralIon.diameter;
				this.locY = centralIon.centerY() - this.diameter/2;
				centralIon.rightIon = this;
			}
			else if (this.BINDING_SITE == "left") {
				this.locX = centralIon.locX - this.diameter;
				this.locY = centralIon.centerY() - this.diameter/2;
				centralIon.leftIon = this;
			}
			else if (this.BINDING_SITE == "rightRight") {
				this.locX = centralIon.locX + centralIon.diameter + centralIon.rightIon.diameter;
				this.locY = centralIon.centerY() - this.diameter/2;
			}
			else if (this.BINDING_SITE == "leftLeft") {
				this.locX = centralIon.locX - this.diameter - centralIon.leftIon.diameter;
				this.locY = centralIon.centerY() - this.diameter/2;
			}
		}
		// central ions and single ions
		else  {
		
		// calculates change in x
			if (isFull()) {
				sinkInX();
			}
			else if (outToLeft()) {
				bounceOffLeft();
			}
			else if (outToRight()) {
				bounceOffRight();
			}
			else if (touchingIon() !=null) {
				bounceOffIonInX(touchingIon());
			}
			else {
				if (this.follower != null) {
					moveTowardInX(follower);
				}
				else {
					this.moveAsNormalInX();
				}
			}
		// calculates change in y
			if (isFull()) {
				sinkInY();
				if (locY > Game.RECT_HEIGHT - diameter - 10 && locY < Game.RECT_HEIGHT - diameter - 2) {
					almostSunk = true;
					for (int i=0; i<outerIons.size(); i++) {
						outerIons.get(i).almostSunk = true;
					}
				}
				else if (locY > Game.RECT_HEIGHT - diameter - 2) {
					almostSunk = false;
					for (int i=0; i<outerIons.size(); i++) {
						outerIons.get(i).almostSunk = false;
					}
				}
			}
			else if (outToTop()) {
				bounceOffTop();
			}
			else if (outToBottom()) {
				bounceOffBottom();
			}
			else if (touchingIon() != null) {
				bounceOffIonInY(touchingIon());
			}
			else {
				if (this.follower != null) {
					moveTowardInY(follower);
				}
				else {
					this.moveAsNormalInY();
				}
			}
		}
		/*else {
			System.out.println("error");
		} */
		update();

	}
}
