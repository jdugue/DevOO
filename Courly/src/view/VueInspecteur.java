/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurInspecteur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import model.Lieu;
import model.Depot;
import model.Livraison;
import model.Noeud;
import model.PlageHoraire;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueInspecteur extends javax.swing.JPanel {

    private ControleurInspecteur controleur;
    private AffichageMode mode = AffichageMode.Empty;
    
    public static enum AffichageMode {
     Empty, NoeudSelected, LivraisonSelected, LivraisonEdit
}
    /**
     * Creates new form VueInspecteur
     */
    public VueInspecteur(ControleurInspecteur controleur) {
        
        initComponents();
        this.setMode(this.mode);
        this.controleur = controleur;
    }
    
    private void clean() {
        this.adresseLabel.setText("");
        this.livraisonIDLabel.setText("");
        this.clientIDTextField.setText("");
        //this.plageHoraireDebutTextField.setText("");
        //this.plageHoraireFinTextField.setText("");
        this.heurePassageTextField.setText("");
        
    }
    
    private void setLivraisonAreaTitle(String title) {
        this.livraisonTitleLabel.setText(title);
    }

    private void setAdresse(String adresse) {
        this.adresseLabel.setText(adresse);
    }
    
    private void setLivraison(Livraison livraison) {
        //this.livraisonIDLabel.setText(Integer.toString(livraison.getId()));
        this.clientIDTextField.setText(Integer.toString(livraison.getClient()));
        this.heurePassageTextField.setText(new SimpleDateFormat("HH:mm:s").format(livraison.getHeurePassage().getTime()));
        this.plagesHorairesComboBox.getModel().setSelectedItem(livraison.getPlageHoraire());
    }
    
    private void setLivraisonEnabled(boolean enable) {
        this.livraisonIDLabel.setText("");
        this.clientIDTextField.setEnabled(enable);
        this.heurePassageTextField.setEnabled(enable);
        this.plagesHorairesComboBox.setEnabled(enable);
    }
    
    
    
    private void cleanLivraison() {
        this.livraisonIDLabel.setText("");
        this.clientIDTextField.setText("");
        this.heurePassageTextField.setText("");
        this.plagesHorairesComboBox.getModel().setSelectedItem(0);
    }
    
    public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires) {
        
        this.plagesHorairesComboBox.removeAllItems();
        for (PlageHoraire plageHoraire : plagesHoraires) {
            this.plagesHorairesComboBox.addItem(plageHoraire);
        }
    }

    public void setLieu(Lieu lieu) {
        if (lieu != null) {

            // Le noeud a un lieu
            this.setLivraisonEnabled(false);

            if (lieu.getClass() == Livraison.class) {

                // le lieu est une livraison
                this.setMode(AffichageMode.LivraisonSelected);
                this.setLivraison((Livraison)lieu);
                this.setLivraisonAreaTitle("Livraison n°" + ((Livraison)lieu).getId());

            } else if (lieu.getClass() == Depot.class) {
                // le lieu est un depot
                this.cleanLivraison();
            }
        } else {

            // le noeud n'a pas de lieu
            this.setMode(AffichageMode.NoeudSelected);
            this.setLivraisonAreaTitle("Ajouter une livraison");
        }            
    }
    
    public void setNoeud(Noeud noeud) {
        if (noeud != null) {
            this.setAdresse(Integer.toString(noeud.getId()));
        } else {
            this.setAdresse("");
        }
    }

    public void setMode(AffichageMode mode) {
        this.mode = mode;
        switch (mode) {
            case Empty:
                this.clean();
                this.cleanLivraison();
                this.setLivraisonEnabled(false);
                this.actionButton.setVisible(false);
                this.cancelButton.setVisible(false);
                break;
                
            case NoeudSelected:
                this.cleanLivraison();
                this.setLivraisonEnabled(true);
                this.actionButton.setVisible(true);
                this.actionButton.setText("Créer la livraison");
                this.cancelButton.setVisible(false);
                this.actionButton.setEnabled(true);
                break;
                
            case LivraisonSelected:
                this.setLivraisonEnabled(false);
                this.actionButton.setVisible(true);
                this.actionButton.setText("Modifier livraison");
                this.actionButton.setEnabled(true);
                this.cancelButton.setVisible(false);
                break; 
                
            case LivraisonEdit:
                this.setLivraisonEnabled(true);
                this.actionButton.setVisible(true);
                this.actionButton.setText("Valider les modifications");
                this.actionButton.setEnabled(true);
                this.cancelButton.setVisible(true);
                break;               
        } 
                
    }
    
    private void actionButtonPressedHandler() {
        switch (this.mode) {
            case LivraisonSelected:
                this.controleur.canEditLivraison();
                break;
                
            case LivraisonEdit:
                this.controleur.shouldEditLivraison();
                break;
                
            case NoeudSelected:
                this.controleur.shouldCreateLivraison(this., TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, null);
                break;
        }
    }
    
    private void cancelButtonPressedHandler() {
        switch (this.mode) {
                
            case LivraisonEdit:
                this.controleur.shouldCancelLivraisonEdit();
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        titleLabel = new javax.swing.JLabel();
        adresseLabel = new javax.swing.JLabel();
        livraisonTitleLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        clientIDTextField = new javax.swing.JTextField();
        livraisonIDLabel = new javax.swing.JLabel();
        heurePassageTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        plagesHorairesComboBox = new javax.swing.JComboBox();
        actionButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        cancelButton = new javax.swing.JButton();

        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setMaximumSize(new java.awt.Dimension(1000, 1000));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(350, 350));
        setSize(new java.awt.Dimension(350, 350));

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        titleLabel.setText("Noeud");

        adresseLabel.setText("adresse");

        livraisonTitleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        livraisonTitleLabel.setText("Livraison");

        jLabel2.setText("ID Client");

        clientIDTextField.setText("jTextField1");
        clientIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientIDTextFieldActionPerformed(evt);
            }
        });

        livraisonIDLabel.setText("ID");

        heurePassageTextField.setText("jTextField1");
        heurePassageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heurePassageTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Heure de passage");

        jLabel4.setText("Plage horaire");

        actionButton.setText("jButton1");
        actionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                actionButtonMousePressed(evt);
            }
        });

        cancelButton.setText("Annuler");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cancelButtonMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientIDTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(heurePassageTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(livraisonTitleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(livraisonIDLabel))
                            .addComponent(plagesHorairesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 138, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(actionButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(adresseLabel)
                        .addGap(14, 14, 14))))
            .addComponent(jSeparator2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(adresseLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(livraisonIDLabel)
                    .addComponent(livraisonTitleLabel))
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(clientIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(heurePassageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(plagesHorairesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(actionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clientIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientIDTextFieldActionPerformed

    private void heurePassageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heurePassageTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_heurePassageTextFieldActionPerformed

    private void actionButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actionButtonMousePressed
        // TODO add your handling code here:
        this.actionButtonPressedHandler();
    }//GEN-LAST:event_actionButtonMousePressed

    private void cancelButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMousePressed
        // TODO add your handling code here:
        this.cancelButtonPressedHandler();
    }//GEN-LAST:event_cancelButtonMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionButton;
    private javax.swing.JLabel adresseLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField clientIDTextField;
    private javax.swing.JTextField heurePassageTextField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel livraisonIDLabel;
    private javax.swing.JLabel livraisonTitleLabel;
    private javax.swing.JComboBox plagesHorairesComboBox;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
