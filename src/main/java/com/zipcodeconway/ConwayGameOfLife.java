package com.zipcodeconway;

import java.util.Random;

public class ConwayGameOfLife {
    SimpleWindow displayWindow;
    private int[][] currentGeneration;
    private int[][] nextGeneration;

    public ConwayGameOfLife(Integer dimension) {
        this.currentGeneration = createRandomStart(dimension);
        this.displayWindow = new SimpleWindow(dimension);
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.displayWindow = new SimpleWindow(dimension);
        this.currentGeneration = startmatrix;
        this.nextGeneration = new int[dimension][dimension];
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(50);

    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        Random rand = new Random();
        int[][] startBoard = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                startBoard[i][j] = rand.nextInt(2);
            }
        }
        return startBoard;
    }

    public int[][] simulate(Integer maxGenerations) {
        int arrSize = currentGeneration.length;
        int generationsCounter = 0;
        for (int max = 0; max <= maxGenerations; max++) {
            this.displayWindow.display(currentGeneration, generationsCounter);
            for (int i = 0; i < arrSize; i++) {
                for (int j = 0; j < arrSize; j++) {

                    int currentState = currentGeneration[i][j];

                    if(i == 0 || i == arrSize -1 || j == 0 || j == arrSize -1){
                        nextGeneration[i][j] = currentState;
                    }else {

                        int neighbors = isAlive(i, j, currentGeneration);


                        if (currentState == 0 && neighbors == 3) {
                            nextGeneration[i][j] = 1;
                        } else if (currentState == 1 && (neighbors < 2 || neighbors > 3)) {
                            nextGeneration[i][j] = 0;
                        } else {
                            nextGeneration[i][j] = currentGeneration[i][j];
                        }
                    }

                }
            }
            copyAndZeroOut(nextGeneration,currentGeneration);
            generationsCounter++;
            this.displayWindow.sleep(125);
        }
        return currentGeneration;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for (int i = 0; i < currentGeneration.length; i++) {
            for (int j = 0; j < currentGeneration.length; j++) {
                currentGeneration[i][j] = nextGeneration[i][j];
                nextGeneration[i][j] = 0;
            }
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {
        int neighborsAlive = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                neighborsAlive += world[row + i][col + j];
            }
        }
        neighborsAlive -= world[row][col];
        return neighborsAlive;
    }
}
