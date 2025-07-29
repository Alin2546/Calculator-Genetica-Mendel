package com.genetica.genetica_calc.Controller;

import com.genetica.genetica_calc.Model.Caracter;
import com.genetica.genetica_calc.Service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CalculatorController {
    @Autowired
    private CalculatorService service;

    private Caracter caracter1;
    private Caracter caracter2;

    @PostMapping("/define-genes")
    public String defineGenes(
            @RequestParam String dominant1,
            @RequestParam String dominant1Desc,
            @RequestParam String recessive1,
            @RequestParam String recessive1Desc,
            @RequestParam(required = false) String dominant2,
            @RequestParam(required = false) String dominant2Desc,
            @RequestParam(required = false) String recessive2,
            @RequestParam(required = false) String recessive2Desc,
            Model model) {

        caracter1 = new Caracter(dominant1.charAt(0), dominant1Desc, recessive1.charAt(0), recessive1Desc);
        if (dominant2 != null && !dominant2.isBlank() && recessive2 != null && !recessive2.isBlank()) {
            caracter2 = new Caracter(dominant2.charAt(0), dominant2Desc, recessive2.charAt(0), recessive2Desc);
        } else {
            caracter2 = null;
        }

        model.addAttribute("dominant1", dominant1);
        model.addAttribute("dominant1Desc", dominant1Desc);
        model.addAttribute("recessive1", recessive1);
        model.addAttribute("recessive1Desc", recessive1Desc);

        model.addAttribute("dominant2", dominant2);
        model.addAttribute("dominant2Desc", dominant2Desc);
        model.addAttribute("recessive2", recessive2);
        model.addAttribute("recessive2Desc", recessive2Desc);

        model.addAttribute("genesDefined", true);
        return "home";
    }

    @PostMapping("/define-parents")
    public String defineParents(
            @RequestParam String parent1,
            @RequestParam String parent2,
            Model model) {
        List<Caracter> caractere = new ArrayList<>();
        if (caracter1 != null) caractere.add(caracter1);
        if (caracter2 != null) caractere.add(caracter2);

        Set<Character> aleleValide = new HashSet<>();
        for (Caracter c : caractere) {
            aleleValide.add(c.getDominant());
            aleleValide.add(c.getRecessive());
        }

        boolean parinte1Valid = parent1.chars().allMatch(ch -> aleleValide.contains((char) ch));
        boolean parinte2Valid = parent2.chars().allMatch(ch -> aleleValide.contains((char) ch));

        if (!parinte1Valid || !parinte2Valid) {
            model.addAttribute("error", "Genotipurile trebuie să conțină doar alelele definite anterior.");
            model.addAttribute("genesDefined", true);
            model.addAttribute("caracter1", caracter1);
            model.addAttribute("caracter2", caracter2);
            return "home";
        }
        List<String> gametesParent1 = service.generateGametes(parent1);
        List<String> gametesParent2 = service.generateGametes(parent2);

        String[][] resultsF1 = service.generatePunnettTable(gametesParent1, gametesParent2, caractere);
        List<Map<String, Object>> phenotypesF1 = service.calculatePhenotypes(resultsF1, caracter1, caracter2);

        Set<String> gametesF1Set = new HashSet<>();
        for (String[] row : resultsF1) {
            gametesF1Set.addAll(Arrays.asList(row));
        }
        List<String> gametesF1 = new ArrayList<>(gametesF1Set);

        String[][] resultsF2 = service.generatePunnettTable(gametesF1, gametesF1, caractere);
        List<Map<String, Object>> phenotypesF2 = service.calculatePhenotypes(resultsF2, caracter1, caracter2);


        model.addAttribute("resultsF2", resultsF2);
        model.addAttribute("phenotypesF2", phenotypesF2);
        model.addAttribute("genesDefined", true);
        model.addAttribute("caracter1", caracter1);
        model.addAttribute("caracter2", caracter2);
        model.addAttribute("parentsDefined", true);
        model.addAttribute("parent1", parent1);
        model.addAttribute("parent2", parent2);
        model.addAttribute("gametes1", gametesParent1);
        model.addAttribute("gametes2", gametesParent2);
        model.addAttribute("resultsF1", resultsF1);
        model.addAttribute("phenotypesF1", phenotypesF1);
        model.addAttribute("gametesF1", gametesF1);

        return "home";
    }
}

