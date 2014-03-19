import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.*;


public class Popup {
String firstCompound;
String secondCompound;
public Popup() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ArrayList<Ion> possibleCations = new ArrayList<Ion>();
            	ArrayList<Ion> possibleAnions = new ArrayList<Ion>();
            	ArrayList<Compound> possibleCompounds = new ArrayList<Compound>();
            	//Cations
            	//possibleCations.add(new Ion("H", "+1"));
            	possibleCations.add(new Ion("Li", "+1"));
            	possibleCations.add(new Ion("Na", "+1"));
            	possibleCations.add(new Ion("K", "+1"));
            	possibleCations.add(new Ion("Be", "+2"));
            	possibleCations.add(new Ion("Mg", "+2"));
            	possibleCations.add(new Ion("Ca", "+2"));
            	possibleCations.add(new Ion("Ba", "+2"));
            	possibleCations.add(new Ion("Sr", "+2"));
            	possibleCations.add(new Ion("Sn", "+2"));
            	possibleCations.add(new Ion("Zn", "+2"));
            	possibleCations.add(new Ion("Cu", "+2"));
            	possibleCations.add(new Ion("Cu", "+2"));
            	possibleCations.add(new Ion("Fe", "+2"));
            	possibleCations.add(new Ion("Co", "+2"));
            	possibleCations.add(new Ion("Ag", "+1"));
            	possibleCations.add(new Ion("Al", "+3"));
            	//Anions
            	possibleAnions.add(new Ion("Fl", "-1"));
            	possibleAnions.add(new Ion("Cl", "-1"));
            	possibleAnions.add(new Ion("Br", "-1"));
            	possibleAnions.add(new Ion("I", "-1"));
            	possibleAnions.add(new Ion("OH", "-1"));
            	possibleAnions.add(new Ion("SO4", "-2"));
            	possibleAnions.add(new Ion("BrO3", "-2"));
            	possibleAnions.add(new Ion("NO3", "-1"));
            	possibleAnions.add(new Ion("CrO4", "-2"));
            	for (int i=0; i<possibleCations.size(); i++) {
            		for (int j=0; j<possibleAnions.size(); j++) {
            			Compound possibleCompound = new Compound(possibleCations.get(i), possibleAnions.get(j));
            			if (possibleCompound.isSoluble()) {
            				possibleCompounds.add(possibleCompound);
            			}
            		}
            	}
            	
               /* try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }*/

                JPanel panel = new JPanel();
                DefaultComboBoxModel firstList = new DefaultComboBoxModel();
                for (int i=0; i<possibleCompounds.size(); i++) {
                	firstList.addElement(possibleCompounds.get(i).getName());
                }
                DefaultComboBoxModel secondList = new DefaultComboBoxModel();
                for (int i=0; i<possibleCompounds.size(); i++) {
                	secondList.addElement(possibleCompounds.get(i).getName());
                }
                panel.add(new JLabel("Choose your first compound:"));
                JComboBox list1 = new JComboBox(firstList);
                panel.add(list1);
                panel.add(new JLabel("Choose your second compound:"));
                JComboBox list2 = new JComboBox(secondList);
                panel.add(list2);

                int result = JOptionPane.showConfirmDialog(null, panel, "Choose Compounds", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (result) {
                    case JOptionPane.OK_OPTION:
                        for (int i=0; i<possibleCompounds.size(); i++) {
                        	if (list1.getSelectedItem() == possibleCompounds.get(i).getName()) {
                        		Game.reactant1 = possibleCompounds.get(i); 
                        	}
                        }
                        for (int i=0; i<possibleCompounds.size(); i++) {
                        	if (list2.getSelectedItem() == possibleCompounds.get(i).getName()) {
                        		Game.reactant2 = possibleCompounds.get(i); 
                        	}
                        }
                        Game.newIons = true;
                        Game.playSimulation = true;
                        break;
                    case JOptionPane.CANCEL_OPTION:
                    	Game.shouldExit = true;
                }

            }
        });
 
    }
	
}

