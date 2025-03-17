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
class Directory {
    private String name;
    private LinkedList<FileEntry> files;
    private LinkedList<Directory> subdirectories;

    public Directory(String name) {
        this.name = name;
        this.files = new LinkedList<>();
        this.subdirectories = new LinkedList<>();
    }

    public void addFile(FileEntry file) {
        files.add(file);
    }

    public void addSubdirectory(Directory subdirectory) {
        subdirectories.add(subdirectory);
    }

    public void removeFile(String fileName) {
//        files.removeIf(file -> file.getName().equals(fileName));
    }

    public void removeSubdirectory(String dirName) {
//        subdirectories.removeIf(dir -> dir.getName().equals(dirName));
    }
}
