package com.genetica.genetica_calc.Service;

import com.genetica.genetica_calc.Model.Caracter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculatorService {

    public List<String> generateGametes(String genotype) {
        List<String[]> allelePairs = new ArrayList<>();
        for (int i = 0; i < genotype.length(); i += 2) {
            allelePairs.add(new String[] {
                    String.valueOf(genotype.charAt(i)),
                    String.valueOf(genotype.charAt(i + 1))
            });
        }

        Set<String> gametesSet = new HashSet<>();
        backtrackGametes(allelePairs, 0, "", gametesSet);

        return new ArrayList<>(gametesSet);
    }

    private void backtrackGametes(List<String[]> pairs, int index, String current, Set<String> gametes) {
        if (index == pairs.size()) {
            gametes.add(current);
            return;
        }
        for (String allele : pairs.get(index)) {
            backtrackGametes(pairs, index + 1, current + allele, gametes);
        }
    }

    public String combineAndSortGenotype(String g1, String g2, List<Caracter> caractere) {
        String combined = g1 + g2;
        StringBuilder result = new StringBuilder();
        for (Caracter caracter : caractere) {
            char dom = caracter.getDominant();
            char rec = caracter.getRecessive();

            char allele1 = 0, allele2 = 0;

            int found = 0;
            for (char c : combined.toCharArray()) {
                if (c == dom || c == rec) {
                    if (found == 0) allele1 = c;
                    else if (found == 1) allele2 = c;
                    found++;
                    if (found == 2) break;
                }
            }
            if (Character.toUpperCase(allele1) == Character.toUpperCase(allele2)) {
                if (Character.isUpperCase(allele1)) {
                    result.append(allele1).append(allele2);
                } else {
                    result.append(allele2).append(allele1);
                }
            } else {
                if (Character.toUpperCase(allele1) < Character.toUpperCase(allele2)) {
                    if (Character.isLowerCase(allele1) && Character.isUpperCase(allele2)) {
                        result.append(allele2).append(allele1);
                    } else {
                        result.append(allele1).append(allele2);
                    }
                } else {
                    if (Character.isLowerCase(allele2) && Character.isUpperCase(allele1)) {
                        result.append(allele1).append(allele2);
                    } else {
                        result.append(allele2).append(allele1);
                    }
                }
            }
        }
        return result.toString();
    }

    public String[][] generatePunnettTable(List<String> gametes1, List<String> gametes2, List<Caracter> caractere) {
        String[][] table = new String[gametes1.size()][gametes2.size()];
        for (int i = 0; i < gametes1.size(); i++) {
            for (int j = 0; j < gametes2.size(); j++) {
                table[i][j] = combineAndSortGenotype(gametes1.get(i), gametes2.get(j), caractere);
            }
        }
        return table;
    }


    public List<Map<String, Object>> calculatePhenotypes(String[][] punnettTable, Caracter c1, Caracter c2) {
        Map<String, Integer> genotypeCounts = new HashMap<>();
        int total = 0;

        for (String[] row : punnettTable) {
            for (String genotype : row) {
                genotypeCounts.put(genotype, genotypeCounts.getOrDefault(genotype, 0) + 1);
                total++;
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : genotypeCounts.entrySet()) {
            String genotype = entry.getKey();
            int count = entry.getValue();
            double percentage = (count * 100.0) / total;
            String phenotype = getPhenotypeFromGenotype(genotype, c1, c2);

            Map<String, Object> map = new HashMap<>();
            map.put("genotype", genotype);
            map.put("phenotype", phenotype);
            map.put("percentage", String.format("%.2f", percentage));
            result.add(map);
        }
        return result;
    }

    private String getPhenotypeFromGenotype(String genotype, Caracter c1, Caracter c2) {
        StringBuilder sb = new StringBuilder();

        if (c1 != null) {
            char dom = c1.getDominant();
            char rec = c1.getRecessive();
            if (genotype.indexOf(dom) >= 0) {
                sb.append(c1.getDominantDesc());
            } else {
                sb.append(c1.getRecessiveDesc());
            }
        }

        if (c2 != null && genotype.length() > 2) {
            if (sb.length() > 0) sb.append(", ");
            char dom = c2.getDominant();
            char rec = c2.getRecessive();
            if (genotype.indexOf(dom) >= 0) {
                sb.append(c2.getDominantDesc());
            } else {
                sb.append(c2.getRecessiveDesc());
            }
        }

        return sb.toString();
    }
    public Map<String, List<String>> classifyGenotypesSimple(String[][] table, List<Caracter> caractere) {
        Map<String, List<String>> classification = new HashMap<>();
        classification.put("dublu_homozigot", new ArrayList<>());
        classification.put("dublu_heterozigot", new ArrayList<>());

        Set<String> processed = new HashSet<>();

        for (String[] row : table) {
            for (String genotype : row) {
                if (processed.contains(genotype)) continue;
                processed.add(genotype);

                boolean allHomo = true;
                boolean allHetero = true;

                for (Caracter c : caractere) {
                    char dom = c.getDominant();
                    char rec = c.getRecessive();

                    long domCount = genotype.chars().filter(ch -> ch == dom).count();
                    long recCount = genotype.chars().filter(ch -> ch == rec).count();

                    if (!((domCount == 2) || (recCount == 2))) {
                        allHomo = false;
                    }

                    if (!(domCount == 1 && recCount == 1)) {
                        allHetero = false;
                    }
                }

                if (allHomo) {
                    classification.get("dublu_homozigot").add(genotype);
                } else if (allHetero) {
                    classification.get("dublu_heterozigot").add(genotype);
                }
            }
        }

        return classification;
    }
}
