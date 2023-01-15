package com.sudokuhandler.solver.models;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;

@Data
@Builder
public class ActionHintDto {
    LinkedList<String> hints;
    int row;
    int column;
    int value;
}
