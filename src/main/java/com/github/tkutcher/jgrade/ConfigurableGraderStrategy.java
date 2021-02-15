package com.github.tkutcher.jgrade;

import com.github.tkutcher.jgrade.gradedtest.GradedTestResult;
import com.github.tkutcher.jgrade.utils.GroovyUtils;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurableGraderStrategy implements GraderStrategy {

    private File configFile;

    public ConfigurableGraderStrategy(File configFile) {
        this.configFile = configFile;
    }

    @Override
    public void grade(List<GradedTestResult> gradedTestResults) {
        try {
            Map<String, GradedTestResult> testResultsMap = gradedTestResults.stream().collect(Collectors.toMap(e -> e.getName(), e -> e));
            Binding binding = new Binding();
            binding.setVariable("testResults", testResultsMap);
            GroovyShell shell = new GroovyShell(binding);
            Map<String, Object> result = (Map<String, Object>) shell.evaluate(configFile);
            List<Map<String, Object>> scoringItems = (List<Map<String, Object>>) result.get("scoringItems");
            gradedTestResults.clear();
            for (int i = 0; i < scoringItems.size(); i++) {
                Map<String, Object> item = scoringItems.get(i);
                String name = (String) item.get("name");
                double score = GroovyUtils.getNumberAsDouble(item.get("score"));
                double maxScore = GroovyUtils.getNumberAsDouble(item.get("maxScore"));
                String visibility = (String) item.get("visibility");
                GradedTestResult r = new GradedTestResult(name, Integer.toString(i), maxScore, visibility);
                r.setScore(score);
                gradedTestResults.add(r);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
