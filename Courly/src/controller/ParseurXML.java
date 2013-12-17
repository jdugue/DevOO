package controller;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Depot;
import model.Lieu;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import model.Plan;
import model.Tournee;
import model.Troncon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;


public class ParseurXML {
	
	public ParseurXML() {
		
	}
	
	public File ouvrirFichier(String path) {
		File xml = new File(path);
		return xml;		
	}
	
	public Plan construirePlanXML(String file) throws FileNotFoundException, NumberFormatException, SAXException {
		Plan plan = null;
		
		File xml = ouvrirFichier(file);
		
		//Si le fichier existe
		//TODO : normaliser les exceptions si bug
		if (!xml.exists()) {
			throw new FileNotFoundException();
		}
		else {
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
                	   vectNoeuds.add(leNouveauNoeud.getId(), leNouveauNoeud);                	   
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
                	   vectNoeuds.get(idNoeudCourant).setTronconsSortants(tronconsNoeud);
                   }
                   plan = new Plan();
                   plan.setNoeuds(vectNoeuds);
                   plan.setTroncons(vectTroncons);
                   
               }	
               else {
            	   throw new SAXException();
               }
			}
			catch (ParserConfigurationException e) {
				System.out.println(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return plan;
	}
	
	public Tournee construireTourneeXML(String file) throws java.text.ParseException, ParserConfigurationException, SAXException, IOException {

		File xml = ouvrirFichier(file);
		Tournee tournee = new Tournee();
		//Si le fichier existe
		if (xml.exists()) {

			ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
			ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();

			Depot depot = new Depot();
			// creation d'un constructeur de documents a l'aide d'une fabrique
			DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
			// lecture du contenu d'un fichier XML avec DOM
			Document document = constructeur.parse(xml);
			Element racine = document.getDocumentElement();

			if (racine.getNodeName().equals("JourneeType")) {

				//Traitement du/des depots
				NodeList listeDepot = racine.getElementsByTagName("Entrepot");
				//Ici je prend que le 1er element
				Element depotElement = (Element) listeDepot.item(0);
				depot.construireAPartirDeDOMXML(depotElement);
				tournee.setDepot(depot);

				//Traitement des plages horaires
				NodeList listePlages = racine.getElementsByTagName("Plage");
				for(int i=0;i<listePlages.getLength();i++) {
					PlageHoraire plage = new PlageHoraire();
					Element plageElement = (Element) listePlages.item(i);
					ArrayList<Livraison> livraisonsPlage = plage.construireAPartirDeDOMXML(plageElement);
					livraisons.addAll(livraisonsPlage);
					if(plages.isEmpty() || !intersectionPlages(plage,plages)){
						plages.add(plage);
					}
					else {
						throw new SAXException();
					}
				}

			}
			else {
				throw new SAXException();
			}
			tournee.setPlagesHoraire(plages);
			tournee.setLivraisons(livraisons);
		}

		return tournee;
	}
        
	private boolean intersectionPlages(PlageHoraire plage,ArrayList<PlageHoraire> plages) {
		for(PlageHoraire p : plages) {
			if(plage.getHeureDebut().before(p.getHeureFin()) && plage.getHeureFin().after(p.getHeureFin())) {
				return true;
			}
			else if(plage.getHeureDebut().before(p.getHeureDebut()) && plage.getHeureFin().after(p.getHeureDebut())){
				return true;
			}
		}
		return false;
	}

	public void setNoeudsFromTournee(Tournee tournee, Plan plan) {
		Integer adresseDepot = tournee.getDepot().getAdresse();
		tournee.getDepot().setNoeud(plan.getNoeuds().get(adresseDepot));
		for (int i=0;i< tournee.getLivraisons().size();i++) {
			Integer adresse = tournee.getLivraisons().get(i).getAdresse();
			tournee.getLivraisons().get(i).setNoeud(plan.getNoeuds().get(adresse));
		}
	}
}
