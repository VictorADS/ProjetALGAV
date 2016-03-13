package triehybride;

import java.util.ArrayList;

import briandais.ArbreBriandais;
import util.Tools;

public class TrieHybride {
	private char key;
	private int val;
	private int nbmot = 0;
	private TrieHybride inf;
	private TrieHybride sup;
	private TrieHybride fils;
	public static int cpt=0;

	public TrieHybride(char key, int val, TrieHybride inf, TrieHybride sup,
			TrieHybride fils) {
		this.key = key;
		this.val = val;
		this.inf = inf;
		this.sup = sup;
		this.fils = fils;
	}

	public TrieHybride(char key, int val) {
		this.key = key;
		this.val = val;
		this.inf = null;
		this.sup = null;
		this.fils = null;
	}

	public TrieHybride() {

	}

	public static TrieHybride THajout(String mot, TrieHybride t, int v) {
		if (t == null) { /* Si arbre est vide */
			if (mot.length() == 0) {
				return t;
			}
			if (mot.length() == 1) {
				return new TrieHybride(Tools.head(mot), v);
			} else {
				TrieHybride a = new TrieHybride(Tools.head(mot), -1);
				a.setFils(THajout(Tools.tail(mot), a.getFils(), v));
				return a;
			}
		} else { /* Si arbre pas vide */
			char c = Tools.head(mot);
			cpt++;
			if (mot.length() == 1 && c == t.getKey()) {
				t.setVal(v);
				return t;
			}
			cpt++;
			if (c < t.getKey()) {
				return new TrieHybride(t.getKey(), t.getVal(), THajout(mot,
						t.getInf(), v), t.getSup(), t.getFils());
			} else {
				cpt++;
				if (c > t.getKey()) {
					return new TrieHybride(t.getKey(), t.getVal(), t.getInf(),
							THajout(mot, t.getSup(), v), t.getFils());

				} else
					return new TrieHybride(t.getKey(), t.getVal(), t.getInf(),
							t.getSup(),
							THajout(Tools.tail(mot), t.getFils(), v));

			}
		}

	}

	public static int hauteur(TrieHybride t) {
		if (t == null) {
			return 0;
		} else {
			int res = 0;
			res = 1 + Tools.MaxThree(hauteur(t.getFils()), hauteur(t.getInf()),
					hauteur(t.getSup()));
			return res;
		}
	}

