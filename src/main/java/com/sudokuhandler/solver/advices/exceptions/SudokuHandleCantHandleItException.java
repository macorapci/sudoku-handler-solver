package com.sudokuhandler.solver.advices.exceptions;

public class SudokuHandleCantHandleItException extends SudokuHandlerException {
    public SudokuHandleCantHandleItException() {
        super("Sudoku handler can't handle it!");
    }
}
