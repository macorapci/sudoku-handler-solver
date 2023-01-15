package com.sudokuhandler.solver.advices.exceptions;

public class SudokuUnknownErrorException extends SudokuHandlerException {
    public SudokuUnknownErrorException() {
        super("Unknown exception occurred!");
    }
}
