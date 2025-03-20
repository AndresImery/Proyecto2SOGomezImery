/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import Classes.*;
import UI.*;
//import UI.SD;

/**
 *
 * @author andresimery
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileSystem fileSystem = JSONManager.loadSystem();

        ArbolFS s = new ArbolFS(fileSystem);
//        s.setVisible(true);
    }
    
}
