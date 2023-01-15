package com.sudokuhandler.solver.services;

import com.sudokuhandler.solver.models.CellDto;

public interface SudokuSelectorService {
    int[] getExcludedRow(int [][] sudokuTable, int row, int column);
    int[] getExcludedColumn(int [][] sudokuTable, int row, int column);
    int[] getExcludedBlock(int [][] sudokuTable, int row, int column);
    CellDto[] getRow(CellDto[][] cellTable, int row);
    CellDto[] getColumn(CellDto[][] cellTable, int column);
    CellDto[][] getExcludedBlock(CellDto [][] sudokuTable, int row, int column);
    CellDto[] getListExcludedRow(CellDto[][] cellTable, int row, int column);
    CellDto[] getListExcludedColumn(CellDto[][] cellTable, int row, int column);
    CellDto[] getListExcludedBlock(CellDto[][] cellTable, int row, int column);
}
