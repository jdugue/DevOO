package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import model.Plan;
import model.Tournee;
import model.Trajet;
import model.Troncon;


public class Dijkstra {
	
	static final long SEC_IN_MILISEC = 1000;
	static final long MIN_IN_MILISEC = 60000;

	/**
	 * computePaths permet de trouver pour un noeud pass� en parametre le temps de parcours jusqu'a tous les autres 
	 * noeuds. De cette fa�on l'attribut previous de tous les noeuds sont fixes par rapport au chemins trouves.
	 * @param noeudSource : le noeud pour lequel on veut calculer les temps de parcours.
	 */
	public void computePaths(Noeud noeudSource) {
		noeudSource.setMinTemps(0);
		PriorityQueue<Noeud> nodeQueue = new PriorityQueue<Noeud>();
		nodeQueue.add(noeudSource);

		while(!nodeQueue.isEmpty()) {
			Noeud head = nodeQueue.poll();

			for (Troncon t : head.getTronconsSortants()) {
				Noeud next = t.getDestination();
				double cost = t.getCost();
				double distanceTotale = head.getMinTemps()+cost;

				if (distanceTotale<next.getMinTemps()) {
					nodeQueue.remove(next);
					next.setMinTemps(distanceTotale);
					next.setPrevious(head);
					nodeQueue.add(next);
				}
			}
		}
	}

	/**
	 * plusCourtCheminVers permet de calculer le plus court chemin d'un noeud pour lequel on a execute computePaths a
	 * un noeud passe en parametre.
	 * @param destination : le noeud de destination du plus court chemin.
	 * @return une liste de noeuds representant le chemin a suivre. Le premier noeud est le noeud d'origine et le dernier
	 * est destination.
	 */
	public List<Noeud> plusCourtCheminVers(Noeud destination) {
		List<Noeud> chemin = new ArrayList<Noeud>();

		for(Noeud noeud=destination;noeud!=null;noeud=noeud.getPrevious()) {
			chemin.add(noeud);
		}
		Collections.reverse(chemin);

		return chemin;
	}

	/**
	 * resetPlan permet de remettre les attributs previous et minTemps de tous les noeuds d'un plan a leur valeur initiale.
	 * @param plan : le plan pour lequel on veut reinitialiser les noeuds.
	 */
	public void resetPlan(Plan plan) {
		for (Noeud n : plan.getNoeuds()) {
			n.setPrevious(null);
			n.setMinTemps(Double.POSITIVE_INFINITY);
		}
	}

	/**
	 * trouverArcMini permet de trouver la valeur la plus petite de temps de parcours entre deux livraisons.
	 * @param trajets : liste des differents trajets entre les livraisons
	 * @return la valeur du plus court temps de parcours entre deux livraisons
	 */
	public Integer trouverArcMini(List<ArrayList<Trajet>> trajets) {
		Integer ret=(int)Double.POSITIVE_INFINITY;

		for(ArrayList<Trajet> l : trajets) {
			for(Trajet t : l) {
				if (t!=null && t.getTempsTrajet()<ret) {
					ret = t.getTempsTrajet();
				}
			}
		}
		return ret;
	}

	/**
	 * trouverArcMaxi permet de trouver la valeur la plus grande de temps de parcours entre deux livraisons.
	 * @param trajets : liste des differents trajets entre les livraisons
	 * @return la valeur du plus long temps de parcours entre deux livraisons
	 */
	public Integer trouverArcMaxi(List<ArrayList<Trajet>> trajets) {
		Integer ret=0;

		for(ArrayList<Trajet> l : trajets) {
			for(Trajet t : l) {
				if (t!=null && t.getTempsTrajet()>ret) {
					ret = t.getTempsTrajet();
				}
			}
		}
		return ret;
	}

	/**
	 * generateMatriceCost permet de creer un tableau a double entree representant le temps de parcours des arcs
	 * entre les livraisons. matriceCosts[i][j] represente le temps de parcours de l'arc (i,j).
	 * @param trajets : liste des differents trajets entre les livraisons
	 * @param nbLivraisons : le nombre de points de livraison et du depot.
	 * @return le tableau des temps de parcours
	 */
	public int[][] generateMatriceCost(List<ArrayList<Trajet>> trajets,Integer nbLivraisons) {
		int[][] ret = new int[nbLivraisons][nbLivraisons];

		for(int i=0;i<nbLivraisons;i++){
			int[] current = new int[nbLivraisons];

			for(int j=0;j<nbLivraisons;j++) {
				if(j==i) {
					current[j]=(int)Double.POSITIVE_INFINITY;
				}
				else {
					current[j]=trajets.get(i).get(j).getTempsTrajet();
				}
			}
			ret[i]=current;
		}		
		return ret;
	}

