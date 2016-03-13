package util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import briandais.ArbreBriandais;
import triehybride.TrieHybride;

public class AffichageTrieHybride extends JFrame {
	/**
	 * 
	 */
	JFrame f;
	TrieHybride trie = null;
	JScrollPane scrollpane=new JScrollPane();
	JPanel panel1;
	JTextArea txt=new JTextArea();
	int nbmot=0;
	double deb=0.0;
	double fin=0.0;
	private static final long serialVersionUID = 1L;

	public AffichageTrieHybride(){
		f=new JFrame("ALGAV : TrieHybride");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());


		panel1.add(scrollpane, BorderLayout.CENTER);

		JButton add = new JButton(" Ajouter Mot");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez ajouter ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					trie = TrieHybride.THajout(mot,trie,nbmot++);
					fin=System.currentTimeMillis();
					if(nbmot<1000)
					AfficherTrie(trie);
					else
						updateJLabel(txt, trie);
				} 
			}
		});
		JButton addequi = new JButton(" Ajouter Mot avec equilibrage");
		addequi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez ajouter ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					trie = TrieHybride.insertionEq(trie,mot,nbmot++);
					fin=System.currentTimeMillis();
					if(nbmot<1000)
					AfficherTrie(trie);
					else
						updateJLabel(txt, trie);
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
					trie = TrieHybride.supprimerMot(trie, mot);
					fin=System.currentTimeMillis();
					if(nbmot<1000)
						AfficherTrie(trie);
					else
						updateJLabel(txt, trie);
				} 
			}
		});
		JButton equi = new JButton(" Equilibrer le trie");
		equi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deb=System.currentTimeMillis();
				trie=TrieHybride.equilibrage(trie);
				fin=System.currentTimeMillis();
				if(nbmot<1000)
				AfficherTrie(trie);
				else
					updateJLabel(txt, trie);
			}
		});
		JButton shake = new JButton(" Tester ShakeSpeare");
		shake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createShakeSpear();
			}
		});
		JButton conv = new JButton(" Convertir en de la Briandais");
		conv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				f=new AffichageBriandais(trie);
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
		JButton prefix = new JButton(" Cherche prefixe");
		prefix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le prefixe que vous souhaiter chercher");
				if(mot!=null){
					deb=System.currentTimeMillis();
					TrieHybride.cpt=0;
					int x=TrieHybride.prefixe(trie, mot);
					fin=System.currentTimeMillis();
					jop1.showMessageDialog(null,"Il y a "+x+" mots qui commencent par "+mot);
				} 
			}
		});

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
		p2.add(add);
		p2.add(addequi);
		p2.add(prefix);
		p2.add(supp);
		p2.add(equi);
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
		f.setSize(1000, 1000);
		f.setVisible(true);
	}
	private static int id =0;
	public void AfficherTrie(TrieHybride b) {
		FileWriter fw;
		trie=b;
		try {
			File f =new File("./Graphviz.gv");
			fw = new FileWriter(f);
			fw.flush();
			fw.write("digraph b{\n");
			if(b!=null){
				fw.write(id+++" [label="+b.getKey()+"];\n");
				addToGV(fw,0,b);
			}
			fw.write("}");
			fw.close();
			id=0;
			Runtime runtime=Runtime.getRuntime();
			System.out.println("Fin graphviz");
			Process p =runtime.exec("dot -Tpng Graphviz.gv -o outfile.png");
			p.waitFor();
			afficheImage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		updateJLabel(txt,b);
	}
	
	public void afficheImage(){
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("./outfile.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			panel1.removeAll();
			panel1.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addToGV(FileWriter fw,int pere, TrieHybride b) {
		if (b == null)
			return;
		else{
			try {
				if(b.getInf()!=null){
					String res="";
					if(b.getInf().getVal()!=-1)
						res="color=red";
					fw.write(id+++" [label=\""+b.getInf().getKey()+"\" "+res+"];\n");
				}else{
					fw.write(id+++" [label=\" \" shape=point ];\n");
				}
				fw.write(pere+"-> "+(id-1)+"\n");
				addToGV(fw,id-1, b.getInf());
				if(b.getFils()!=null){
					String res="";
					if(b.getFils().getVal()!=-1)
						res="color=red";
					fw.write(id+++" [label=\""+b.getFils().getKey()+"\""+res+"];\n");
				}else{
					fw.write(id+++" [label=\" \" shape=point ];\n");
				}
				fw.write(pere+"-> "+(id-1)+"\n");
				addToGV(fw,id-1, b.getFils());
				if(b.getSup()!=null){
					String res="";
					if(b.getSup().getVal()!=-1)
						res="color=red";
					fw.write(id+++" [label=\""+b.getSup().getKey()+"\""+res+"];\n");
				}else{
					fw.write(id+++" [label=\" \" shape=point ];\n");
				}
				fw.write(pere+"-> "+(id-1)+"\n");
				addToGV(fw,id-1, b.getSup());
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void updateJLabel(JTextArea j, TrieHybride b){
		System.out.println(deb+" et "+fin);
		nbmot=TrieHybride.ComptageMots(b);
		String res ="Nombre de mot : "+nbmot+"\n"
				+ "Nombre de null : "+TrieHybride.comptageNil(b)+"\n"
				+ "Hauteur de l'arbre : "+TrieHybride.hauteur(b)+"\n"
				+ "Profondeur moyenne : "+TrieHybride.ProfondeurMoyenne(b)+"\n"
				+ "Temps d'éxécution : "+(fin-deb)+" ms";
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

		trie=null;
		int j=0;
		deb=System.currentTimeMillis();
		for (String m : mots) {
			trie= TrieHybride.THajout(m, trie, j++);
		}
		fin=System.currentTimeMillis();
		deb=System.currentTimeMillis();
		for (String m : motTexte) {
			TrieHybride.search(trie, m);
		}
		fin=System.currentTimeMillis();
		updateJLabel(txt, trie);
	}
	public AffichageTrieHybride(ArbreBriandais b){
		f=new JFrame("ALGAV : TrieHybride");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());


		panel1.add(scrollpane, BorderLayout.CENTER);

		JButton add = new JButton(" Ajouter Mot");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop1 = new JOptionPane();
				String mot = jop1.showInputDialog("Entrez le mot que vous souhaitez ajouter ");
				if(mot!=null){
					deb=System.currentTimeMillis();
					trie = TrieHybride.THajout(mot,trie,nbmot++);
					fin=System.currentTimeMillis();
					if(nbmot<1000)
					AfficherTrie(trie);
					else
						updateJLabel(txt, trie);
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
					trie = TrieHybride.supprimerMot(trie, mot);
					fin=System.currentTimeMillis();
					if(nbmot<1000)
					AfficherTrie(trie);
					else
						updateJLabel(txt, trie);
				} 
			}
		});
		JButton equi = new JButton(" Equilibrer le trie");
		equi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deb=System.currentTimeMillis();
				trie=TrieHybride.equilibrage(trie);
				fin=System.currentTimeMillis();
				if(nbmot<1000)
				AfficherTrie(trie);
				else
					updateJLabel(txt, trie);
			}
		});
		JButton shake = new JButton(" Tester ShakeSpeare");
		shake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createShakeSpear();
			}
		});
		JButton conv = new JButton(" Convertir en de la Briandais");
		conv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				f=new AffichageBriandais(trie);
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

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
		p2.add(add);
		p2.add(supp);
		p2.add(equi);
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
		
		trie=ArbreBriandais.conversionToTrie(b);
		AfficherTrie(trie);
		
		f.add("Center", panel1);
		f.add("East", containerRight);
		f.setLocationRelativeTo(null);
		f.setSize(1000, 1000);
		f.setVisible(true);
	}
}
