package util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Affichage extends JFrame {

	/**
	 * 
	 */
	JFrame f=this;
	private static final long serialVersionUID = 1L;
	public Affichage(){
		this.setTitle("ALGAV");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);

		JButton b1 = new JButton("Tester Arbre de la Briandais");
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				f=new AffichageBriandais();
			}
		});
		JButton b2 = new JButton("Tester TrieHybride");
		b2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				f=new AffichageTrieHybride();
			}
		});

		JPanel panel1 = new JPanel();
		panel1.add(b1);
		panel1.add(b2);

		
		this.setContentPane(panel1);
		this.setVisible(true);
	}
}
