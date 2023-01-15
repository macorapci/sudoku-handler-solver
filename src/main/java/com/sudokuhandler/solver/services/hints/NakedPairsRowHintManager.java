package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@Service
public class NakedPairsRowHintManager implements HintService {
    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.NAKED_PAIRS_ROW;
    }

    @Override
    public String createHint(EliminateMoveDto eliminateMoveDto) {
        var eliminatedList = eliminateMoveDto.getEliminateDtoList().stream()
                .map(EliminateDto::getEliminatedValue)
                .toList();

        var eliminateIndex = eliminateMoveDto.getEliminateDtoList().stream()
                .map(this::getPointFromEliminateDto)
                .collect(Collectors.joining(", "));

        String message = """
                {0}. row have 2 cells with same 2 possible values.\s
                Other cells can''t have the same values so {1} are eliminating from {2} indexes.\s
                """;

        var row = eliminateMoveDto.getRow() + 1;
        return MessageFormat.format(message, row, eliminatedList, eliminateIndex);
    }

    private String getPointFromEliminateDto(EliminateDto eliminateDto) {
        return MessageFormat.format("[{0}, {1}]", eliminateDto.getRow() + 1, eliminateDto.getColumn() + 1);
    }
}
