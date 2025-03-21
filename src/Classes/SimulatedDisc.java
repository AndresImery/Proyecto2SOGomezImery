/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import javax.swing.JOptionPane;
import structures.*;

/**
 *
 * @author andre
 */
public class SimulatedDisc {
    public int available_blocks;
    public int total_blocks;
    public FileSystem fileSystem;
    public Block[] blocks;

    public SimulatedDisc(int total_blocks,FileSystem fileSystem) {
        this.total_blocks = this.available_blocks =total_blocks;
        this.fileSystem = fileSystem;
        blocks = new Block[this.total_blocks];
        for (int i = 0; i < this.total_blocks; i++) {
            blocks[i] = new Block(i);
        }
    }
    
    
    public int selectStartBlock(){
        for (int i = 0; i < this.total_blocks; i++) {
            if(!blocks[i].ocupado){
                return i;
            }
        }
        return -1;
    }
    
    public Integer createFile(String name, int size, String permissions, Directory directory) {
        if (size > this.available_blocks) {
            JOptionPane.showMessageDialog(null, "No hay suficientes bloques disponibles");
            return null;
        }

        int count = 0;
        Block firstBlock = null;
        Block prev = null;

        for (int i = 0; i < this.total_blocks && count < size; i++) {
            if (!blocks[i].ocupado) {
                blocks[i].ocupado = true;
                
                if (firstBlock == null) {
                    firstBlock = blocks[i];
                }

                if (prev != null) {
                    prev.setNextBlock(blocks[i]);
                }

                prev = blocks[i];
                count++;
            }
        }

        if (count < size) {
            JOptionPane.showMessageDialog(null, "Error inesperado: no se pudieron asignar todos los bloques");
            return null;
        }

        // Crear archivo con referencia al primer bloque asignado
//        FileEntry f = new FileEntry(name, size, firstBlock.getId(), permissions);
//        directory.addFile(f);
        this.available_blocks -= size;
        return firstBlock.getId();
    }
    
    public void releaseBlocks(int startBlockId) {
        Block current = blocks[startBlockId];
        while (current != null) {
            current.ocupado = false;
            Block next = current.getNextBlock();
            current.setNextBlock(null);
            current = next;
            this.available_blocks++;
        }
    }

    public boolean checkSize(int fileSize) {
        if (available_blocks >= fileSize) {
            return true;
        }
        return false;
    }
    
    public void loadFilesToBlocks() {
        // Reiniciar todos los bloques
        for (Block block : blocks) {
            block.ocupado = false;
            block.setNextBlock(null);
        }
        available_blocks = total_blocks;

        // Recorrer el sistema de archivos y asignar bloques encadenados
        assignBlocksRecursive(fileSystem.getRootDirectory());
    }


    private void assignBlocksRecursive(Directory dir) {
        // Asignar a los archivos de este directorio
        Node fileNode = dir.getFiles().getHead();
        while (fileNode != null) {
            FileEntry file = (FileEntry) fileNode.getData();
            assignBlocksToFile(file); // 🔗 encadena bloques
            fileNode = fileNode.getNext();
        }

        // Repetir en subdirectorios
        Node subNode = dir.getSubdirectories().getHead();
        while (subNode != null) {
            Directory subDir = (Directory) subNode.getData();
            assignBlocksRecursive(subDir);
            subNode = subNode.getNext();
        }
    }


    private void assignBlocksToFile(FileEntry file) {
        int size = file.getSizeInBlocks();
        int count = 0;
        Block first = null;
        Block prev = null;

        for (int i = 0; i < blocks.length && count < size; i++) {
            if (!blocks[i].ocupado) {
                blocks[i].ocupado = true;

                if (first == null) {
                    first = blocks[i];
                }

                if (prev != null) {
                    prev.setNextBlock(blocks[i]);
                }

                prev = blocks[i];
                count++;
            }
        }

        if (count == size && first != null) {
            file.setStartBlock(first.getId());
            available_blocks -= size;
        } else {
            System.err.println("No hay suficientes bloques para asignar el archivo: " + file.getName());
        }
    }

    
    public void printBlocks() {
    System.out.println("=== Estado de los bloques en SimulatedDisc ===");
    for (int i = 0; i < blocks.length; i++) {
        Block b = blocks[i];
        String estado = b.ocupado ? "OCUPADO" : "LIBRE";
        String next = (b.getNextBlock() != null) ? String.valueOf(b.getNextBlock().getId()) : "null";
        System.out.println("Bloque " + b.getId() + ": " + estado + " -> Siguiente: " + next);
    }
    System.out.println("Bloques disponibles: " + this.available_blocks + "/" + this.total_blocks);
}


    
}
