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
    private SimulatedDisc simulatedDisc;

    public FileTreeContextMenu(JTree tree, FileSystem fileSystem, ArbolFS arbolfs, SimulatedDisc simulatedDisc) {
        this.tree = tree;
        this.fileSystem = fileSystem;
        this.menu = new JPopupMenu();
        this.arbolfs = arbolfs;
        this.simulatedDisc = simulatedDisc;

        JMenuItem addDirectory = new JMenuItem("➕ Agregar Directorio");
        JMenuItem addFile = new JMenuItem("📄 Agregar Archivo");
        JMenuItem delete = new JMenuItem("❌ Eliminar");
        JMenuItem update = new JMenuItem("✏️ Cambiar nombre");

        menu.add(addDirectory);
        menu.add(addFile);
        menu.add(delete);
        menu.add(update);

        // Evento para agregar un directorio
        addDirectory.addActionListener(e -> agregarDirectorio());

        // Evento para agregar un archivo
        addFile.addActionListener(e -> agregarArchivo());

        // Evento para eliminar un nodo
        delete.addActionListener(e -> eliminarNodo());

        update.addActionListener(e -> update());

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

    private void update() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }
        Directory parentDir = this.buscarDir(selectedNode.getParent().toString(), this.fileSystem.getRootDirectory());
        String nombre = JOptionPane.showInputDialog(null, selectedNode.toString() + "Nombre del Archivo:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            String name = selectedNode.toString().split("-")[0].trim();
            FileEntry f = this.buscarArchivo(parentDir, name);
            if (f != null) {
                f.setName(nombre);
                selectedNode.setUserObject(nombre + "      -" + selectedNode.toString().split("-")[1].trim());

            } else {
                Directory d = this.buscarDir(selectedNode.toString(), this.fileSystem.getRootDirectory());
                d.setName(nombre);
                selectedNode.setUserObject(nombre);
            }

        }
        arbolfs.jm.saveSystem(fileSystem);
        actualizarArbol();

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
        Directory parentDir = this.buscarDir(selectedNode.toString(), this.fileSystem.getRootDirectory());
        if (parentDir == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un directorio para agregar un subdirectorio.");
            return;
        }
        String nombre = JOptionPane.showInputDialog(null, "Nombre del Directorio:");
        if (nombre != null && !nombre.trim().isEmpty() && !nombre.contains("-")) {
            Directory nuevoDir = new Directory(nombre);

            // Agregar el nuevo directorio al sistema de archivos
            if (parentDir != null) {
                parentDir.addSubdirectory(nuevoDir);
            }

            // Agregar el nodo al JTree
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nombre);
            selectedNode.add(newNode);

            actualizarArbol();
            arbolfs.jm.saveSystem(fileSystem);

        }
    }

    private void agregarArchivo() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }

        // Obtener el directorio padre
        Directory parentDir = this.buscarDir(selectedNode.toString(), this.fileSystem.getRootDirectory());
        if (parentDir != null) {
            new NewFile(fileSystem, parentDir, this.tree, this, simulatedDisc);
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un directorio para agregar un archivo.");
        }
        arbolfs.jm.saveSystem(fileSystem);

    }

    private void eliminarNodo() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode == null || selectedNode.isRoot()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Eliminar '" + selectedNode.toString() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
        String parentName = parentNode.toString();
        Directory parentDir = this.buscarDir(parentName, this.fileSystem.getRootDirectory());

        if (parentDir == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el directorio padre en el sistema.");
            return;
        }

        String target = selectedNode.toString();

        // 1. Eliminar de lista de archivos
        Node fileNode = parentDir.getFiles().getHead();
        while (fileNode != null) {
            FileEntry f = (FileEntry) fileNode.getData();
            if (target.contains(f.getName())) {
                // Liberar bloques
                simulatedDisc.releaseBlocks(f.getStartBlock());
                parentDir.getFiles().remove(f);
                JSONManager.saveSystem(fileSystem);
                break;
            }
            fileNode = fileNode.getNext();
        }

        // 2. Eliminar subdirectorio (si aplica)
        Node dirNode = parentDir.getSubdirectories().getHead();
        while (dirNode != null) {
            Directory d = (Directory) dirNode.getData();
            if (d.getName().equals(target)) {
                Node fn = d.getFiles().getHead();
                while (fn != null) {
                    FileEntry f = (FileEntry) fn.getData();

                    // Liberar bloques
                    simulatedDisc.releaseBlocks(f.getStartBlock());
                    d.getFiles().remove(f);
                    fn = fn.getNext();

                }
                parentDir.getSubdirectories().remove(d);

            }
            dirNode = dirNode.getNext();
        }
        // 3. Eliminar visualmente en el árbol
        parentNode.remove(selectedNode);
        actualizarArbol();
        arbolfs.updateTable();
        simulatedDisc.printBlocks();
        arbolfs.jm.saveSystem(fileSystem);

    }

    public void actualizarArbol() {
        ((DefaultTreeModel) tree.getModel()).reload();
    }

    private Directory buscarDirectorio(String nombre) {
        if (fileSystem.getRootDirectory().getName().equals(nombre)) {
            return fileSystem.getRootDirectory();
        }
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

    public Directory buscarDir(String nombre, Directory actual) {
        if (actual == null || actual.getName().equals(nombre)) {
            return actual;
        } else {
            Node aux = actual.getSubdirectories().getHead();
            if (aux == null) {
                return null;
            }
            Directory dir = (Directory) aux.getData();
            Directory buscado = dir;
            while (aux != null) {
                buscado = buscarDir(nombre, dir);
                if (buscado != null) {
                    return buscado;
                }
                aux = aux.getNext();
                Directory d = (Directory) aux.getData();

            }
            return null;
        }
    }

    private FileEntry buscarArchivo(Directory dir, String archivo) {

        if (dir != null) {
            FileEntry file = dir.getFiles().search(archivo);
            if (file != null) {
                return file;
            }
        }
        return null;
    }

    public ArbolFS getArbolfs() {
        return arbolfs;
    }

}
