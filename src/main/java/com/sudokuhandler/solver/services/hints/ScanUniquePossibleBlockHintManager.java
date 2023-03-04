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
class ScanUniquePossibleBlockHintManager implements HintService {
    private static final String MESSAGE_KEY = "hints.uniquePossible.block";
    private static final String MESSAGE_TEMPLATE = """
                {0}x{1} block have to include 1-9 numbers.\s
                Other cells can''t have remaining value so {2} are eliminating from {3}.row {4}.column index.
                """;

    private final LocalizationService localizationService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.SCAN_UNIQUE_POSSIBLE_BLOCK;
    }

    @Override
    public String createHint(EliminateMoveDto eliminateMoveDto) {
        var eliminatedList = eliminateMoveDto.getEliminateDtoList().stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();

        var row = eliminateMoveDto.getRow() + 1;
        var column = eliminateMoveDto.getColumn() + 1;
        var blockRow = (eliminateMoveDto.getRow() / 3) + 1;
        var blockColumn = (eliminateMoveDto.getColumn() / 3) + 1;
        String defaultMessage = MessageFormat.format(MESSAGE_TEMPLATE, blockRow, blockColumn, eliminatedList, row, column);
        return this.localizationService.getLocalText(MESSAGE_KEY, defaultMessage, blockRow, blockColumn, eliminatedList, row, column);
    }
}
