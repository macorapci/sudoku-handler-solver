package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.*;
import com.sudokuhandler.solver.services.SudokuSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@RequiredArgsConstructor
class NakedPairsRowAlgorithm extends BaseNakedPairsAlgorithm {
    private final SudokuSelectorService sudokuSelectorService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.NAKED_PAIRS_ROW;
    }

    @Override
    public Optional<EliminateMoveDto> eliminate(@NotNull CellDto[][] cellTable, int row, int column) {
        var excludedCellRow = this.sudokuSelectorService.getListExcludedRow(cellTable, row, column);
        var selectedCell = cellTable[row][column];
        var pairValueList = super.getPairValueList(excludedCellRow, selectedCell);

        if (pairValueList.isEmpty()) {
            return Optional.empty();
        }

        var cellRow = this.sudokuSelectorService.getRow(cellTable, row);
        return Optional.of(this.getEliminateMoveDto(cellRow, pairValueList.get(), row, column));
    }

    public EliminateMoveDto getEliminateMoveDto(CellDto[] cellRow, List<Integer> remainingValue,
                                                int row, int column) {
        var list = new ArrayList<EliminateDto>();

        for (int k=0; k<cellRow.length; k++) {
            if (cellRow[k].getPossibleList() == null || cellRow[k].getPossibleList().equals(remainingValue)) {
                continue;
            }

            var tempColumn = k;
            var eliminateList = remainingValue.stream()
                    .filter(integer -> cellRow[tempColumn].getPossibleList().contains(integer))
                    .map(integer -> EliminateDto.builder().row(row).column(tempColumn).eliminatedValue(integer).build())
                    .toList();

            list.addAll(eliminateList);
        }

        return EliminateMoveDto.builder()
                .sudokuAlgorithmType(this.getSudokuAlgorithmType())
                .row(row)
                .column(column)
                .eliminateDtoList(list)
                .build();
    }
}
