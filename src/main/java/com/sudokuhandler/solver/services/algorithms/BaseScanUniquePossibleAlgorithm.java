package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.models.EliminateDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

abstract class BaseScanUniquePossibleAlgorithm implements SudokuAlgorithmService {

    public Optional<List<Integer>> getEliminateList(CellDto[] excludedCellArray, CellDto selectedCellDto) {
        var possibleList = selectedCellDto.getPossibleList();
        if (possibleList == null || possibleList.isEmpty()) {
            return Optional.empty();
        }

        var uniquePossible = possibleList.stream()
                .filter(integer -> !this.isRowHasSelectedValue(excludedCellArray, integer))
                .findAny();

        if (uniquePossible.isEmpty()) {
            return Optional.empty();
        }

        var uniqueValue = uniquePossible.get();

        var eliminateValueList = possibleList.stream()
                .filter(integer -> !uniqueValue.equals(integer))
                .toList();
        return Optional.of(eliminateValueList);
    }

    public Optional<EliminateMoveDto> getEliminateMoveDto(List<Integer> eliminateValueList, int row, int column) {
        var eliminateList = eliminateValueList
                .stream()
                .map(integer -> EliminateDto.builder().row(row).column(column).eliminatedValue(integer).build())
                .toList();

        return Optional.of(EliminateMoveDto.builder()
                .row(row)
                .column(column)
                .eliminateDtoList(eliminateList)
                .sudokuAlgorithmType(this.getSudokuAlgorithmType())
                .build());
    }

    private boolean isRowHasSelectedValue(CellDto[] cellTable, int value) {
        return Arrays.stream(cellTable)
                .filter(cellDto -> cellDto.getPossibleList() != null)
                .anyMatch(cellDto -> cellDto.getPossibleList().contains(value));
    }
}
