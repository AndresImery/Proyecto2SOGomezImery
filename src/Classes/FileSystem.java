/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.util.*;



/**
 *
 * @author andresimery
 */
class FileSystem {
    private Directory rootDirectory;
    private int availableSpace;
    private Map<String, Integer> allocationTable;

    public FileSystem(int totalSpace) {
        this.rootDirectory = new Directory("root");
        this.availableSpace = totalSpace;
        this.allocationTable = new HashMap<>();
    }

    public void createFile(String name, int size, String permissions) {
        if (size > availableSpace) {
            System.out.println("Not enough space to create file.");
            return;
        }
        int startBlock = allocateBlocks(size);
        FileEntry newFile = new FileEntry(name, size, startBlock, permissions);
        rootDirectory.addFile(newFile);
        allocationTable.put(name, startBlock);
        availableSpace -= size;
    }

    private int allocateBlocks(int size) {
        // Simulate block allocation, returning a starting block index
        return new Random().nextInt(100);
    }
}
