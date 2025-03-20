/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import javax.swing.JOptionPane;

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
    
    public void createFile(String name, int size, String permissions, Directory directory){
        if(size > this.available_blocks){
            JOptionPane.showMessageDialog(null, "No hay suficientes bloques disponibles");
            return;
        }else{
            FileEntry f = new FileEntry(name, size, this.selectStartBlock(), permissions);
            directory.addFile(f);
            this.available_blocks -= size;
        }
    }
    
}
