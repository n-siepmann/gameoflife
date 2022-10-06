/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicksiepmann.gameoflife;

import lombok.Getter;

/**
 *
 * @author Nick.Siepmann
 */
public class GameBoard {

    @Getter
    boolean[][] grid;

    public GameBoard(int dimensions) {
        this.grid = new boolean[dimensions][dimensions];
    }

    public void setCell(int x, int y, boolean setTrue) {
        this.grid[x][y] = setTrue;
    }

    public boolean getCell(int x, int y) {
        return this.grid[x][y];
    }

    public boolean run() {
        boolean alive = false;
        boolean[][] newGrid = new boolean[grid.length][grid.length];

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                int[][] bounds = new int[2][3]; //xStart, x, xEnd / yStart, y, yEnd
                bounds[0][0] = x - 1;
                bounds[0][1] = x;
                bounds[0][2] = x + 1;
                bounds[1][0] = y - 1;
                bounds[1][1] = y;
                bounds[1][2] = y + 1;

                for (int i = 0; i < 2; i++) {
                    if (bounds[i][0] < 0) {
                        bounds[i][0] += grid.length;
                    }
                    if (bounds[i][2] >= grid.length) {
                        bounds[i][2] -= grid.length;
                    }
                }
                int neighbourCount = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (grid[bounds[0][i]][bounds[1][j]]) {
                            if (!(i == 1 & j == 1)) {
                                neighbourCount++;
                            }
                        }
                    }
                }

                if (grid[x][y]) {
                    if (neighbourCount > 1 && neighbourCount < 4) {
                        newGrid[x][y] = true;
//                        alive = true;
                    }
                } else {
                    if (neighbourCount == 3) {
                        newGrid[x][y] = true;
                        alive = true;
                    }
                }
            }
        }
        this.grid = newGrid;
        return alive;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]) {
                    output.append("#");
                } else {
                    output.append("0");
                }
            }
            output.append("\n");
        }

        return output.toString();
    }

}
