package com.sudokuhandler.solver.advices.exceptions;

public class SudokuHasNoSpaceException extends SudokuHandlerException {
    public SudokuHasNoSpaceException() {
        super("Sudoku table has no empty line.");
    }
}
