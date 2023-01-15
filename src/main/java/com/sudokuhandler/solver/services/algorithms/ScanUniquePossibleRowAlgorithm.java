package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import com.sudokuhandler.solver.services.SudokuSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@RequiredArgsConstructor
class ScanUniquePossibleRowAlgorithm extends BaseScanUniquePossibleAlgorithm {
    private final SudokuSelectorService sudokuSelectorService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.SCAN_UNIQUE_POSSIBLE_ROW;
    }

    @Override
    public Optional<EliminateMoveDto> eliminate(@NotNull CellDto[][] cellTable, int row, int column) {
        var excludedCellRow = this.sudokuSelectorService.getListExcludedRow(cellTable, row, column);
        var selectedCell = cellTable[row][column];
        var eliminateValueList = super.getEliminateList(excludedCellRow, selectedCell);

        if (eliminateValueList.isEmpty()) {
            return Optional.empty();
        }

        return super.getEliminateMoveDto(eliminateValueList.get(), row, column);
    }
}