	/**
	 * generateMatriceSucc permet de creer un tableau a double entree qui contient pour chaque livraison ses livraisons
	 * destinations. matriceSucc[i] contient toutes les livraisons auxquelles on peut se rendre a partir de la livraison i.
	 * @param tournee : la Tournee pour laquelle on veut generer un parcours
	 * @return le tableau des successeurs des livraisons
	 */
	public int[][] generateMatriceSucc(Tournee tournee) {
		int nbLivraisons = tournee.getLivraisons().size();
		int[][] ret = new int[nbLivraisons+1][nbLivraisons+1];
		PlageHoraire first = tournee.getFirstPlageHoraire();
		
		//successeurs de Depot : premiere plage horaire
		int[] succDepot = new int[first.getLivraisons().size()];
		for(int i=0; i<first.getLivraisons().size(); i++) {
			
			succDepot[i]=tournee.getLivraisons().indexOf(first.getLivraisons().get(i)) + 1;
		}
		ret[0] = succDepot;
		
		//successeurs de livraison i+1
		for (int i=0;i<nbLivraisons;i++){
			PlageHoraire currentTime = tournee.getLivraisons().get(i).getPlageHoraire();
			PlageHoraire next = new PlageHoraire();
			if (tournee.getPlagesHoraire().indexOf(currentTime) < tournee.getPlagesHoraire().size()-1) {
				next = tournee.getPlagesHoraire().get(tournee.getPlagesHoraire().indexOf(currentTime)+1);
			}			
			int[] current = new int[currentTime.getLivraisons().size() + next.getLivraisons().size()];
			current[0] = 0;
			int index=1;
			for(int j=0;j<nbLivraisons;j++) {
				if(i!=j && ( tournee.getLivraisons().get(j).getPlageHoraire().equals(currentTime) || tournee.getLivraisons().get(j).getPlageHoraire().equals(next)) ) {
					current[index++]=j+1;
				}
			}
			ret[i+1]=current;
		}		
		return ret;
	}

	/**
	 * 
	 * @param plan : le Plan actuellement chargé
	 * @param tournee : La tournee dont on souhaite générer les trajets
	 * @return Une matrice de trajets reliant les trajets de la tournée
	 */
	public List<ArrayList<Trajet>> genererMatriceTrajets(Plan plan, Tournee tournee) {
		List<ArrayList<Trajet>> trajets = new ArrayList<ArrayList<Trajet>>();

		
		for(int i=0;i<tournee.getLivraisons().size()+1;i++){
			resetPlan(plan);
			ArrayList<Trajet> trajetsCourants = new ArrayList<Trajet>();
			if(i==0) {
				Noeud noeudDepot = tournee.getDepot().getNoeud();
				computePaths(noeudDepot);
			} else {
				Noeud noeudLivraison = tournee.getLivraisons().get(i-1).getNoeud();
				computePaths(noeudLivraison);
			}
			for (int j=0;j<tournee.getLivraisons().size()+1;j++) {
				if(j!=i) {
					if(j==0) {
						List<Noeud> chemin = plusCourtCheminVers(tournee.getDepot().getNoeud());
						
						Trajet trajet = new Trajet(chemin);
						trajetsCourants.add(j,trajet);
					} else {
						List<Noeud> chemin = plusCourtCheminVers(tournee.getLivraisons().get(j-1).getNoeud());

						Trajet trajet = new Trajet(chemin);
						trajetsCourants.add(j,trajet);
					}
				}
				else {
					trajetsCourants.add(j,null);
				}
			}
			trajets.add(trajetsCourants);
		}

		return trajets;
	}
	
