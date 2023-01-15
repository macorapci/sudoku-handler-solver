package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ScanUniquePossibleRowHintManager implements HintService {
    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.SCAN_UNIQUE_POSSIBLE_ROW;
    }

    @Override
    public String createHint(EliminateMoveDto eliminateMoveDto) {
        var eliminatedList = eliminateMoveDto.getEliminateDtoList().stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();
        String message = """
                {0}. row have to include 1-9 numbers.\s
                Other cells can''t have remaining value so {1} are eliminating from {2}.row {3}.column index.\s
                """;
        var row = eliminateMoveDto.getRow() + 1;
        var column = eliminateMoveDto.getColumn() + 1;
        return MessageFormat.format(message, row, eliminatedList, row, column);
    }
}
