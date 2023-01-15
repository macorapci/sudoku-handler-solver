package com.sudokuhandler.solver.advices.exceptions;

public class SudokuTableAllCellsAreEmpty extends SudokuHandlerException {
    public SudokuTableAllCellsAreEmpty() {
        super("Sudoku table's all cells are empty.");
    }
}
