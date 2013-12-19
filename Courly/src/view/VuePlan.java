/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurPlan;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;
import model.Plan;
import model.Tournee;
import model.Trajet;
import model.Troncon;

/**
 * Elément graphique qui dessine un plan et une tournée.
 * @author tanguyhelesbeux
 */
public class VuePlan extends javax.swing.JPanel {
    
    private ControleurPlan controleur;
    private Plan plan = new Plan();
    private Tournee tournee = new Tournee();
    
    
    /**
     * Lient les éléments du modèle aux vues associées
     */
    private final HashMap<Noeud, VueNoeud> vueNoeuds = new HashMap<Noeud, VueNoeud>();
    private final HashMap<Troncon, VueTroncon> vueTroncons = new HashMap<Troncon, VueTroncon>();
        
    
    /**
     * Valeur en pixel de la taille des futur VueNoeud
     */
    public static final int noeudSize = 14;
    
    /**
     * Valeur en pixel qui sépare les bordures du plan de son contenu
     */
    private static final int padding = 50;
    
    /**
     * Coordonnées périphériques des éléments du plan
     */
    private int minX, minY, maxX, maxY;
    
    /**
    * Valeur de Zoom du plan
    */
    private double zoomScale = 1.0;
    
    
    /**
     * Couleur de fond du plan
     */
    private static final Color BackgroundColor = new Color(233, 229, 220);
    
    /**
     * Liste des couleurs utilisées pour représenter les plages horaires
     */
    private static ArrayList<Color> plageHoraireColors;
    
    
    
    

    public void setControleur(ControleurPlan controleur) {
        this.controleur = controleur;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
        this.paint();
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
        this.paint();
    }
    
    /**
     * Initialise les couleurs utilisable pour symbolier les plages horaires sur le plan
     */
    private void initPlageHoraireColors() {
        VuePlan.plageHoraireColors = new ArrayList<Color>();
        
        plageHoraireColors.add(Color.green);
        plageHoraireColors.add(Color.blue);
        plageHoraireColors.add(Color.pink);
        plageHoraireColors.add(Color.black);
        plageHoraireColors.add(Color.yellow);
    }
    
    /**
     * Détermine la couleur à utiliser pour une plage horaire
     * @param plageHoraire
     * @return Color
     */
    public Color colorForPlageHoraire(PlageHoraire plageHoraire) {
        int plageIndex = this.tournee.getPlagesHoraire().indexOf(plageHoraire)%plageHoraireColors.size();
        
        if (plageIndex >= 0) {
            return plageHoraireColors.get(plageIndex);
        } else {
            System.out.println("Un trajet n'a pas de plage horaire !!\nIl apparait en rouge.");
            return Color.RED;
        }
    }
    
    /**
     * Ajoute une vueNoeud au plan
     * @param vueNoeud : VueNoeud à ajouter
     */
    private void addVueNoeud(VueNoeud vueNoeud) {
        this.add(vueNoeud);
        vueNoeud.setPlan(this);
        vueNoeud.setVisible(true);
    }

    private void setMinX(int minX) {
        this.minX = minX;
        this.updateVuePlanFrame();
    }

    private void setMinY(int minY) {
        this.minY = minY;
        this.updateVuePlanFrame();
    }

    private void setMaxX(int maxX) {
        this.maxX = maxX;
        this.updateVuePlanFrame();
    }

    private void setMaxY(int maxY) {
        this.maxY = maxY;
        this.updateVuePlanFrame();
    }
    
    /**
     * Modifie les limites du plan pour recadrer sa vue.
     */
    private void updateVuePlanFrame() {
        Dimension dimension = new Dimension(this.scaledCoordonateHorizontal(maxX) + padding, this.scaledCoordonateVertical(maxY) + padding);
        this.setPreferredSize(dimension);
    }

