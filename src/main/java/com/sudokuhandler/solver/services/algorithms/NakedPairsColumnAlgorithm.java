package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import com.sudokuhandler.solver.services.SudokuSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class NakedPairsColumnAlgorithm extends BaseNakedPairsAlgorithm {
    private final SudokuSelectorService sudokuSelectorService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.NAKED_PAIRS_COLUMN;
    }

    @Override
    public Optional<EliminateMoveDto> eliminate(@NotNull CellDto[][] cellTable, int row, int column) {
        var excludedCellColumn = this.sudokuSelectorService.getListExcludedColumn(cellTable, row, column);
        var selectedCell = cellTable[row][column];
        var pairValueList = super.getPairValueList(excludedCellColumn, selectedCell);

        if (pairValueList.isEmpty()) {
            return Optional.empty();
        }

        var cellColumn = this.sudokuSelectorService.getColumn(cellTable, column);
        return Optional.of(this.getEliminateMoveDto(cellColumn, pairValueList.get(), row, column));
    }

    public EliminateMoveDto getEliminateMoveDto(CellDto[] cellColumn, List<Integer> remainingValue,
                                                int row, int column) {
        var list = new ArrayList<EliminateDto>();

        for (int k=0; k<cellColumn.length; k++) {
            if (cellColumn[k].getPossibleList() == null || cellColumn[k].getPossibleList().equals(remainingValue)) {
                continue;
            }

            var tempRow = k;
            var eliminateList = remainingValue.stream()
                    .filter(integer -> cellColumn[tempRow].getPossibleList().contains(integer))
                    .map(integer -> EliminateDto.builder().row(tempRow).column(column).eliminatedValue(integer).build())
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
