package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Depot;
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

/**
 * @author mael
 * Un parseur XML pour lire les fichiers d'entrée et instancier les classes correspondantes.
 */
public class ParseurXML {
	
	private final static String NODE_BASE_PLAN = "Reseau";
	private final static String NODE_NOEUD = "Noeud";
	private final static String NODE_TRONCONS = "TronconSortant";
	private final static String NODE_BASE_TOURNEE = "JourneeType";
	private final static String NODE_DEPOT = "Entrepot";
	private final static String NODE_PLAGE = "Plage";
	
	public ParseurXML() {
		
	}
	
	/**
	 * Ouvre un fichier
	 * @param path : Le chemin du fichier
	 * @return Un {@link File} instancié avec le fichier choisi
	 */
	public File ouvrirFichier(String path) {
		File xml = new File(path);
		return xml;		
	}
	
	/**
	 * Construit un plan depuis un XML
	 * @param file : Le chemin du fichier XML désiré
	 * @return Le Plan instancié
	 * @throws FileNotFoundException Si le fichier n'est pas trouvé
	 * @throws SAXException En cas d'erreur dans le XML
	 */
	public Plan construirePlanXML(String file) throws FileNotFoundException, SAXException {
		Plan plan = null;
		
		File xml = ouvrirFichier(file);
		
		//Si le fichier existe
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
               
               if (racine.getNodeName().equals(NODE_BASE_PLAN)) 
               {   
            	   //Traitement des noeuds
                   NodeList listeNoeuds = racine.getElementsByTagName(NODE_NOEUD);
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
                	   NodeList listeTroncons = noeudElement.getElementsByTagName(NODE_TRONCONS);
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
			} catch (IOException e) {
			}
		}
		return plan;
	}
	
	/**
	 * Construit une Tournee à partir d'un fichier XML
	 * @param file : Le chemin d'accès au fichier XML choisi
	 * @return Une Tournee instanciée
	 * @throws java.text.ParseException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
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

			if (racine.getNodeName().equals(NODE_BASE_TOURNEE)) {

				//Traitement du/des depots
				NodeList listeDepot = racine.getElementsByTagName(NODE_DEPOT);
				//Ici je prend que le 1er element
				Element depotElement = (Element) listeDepot.item(0);
				depot.construireAPartirDeDOMXML(depotElement);
				tournee.setDepot(depot);

				//Traitement des plages horaires
				NodeList listePlages = racine.getElementsByTagName(NODE_PLAGE);
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
        
	/**
	 * Permet de vérifier que la plage en cours d'instanciation ne chevauche pas une plage déjà existante
	 * @param plage : La plage courante
	 * @param plages : La liste des plages existantes
	 * @return False si tout va bien, True dans le cas contraire
	 */
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

	/**
	 * Lie les Livraisons de la Tournee aux noeuds du Plan
	 * @param tournee
	 * @param plan
	 */
	public void setNoeudsFromTournee(Tournee tournee, Plan plan) {
		Integer adresseDepot = tournee.getDepot().getAdresse();
		tournee.getDepot().setNoeud(plan.getNoeuds().get(adresseDepot));
		for (int i=0;i< tournee.getLivraisons().size();i++) {
			Integer adresse = tournee.getLivraisons().get(i).getAdresse();
			tournee.getLivraisons().get(i).setNoeud(plan.getNoeuds().get(adresse));
		}
	}
}
