/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurPlan;
import model.Troncon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import model.Trajet;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueTroncon extends javax.swing.JPanel {
    
    private Troncon tronconAller;
    private Troncon tronconRetour;
    private ArrayList<Trajet> trajets;
    
    private final ControleurPlan controleur;
    
    private static final int noeudSize = ControleurPlan.noeudSize;
    private static final int minWidth = noeudSize; 
    private static final int minHeight = noeudSize; 
    private static final int tronconWidth = 8;
    private static final int bordersWidth = 1;
    private static final int trajetWidth = 4;
    private static final int trajetPadding = 2;
    
    private static final Color FillColor = Color.WHITE;
    private static final Color BorderColor = new Color(198, 190, 180);
    
    private static final int padding = 10;

    /**
     * Creates new form VueTroncon
     * @param troncon
     * @param controleur
     */

    public VueTroncon(Troncon troncon, ControleurPlan controleur) {
        initComponents();
        this.setTronconAller(troncon);
        this.setVisible(true);
        this.setOpaque(false);
        this.controleur = controleur;
        this.setBackground(Color.BLACK);        
    }

    public Troncon getTronconAller() {
        return tronconAller;
    }

    public final void setTronconAller(Troncon troncon) {
        this.tronconAller = troncon;
    }

    public Troncon getTronconRetour() {
        return tronconRetour;
    }

    public void setTronconRetour(Troncon tronconRetour) {
        this.tronconRetour = tronconRetour;
    }
    
    public void addTrajet(Trajet trajet) {
        if (this.trajets == null) {
            this.trajets = new ArrayList<Trajet>();
        }
        this.trajets.add(trajet);
        this.repaint();
    }    
    
        @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);       
        
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(tronconWidth));
        
        int xDepart, yDepart, xArrivee, yArrivee;
        
         if (this.getWidth() <= minWidth) {
            xDepart = minWidth/2;
            xArrivee = minWidth/2;
        } else if (this.getTronconAller().getOrigine().getX() > this.getTronconAller().getDestination().getX()) {
            xDepart = this.getWidth() - noeudSize/2;
            xArrivee = noeudSize/2;
        } else {
            xDepart = noeudSize/2;
            xArrivee = this.getWidth() - noeudSize/2;
        }
        
        if (this.getHeight() <= minHeight) {
            yDepart = minHeight/2;
            yArrivee = minHeight/2;
        } else if (this.getTronconAller().getOrigine().getY() > this.getTronconAller().getDestination().getY()) {
            yDepart = this.getHeight() - noeudSize/2;
            yArrivee = noeudSize/2;
        } else {
            yDepart = noeudSize/2;
            yArrivee = this.getHeight() - noeudSize/2;
        }
        
        int width = this.getWidth();
        int height = this.getHeight();
        
        double size = Math.sqrt(Math.pow((xDepart - xArrivee), 2) + Math.pow((yDepart - yArrivee), 2));
        int intSize = (int)Math.round(size);
        //System.out.println(intSize);
        
        double angle;
        if (yDepart != yArrivee) {
            angle = Math.atan((double)(xDepart - xArrivee)/(yDepart - yArrivee));
        } else {
            angle = Math.PI/2;
        }        
        //System.out.println(angle);
        
        AffineTransform matrix = g2D.getTransform(); // Backup
        g2D.rotate(-angle, width/2, height/2);
        
        int x1Draw = width/2;
        int y1Draw = height/2 - intSize/2;
        int x2Draw = width/2;
        int y2Draw = height/2 + intSize/2;
        
        /* Begin */
        
        // fill troncon
        g.setColor(FillColor);
        g2D.setStroke(new BasicStroke(tronconWidth));
        g.drawLine(x1Draw, y1Draw, x2Draw, y2Draw);
        
        // draw borders
        g.setColor(BorderColor);
        g2D.setStroke(new BasicStroke(bordersWidth));
        g.drawLine(x1Draw - tronconWidth/2, y1Draw, x2Draw - tronconWidth/2, y2Draw);
        g.drawLine(x1Draw + tronconWidth/2, y1Draw, x2Draw + tronconWidth/2, y2Draw);
        
        // draw Trajets
        if (this.trajets != null && !this.trajets.isEmpty()) {
            int dephasage = (this.trajets.size() - 1)*(trajetWidth + trajetPadding)/2;            

            Stroke trajetStroke = new BasicStroke(trajetWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 8}, 0);
            g2D.setStroke(trajetStroke);

            for (Trajet trajet : this.trajets) {
                g.setColor(this.controleur.colorForPlageHoraire(trajet.getPlage()));
                g.drawLine(x1Draw - dephasage, y1Draw, x2Draw - dephasage, y2Draw);
                dephasage -= (trajetWidth + trajetPadding);
            }
        }        
        
        /* End */
        g2D.setTransform(matrix);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickedHandler(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitedHandler(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnterHandler(evt);
            }
        });

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

    private void mouseEnterHandler(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
    }                                  

    private void mouseExitedHandler(java.awt.event.MouseEvent evt) {                                    
        // TODO add your handling code here:
    }                                   

    private void mouseClickedHandler(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        System.out.println(this);
    }                       


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
