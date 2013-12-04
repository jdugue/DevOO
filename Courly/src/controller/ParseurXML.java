package controller;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Noeud;
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
		
	}
	
	public static void main(String[] args) {
		ParseurXML pars = new ParseurXML();
		pars.construirePlanXML();
	}
}
