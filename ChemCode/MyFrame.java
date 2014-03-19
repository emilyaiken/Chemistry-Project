import javax.swing.*;

import java.awt.event.*;

public class MyFrame extends JFrame {
	public MyFrame()
	{
		super("Double Replacement Reaction");
		
		JMenu exitMenu = new JMenu("Options");
		exitMenu.setMnemonic('O');
		JMenuItem newReaction = new JMenuItem("New Reaction");
		newReaction.setMnemonic('N');
		JMenuItem quit = new JMenuItem("Quit");
		quit.setMnemonic('Q');
		exitMenu.add(newReaction);
		exitMenu.add(quit);
		
		//adding action listener to menu items
		newReaction.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					//Game.playSimulation = false;
					new Popup();
				}
			}
		);
		quit.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						Game.shouldExit = true;
					}
				}
			);
				
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(exitMenu);
		
		getContentPane();
		setSize(600, 600);
		setVisible(true);
	}
	
}
