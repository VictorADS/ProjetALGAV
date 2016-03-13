package briandais;

import java.util.ArrayList;

import triehybride.TrieHybride;
import util.Tools;

public class ArbreBriandais {

	private char key;
	private ArbreBriandais fils;
	private ArbreBriandais frere;
	public static int cpt=0;
	/* Constructeur */

	public ArbreBriandais(char key, ArbreBriandais fils, ArbreBriandais frere) {
		this.key = key;
		this.fils = fils;
		this.frere = frere;

	}
	public ArbreBriandais(char key) {
		this.key = key;
		this.fils = null;
		this.frere = null;

	}

	public ArbreBriandais(String mot) {
		if (mot.length() == 0) {
			this.key = '\0';
			this.fils = null;
			this.frere = null;
		} else {
			this.key = Tools.head(mot);
			this.fils = new ArbreBriandais(Tools.tail(mot));
			this.frere = null;
		}
	}

	/* Getter */

	public char getKey() {
		return key;
	}

	public ArbreBriandais getFils() {
		return fils;
	}

	public ArbreBriandais getFrere() {
		return frere;
	}

	/* Setter */

	public void setKey(char key) {
		this.key = key;
	}

	public void setFrere(ArbreBriandais frere) {
		this.frere = frere;
	}

	public void setFils(ArbreBriandais fils) {
		this.fils = fils;
	}

	/* Adder */

	public static ArbreBriandais ajouterMot(ArbreBriandais abr, String mot) {
		if (abr == null) {
			return new ArbreBriandais(mot);
		}
		cpt++;
		if (mot.length() == 0 && abr.getKey() == '\0') {
			return abr;
		}
		cpt++;
		if (Tools.head(mot) == '\0') {
			return new ArbreBriandais('\0', null, abr);
		} else {
			char t = Tools.head(mot);
			String q = Tools.tail(mot);
			cpt++;
			if (t > abr.getKey()) {
				abr.setFrere(ajouterMot(abr.getFrere(), mot));
			}
			cpt++;
			if (t == abr.getKey()) {
				abr.setFils(ajouterMot(abr.getFils(), q));
			}
			cpt++;
			if (t < abr.getKey()) {
				ArbreBriandais b = new ArbreBriandais(mot);
				b.setFrere(abr);
				return b;
			}
			return abr;

		}
	}

	public static int hauteur(ArbreBriandais a) {
		if(a==null)
			return 0;
		cpt++;
		if (a.getKey() == '\0') {
				return 1+ArbreBriandais.hauteur(a.getFrere());
		} else {
			int hfrere = 0;
			int hfils = 0;
			hfrere =1+ArbreBriandais.hauteur(a.getFrere());
			hfils = 1 + ArbreBriandais.hauteur(a.getFils());
			return Math.max(hfils, hfrere);
		}
	}

	public static int ProfondeurMoyenne(ArbreBriandais abr) {
		if(abr==null)
			return 0;
		int res = abr.profondeurTotal(1);
		return res / ArbreBriandais.comptageMots(abr);
	}

	private int profondeurTotal(int prof) {
		int res = 0;
		cpt++;
		if (this.getKey() == '\0') {
			if (this.getFrere() != null) {
				return prof + getFrere().profondeurTotal(prof + 1);
			} else
				return prof;
		} else {
			if (this.getFrere() != null)
				res += getFrere().profondeurTotal(prof + 1);
			if (this.getFils() != null) {
				res += getFils().profondeurTotal(prof + 1);
			}
		}
		return res;
	}

	public static int prefixe(ArbreBriandais abr, String mot) {
		if (abr == null)
			return 0;
		if (mot.length() == 0)
			return comptageMots(abr);
		else {
			char t = Tools.head(mot);
			String q = Tools.tail(mot);
			cpt++;
			if (t == abr.getKey()) {
				return prefixe(abr.getFils(), q);
			}
			cpt++;
			if (t > abr.getKey()) {
				return prefixe(abr.getFrere(), mot);
			}

		}
		return 0;
	}

