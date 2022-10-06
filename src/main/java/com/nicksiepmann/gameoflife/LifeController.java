/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicksiepmann.gameoflife;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Nick.Siepmann
 */
@RequiredArgsConstructor
@Data
public class LifeController {

    private int interval;
    private int size;
    private boolean running;
    private GameBoard board;

    public void newGame(int size) {
        this.size = size;
        this.running = false;
        this.board = new GameBoard(size);
        this.interval = 100;
    }

    public void clearGame() {
        this.running = false;
        this.board = null;
    }

    public void stop() {
        this.running = false;
    }

    public boolean hasBoard() {
        return this.board != null;
    }
}
