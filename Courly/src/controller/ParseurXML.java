package controller;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Noeud;
import model.Plan;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class ParseurXML {
	
	public ParseurXML() {
		
	}
	
	public File ouvrirFichier() {
		File xml = new File("/home/mael/Documents/WorkplaceEclipse/DevOO/XML Examples/plan10x10.xml");
		return xml;
		
	}
	
	public Plan construirePlanXML() {
		Plan plan = new Plan();
		
		File xml = ouvrirFichier();
		
		//Si le fichier existe
		if (xml != null) {			
			try {
                // creation d'un constructeur de documents a l'aide d'une fabrique
               DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
               // lecture du contenu d'un fichier XML avec DOM
               Document document = constructeur.parse(xml);
               Element racine = document.getDocumentElement();
               
               if (racine.getNodeName().equals("Reseau")) {
            	   
            	   //Traitement des noeuds
                   NodeList liste = racine.getElementsByTagName("Noeud");
                   Vector<Noeud> vectNoeuds = new Vector<Noeud>();
                   
                   for(int i=0; i<liste.getLength();i++) {
                	   Element noeudElement = (Element) liste.item(i);
                	   Noeud leNouveauNoeud = new Noeud();
                	   leNouveauNoeud.construireAPartirDeDOMXML(noeudElement);
                	   vectNoeuds.add(leNouveauNoeud.getID(), leNouveauNoeud);                	   
                   }
                   
                   plan.setNoeuds(vectNoeuds);
                    
               }			
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println(plan.getNoeuds().get(0).getX());
		return plan;
	}
	
	public void construireTourneeXML() {
		
	}
	
	public static void main(String[] args) {
		ParseurXML pars = new ParseurXML();
		pars.construirePlanXML();
	}
}