	public static ArbreBriandais supprimerMot(ArbreBriandais abr, String mot) {
		if (abr == null) {
			return abr;
		} else {
			if (mot.length() == 0) {
				cpt++;
				if (abr.getKey() == '\0') {
					return abr.getFrere();
				} else {

				}
			} else {
				char h = Tools.head(mot);
				String t = Tools.tail(mot);
				cpt++;
				if (h > abr.getKey()) {
					return new ArbreBriandais(abr.getKey(), abr.getFils(),
							supprimerMot(abr.getFrere(), mot));
				} else {
					cpt++;
					if (h == abr.getKey()) {
						ArbreBriandais f = supprimerMot(abr.getFils(), t);
						if (f == null) {
							return abr.getFrere();
						} else {
							return new ArbreBriandais(abr.getKey(), f,
									abr.getFrere());
						}
					} else {
					}
				}

			}
		}
		return abr;
	}

	/* Recherche mot */

	public static boolean recherche(ArbreBriandais a,String mot) {
		cpt+=2;
		if (a.getKey()== '\0' && Tools.head(mot) == '\0') {
			return true;
		} else {
			char h = Tools.head(mot);
			String t = Tools.tail(mot);
			cpt++;
			if (h == '\0') {
				return false;
			} else {
				cpt++;
				if (h == a.getKey()) {
					return ArbreBriandais.recherche(a.getFils(),t);
				} else {
					cpt++;
					if (h > a.getKey()) {
						if (a.getFrere() != null) {
							return ArbreBriandais.recherche(a.getFrere(),t);
						}
					}
				}
			}

		}
		return false;
	}

	public static int comptageMots(ArbreBriandais abr) {
		int i = 0;
		if(abr==null)
			return 0;
		cpt++;
		if (abr.getKey() == '\0')
			i++;
		if (abr.getFils() != null)
			i += comptageMots(abr.getFils());
		if (abr.getFrere() != null)
			i += comptageMots(abr.getFrere());
		return i;

	}

	public static int comptageNil(ArbreBriandais abr) {
		int i = 0;
		if(abr==null)
			return 0;
		
		if (abr.getFils() == null)
			i++;
		else
			i += comptageNil(abr.getFils());
		if (abr.getFrere() == null)
			i++;
		else
			i += comptageNil(abr.getFrere());
		return i;
	}

	/* Affichage */
	public static ArrayList<String> listeMots(ArbreBriandais a) {
		ArrayList<String> list = new ArrayList<String>();
		listeMotsArbre(list, a, "");
		return list;
	}

	public static void listeMotsArbre(ArrayList<String> list, ArbreBriandais a,
			String mot) {
		if (a == null)
			return;
		cpt++;
		if (a.getKey() == '\0') {
			list.add(mot);
		}
		listeMotsArbre(list, a.getFils(), mot + a.getKey());
		listeMotsArbre(list, a.getFrere(), mot);
	}



	public static ArbreBriandais fusion(ArbreBriandais ab1, ArbreBriandais ab2) {
		if (ab1 == null) {
			return ab2;
		}
		if (ab2 == null) {
			return ab1;
		}
		cpt++;
		if (ab1.getKey() == ab2.getKey()) {
			ab1.setFils(ArbreBriandais.fusion(ab1.getFils(), ab2.getFils()));
			ab1.setFrere(ArbreBriandais.fusion(ab1.getFrere(), ab2.getFrere()));
			return ab1;
		}
		cpt++;
		if (ab1.getKey() < ab2.getKey()) {
			ab1.setFrere(ArbreBriandais.fusion(ab1.getFrere(), ab2));
			return ab1;
		}
		cpt++;
		if (ab1.getKey() > ab2.getKey()) {
			ab2.setFrere(ArbreBriandais.fusion(ab2.getFrere(), ab1));
			return ab2;
		}
		return ab2;
	}
	private static int cptNb=0;
	public static TrieHybride conversionToTrie(ArbreBriandais ab){
		TrieHybride t=ArbreBriandais.conversion(ab);
		cptNb=0;
		return t;
	}
	public static TrieHybride conversion(ArbreBriandais ab) {
		if (ab == null) {
			return null;
		}
		cpt++;
		if (ab.getKey() == '\0')
			return ArbreBriandais.conversion(ab.getFrere());
		cpt++;
		if (ab.getFils().getKey() == '\0')
			return new TrieHybride(ab.key, cptNb++, null,
					ArbreBriandais.conversion(ab.getFrere()),
					ArbreBriandais.conversion(ab.getFils().getFrere()));
		else
			return new TrieHybride(ab.getKey(), -1, null,
					ArbreBriandais.conversion(ab.getFrere()),
					ArbreBriandais.conversion(ab.getFils()));
	}
}
