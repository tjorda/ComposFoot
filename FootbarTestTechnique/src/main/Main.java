package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Model.Aillier;
import Model.Attaquant;
import Model.DefenseurCentral;
import Model.DefenseurLateral;
import Model.GardienDeBut;
import Model.Joueur;
import Model.MilieuDeTerrain;
import Model.Poste;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<nomDePoste, Poste> listeDePostes = new HashMap<nomDePoste, Poste>();
		listeDePostes = initiatePostes();
		ArrayList<Joueur> listeDeJoueurs = new ArrayList<Joueur>();
		listeDeJoueurs = initiateList(listeDePostes);
		ArrayList<ArrayList<Joueur>> compositions = new ArrayList<ArrayList<Joueur>>();
		ArrayList<ArrayList<ArrayList<Joueur>>> ensemble = new ArrayList<ArrayList<ArrayList<Joueur>>>();
		for(Map.Entry mapentry : listeDePostes.entrySet()) {
			compositions = getAllPlayerCombinationForAPosition(listeDeJoueurs, (Poste) mapentry.getValue());
			printCombinations(compositions);
			ensemble.add(compositions);
		}
		
		
		int nombreDeCompositions = 1;
		for(ArrayList<ArrayList<Joueur>> subComps : ensemble) {
			nombreDeCompositions *= subComps.size();
		}
		System.out.println(ensemble);
		ArrayList<ArrayList<ArrayList<Joueur>>> resultat = trouverToutesLesCompos(ensemble);
		
		
		int iterator = 1;
		for(ArrayList<ArrayList<Joueur>> compos : resultat) {
			printFinalComps(compos, iterator++);
		}
		System.out.println("nombre de compos attendues : "+ nombreDeCompositions);
		
		
	}
	
	
	
////////////////////////////////////// Méthodes pour génerer les compositions possibles /////////////////////////
	
	public static void generer (Stack <ArrayList<Joueur>> stack, int index, ArrayList<ArrayList<ArrayList<Joueur>>> subComps, int excluded, ArrayList<ArrayList<ArrayList<Joueur>>> result)
    {
    	index = index +1;
        if(index < subComps.size()+1){
            for(int j = 0; j < subComps.get(index-1).size(); j++){
                stack.push(subComps.get(index-1).get(j));
                generer (stack, index, subComps, excluded, result);
                stack.pop();
            }
        }
        else{
            result.add(new ArrayList<ArrayList<Joueur>>(stack)); 
        }  
        
        
    }
	
	public static ArrayList<ArrayList<ArrayList<Joueur>>> trouverToutesLesCompos(ArrayList<ArrayList<ArrayList<Joueur>>> subComps){
		ArrayList<ArrayList<ArrayList<Joueur>>> compositions = new ArrayList<ArrayList<ArrayList<Joueur>>>();
		Stack<ArrayList<Joueur>> stack = new Stack<ArrayList<Joueur>>();
		generer(stack, 0, subComps, 1, compositions);
		return compositions;
	}

	
////////////////////////////////////// Méthodes pour trouver les possibilités par postes //////////////////
	
	private static void helper(ArrayList<int[]> combinations, int data[], int start, int end, int index) {
	    if (index == data.length) {
	        int[] combination = data.clone();
	        combinations.add(combination);
	    } else if (start <= end) {
	        data[index] = start;
	        helper(combinations, data, start + 1, end, index + 1);
	        helper(combinations, data, start + 1, end, index);
	    }
	}
	
	public static ArrayList<ArrayList<Joueur>> getAllPlayerCombinationForAPosition(ArrayList<Joueur> players, Poste poste){
		ArrayList<Joueur> playerList = getPlayersFromPosition(players, poste);
		ArrayList<int[]> combinationList = generate(poste.numberOfOccurence, playerList.size()); 
		ArrayList<ArrayList<Joueur>> combinations = new ArrayList<ArrayList<Joueur>>();
		for(int combination[] : combinationList) {
			combinations.add(generatePlayerList(combination,playerList));
		}		
		return combinations;
	}
	
	public static ArrayList<int[]> generate(int r, int n) {
	    ArrayList<int[]> combinations = new ArrayList<>();
	    helper(combinations, new int[r], 0, n-1, 0);
	    return combinations;
	}
	
	public static ArrayList<Joueur> getPlayersFromPosition(ArrayList<Joueur> players, Poste poste) {
		
		ArrayList<Joueur> nombre = new ArrayList<Joueur>();
		for (Joueur player : players) {
			if (player.poste == poste){
				nombre.add(player);
			}
		}
		return nombre;
	}
	
	public static ArrayList<Joueur> generatePlayerList(int[] values, ArrayList<Joueur> players){
		ArrayList<Joueur> combinaison = new ArrayList<Joueur>();
		for(int value : values) {
			combinaison.add(players.get(value));
		}
		return combinaison;
	}

