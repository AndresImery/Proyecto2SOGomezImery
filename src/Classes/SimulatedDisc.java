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

    public SimulatedDisc(int total_blocks) {
        this.total_blocks = this.available_blocks =total_blocks;
        this.fileSystem = new FileSystem(total_blocks);
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
        for (Block block : blocks) {
            block.ocupado = false;
            block.setNextBlock(null);
        }
        available_blocks = total_blocks;

        assignDirectoryBlocks(fileSystem.getRootDirectory());
    }

    private void assignDirectoryBlocks(Directory dir) {
        Node fileNode = dir.getFiles().getHead();
        while (fileNode != null) {
            FileEntry file = (FileEntry) fileNode.getData();
            assignBlocksToFile(file);
            fileNode = fileNode.getNext();
        }

        // 2. Recurse into subdirectories
        Node subdirNode = dir.getSubdirectories().getHead();
        while (subdirNode != null) {
            Directory sub = (Directory) subdirNode.getData();
            assignDirectoryBlocks(sub);
            subdirNode = subdirNode.getNext();
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
            System.err.println("⛔ Not enough blocks to reassign file: " + file.getName());
        }
    }

    
}
