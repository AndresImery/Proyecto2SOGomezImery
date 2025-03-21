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
import Classes.SimulatedDisc;
import javax.swing.JMenuItem;
import structures.Node;
import Classes.JSONManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import javax.swing.UIManager;
/**
 *
 * @author andre
 */
public class ArbolFS extends javax.swing.JFrame {
    static SimulatedDisc sd;
    public JSONManager jm;
    private static JTree tree;
    private static FileSystem fileSystem;
    public int mode;

    /**
     * Creates new form ArbolFS
     */
    public ArbolFS(FileSystem fileSystem) {
        initComponents();
        this.fileSystem = fileSystem;
        this.sd = new SimulatedDisc(100, fileSystem);
        sd.loadFilesToBlocks(); 
        sd.printBlocks();
        mode = 1;
       
//        this.setVisible(false);
        
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
        
        
        
        TreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean a) {
                // Llamar al método de la superclase para obtener el componente base
                super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, a);

                // Verificar si el nodo es un nodo hoja especial
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                    String[] nombre = node.toString().split("-");
                    if (node.isLeaf() && nombre.length== 1) {
                        // Obtener el nodo padre
                        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                        if (parent != null) {
                            // Establecer el ícono del nodo padre
                            setIcon(UIManager.getIcon("FileView.directoryIcon")); // Cambia esto si deseas un ícono diferente
                        }
                    }
                }

                return this;
            }
        };

        // Asignar el renderer al JTree
        tree.setCellRenderer(renderer);
        new FileTreeContextMenu(tree, fileSystem, this, sd);
        JScrollPane scrollPane = new JScrollPane(tree);

        

        Dimension preferredSize = new Dimension(300, 300);
        tree.setPreferredSize(preferredSize);
        scrollPane.setPreferredSize(preferredSize);

        jPanelJTree.add(scrollPane, BorderLayout.CENTER);
        updateTable(); // después de cargar el árbol


//        jPanelJTree.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    
    public void updateTable() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpiar tabla

        // Llenar la tabla con los archivos del sistema
        addFilesToTable(fileSystem.getRootDirectory(), model);
    }

    private void addFilesToTable(Directory dir, javax.swing.table.DefaultTableModel model) {
        Node fileNode = dir.getFiles().getHead();
        while (fileNode != null) {
            FileEntry f = (FileEntry) fileNode.getData();
            model.addRow(new Object[]{f.getName(), f.getSizeInBlocks(), f.getStartBlock()});
            fileNode = fileNode.getNext();
        }

        Node subNode = dir.getSubdirectories().getHead();
        while (subNode != null) {
            Directory sub = (Directory) subNode.getData();
            addFilesToTable(sub, model); // recursivo
            subNode = subNode.getNext();
        }
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
        // Crear un DefaultTreeCellRenderer
        
        Node aux = actual.getSubdirectories().getHead();
        while (aux != null) {
            nuevo.add(this.recorrer((Directory) aux.getData()));
            aux = aux.getNext();
        }

        Node aux2 = actual.getFiles().getHead();
        while (aux2 != null) {
            FileEntry file = (FileEntry) aux2.getData();
            nuevo.add(new DefaultMutableTreeNode(file.getName() + "    -" + file.getSizeInBlocks() + " blocks"));
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
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelJTree.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanelJTree, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 350));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Ver SD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, -1));

        jLabel1.setText("Haz click derecho para editar los archivos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jButton3.setText("Cargar JSON");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        jButton4.setText("Guardar JSON");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Modo Administrador", "Modo Usuario" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 190, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Bloques", "Primer Bloque"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 480, 170));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 580, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SD s = new SD(sd);
        s.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         jm.saveSystem(sd.fileSystem);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
      sd.fileSystem = jm.loadSystem();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if(this.jComboBox1.getSelectedIndex() == 0){
            this.mode = 1;
        }else{
            this.mode = 2;
        }
        System.out.println(mode);
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelJTree;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
