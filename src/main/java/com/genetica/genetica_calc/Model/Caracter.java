package com.genetica.genetica_calc.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Caracter {
    private char dominant;
    private String dominantDesc;
    private char recessive;
    private String recessiveDesc;
}