	public static int comptageNil(TrieHybride t) {
		int i=0;
		if (t != null){
			if(t.getFils()==null)
				i++;
			else
				i += comptageNil(t.getFils());
			if(t.getSup()==null)
				i++;
			else
				i += comptageNil(t.getSup());
			if(t.getInf()==null)
				i++;
			else
				i += comptageNil(t.getInf());
		}
		return i;
	}
	public static TrieHybride insertionEq(TrieHybride t, String mot,int v){
		t=TrieHybride.THajout(mot, t, v);
		t=TrieHybride.equilibrage(t);
		return t;
	}
	public static TrieHybride equilibrage(TrieHybride t){
		if(t==null)
			return null;

		int hinf=TrieHybride.hauteur(t.getInf());
		int hsup=TrieHybride.hauteur(t.getSup());
		int difference=hinf-hsup;

		if(difference>=2){ // Hinf est trop grand rotation droite
			if(hauteur(t.getInf().getSup()) - hauteur(t.getInf().getInf())   >= 2) { //Le sous arbre droit est plus grand on le met en racine
				t.setInf(TrieHybride.rotG(t.getInf()));

			}	
			t=TrieHybride.rotD(t);
		}else{
			if(difference<=-2){ //hsup est trop grand rotation droite

				if(hauteur(t.getSup().getInf()) - hauteur(t.getSup().getSup()) >=2) {
					t.setSup(TrieHybride.rotD(t.getSup()));

				}
				t=TrieHybride.rotG(t);
			}
		}
		return t;

	}
	public static TrieHybride equilibrageRec(TrieHybride t){
		if(t==null)
			return null;
		t.setInf(TrieHybride.equilibrage(t.getInf()));
		t.setFils(TrieHybride.equilibrage(t.getFils()));
		t.setSup(TrieHybride.equilibrage(t.getSup()));
		int hinf=TrieHybride.hauteur(t.getInf());
		int hsup=TrieHybride.hauteur(t.getSup());
		int difference=hinf-hsup;

		if(difference>=2){ // Hinf est trop grand rotation droite
			if(hauteur(t.getInf().getSup()) - hauteur(t.getInf().getInf())   >= 2) { //Le sous arbre droit est plus grand on le met en racine
				t.setInf(TrieHybride.rotG(t.getInf()));

			}	
			t=TrieHybride.rotD(t);
		}else{
			if(difference<=-2){ //hsup est trop grand rotation droite

				if(hauteur(t.getSup().getInf()) - hauteur(t.getSup().getSup()) >=2) {
					t.setSup(TrieHybride.rotD(t.getSup()));

				}
				t=TrieHybride.rotG(t);
			}
		}
		return t;

	}
	public static TrieHybride rotG(TrieHybride t){
		TrieHybride temps=t.getSup();
		TrieHybride inf=temps.getInf();
		t.setSup(inf);
		temps.setInf(t);
		return temps;
	}
	public static TrieHybride rotD(TrieHybride t){

		TrieHybride temps=t.getInf();
		TrieHybride sup=temps.getSup();
		t.setInf(sup);
		temps.setSup(t);
		return temps;
		
	}
	public static ArbreBriandais conversionTrieToArb(TrieHybride t) {
		if (t == null)
			return null;
		if (t.getInf() != null) {
			ArbreBriandais abr = TrieHybride.conversionTrieToArb(t.getInf());
			ArbreBriandais curr = new ArbreBriandais(t.getKey());
			abr = ArbreBriandais.fusion(abr, curr);
			cpt++;
			if (t.getVal() != -1) {
				ArbreBriandais newmot = new ArbreBriandais("");
				curr.setFils(newmot);
				newmot.setFrere(TrieHybride.conversionTrieToArb(t.getFils()));
			} else {
				curr.setFils(TrieHybride.conversionTrieToArb(t.getFils()));
			}
			curr.setFrere(TrieHybride.conversionTrieToArb(t.getSup()));
			return abr;

		} else {
			ArbreBriandais abr = new ArbreBriandais(t.getKey());
			cpt++;
			if (t.getVal() != -1) {
				ArbreBriandais newmot = new ArbreBriandais("");
				abr.setFils(newmot);
				newmot.setFrere(TrieHybride.conversionTrieToArb(t.getFils()));
			} else {
				abr.setFils(TrieHybride.conversionTrieToArb(t.getFils()));
			}
			abr.setFrere(TrieHybride.conversionTrieToArb(t.getSup()));
			return abr;
		}
	}

	public static  int ComptageMots(TrieHybride h) {
		int res = 0;
		if (h != null) {
			if (h.getVal() != -1)
				res++;
			res += ComptageMots(h.getFils());
			res += ComptageMots(h.getInf());
			res += ComptageMots(h.getSup());

		}
		return res;
	}

	public static ArrayList<String> listeMots(TrieHybride t) {
		ArrayList<String> list = new ArrayList<String>();
		listeMotsTrie(list, t, "");
		return list;
	}

	public static void listeMotsTrie(ArrayList<String> list, TrieHybride t, String mot) {
		if (t == null)
			return;

		listeMotsTrie(list, t.getInf(), mot);
		if (t.getVal() != -1) {
			list.add(mot + t.getKey());
		}
		listeMotsTrie(list, t.getFils(), mot + t.getKey());
		listeMotsTrie(list, t.getSup(), mot);
	}

	public static int prefixe(TrieHybride abr, String mot) {
		if (abr == null)
			return 0;
		if (mot.length() == 0)
			return ComptageMots(abr);
		if (mot.length() == 1) {
			if (abr.getKey() == Tools.head(mot)) {
				int i = 0;
				if (abr.getVal() != -1)
					i++;
				i += ComptageMots(abr.getFils());
				return i;
			}
		}
		char t = Tools.head(mot);
		String q = Tools.tail(mot);
		if (t == abr.getKey()) {
			return prefixe(abr.getFils(), q);
		}
		if (t > abr.getKey()) {
			return prefixe(abr.getSup(), mot);
		} else {
			return prefixe(abr.getInf(), mot);
		}
	}

