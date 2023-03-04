package com.sudokuhandler.solver.advices.exceptions;

public class SudokuCouldNotSolveWithBruteForce extends SudokuHandlerException {
    public SudokuCouldNotSolveWithBruteForce() {
        super("Sudoku could not solve with brute force.");
    }
}
