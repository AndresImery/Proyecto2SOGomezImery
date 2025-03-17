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
class FileEntry {
    private String name;
    private int sizeInBlocks;
    private int startBlock;
    private LinkedList<Integer> blockList;
    private String permissions;

    public FileEntry(String name, int sizeInBlocks, int startBlock, String permissions) {
        this.name = name;
        this.sizeInBlocks = sizeInBlocks;
        this.startBlock = startBlock;
        this.blockList = new LinkedList<Integer>();
        this.permissions = permissions;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public String getInfo() {
        return "File: " + name + ", Size: " + sizeInBlocks + " blocks, Start Block: " + startBlock;
    }
}
