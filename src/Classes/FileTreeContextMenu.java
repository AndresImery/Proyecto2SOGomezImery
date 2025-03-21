/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import structures.Node;
import Classes.*;
import UI.*;

/**
 *
 * @author andresimery
 */
public class FileTreeContextMenu {

    private JTree tree;
    private JPopupMenu menu;
    private FileSystem fileSystem;
    private ArbolFS arbolfs;
    int mode;

    public FileTreeContextMenu(JTree tree, FileSystem fileSystem, ArbolFS arbolfs) {
        this.tree = tree;
        this.fileSystem = fileSystem;
        this.menu = new JPopupMenu();
        this.arbolfs = arbolfs;

        JMenuItem addDirectory = new JMenuItem("➕ Agregar Directorio");
        JMenuItem addFile = new JMenuItem("📄 Agregar Archivo");
        JMenuItem delete = new JMenuItem("❌ Eliminar");

        menu.add(addDirectory);
        menu.add(addFile);
        menu.add(delete);

        // Evento para agregar un directorio
        addDirectory.addActionListener(e -> agregarDirectorio());

        // Evento para agregar un archivo
        addFile.addActionListener(e -> agregarArchivo());

        // Evento para eliminar un nodo
        delete.addActionListener(e -> eliminarNodo());

        // Agregar el listener de click derecho al JTree
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (arbolfs.mode == 1) {
                        mostrarMenu(e);

                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (arbolfs.mode == 1) {
                        mostrarMenu(e);

                    }
                }
            }
        });
    }

    private void mostrarMenu(MouseEvent e) {
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            tree.setSelectionPath(path); // Seleccionamos el nodo sobre el que hicimos click derecho
            menu.show(tree, e.getX(), e.getY());
        }
    }

    private void agregarDirectorio() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
        Directory parentDir = buscarDirectorio(selectedNode.toString());
        if (parentDir == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un directorio para agregar un subdirectorio.");
            return;
        }
        String nombre = JOptionPane.showInputDialog(null, "Nombre del Directorio:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            Directory nuevoDir = new Directory(nombre);

            // Agregar el nuevo directorio al sistema de archivos
            if (parentDir != null) {
                parentDir.addSubdirectory(nuevoDir);
            }

            // Agregar el nodo al JTree
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nombre);
            selectedNode.add(newNode);

            actualizarArbol();
        }
    }

    private void agregarArchivo() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }

        // Obtener el directorio padre
        Directory parentDir = buscarDirectorio(selectedNode.toString());
        if (parentDir != null) {
            new NewFile(fileSystem, parentDir, this.tree, this);
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un directorio para agregar un archivo.");
        }
    }

    private void eliminarNodo() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null || selectedNode.isRoot()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar '" + selectedNode.toString() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ((DefaultMutableTreeNode) selectedNode.getParent()).remove(selectedNode);
            actualizarArbol();
        }
    }

    public void actualizarArbol() {
        ((DefaultTreeModel) tree.getModel()).reload();
    }

    private Directory buscarDirectorio(String nombre) {
        Node aux = fileSystem.getRootDirectory().getSubdirectories().getHead();
        while (aux != null) {
            Directory dir = (Directory) aux.getData();
            if (dir.getName().equals(nombre)) {
                return dir;
            }
            aux = aux.getNext();
        }
        return null;
    }
}
