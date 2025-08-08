package com.genetica.genetica_calc.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class F2Result {
    private String genotypeF1;
    private List<String> gametes;
    private String[][] table;
    private List<Map<String, Object>> phenotypes;
    private Map<String, List<String>> simpleClassification;

}
