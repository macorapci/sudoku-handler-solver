package com.sudokuhandler.solver.services.hints;

import com.sudokuhandler.solver.models.ActionDto;
import com.sudokuhandler.solver.models.ActionHintDto;

public interface MultipleHintService {
    ActionHintDto createActionHintDto(ActionDto actionDto);
    String createHintSinglePossible(ActionDto actionDto);
}
