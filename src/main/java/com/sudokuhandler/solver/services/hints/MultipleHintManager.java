package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.ActionDto;
import com.sudokuhandler.solver.models.ActionHintDto;
import com.sudokuhandler.solver.models.SudokuAlgorithmType;
import com.sudokuhandler.solver.services.LocalizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultipleHintManager implements MultipleHintService {
    private static final String MESSAGE_KEY = "hints.singlePossible";
    private static final String DEFAULT_MESSAGE = "Single possible for {0}x{1} cell is {2}.";

    private final List<HintService> hintServiceList;
    private final LocalizationService localizationService;

    @Override
    public ActionHintDto createActionHintDto(ActionDto actionDto) {
        var hintList = actionDto.getEliminateList()
                .stream()
                .map(eliminateMoveDto -> this.getHintServiceByAlgorithmType(eliminateMoveDto.getSudokuAlgorithmType()).createHint(eliminateMoveDto))
                .collect(Collectors.toCollection(LinkedList::new));

        String lastHint = this.createHintSinglePossible(actionDto);
        hintList.add(lastHint);

        return ActionHintDto.builder()
                .hints(hintList)
                .row(actionDto.getRow())
                .column(actionDto.getColumn())
                .value(actionDto.getValue())
                .build();
    }

    @Override
    public String createHintSinglePossible(ActionDto actionDto) {
        return this.localizationService.getLocalText(MESSAGE_KEY, DEFAULT_MESSAGE,
                actionDto.getRow() + 1, actionDto.getColumn() + 1, actionDto.getValue());
    }

    private HintService getHintServiceByAlgorithmType(SudokuAlgorithmType sudokuAlgorithmType) {
        return this.hintServiceList.stream()
                .filter(hintService -> hintService.getSudokuAlgorithmType().equals(sudokuAlgorithmType))
                .findAny()
                .orElseThrow();
    }
}