///////////////////////////////////////////// Méthodes d'initialisation ///////////////////////////////////

	public static HashMap<nomDePoste, Poste> initiatePostes(){
		
		HashMap<nomDePoste, Poste> listeDePostes = new HashMap<nomDePoste, Poste>();
		
		Poste attaquant = new Attaquant();
		Poste aillier = new Aillier();
		Poste defenseurCentral = new DefenseurCentral();
		Poste defenseurLateral = new DefenseurLateral();
		Poste milieu = new MilieuDeTerrain();
		Poste gardien = new GardienDeBut();

		listeDePostes.put(nomDePoste.GARDIEN, gardien);
		listeDePostes.put(nomDePoste.DEFENSEURCENTRAL, defenseurCentral);
		listeDePostes.put(nomDePoste.LATERAL, defenseurLateral);
		listeDePostes.put(nomDePoste.MILIEUDETERRAIN, milieu);
		listeDePostes.put(nomDePoste.AILLIER, aillier);
		listeDePostes.put(nomDePoste.ATTAQUANT, attaquant);
		
		return listeDePostes;
	}
	
	public static ArrayList<Joueur> initiateList(HashMap<nomDePoste, Poste> postes){
		
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(new Joueur("Lopes", postes.get(nomDePoste.GARDIEN)));
		joueurs.add(new Joueur("Tatarousanu", postes.get(nomDePoste.GARDIEN)));
		//joueurs.add(new Joueur("Raciopi", postes.get(nomDePoste.GARDIEN)));
		joueurs.add(new Joueur("Marcelo", postes.get(nomDePoste.DEFENSEURCENTRAL)));
		joueurs.add(new Joueur("Denayer", postes.get(nomDePoste.DEFENSEURCENTRAL)));
		joueurs.add(new Joueur("Yanga-Mbiwa", postes.get(nomDePoste.DEFENSEURCENTRAL)));
		//joueurs.add(new Joueur("Solet", postes.get(nomDePoste.DEFENSEURCENTRAL)));
		joueurs.add(new Joueur("Andersen", postes.get(nomDePoste.DEFENSEURCENTRAL)));
		joueurs.add(new Joueur("Dubois", postes.get(nomDePoste.LATERAL)));
		joueurs.add(new Joueur("Rafael", postes.get(nomDePoste.LATERAL)));
		//joueurs.add(new Joueur("Tete", postes.get(nomDePoste.LATERAL)));
		joueurs.add(new Joueur("Marçal", postes.get(nomDePoste.LATERAL)));
		joueurs.add(new Joueur("Kone", postes.get(nomDePoste.LATERAL)));
		joueurs.add(new Joueur("Guimares", postes.get(nomDePoste.MILIEUDETERRAIN)));
		joueurs.add(new Joueur("Rene-Adelaide", postes.get(nomDePoste.MILIEUDETERRAIN)));
		//joueurs.add(new Joueur("Lucas", postes.get(nomDePoste.MILIEUDETERRAIN)));
		joueurs.add(new Joueur("Caqueret", postes.get(nomDePoste.MILIEUDETERRAIN)));
		joueurs.add(new Joueur("Cherki", postes.get(nomDePoste.MILIEUDETERRAIN)));
		joueurs.add(new Joueur("Aouar", postes.get(nomDePoste.MILIEUDETERRAIN)));
		//joueurs.add(new Joueur("Mendes", postes.get(nomDePoste.MILIEUDETERRAIN)));
		joueurs.add(new Joueur("Tousart", postes.get(nomDePoste.MILIEUDETERRAIN)));
		joueurs.add(new Joueur("Terrier", postes.get(nomDePoste.AILLIER)));
		//joueurs.add(new Joueur("Traore", postes.get(nomDePoste.AILLIER)));
		joueurs.add(new Joueur("Toko-Ekambi", postes.get(nomDePoste.AILLIER)));
		joueurs.add(new Joueur("Depay", postes.get(nomDePoste.AILLIER)));
		joueurs.add(new Joueur("Cornet", postes.get(nomDePoste.AILLIER)));
		joueurs.add(new Joueur("Dembele", postes.get(nomDePoste.ATTAQUANT)));
		joueurs.add(new Joueur("Gouiri", postes.get(nomDePoste.ATTAQUANT)));
		
		return joueurs;
	}
	

/////////////////////////////////////////////  	Méthodes d'affichage //////////////////////////////////////

	public static void printCombinations(ArrayList<ArrayList<Joueur>> listes) {
		System.out.println(listes.get(0).get(0).poste.nom.toString()+" : ");
		for(int i=0; i<listes.size(); i++) {
			for (int j=0; j<listes.get(i).size(); j++) {
				System.out.print(listes.get(i).get(j).toString());
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public static void printFinalComps(ArrayList<ArrayList<Joueur>> listes, int iterator) {
		System.out.println("Composition "+ iterator + " : \n");
		for(int i=0; i<listes.size(); i++) {
			for (int j=0; j<listes.get(i).size(); j++) {
				System.out.print(listes.get(i).get(j).toString()+"   ");
			}
			System.out.println("");
		}
		System.out.println("\n\n");
	}

}
