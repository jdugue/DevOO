/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurFenetrePrincipale;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import model.Tournee;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueFenetrePrincipale extends javax.swing.JFrame implements ActionListener {
    
    private ControleurFenetrePrincipale controleurFenetrePrincipale;
    private HashMap<JRadioButtonMenuItem, Tournee> mapTournees;
    private JRadioButtonMenuItem selectedItem;
    
    private String commentText;
    private ButtonGroup frameSizeButtonGroup = new ButtonGroup();
    
    public static enum MessageType {
     MessageTypeError, MessageTypeWarning, MessageTypeSuccess, MessageTypeLog
}

    private ArrayList<String> colorMsg = new ArrayList();
    /**
     * Creates new form VueFenetrePrincipale
     */
    public VueFenetrePrincipale() {
        
        initComponents();
        
        initColors();
        
        this.setFrameSizeBig(true);
        this.commentText = "";
        this.commentArea.setEditable(false);
        this.commentArea.setContentType("text/html");
        
        this.controleurFenetrePrincipale = new ControleurFenetrePrincipale(this);
    }
    
    private void initColors() {
        colorMsg.add("#a00a11"); // ERROR
        colorMsg.add("#d49e15"); // WARNING
        colorMsg.add("#2ca024"); // SUCCESS
        colorMsg.add("#333333"); // LOG
    }

    public void setMessage(String msg, MessageType msgType) {        
        String text = "<font color='"+colorMsg.get(msgType.ordinal())+"'>" + msg + "</font><br>";
        this.commentText = this.commentText + text;
        this.commentArea.setText("<html><head></head><body>"+this.commentText+"</body></html>");
    }
    
    public void canLoadLivraison(boolean canLoadLivraison) {
        this.itemChargerLivraisons.setEnabled(canLoadLivraison);
    }
    
    public void canExportTournee(boolean canExportTournee) {
        this.itemExporter.setEnabled(canExportTournee);
    }
    
    public void canUndo(boolean canUndo) {
        this.itemAnnuler.setEnabled(canUndo);
    }
    
    public void canRedo(boolean canRedo) {
        this.itemRefaire.setEnabled(canRedo);
    }
    
    public void addTournee(Tournee tournee, String displayName, boolean select) {
        if (this.mapTournees == null) {
            this.mapTournees = new HashMap<JRadioButtonMenuItem, Tournee>();
        }
        
        JRadioButtonMenuItem item = new JRadioButtonMenuItem();
        item.setText(displayName);
        item.setSelected(false);
        item.addActionListener(this);
        
        this.mapTournees.put(item, tournee);
        this.subMenuTournees.add(item);
        
        if (select) {
            this.setSelectedItem(item);
            this.controleurFenetrePrincipale.selectTournee(tournee);
        }
        
        if (this.mapTournees.size() > 0) {
            this.subMenuTournees.setEnabled(true);
        }
    }
    
    public void removeAllTournee() {
        if (this.mapTournees != null) {
            for (JRadioButtonMenuItem item : this.mapTournees.keySet()) {
                this.subMenuTournees.remove(item);
            }
            this.mapTournees.clear();
        }
        this.subMenuTournees.setEnabled(false);
    }
    
    public boolean shouldAutoCalculateTournee() {
        return this.itemCalculAutomatique.isSelected();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        scrollPanePlan = new javax.swing.JScrollPane();
        scrollPaneInspecteur = new javax.swing.JScrollPane();
        scrollPaneComment = new javax.swing.JScrollPane();
        commentArea = new javax.swing.JEditorPane();
        menuBar = new javax.swing.JMenuBar();
        menuFichier = new javax.swing.JMenu();
        itemChargerPlan = new javax.swing.JMenuItem();
        itemChargerLivraisons = new javax.swing.JMenuItem();
        itemExporter = new javax.swing.JMenuItem();
        menuEdition = new javax.swing.JMenu();
        itemRefaire = new javax.swing.JMenuItem();
        itemAnnuler = new javax.swing.JMenuItem();
        menuVenu = new javax.swing.JMenu();
        itemZoomOut = new javax.swing.JMenuItem();
        itemZoomIn = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        subMenuFrameSize = new javax.swing.JMenu();
        radioItemFrameLitle = new javax.swing.JRadioButtonMenuItem();
        radioItemFrameBig = new javax.swing.JRadioButtonMenuItem();
        menuTournees = new javax.swing.JMenu();
        itemCalculer = new javax.swing.JMenuItem();
        itemCalculAutomatique = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        subMenuTournees = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 400));
        setPreferredSize(new java.awt.Dimension(1200, 800));

        scrollPanePlan.setMaximumSize(new java.awt.Dimension(800, 678));
        scrollPanePlan.setMinimumSize(new java.awt.Dimension(0, 0));
        scrollPanePlan.setPreferredSize(new java.awt.Dimension(800, 678));
        scrollPanePlan.setSize(new java.awt.Dimension(800, 678));

        commentArea.setEditable(false);
        commentArea.setDragEnabled(false);
        commentArea.setMaximumSize(new java.awt.Dimension(800, 88));
        commentArea.setMinimumSize(new java.awt.Dimension(200, 88));
        commentArea.setPreferredSize(new java.awt.Dimension(800, 88));
        commentArea.setSize(new java.awt.Dimension(800, 88));
        scrollPaneComment.setViewportView(commentArea);

        menuFichier.setText("Fichier");

        itemChargerPlan.setText("Charger plan...");
        itemChargerPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemChargerPlanActionPerformed(evt);
            }
        });
        menuFichier.add(itemChargerPlan);

        itemChargerLivraisons.setText("Charger livraisons...");
        itemChargerLivraisons.setEnabled(false);
        itemChargerLivraisons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemChargerLivraisonsActionPerformed(evt);
            }
        });
        menuFichier.add(itemChargerLivraisons);

        itemExporter.setText("Exporter...");
        itemExporter.setEnabled(false);
        itemExporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExporterActionPerformed(evt);
            }
        });
        menuFichier.add(itemExporter);

        menuBar.add(menuFichier);

        menuEdition.setText("Edition");

        itemRefaire.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        itemRefaire.setText("Refaire");
        itemRefaire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRefaireActionPerformed(evt);
            }
        });
        menuEdition.add(itemRefaire);

        itemAnnuler.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        itemAnnuler.setText("Annuler");
        itemAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAnnulerActionPerformed(evt);
            }
        });
        menuEdition.add(itemAnnuler);

        menuBar.add(menuEdition);

        menuVenu.setText("Vue");

        itemZoomOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS, java.awt.event.InputEvent.CTRL_MASK));
        itemZoomOut.setText("Zoom Out");
        itemZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemZoomOutActionPerformed(evt);
            }
        });
        menuVenu.add(itemZoomOut);

        itemZoomIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_EQUALS, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemZoomIn.setText("Zoom In");
        itemZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemZoomInActionPerformed(evt);
            }
        });
        menuVenu.add(itemZoomIn);
        menuVenu.add(jSeparator1);

        subMenuFrameSize.setText("Interface");

        this.frameSizeButtonGroup.add(radioItemFrameLitle);
        radioItemFrameLitle.setText("Petite");
        radioItemFrameLitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioItemFrameLitleActionPerformed(evt);
            }
        });
        subMenuFrameSize.add(radioItemFrameLitle);

        this.frameSizeButtonGroup.add(radioItemFrameBig);
        radioItemFrameBig.setSelected(true);
        radioItemFrameBig.setText("Grande");
        radioItemFrameBig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioItemFrameBigActionPerformed(evt);
            }
        });
        subMenuFrameSize.add(radioItemFrameBig);

        menuVenu.add(subMenuFrameSize);

        menuBar.add(menuVenu);

        menuTournees.setText("Tournée");

        itemCalculer.setText("Calculer");
        itemCalculer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCalculerActionPerformed(evt);
            }
        });
        menuTournees.add(itemCalculer);

        itemCalculAutomatique.setSelected(true);
        itemCalculAutomatique.setText("Calcul automatique");
        menuTournees.add(itemCalculAutomatique);
        menuTournees.add(jSeparator2);

        subMenuTournees.setText("Choisir livraison");
        subMenuTournees.setEnabled(false);
        menuTournees.add(subMenuTournees);

        menuBar.add(menuTournees);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneComment)
                    .addComponent(scrollPanePlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneInspecteur, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPaneInspecteur)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPanePlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneComment, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemChargerLivraisonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemChargerLivraisonsActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.shouldLoadLivraison();
    }//GEN-LAST:event_itemChargerLivraisonsActionPerformed

    private void itemZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemZoomOutActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.shouldZoomOut();
    }//GEN-LAST:event_itemZoomOutActionPerformed

    private void itemZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemZoomInActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.shouldZoomIn();
    }//GEN-LAST:event_itemZoomInActionPerformed

    private void itemExporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExporterActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.shouldExportTournee();
    }//GEN-LAST:event_itemExporterActionPerformed
    
    private void itemAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAnnulerActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.undo();
    }//GEN-LAST:event_itemAnnulerActionPerformed

    private void itemRefaireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRefaireActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.redo();
    }//GEN-LAST:event_itemRefaireActionPerformed

    private void itemChargerPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemChargerPlanActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.shouldLoadPlan();
    }//GEN-LAST:event_itemChargerPlanActionPerformed

    private void radioItemFrameLitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioItemFrameLitleActionPerformed
        // TODO add your handling code here:
        this.setFrameSizeBig(false);
    }//GEN-LAST:event_radioItemFrameLitleActionPerformed

    private void radioItemFrameBigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioItemFrameBigActionPerformed
        // TODO add your handling code here:
        this.setFrameSizeBig(true);
    }//GEN-LAST:event_radioItemFrameBigActionPerformed

    private void itemCalculerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCalculerActionPerformed
        // TODO add your handling code here:
        this.controleurFenetrePrincipale.shouldRecalculateTournee();
    }//GEN-LAST:event_itemCalculerActionPerformed
    
    public void actionPerformed(ActionEvent e) {
        //...Get information from the action event...
        //...Display it in the text area...
        this.tourneeItemActionPerformed(e);
    }
    
    private void tourneeItemActionPerformed(ActionEvent e) {
        JRadioButtonMenuItem item = (JRadioButtonMenuItem)e.getSource();
        Tournee tournee = this.mapTournees.get(item);
        this.setSelectedItem(item);
        this.controleurFenetrePrincipale.selectTournee(tournee);
    }

    private void setSelectedItem(JRadioButtonMenuItem selectedItem) {
        if (this.selectedItem != null) {
            this.selectedItem.setSelected(false);
        }
        
        this.selectedItem = selectedItem;
        this.selectedItem.setSelected(true);
    }
    
    private void setFrameSizeBig(boolean big) {

        Dimension fenetreSize;
        Dimension planSize;
        Dimension commentAreaSize;
            
        if (big) {            
            fenetreSize = new Dimension(1400, 800);
            planSize = new Dimension(1000, 658);
            commentAreaSize = new Dimension (800, 108);
        } else {
            fenetreSize = new Dimension(1200, 600);
            planSize = new Dimension(400, 458);
            commentAreaSize = new Dimension (400, 108);
        }
        
        this.setSize(fenetreSize);
        this.scrollPanePlan.setPreferredSize(planSize);
        this.scrollPanePlan.setSize(planSize);
        this.commentArea.setSize(commentAreaSize);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VueFenetrePrincipale fenetre = new VueFenetrePrincipale();
                fenetre.setVisible(true);
                
                
            }
        });
    }

    public JScrollPane getScrollPanePlan() {
        return scrollPanePlan;
    }

    public JEditorPane getCommentArea() {
        return commentArea;
    }

    public JScrollPane getScrollPaneInspecteur() {
        return scrollPaneInspecteur;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane commentArea;
    private javax.swing.JMenuItem itemAnnuler;
    private javax.swing.JCheckBoxMenuItem itemCalculAutomatique;
    private javax.swing.JMenuItem itemCalculer;
    private javax.swing.JMenuItem itemChargerLivraisons;
    private javax.swing.JMenuItem itemChargerPlan;
    private javax.swing.JMenuItem itemExporter;
    private javax.swing.JMenuItem itemRefaire;
    private javax.swing.JMenuItem itemZoomIn;
    private javax.swing.JMenuItem itemZoomOut;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdition;
    private javax.swing.JMenu menuFichier;
    private javax.swing.JMenu menuTournees;
    private javax.swing.JMenu menuVenu;
    private javax.swing.JRadioButtonMenuItem radioItemFrameBig;
    private javax.swing.JRadioButtonMenuItem radioItemFrameLitle;
    private javax.swing.JScrollPane scrollPaneComment;
    private javax.swing.JScrollPane scrollPaneInspecteur;
    private javax.swing.JScrollPane scrollPanePlan;
    private javax.swing.JMenu subMenuFrameSize;
    private javax.swing.JMenu subMenuTournees;
    // End of variables declaration//GEN-END:variables
}
