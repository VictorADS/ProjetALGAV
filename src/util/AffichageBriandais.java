package util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import triehybride.TrieHybride;
import briandais.ArbreBriandais;

public class AffichageBriandais extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame f;
	JTree t;
	DefaultMutableTreeNode root;
	ArbreBriandais b = null;
	JScrollPane scrollpane;
	JPanel panel1;
	JTextArea txt=new JTextArea();
	double deb=0;
	double fin=0;
	int nbcomparaison=0;
	public AffichageBriandais() {
		f = new JFrame("ALGAV : Arbre de la Briandais");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());

		root = new DefaultMutableTreeNode("");
		t = new JTree(root);
		t.setRootVisible(false);
		scrollpane = new JScrollPane(t);
		panel1.add(scrollpane, BorderLayout.CENTER);

		JButton add = new JButton(" Ajouter Mot");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez ajouter ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					ArbreBriandais.cpt=0;
					b = ArbreBriandais.ajouterMot(b, mot);
					nbcomparaison=ArbreBriandais.cpt;
					fin=System.currentTimeMillis();

				AfficherJTree(b);
				} 
			}
		});
		JButton supp = new JButton(" Supprimer Mot");
		supp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez supprimer ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					ArbreBriandais.cpt=0;
					b = ArbreBriandais.supprimerMot(b, mot);
					fin=System.currentTimeMillis();
					nbcomparaison=ArbreBriandais.cpt;
					AfficherJTree(b);
				} 
			}
		});
		JButton prefix = new JButton(" Cherche prefixe");
		prefix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le prefixe que vous souhaiter chercher");
				if(mot!=null){
					deb=System.currentTimeMillis();
					ArbreBriandais.cpt=0;
					int x=ArbreBriandais.prefixe(b, mot);
					fin=System.currentTimeMillis();
					nbcomparaison=ArbreBriandais.cpt;
					jop1.showMessageDialog(null,"Il y a "+x+" mots qui commencent par "+mot);
				} 
			}
		});
		
		JButton fusion = new JButton(" Fusionner un arbre");
		fusion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez la phrase que vous voulez fusionner ");
				if(mot!=null){
					String[] mots=mot.split(" ");
					ArbreBriandais b1=null;
					for(String m : mots)
						b1 = ArbreBriandais.ajouterMot(b1, m);
					ArbreBriandais.cpt=0;
					deb=System.currentTimeMillis();
					b=ArbreBriandais.fusion(b, b1);
					nbcomparaison=ArbreBriandais.cpt;
					fin=System.currentTimeMillis();
					AfficherJTree(b);
				} 
			}
		});
		JButton shake = new JButton(" Tester ShakeSpeare");
		shake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createShakeSpear();
			}
		});
		JButton accueil = new JButton(" Revenir à l'accueil");
		accueil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				f=new Affichage();
			}
		});
		JButton conv = new JButton(" Convertir en de la Briandais");
		conv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				f=new AffichageTrieHybride(b);
				
			}
		});
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
		p2.add(add);
		p2.add(prefix);
		p2.add(supp);
		p2.add(fusion);
		p2.add(shake);
		p2.add(conv);
		p2.add(accueil);
		
		updateJLabel(txt, null);
		JPanel p3=new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
		p3.add(txt);
		
		JPanel containerRight= new JPanel();
		containerRight.setLayout(new GridLayout(2,1));
		containerRight.add(p2);
		containerRight.add(p3);
		
		f.add("Center", panel1);
		f.add("East", containerRight);
		f.setLocationRelativeTo(null);
		f.setSize(500, 500);
		f.setVisible(true);
	}

	public void AfficherJTree(ArbreBriandais b) {
		root = new DefaultMutableTreeNode();
		addToNode(root, root, b);

		((DefaultTreeModel) t.getModel()).setRoot(root);
		((DefaultTreeModel) t.getModel()).reload();
		updateJLabel(txt, b);
	}

	public void addToNode(DefaultMutableTreeNode gp,
			DefaultMutableTreeNode pere, ArbreBriandais b) {
		if (b == null)
			return;
		DefaultMutableTreeNode fils = null;
		fils = new DefaultMutableTreeNode(b.getKey());
		pere.add(fils);
		addToNode(gp, pere, b.getFrere());
		addToNode(pere, fils, b.getFils());
	}
	public void updateJLabel(JTextArea j, ArbreBriandais b){
		String res ="Nombre de mot : "+ArbreBriandais.comptageMots(b)+"\n"
				+ "Nombre de null : "+ArbreBriandais.comptageNil(b)+"\n"
				+ "Hauteur de l'arbre : "+ArbreBriandais.hauteur(b)+"\n"
				+ "Profondeur moyenne : "+ArbreBriandais.ProfondeurMoyenne(b)+"\n"
				+ "Temps d'éxécution : "+(fin-deb)/1f+" ms"+"\n"
				+ "Nombre de comparaison : "+nbcomparaison;
		j.setText(res);
		j.setEditable(false);
		j.setOpaque(false);
	}
	public void createShakeSpear() {
		ArrayList<String> mots = new ArrayList<String>();
		ArrayList<String> motTexte = new ArrayList<String>();

		try {
			Files.walk(Paths.get("./Shakespeare")).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					File f = filePath.toFile();
					Scanner c;
					try {
						c = new Scanner(f);
						while (c.hasNextLine()) {
							String ligne = c.nextLine();
							mots.add(ligne);
							if(f.getName().equals("john.txt"))
							motTexte.add(ligne);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArbreBriandais.cpt=0;
		deb=System.currentTimeMillis();
		for (String m : mots) {
			b = ArbreBriandais.ajouterMot(b, m);
		}
		nbcomparaison=ArbreBriandais.cpt;
		fin=System.currentTimeMillis();
		deb=System.currentTimeMillis();
		for (String m : motTexte) {
			ArbreBriandais.recherche(b, m);
		}
		nbcomparaison=ArbreBriandais.cpt;
		fin=System.currentTimeMillis();
		
		AfficherJTree(b);
	}
	public AffichageBriandais(TrieHybride tr){
		f = new JFrame("ALGAV : Arbre de la Briandais");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());

		root = new DefaultMutableTreeNode("");
		t = new JTree(root);
		t.setRootVisible(false);
		scrollpane = new JScrollPane(t);
		panel1.add(scrollpane, BorderLayout.CENTER);

		JButton add = new JButton(" Ajouter Mot");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez ajouter ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					ArbreBriandais.cpt=0;
					b = ArbreBriandais.ajouterMot(b, mot);
					nbcomparaison=ArbreBriandais.cpt;
					fin=System.currentTimeMillis();

				AfficherJTree(b);
				} 
			}
		});
		JButton supp = new JButton(" Supprimer Mot");
		supp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez supprimer ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					ArbreBriandais.cpt=0;
					b = ArbreBriandais.supprimerMot(b, mot);
					fin=System.currentTimeMillis();
					nbcomparaison=ArbreBriandais.cpt;
					AfficherJTree(b);
				} 
			}
		});
		JButton fusion = new JButton(" Fusionner un arbre");
		fusion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez la phrase que vous voulez fusionner ");
				if(mot!=null){
					String[] mots=mot.split(" ");
					ArbreBriandais b1=null;
					for(String m : mots)
						b1 = ArbreBriandais.ajouterMot(b1, m);
					ArbreBriandais.cpt=0;
					deb=System.currentTimeMillis();
					b=ArbreBriandais.fusion(b, b1);
					nbcomparaison=ArbreBriandais.cpt;
					fin=System.currentTimeMillis();
					AfficherJTree(b);
				} 
			}
		});
		JButton shake = new JButton(" Tester ShakeSpeare");
		shake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createShakeSpear();
			}
		});
		JButton accueil = new JButton(" Revenir à l'accueil");
		accueil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				f=new Affichage();
			}
		});
		JButton conv = new JButton(" Convertir en de la Briandais");
		conv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				f=new AffichageTrieHybride(b);
				
			}
		});
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
		p2.add(add);
		p2.add(supp);
		p2.add(fusion);
		p2.add(shake);
		p2.add(conv);
		p2.add(accueil);
		
		updateJLabel(txt, null);
		JPanel p3=new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
		p3.add(txt);
		
		JPanel containerRight= new JPanel();
		containerRight.setLayout(new GridLayout(2,1));
		containerRight.add(p2);
		containerRight.add(p3);
		
		b=TrieHybride.conversionTrieToArb(tr);
		AfficherJTree(b);
		
		f.add("Center", panel1);
		f.add("East", containerRight);
		f.setLocationRelativeTo(null);
		f.setSize(500, 500);
		f.setVisible(true);
	}

}