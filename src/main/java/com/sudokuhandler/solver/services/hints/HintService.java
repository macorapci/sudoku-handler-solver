package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;

public interface HintService {
    SudokuAlgorithmType getSudokuAlgorithmType();
    String createHint(EliminateMoveDto eliminateMoveDto);
}
