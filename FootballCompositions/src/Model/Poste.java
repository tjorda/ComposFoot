package Model;

import main.nomDePoste;

public abstract class Poste {
	
	public nomDePoste nom;
	public int numberOfOccurence;
	
	public Poste() {
		this.numberOfOccurence = 0;
	}
	
	public void setNumberOfOccurence(int number) {
		this.numberOfOccurence = number;
	}
	
}