	public static boolean search(TrieHybride h, String m) {
		if (h == null)
			return false;
		if (m.length() == 1) {
			if (h.getKey() == Tools.head(m) && h.getVal() != -1)
				return true;
			else
				return false;
		} else {
			char c = Tools.head(m);
			if (c == h.getKey()) {
				return search(h.getFils(), Tools.tail(m));
			}
			if (c < h.getKey()) {
				return search(h.getInf(), m);
			} else
				return search(h.getSup(), m);
		}
	}

	public static  double ProfondeurMoyenne(TrieHybride abr) {
		if(abr==null)
			return 0;
		int res = TrieHybride.profondeurTotal(abr, 1);
		return res / ComptageMots(abr);
	}

	public static int profondeurTotal(TrieHybride abr, int prof) {
		int res = 0;

		if (abr == null) {
			return res;
		}

		if (abr.getVal() != -1)
			res += prof;

		res += profondeurTotal(abr.getInf(), prof + 1)
				+ profondeurTotal(abr.getFils(), prof + 1)
				+ profondeurTotal(abr.getSup(), prof + 1);

		return res;
	}

	public static TrieHybride supprimerMot(TrieHybride t, String mot) {
		if (t == null) {
			return null;
		}
		
		if (mot.length() == 1) {
			if(t.getKey()==Tools.head(mot)){
				if(t.getVal()!=-1){
					if(t.getFils()!=null){
						t.setVal(-1);
						return t;
					}else
						return TrieHybride.insererDroit(t.getInf(),t.getSup());
				}
			}else{
				if (Tools.head(mot) < t.getKey()) {

					return new TrieHybride(t.getKey(), t.getVal(), supprimerMot(
							t.getInf(), mot), t.getSup(), t.getFils());
				}else{
					if (Tools.head(mot) > t.getKey()) {
						return new TrieHybride(t.getKey(), t.getVal(), t.getInf(),
								supprimerMot(t.getSup(), mot), t.getFils());
					}
				}
			}

		} else {
			if (Tools.head(mot) < t.getKey()) {

				return new TrieHybride(t.getKey(), t.getVal(), supprimerMot(
						t.getInf(), mot), t.getSup(), t.getFils());
			}
			if (Tools.head(mot) == t.getKey()) {
				TrieHybride f = supprimerMot(t.getFils(), Tools.tail(mot));
				if (f == null) {
					return TrieHybride.insererDroit(t.getInf(), t.getSup());
				} else {
					return new TrieHybride(t.getKey(), t.getVal(), t.getInf(),
							t.getSup(), f);
				}
			}
			if (Tools.head(mot) > t.getKey()) {
				return new TrieHybride(t.getKey(), t.getVal(), t.getInf(),
						supprimerMot(t.getSup(), mot), t.getFils());
			}
		}
		return null;
	}

	public static TrieHybride insererDroit(TrieHybride ab1, TrieHybride ab2) {

		if (ab1 == null) {
			return ab2;
		}

		if (ab2 == null) {
			return ab1;
		}

		if (ab1.getSup() != null) {
			insererDroit(ab1.getFils(), ab2);
		} else {
			ab1.setSup(ab2);
			return ab1;
		}
		return ab2;
	}

	// public
	/* GETTERS */

	public char getKey() {
		return key;
	}

	public int getVal() {
		return val;
	}

	public TrieHybride getInf() {
		return inf;
	}

	public TrieHybride getSup() {
		return sup;
	}

	public TrieHybride getFils() {
		return fils;
	}

	public int getNbmot() {
		return nbmot;
	}

	/* SETTERS */

	public void setNbmot(int nbmot) {
		this.nbmot = nbmot;
	}

	public void setKey(char key) {
		this.key = key;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public void setInf(TrieHybride inf) {
		this.inf = inf;
	}

	public void setSup(TrieHybride sup) {
		this.sup = sup;
	}

	public void setFils(TrieHybride fils) {
		this.fils = fils;
	}

	/* AFFICHAGE */

	public static String print(TrieHybride t) {
		if (t == null) {
			return "[]";
		}
		String res="Cle "+t.getKey()+" val "+t.getVal()+"\nFils inf "+TrieHybride.print(t.getInf())+" \nFils eq "+TrieHybride.print(t.getFils())+" \nFils sup "+TrieHybride.print(t.getSup());

		return res;
	}
}
