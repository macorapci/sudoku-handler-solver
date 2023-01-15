package com.sudokuhandler.solver.advices.exceptions;

public class SudokuTableNotValidException extends SudokuHandlerException {
    public SudokuTableNotValidException() {
        super("Your sudoku table not valid.");
    }
}
