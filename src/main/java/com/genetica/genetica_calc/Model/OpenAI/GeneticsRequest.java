package com.genetica.genetica_calc.Model.OpenAI;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GeneticsRequest {

    @NotBlank
    private String problemStatement;
}
