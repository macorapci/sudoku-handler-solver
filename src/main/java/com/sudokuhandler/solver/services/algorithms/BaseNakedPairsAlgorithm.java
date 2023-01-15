package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.models.CellDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

abstract class BaseNakedPairsAlgorithm implements SudokuAlgorithmService {
    public Optional<List<Integer>> getPairValueList(CellDto[] excludedCellArray, CellDto selectedCell) {
        if (selectedCell.getPossibleList().size() != 2) {
            return Optional.empty();
        }

        return Arrays.stream(excludedCellArray)
                .filter(cellDto -> cellDto.getPossibleList() != null)
                .filter(cellDto -> cellDto.getPossibleList().equals(selectedCell.getPossibleList()))
                .findAny()
                .map(CellDto::getPossibleList);
    }
}
