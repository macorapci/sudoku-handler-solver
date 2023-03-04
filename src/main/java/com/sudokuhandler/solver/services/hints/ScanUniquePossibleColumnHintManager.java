package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import com.sudokuhandler.solver.services.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
class ScanUniquePossibleColumnHintManager implements HintService {
    private static final String MESSAGE_KEY = "hints.uniquePossible.column";
    private static final String MESSAGE_TEMPLATE = """
                {0}. column have to include 1-9 numbers.\s
                Other cells can''t have remaining value so {1} are eliminating from {2}.row {3}.column index.
                """;

    private final LocalizationService localizationService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.SCAN_UNIQUE_POSSIBLE_COLUMN;
    }

    @Override
    public String createHint(EliminateMoveDto eliminateMoveDto) {
        var eliminatedList = eliminateMoveDto.getEliminateDtoList().stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();

        var row = eliminateMoveDto.getRow() + 1;
        var column = eliminateMoveDto.getColumn() + 1;
        String defaultMessage = MessageFormat.format(MESSAGE_TEMPLATE, column, eliminatedList, row, column);
        return this.localizationService.getLocalText(MESSAGE_KEY, defaultMessage, column, eliminatedList, row, column);
    }
}
