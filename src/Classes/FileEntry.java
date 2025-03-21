/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import structures.*;

/**
 *
 * @author andresimery
 */
public class FileEntry { // Archivo
    private String name;
    private int sizeInBlocks;
    private int startBlock; // ID del primer bloque
    private String permissions;

    public FileEntry(String name, int sizeInBlocks, int startBlock, String permissions) {
        this.name = name;
        this.sizeInBlocks = sizeInBlocks;
        this.startBlock = startBlock;
        this.permissions = permissions;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSizeInBlocks() {
        return sizeInBlocks;
    }

    public void setSizeInBlocks(int sizeInBlocks) {
        this.sizeInBlocks = sizeInBlocks;
    }

    public int getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(int startBlock) {
        this.startBlock = startBlock;
    }

//    public LinkedList<Integer> getBlockList() {
//        return blockList;
//    }
//
//    public void setBlockList(LinkedList<Integer> blockList) {
//        this.blockList = blockList;
//    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public String getInfo() {
        return "Archivo: " + name + ", Tamaño: " + sizeInBlocks + " bloques. Inicia en el bloque #" + startBlock;
    } 
}
