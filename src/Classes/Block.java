/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author andresimery
 */
class Block {
    private int id;
    private Block nextBlock; // Can be null if it's the last block

    public Block(int id) {
        this.id = id;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }
}
