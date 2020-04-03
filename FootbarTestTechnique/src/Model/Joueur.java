package Model;

public class Joueur {
	public String nom;
	public Poste poste;
	
	public Joueur(String name, Poste position) {
		this.nom = name;
		this.poste = position;
	}
	
	public String toString() {
		String s = this.nom+" ";
		return s;
	}
}