    /**
     * Modifie le zoom du plan et le redessine
     * @param zoomScale : nouvelle 
     */
    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
        this.updateVuePlanFrame();
        this.paint();
    }
    /**
     * Ajoute le noeud au plan
     * @param noeud : Noeud avec lequel la vueNoeud est instanciée
     */
    public void addNoeud(Noeud noeud) {        
        this.plan.addNoeud(noeud);  
        this.createVueNoeudFromNoeud(noeud);
    }
    
    /**
     * Converti une coordonnée verticale réelle en coordonnée d'affichage.
     * @param coordonate : coordonnée réelle
     * @return  coordonnée en pixels pour l'affichage
     */
    private int scaledCoordonateVertical(int coordonate) {
        return (int)(this.zoomScale * (coordonate - minY)) + padding;
    }
    
    /**
     * Converti une coordonnée horizontale réelle en coordonnée d'affichage.
     * @param coordonate : coordonnée réelle
     * @return  coordonnée en pixels pour l'affichage
     */
    private int scaledCoordonateHorizontal(int coordonate) {
        return (int)(this.zoomScale * (coordonate - minX)) + padding;
    }
    
    
    /**
     * Converti une taille dans réelle en une taille d'affichage.
     * @param size : taille réelle
     * @return taille en pixels pour l'affichage
     */
    private int scaledSize(int size) {
        return (int)(this.zoomScale * size);
    }
    
    
    /**
     * Ajoute les noeuds au plan.
     * @param noeuds : ArrayList de Noeud à ajouter
     */
    public void addAllNoeuds(ArrayList<Noeud> noeuds) {
        for (Noeud noeud : noeuds) {
            this.addNoeud(noeud);
        }
    }  
    
    /**
     * Créer une VueTroncon et l'ajoute au plan.
     * @param troncon : troncon avec lequel la vue sera instanciée
     */
    private void createVueTronconFromTroncon(Troncon troncon) {

        VueTroncon vueTroncon = this.vueTroncons.get(troncon);
        
        if (vueTroncon == null) {
            // Vue Troncon
            vueTroncon = new VueTroncon(troncon, this);
            this.vueTroncons.put(troncon, vueTroncon);

            int x = Math.min(troncon.getOrigine().getX(), troncon.getDestination().getX());
            int y = Math.min(troncon.getOrigine().getY(), troncon.getDestination().getY());
            vueTroncon.setLocation(this.scaledCoordonateHorizontal(x) - noeudSize/2, this.scaledCoordonateVertical(y) - noeudSize/2);

            int width = Math.abs(troncon.getDestination().getX() - troncon.getOrigine().getX());
            int height = Math.abs(troncon.getDestination().getY() - troncon.getOrigine().getY());

            vueTroncon.setSize(this.scaledSize(width) + noeudSize, this.scaledSize(height) + noeudSize);

            this.add(vueTroncon);
        } else {
           vueTroncon.setTronconRetour(troncon);
        }  
    }
    
    /**
     * Ajoute le tronçon au plan.
     * @param troncon : tronçon à ajouter
     */
    public void addTroncon(Troncon troncon) {
        this.plan.addTroncon(troncon);
        this.createVueTronconFromTroncon(troncon);
        //this.paint();
    }
    
    
    /**
     * Ajoute les troncons passés en paramêtre au plan.
     * @param troncons : tronçons à ajouter au plan
     */
    public void addAllTroncons(ArrayList<Troncon> troncons) {
        for (Troncon troncon : troncons) {
        	this.plan.addTroncon(troncon);
        }
        this.paint();
    }
    
    
    /**
     * Efface tout le contenu du plan et vide supprime les vueNoeuds et vueTroncons.
     */
    private void cleanVuePlan() {
        this.removeAll();
        this.updateUI();
        this.vueNoeuds.clear();
        this.vueTroncons.clear();
    }
    
    
    /**
     * Calcul la zone du plan qui contient des éléments à afficher.
     * Tous les éléments dessinés se trouveront entre les points (xMin, yMin) et (xMax, yMax)
     */
    private void findFrameBounds() {
        boolean first = true;
        for (Noeud noeud : this.plan.getNoeuds()) {
            if (first) {
                this.setMaxX(noeud.getX());
                this.setMaxY(noeud.getY());
                this.setMinX(noeud.getX());
                this.setMinY(noeud.getY());
                first = false;
            } else {
                if (noeud.getX() > this.maxX) {
                    this.setMaxX(noeud.getX());
                }
                if (noeud.getY() > this.maxY) {
                    this.setMaxY(noeud.getY());
                }
                if (noeud.getX() < this.minX) {
                    this.setMinX(noeud.getX());
                }
                if (noeud.getY() < this.minY) {
                    this.setMinY(noeud.getY());
                }
            }
        }
    }
    
    /**
     * Redessine le plan et tous les objets qu'il contient.
     */
    public void paint() {
        this.cleanVuePlan();
        this.findFrameBounds();
        
        for (Noeud noeud : this.plan.getNoeuds()) {
            this.createVueNoeudFromNoeud(noeud);
        }
        this.paintLivraisons();
        this.paintDepot();
        
        for (Troncon troncon : this.plan.getTroncons()) {
            this.createVueTronconFromTroncon(troncon);
        }
        this.paintTrajets();
        
        for (VueTroncon vueTroncon : this.vueTroncons.values())
        {
            vueTroncon.repaint();
        }
    }
    
    /**
     * Dessine les trajets de la tournée.
     */
    private void paintTrajets() {
        if (this.tournee != null) {
            if (this.tournee.getTrajets() != null) {
                for (Trajet trajet : this.tournee.getTrajets()) {
                    for (Troncon troncon : trajet.getTroncons()) {
                        VueTroncon vueTroncon = this.vueTroncons.get(troncon);

                        if (vueTroncon != null) {
                            vueTroncon.addTrajet(trajet);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Dessine les livraisons de la tournée.
     */
    private void paintLivraisons() {
        if (this.tournee != null) {
            if (this.tournee.getLivraisons() != null) {
                for (Livraison livraison : this.tournee.getLivraisons()) {
                    VueNoeud vueNoeud = this.vueNoeuds.get(livraison.getNoeud());

                    if (vueNoeud != null) {
                        int livraisonOrder = this.tournee.getLivraisons().indexOf(livraison) + 1;
                        boolean shouldDisplayOrder = (livraison.getHeureArrivee() != null);
                        VueLivraison vueLivraison = new VueLivraison(livraison, livraisonOrder, shouldDisplayOrder);
                        vueNoeud.setVueLivraison(vueLivraison);
                        this.setComponentZOrder(vueNoeud, this.getComponentCount()-1);
                    }
                }
            }
        }
    }
    
    
    /**
     * Dessine le dépot de la tournée.
     */
    private void paintDepot() {
        if (this.tournee != null) {
            if (this.tournee.getDepot() != null) {
                VueNoeud vueNoeud = this.vueNoeuds.get(this.tournee.getDepot().getNoeud());

                if (vueNoeud != null) {
                    vueNoeud.setVueLieu(new VueDepot(this.tournee.getDepot()));
                    this.setComponentZOrder(vueNoeud, this.getComponentCount()-1);
                }
            }
        }
    }

    /**
     * Instancie une vueNoeud. La vue est initialisée avec le noeud passé en paramêtre et ajoutée au plan.
     * Si une vueNoeud existe déjà pour le noeud donné, rien ne se passe.
     * @param noeud : noeud avec lequel est instanciée la vueNoeud
     */
    private void createVueNoeudFromNoeud(Noeud noeud) {
        VueNoeud vueNoeud = this.vueNoeuds.get(noeud);
        if (vueNoeud == null) {
            // Vue noeud
            vueNoeud = new VueNoeud(noeud);
            this.vueNoeuds.put(noeud, vueNoeud);

            vueNoeud.setSize(noeudSize, noeudSize);
            int xLocation = this.scaledCoordonateHorizontal(vueNoeud.getNoeud().getX()) - vueNoeud.getWidth()/2;
            int yLocation = this.scaledCoordonateVertical(vueNoeud.getNoeud().getY()) - vueNoeud.getHeight()/2;
            vueNoeud.setLocation(xLocation, yLocation);
            vueNoeud.setPlan(this);

            this.addVueNoeud(vueNoeud);
        }
    }
    
    /**
     * Attribue l'état selected à la vueNoeud associée au noeud passé en paramêtre.
     * @param noeud : noeud qui doit être sélectionné/déselectionné
     * @param selected
     */
    public void setNoeudSelected(Noeud noeud, boolean selected) {
        VueNoeud vueNoeud = this.vueNoeuds.get(noeud);
        vueNoeud.setSelected(selected);
        if (vueNoeud.getVueLieu() != null) {
            vueNoeud.getVueLieu().setSelected(selected);
        }
        
    }
    
    /**
     * Appelé par vueNoeud, signale au controleur qu'un noeud a été sélectionné.
     * @param vueNoeud : vueNoeud cliquée
     */
    protected void didSelectVueNoeud(VueNoeud vueNoeud) {        
        Noeud noeud = vueNoeud.getNoeud();
        this.controleur.didSelectNoeud(noeud);
    }
    
    /**
     * Appelé par vueLieu, signale au controleur qu'un lieu a été sélectionné.
     * @param vueLieu : vueLieu cliquée
     */
    protected void didSelectVueLieu(VueLieu vueLieu) {
        VueNoeud vueNoeud = this.vueNoeuds.get(vueLieu.getLieu().getNoeud());
        vueNoeud.setSelected(true);
        this.controleur.didSelectLieu(vueLieu.getLieu());
    }
    
    
    /**
     * Creates new form VuePlan
     */
    public VuePlan() {
        initComponents();
        this.setBackground(BackgroundColor);
        this.initPlageHoraireColors();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
