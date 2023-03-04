package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import com.sudokuhandler.solver.services.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class NakedPairsBlockHintManager implements HintService {
    private static final String MESSAGE_KEY = "hints.nakedPairs.block";
    private static final String MESSAGE_TEMPLATE = """
                {0}x{1} block have 2 cells with same 2 possible values.\s
                Other cells can''t have the same values so {2} are eliminating from {3} indexes.
                """;

    private final LocalizationService localizationService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.NAKED_PAIRS_BLOCK;
    }

    @Override
    public String createHint(EliminateMoveDto eliminateMoveDto) {
        var eliminatedList = eliminateMoveDto.getEliminateDtoList().stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();

        var eliminateIndex = eliminateMoveDto.getEliminateDtoList().stream()
                .map(this::getPointFromEliminateDto)
                .collect(Collectors.joining(", "));

        var blockRow = (eliminateMoveDto.getRow() / 3) + 1;
        var blockColumn = (eliminateMoveDto.getColumn() / 3) + 1;

        String defaultMessage = MessageFormat.format(MESSAGE_TEMPLATE, blockRow, blockColumn, eliminatedList, eliminateIndex);
        return this.localizationService.getLocalText(MESSAGE_KEY, defaultMessage, blockRow, blockColumn, eliminatedList, eliminateIndex);
    }

    private String getPointFromEliminateDto(EliminateDto eliminateDto) {
        return MessageFormat.format("[{0}, {1}]", eliminateDto.getRow() + 1, eliminateDto.getColumn() + 1);
    }
}
