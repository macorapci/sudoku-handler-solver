package com.sudokuhandler.solver.services.algorithms;

import com.sudokuhandler.solver.advices.exceptions.SudokuHandleCantHandleItException;
import com.sudokuhandler.solver.constants.SudokuConstant;
import com.sudokuhandler.solver.models.ActionDto;
import com.sudokuhandler.solver.models.CellDto;
import com.sudokuhandler.solver.models.EliminateMoveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SudokuMultipleAlgorithmManager implements SudokuMultipleAlgorithmService {
    private final List<SudokuAlgorithmService> sudokuAlgorithmServiceList;

    @Override
    public ActionDto solveSudoku(CellDto[][] cellTable) {
        var eliminateMoveLinkedList = new LinkedList<EliminateMoveDto>();
        var actionOptional = this.findSinglePossible(cellTable, eliminateMoveLinkedList);
        var newCellTable = cellTable.clone();

        while (actionOptional.isEmpty()) {
            var eliminateMoveDto = this.solveSudoku(cellTable, this.sudokuAlgorithmServiceList)
                    .orElseThrow(SudokuHandleCantHandleItException::new);

            eliminateMoveLinkedList.add(eliminateMoveDto);
            newCellTable = this.deletePossibles(newCellTable, eliminateMoveDto);
            actionOptional = this.findSinglePossible(newCellTable, eliminateMoveLinkedList);
        }

        return actionOptional.get();
    }

    @Override
    public Optional<ActionDto> findSinglePossible(CellDto[][] cellTable,
                                                  LinkedList<EliminateMoveDto> eliminateList) {
        for (var k = 0; k< SudokuConstant.TABLE_LINE_COUNT; k++) {
            for (var j=0; j<SudokuConstant.TABLE_LINE_COUNT; j++) {
                if (cellTable[k][j].getValue() != 0) {
                    continue;
                }
                var possibleList = cellTable[k][j].getPossibleList();
                if (possibleList.size() == 1) {
                    var actionDto = ActionDto.builder()
                            .eliminateList(eliminateList)
                            .row(k)
                            .column(j)
                            .value(possibleList.get(0))
                            .build();
                    return Optional.of(actionDto);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<EliminateMoveDto> solveSudoku(CellDto[][] cellTable, List<SudokuAlgorithmService> sudokuAlgorithmServiceList) {
        return sudokuAlgorithmServiceList.stream()
                .sorted(Comparator.comparingInt(o -> o.getSudokuAlgorithmType().getWeight()))
                .map(sudokuAlgorithmService -> this.solveSudoku(cellTable, sudokuAlgorithmService))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    private Optional<EliminateMoveDto> solveSudoku(CellDto[][] cellTable, SudokuAlgorithmService sudokuAlgorithmService) {
        return IntStream.range(0, 81)
                .mapToObj(value -> sudokuAlgorithmService.eliminate(cellTable, value/9, value%9))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    private CellDto[][] deletePossibles(CellDto[][] cellTable, EliminateMoveDto eliminateMoveDto) {

        try {
            var result = cellTable.clone();
            eliminateMoveDto.getEliminateDtoList()
                    .forEach(eliminateDto -> {
                        result[eliminateDto.getRow()][eliminateDto.getColumn()].getPossibleList().remove(Integer.valueOf(eliminateDto.getEliminatedValue()));
                    });

            return result;
        } catch (Exception exception) {
            log.error("Possible can't remove from possibles. eliminateMoveDto: {}", eliminateMoveDto, exception);
            throw new RuntimeException("Unexpected error!");
        }

    }
}
