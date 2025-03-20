/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import Classes.Block;
import Classes.SimulatedDisc;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

/**
 *
 * @author andre
 */
public class SD extends javax.swing.JFrame {

    Block[] blocks;
    static SimulatedDisc sd;
    /**
     * Creates new form SD
     */
    public SD(SimulatedDisc sd) {
        this.sd = sd;
        this.blocks = new Block[sd.total_blocks];
        for (int i = 0; i < sd.total_blocks; i++) {
            blocks[i] = new Block(i + 1);
        }
        int numBotones = sd.total_blocks;
        initComponents();
        setTitle("Tablero de JToggleButtons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int maxColumnas = 5; 
        int filas = (int) Math.ceil((double) numBotones / maxColumnas); 
        setLayout(new GridBagLayout()); 


        crearTablero(numBotones, maxColumnas, filas);

        setSize(1200, 1000); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearTablero(int numBotones, int maxColumnas, int filas) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0; 
        gbc.insets = new Insets(5, 5, 5, 5); 

        for (int i = 0; i < numBotones; i++) {
            JToggleButton toggleButton = new JToggleButton("" + (i + 1));
            toggleButton.setPreferredSize(new Dimension(120, 60)); 
   
            gbc.gridx = i % maxColumnas;
            gbc.gridy = i / maxColumnas; 
            add(toggleButton, gbc); 
            if (blocks[i].ocupado) {
                toggleButton.setSelected(true);

            }
//            toggleButton.setEnabled(false);
            toggleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (toggleButton.isSelected()) {
                        toggleButton.setSelected(false);
                    } else {
                        toggleButton.setSelected(true);

                    }
                }
            });
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1140;
        gridBagConstraints.ipady = 590;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 29, 42);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(SD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SD(sd).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
