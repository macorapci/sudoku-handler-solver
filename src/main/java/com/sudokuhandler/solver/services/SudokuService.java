package com.sudokuhandler.solver.services;

import com.sudokuhandler.solver.advices.exceptions.SudokuHandlerException;
import com.sudokuhandler.solver.models.ActionDto;
import com.sudokuhandler.solver.models.ActionHintDto;
import com.sudokuhandler.solver.models.CellDto;

public interface SudokuService {
    ActionHintDto solveSudoku(int [][] sudokuTable);
    void validateSudokuTable(int [][] sudokuTable) throws SudokuHandlerException;
    CellDto[][] sudokuTableToCellTable(int [][] sudokuTable);
    ActionHintDto createActionHintDto(ActionDto actionDto);
}
