/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import Classes.Directory;
import Classes.FileEntry;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import Classes.FileSystem;
import Classes.FileTreeContextMenu;
import javax.swing.JMenuItem;
import structures.Node;

/**
 *
 * @author andre
 */
public class ArbolFS extends javax.swing.JFrame {

    private static JTree tree;
    private static FileSystem fileSystem;

    /**
     * Creates new form ArbolFS
     */
    public ArbolFS(FileSystem fileSystem) {
        initComponents();
//        this.setVisible(false);
        this.fileSystem = fileSystem;
//        JFrame frame = new JFrame("File System");
//        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
//        fileSystem = new FileSystem(45);
//        fileSystem.getRootDirectory().addFile(new FileEntry("asg", WIDTH, 12, "sda"));
//        fileSystem.getRootDirectory().addFile(new FileEntry("21432", 32, 12, "sda"));
//        fileSystem.getRootDirectory().addFile(new FileEntry("fdsvxc ", 44, 12, "dfsds"));
//
//        fileSystem.getRootDirectory().addSubdirectory(new Directory("S1"));
//        fileSystem.getRootDirectory().addSubdirectory(new Directory("S2"));
//        fileSystem.getRootDirectory().addSubdirectory(new Directory("S3"));

        tree = new JTree(this.actualizar());
        new FileTreeContextMenu(tree, fileSystem, this);
        JScrollPane scrollPane = new JScrollPane(tree);

        

        Dimension preferredSize = new Dimension(300, 300);
        tree.setPreferredSize(preferredSize);
        scrollPane.setPreferredSize(preferredSize);

        jPanelJTree.add(scrollPane, BorderLayout.CENTER);

//        jPanelJTree.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    
   public void updateTree() {
        tree = new JTree(this.actualizar());
   }
    
    

    public DefaultMutableTreeNode actualizar() {
        DefaultMutableTreeNode root = this.recorrer(fileSystem.getRootDirectory());
        return root;
    }

    public DefaultMutableTreeNode recorrer(Directory actual) {
        DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(actual.getName());
        Node aux = actual.getSubdirectories().getHead();
        while (aux != null) {
            nuevo.add(this.recorrer((Directory) aux.getData()));
            aux = aux.getNext();
        }

        Node aux2 = actual.getFiles().getHead();
        while (aux2 != null) {
            FileEntry file = (FileEntry) aux2.getData();
            nuevo.add(new DefaultMutableTreeNode(file.getName() + "    (" + file.getSizeInBlocks() + "blocks)"));
            aux2 = aux2.getNext();
        }

        return nuevo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelJTree = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelJTree.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanelJTree, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 350));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Ver SD");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 580, 130));

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
            java.util.logging.Logger.getLogger(ArbolFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArbolFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArbolFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArbolFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArbolFS(fileSystem).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelJTree;
    // End of variables declaration//GEN-END:variables
}
