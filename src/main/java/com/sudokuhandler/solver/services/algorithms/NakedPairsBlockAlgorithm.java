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
class NakedPairsBlockAlgorithm extends BaseNakedPairsAlgorithm {
    private final SudokuSelectorService sudokuSelectorService;

    @Override
    public SudokuAlgorithmType getSudokuAlgorithmType() {
        return SudokuAlgorithmType.NAKED_PAIRS_BLOCK;
    }

    @Override
    public Optional<EliminateMoveDto> eliminate(@NotNull CellDto[][] cellTable, int row, int column) {
        var excludedCellBlock = this.sudokuSelectorService.getListExcludedBlock(cellTable, row, column);
        var selectedCell = cellTable[row][column];
        var pairValueList = super.getPairValueList(excludedCellBlock, selectedCell);

        if (pairValueList.isEmpty()) {
            return Optional.empty();
        }

        var cellBlock = this.sudokuSelectorService.getExcludedBlock(cellTable, row, column);
        return Optional.of(this.getEliminateMoveDto(cellBlock, pairValueList.get(), row, column));
    }

    public EliminateMoveDto getEliminateMoveDto(CellDto[][] cellBlock, List<Integer> remainingValue,
                                                int row, int column) {
        var list = new ArrayList<EliminateDto>();
        int blockRowIndex = (row / 3) * 3;
        int blockColumnIndex = (column / 3) * 3;

        for (int k=0; k<cellBlock.length; k++) {
            for (int j=0; j<cellBlock[0].length; j++) {
                if (cellBlock[k][j].getPossibleList() == null || cellBlock[k][j].getPossibleList().equals(remainingValue)) {
                    continue;
                }

                var realRowIndex = k + blockRowIndex;
                var realColumnIndex = j + blockColumnIndex;
                var tempRow = k;
                var tempColumn = j;
                var eliminateList = remainingValue.stream()
                        .filter(integer -> cellBlock[tempRow][tempColumn].getPossibleList().contains(integer))
                        .map(integer -> EliminateDto.builder().row(realRowIndex).column(realColumnIndex).eliminatedValue(integer).build())
                        .toList();

                list.addAll(eliminateList);
            }
        }

        return EliminateMoveDto.builder()
                .sudokuAlgorithmType(this.getSudokuAlgorithmType())
                .row(row)
                .column(column)
                .eliminateDtoList(list)
                .build();
    }
}
