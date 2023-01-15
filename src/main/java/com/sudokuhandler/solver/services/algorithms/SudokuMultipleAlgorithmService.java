package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.ActionDto;
import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;

import java.util.LinkedList;
import java.util.Optional;

public interface SudokuMultipleAlgorithmService {
    ActionDto solveSudoku(CellDto[][] cellTable);
    Optional<ActionDto> findSinglePossible(CellDto[][] cellTable,
                                           LinkedList<EliminateMoveDto> eliminateList);
}
