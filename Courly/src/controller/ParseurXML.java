package controller;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Depot;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import model.Plan;
import model.Troncon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class ParseurXML {
	
	public ParseurXML() {
		
	}
	
	public File ouvrirFichier(String path) {
		File xml = new File(path);
		return xml;
		
	}
	
	public Plan construirePlanXML() {
		Plan plan = new Plan();
		
		File xml = ouvrirFichier("../XML Examples/plan10x10.xml");
		
		//Si le fichier existe
		//TODO : normaliser les exceptions si bug
		if (xml != null) {			
			try {
                // creation d'un constructeur de documents a l'aide d'une fabrique
               DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
               // lecture du contenu d'un fichier XML avec DOM
               Document document = constructeur.parse(xml);
               Element racine = document.getDocumentElement();
               
               if (racine.getNodeName().equals("Reseau")) 
               {   
            	   //Traitement des noeuds
                   NodeList listeNoeuds = racine.getElementsByTagName("Noeud");
                   ArrayList<Noeud> vectNoeuds = new ArrayList<Noeud>();
                   
                   for(int i=0; i<listeNoeuds.getLength();i++) 
                   {
                	   Element noeudElement = (Element) listeNoeuds.item(i);
                	   Noeud leNouveauNoeud = new Noeud();
                	   leNouveauNoeud.construireAPartirDeDOMXML(noeudElement);
                	   vectNoeuds.add(leNouveauNoeud.getID(), leNouveauNoeud);                	   
                   }
                   
                   
                   //Traitement Tronçons
                   ArrayList<Troncon> vectTroncons = new ArrayList<Troncon>();
                   for(int i=0; i<listeNoeuds.getLength();i++) 
                   {                	    
                	   Element noeudElement = (Element) listeNoeuds.item(i);
                	   Integer idNoeudCourant = Integer.parseInt(noeudElement.getAttribute("id"));
                	   
                	   //On récupère les Troncons issus de ce noeud
                	   NodeList listeTroncons = noeudElement.getElementsByTagName("TronconSortant");
                	   ArrayList<Troncon> tronconsNoeud = new ArrayList<Troncon>();
                	   
                	   for (int j=0; j<listeTroncons.getLength();j++) 
                	   {
                		   Element tronconElement = (Element) listeTroncons.item(j);
                		   Troncon leNouveauTroncon = new Troncon();
                		   leNouveauTroncon.construireAPartirDeDOMXML(tronconElement,idNoeudCourant,vectNoeuds);
                		   
                		   //Ajout à la liste des tous les tronçons
                		   vectTroncons.add(leNouveauTroncon);
                		   
                		   //Ajout à la liste des tronçons du noeud courant
                		   tronconsNoeud.add(leNouveauTroncon);
                	   }
                	   vectNoeuds.get(idNoeudCourant).setTroncons(tronconsNoeud);
                   }
                   plan.setNoeuds(vectNoeuds);
                   plan.setTroncons(vectTroncons);
                   
               }			
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
		return plan;
	}
	
	public void construireTourneeXML() {
		
		File xml = ouvrirFichier("../XML Examples/livraison10x10-2.xml");
		
		//Si le fichier existe
		//TODO : normaliser les exceptions si bug
		if (xml != null) {
			
			ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
			ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();

			Depot depot = new Depot();
			try {
                // creation d'un constructeur de documents a l'aide d'une fabrique
               DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
               // lecture du contenu d'un fichier XML avec DOM
               Document document = constructeur.parse(xml);
               Element racine = document.getDocumentElement();
               
               if (racine.getNodeName().equals("JourneeType")) {
            	   
            	   //Traitement du/des depots
            	   NodeList listeDepot = racine.getElementsByTagName("Entrepot");
            	   //Ici je prend que le 1er element
            	   //TODO Demander si il peut en avoir plusieurs (de depots)
            	   Element depotElement = (Element) listeDepot.item(0);
            	   depot.construireAPartirDeDOMXML(depotElement);
            	   
            	   //Traitement des plages horaires
            	   NodeList listePlages = racine.getElementsByTagName("Plage");
            	   for(int i=0;i<listePlages.getLength();i++) {
            		   PlageHoraire plage = new PlageHoraire();
            		   Element plageElement = (Element) listePlages.item(i);
            		   ArrayList<Livraison> livraisonsPlage = plage.construireAPartirDeDOMXML(plageElement);
            		   livraisons.addAll(livraisonsPlage);
            		   plages.add(plage);
            	   }
            	   
               }
               
			}
			catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(plages.get(0).getLivraisons().get(0).getClient());
		}
		
		
		
	}
	
	public static void main(String[] args) {
		ParseurXML pars = new ParseurXML();
		pars.construireTourneeXML();
	}
}
