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
public class FileSystem { // Sistema

    private Directory rootDirectory;
    private int availableSpace;
    private Map<String, Integer> allocationTable;

    public FileSystem(int totalSpace) {
        this.rootDirectory = new Directory("root");
        this.availableSpace = totalSpace;
        this.allocationTable = new HashMap<>();
    }

    public Directory getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(int availableSpace) {
        this.availableSpace = availableSpace;
    }

    public Map<String, Integer> getAllocationTable() {
        return allocationTable;
    }

    public void setAllocationTable(Map<String, Integer> allocationTable) {
        this.allocationTable = allocationTable;
    }
    
    

    public void createFile(String name, int size, String permissions) {
        if (size > availableSpace) {
            System.out.println("ERROR\n No hay suficiente espacio para crear el archivo");
            return;
        }
        int startBlock = allocateBlocks(size);
        FileEntry newFile = new FileEntry(name, size, startBlock, permissions);
        rootDirectory.addFile(newFile);
        allocationTable.put(name, startBlock);
        availableSpace -= size;
    }

    private int allocateBlocks(int size) {
        return new Random().nextInt(100);
    }

    public void readFile(String name) {
        FileEntry file = rootDirectory.getFiles().search(name);
        if (file != null) {
            System.out.println("Leyendo archivo: " + file.getName() + ", Tamaño: " + file.getSizeInBlocks() + " bloques");
        } else {
            System.out.println("ERROR: Archivo no encontrado");
        }
    }

    public void updateFileName(String oldName, String newName) {
        FileEntry file = rootDirectory.getFiles().search(oldName);
        if (file != null) {
            file.setName(newName);
            allocationTable.put(newName, allocationTable.remove(oldName));
            System.out.println("Archivo renombrado de " + oldName + " a " + newName);
        } else {
            System.out.println("ERROR: Archivo no encontrado");
        }
    }

    public void deleteFile(String name) {
        FileEntry file = rootDirectory.getFiles().search(name);
        if (file != null) {
            rootDirectory.removeFile(name);
            freeBlocks(file.getStartBlock(), file.getSizeInBlocks());
            allocationTable.remove(name);
            availableSpace += file.getSizeInBlocks();
            System.out.println("Archivo eliminado: " + name);
        } else {
            System.out.println("ERROR: Archivo no encontrado");
        }
    }

    private void freeBlocks(int startBlock, int size) {

        System.out.println("Bloques liberados desde " + startBlock + " de tamaño " + size);
    }

 
    public void createDirectory(String name) {
        Directory newDir = new Directory(name);
        rootDirectory.addSubdirectory(newDir);
        System.out.println("Directorio creado: " + name);
    }

    
    public void updateDirectoryName(String oldName, String newName) {
        Directory dir = rootDirectory.getSubdirectories().search(oldName);
        if (dir != null) {
            dir.setName(newName);
            System.out.println("Directorio renombrado de " + oldName + " a " + newName);
        } else {
            System.out.println("ERROR: Directorio no encontrado");
        }
    }
}
