package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;

interface HintService {
    SudokuAlgorithmType getSudokuAlgorithmType();
    String createHint(EliminateMoveDto eliminateMoveDto);
}
