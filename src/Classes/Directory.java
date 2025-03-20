/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import structures.*;
import so.*;

/**
 *
 * @author andresimery
 */
public class Directory { // Directorio
    private String name;
    private LinkedList<FileEntry> files;
    private LinkedList<Directory> subdirectories;

    public Directory(String name) {
        this.name = name;
        this.files = new LinkedList<>();
        this.subdirectories = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<FileEntry> getFiles() {
        return files;
    }

    public void setFiles(LinkedList<FileEntry> files) {
        this.files = files;
    }

    public LinkedList<Directory> getSubdirectories() {
        return subdirectories;
    }

    public void setSubdirectories(LinkedList<Directory> subdirectories) {
        this.subdirectories = subdirectories;
    }
    
    

    public void addFile(FileEntry file) {
        files.add(file);
    }

    public void addSubdirectory(Directory subdirectory) {
        subdirectories.add(subdirectory);
    }

    public void removeFile(String fileName) {
        Node<FileEntry> current = files.getHead();
        while (current != null) {
            if (current.getData().getName().equals(fileName)) {
                files.remove(current.getData());
                break;
            }
            current = current.getNext();
        }
    }

    public void removeSubdirectory(String dirName) {
        Node<Directory> current = subdirectories.getHead();
        while (current != null) {
            if (current.getData().getName().equals(dirName)) {
                subdirectories.remove(current.getData());
                break;
            }
            current = current.getNext();
        }
    }
    
    
    
}
