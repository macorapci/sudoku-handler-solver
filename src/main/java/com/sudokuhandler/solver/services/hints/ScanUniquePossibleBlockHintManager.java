package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ScanUniquePossibleBlockHintManager implements HintService {
    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.SCAN_UNIQUE_POSSIBLE_BLOCK;
    }

    @Override
    public String createHint(EliminateMoveDto eliminateMoveDto) {
        var eliminatedList = eliminateMoveDto.getEliminateDtoList().stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();
        String message = """
                {0}x{1} block have to include 1-9 numbers.\s
                Other cells can''t have remaining value so {2} are eliminating from {3}.row {4}.column index.\s
                """;
        var row = eliminateMoveDto.getRow() + 1;
        var column = eliminateMoveDto.getColumn() + 1;
        var blockRow = (eliminateMoveDto.getRow() / 3) + 1;
        var blockColumn = (eliminateMoveDto.getColumn() / 3) + 1;
        return MessageFormat.format(message, blockRow, blockColumn, eliminatedList, row, column);
    }
}