	/**
	 * 
	 * @param tournee : La tournee que l'on souhaite tracer
	 * @param trajets : La matrice des trajets reliant les livraisons de la tournée
	 */
	public void choco(Tournee tournee,List<ArrayList<Trajet>> trajets) {
		//Param Choco
		Integer nbLivraisons = tournee.getLivraisons().size()+1;
		Integer arcMini = trouverArcMini(trajets);
		Integer arcMaxi = trouverArcMaxi(trajets);
		int[][] matriceCosts = generateMatriceCost(trajets,nbLivraisons);
		int[][] matriceSucc = generateMatriceSucc(tournee);
		
		// Création du solveur
		Solver solver = new Solver();

		// Déclaration des variables
		IntVar[] xNext = new IntVar[nbLivraisons];
		for (int i = 0; i < nbLivraisons; i++) {
			xNext[i] = VariableFactory.enumerated("Next " + i, matriceSucc[i], solver);
		}
		IntVar[] xCost = VariableFactory.boundedArray("Cost ", nbLivraisons, arcMini, arcMaxi, solver);
		
		int	bound=nbLivraisons*arcMaxi - 1;
		
		//bound : cpmax*nbliv si bloq mettre derniere valeur
		IntVar xTotalCost = VariableFactory.bounded("Total cost ", nbLivraisons*arcMini, bound , solver);

		// Déclaration des contraintes
		for (int i = 0; i < nbLivraisons; i++) {
			solver.post(IntConstraintFactory.element(xCost[i], matriceCosts[i], xNext[i], 0, "none"));
		}
		solver.post(IntConstraintFactory.circuit(xNext,0));
		solver.post(IntConstraintFactory.sum(xCost, xTotalCost));

		// Résolution
		solver.set(IntStrategyFactory.firstFail_InDomainMin(xNext));
		solver.findOptimalSolution(ResolutionPolicy.MINIMIZE,xTotalCost);

		ArrayList<Trajet> trajetsTournee = new ArrayList<Trajet>();
		int abs = 0;
		for (int i =0; i<nbLivraisons;i++) {		
			int ord = xNext[abs].getValue();	
			Livraison livCourante = new Livraison();
			Trajet trajetCourant = trajets.get(abs).get(ord);			
			PlageHoraire plage;
			Date heureArrivee;
			Date heureDepart;
			
			if(ord==0) {//On va vers le dépot
				livCourante = tournee.getLivraisons().get(abs-1);
				heureDepart = new Date(livCourante.getHeureArrivee().getTime()+10*MIN_IN_MILISEC);
				livCourante.setHeureDepart(heureDepart);
				plage = livCourante.getPlageHoraire();
			} 
			else {
				Livraison livNext = tournee.getLivraisons().get(ord-1);	
				plage = livNext.getPlageHoraire();
				if(abs == 0) {//On venait du depot
					Date sortieDepot = tournee.getFirstPlageHoraire().getHeureDebut();
					heureArrivee = new Date(sortieDepot.getTime()+SEC_IN_MILISEC*trajetCourant.getTempsTrajet());
					livNext.setHeureArrivee(heureArrivee);
					heureDepart = new Date(livNext.getHeureArrivee().getTime() + MIN_IN_MILISEC*10);
					livNext.setHeureDepart(heureDepart);
				} else {
					livCourante = tournee.getLivraisons().get(abs-1);
					if(livCourante.getHeureArrivee().after(livCourante.getPlageHoraire().getHeureDebut()))
					{
						heureDepart = new Date(livCourante.getHeureArrivee().getTime() + MIN_IN_MILISEC*10);
					} else {
						heureDepart = new Date(livCourante.getPlageHoraire().getHeureDebut().getTime() + MIN_IN_MILISEC*10);
					}
					livCourante.setHeureDepart(heureDepart);
					heureArrivee = new Date(livCourante.getHeureDepart().getTime() + SEC_IN_MILISEC*trajetCourant.getTempsTrajet());
					livNext.setHeureArrivee(heureArrivee);
				}				
			}
			
			trajetCourant.setPlage(plage);
			trajetsTournee.add(trajetCourant);
			
			abs=ord;
		}
		
		tournee.setTrajets(trajetsTournee);
	}
	
	/**
	 * Permet de compléter l'instance de tournée passée en paramètre
	 * @param plan : Le plan actuel
	 * @param tournee : La tournée à compléter
	 */
	public void initTournee(Plan plan, Tournee tournee) {
		List<ArrayList<Trajet>> trajets = genererMatriceTrajets(plan, tournee);

		choco(tournee,trajets);
		Collections.sort(tournee.getLivraisons());
	}

}
