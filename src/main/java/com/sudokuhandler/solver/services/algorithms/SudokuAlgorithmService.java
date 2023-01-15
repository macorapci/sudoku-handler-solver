package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;

import javax.validation.constraints.NotNull;
import java.util.Optional;

interface SudokuAlgorithmService {
    SudokuAlgorithmType getSudokuAlgorithmType();
    Optional<EliminateMoveDto> eliminate(@NotNull CellDto[][] cellTable, int row, int column);
}
