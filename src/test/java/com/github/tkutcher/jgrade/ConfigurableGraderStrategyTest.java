package com.github.tkutcher.jgrade;

import com.github.tkutcher.jgrade.gradedtest.GradedTestResult;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.tkutcher.jgrade.gradedtest.GradedTestResult.HIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigurableGraderStrategyTest {

    private ConfigurableGraderStrategy unit;

    @Before
    public void initUnit() throws URISyntaxException {
        URL configFileURL = getClass().getClassLoader().getResource("testScoringConfig.groovy");
        File configFile = new File(configFileURL.toURI());
        this.unit = new ConfigurableGraderStrategy(configFile);
    }

    private static GradedTestResult failedGradedTestResult(String name, double points) {
        GradedTestResult r = new GradedTestResult(name, "", points, HIDDEN);
        r.setPassed(false);
        return r;
    }

    private static GradedTestResult successfulGradedTestResult(String name, double points) {
        GradedTestResult r = new GradedTestResult(name, "", points, HIDDEN);
        r.setPassed(true);
        r.setScore(points);
        return r;
    }

    @Test
    public void testConfigurableGraderStrategy() {
        List<GradedTestResult> l = new ArrayList<>();
        l.add(successfulGradedTestResult("Alternative Solution 1", 1.0));
        l.add(successfulGradedTestResult("Alternative Solution 2", 0.0));
        l.add(successfulGradedTestResult("Single Solution", 1.0));

        this.unit.grade(l);

        Optional<GradedTestResult> oScoringItem1 = l.stream().filter(item -> item.getName().equals("Scoring Item 1")).findFirst();
        assertTrue(oScoringItem1.isPresent());
        assertEquals(1.0, oScoringItem1.get().getScore(), 0);

        Optional<GradedTestResult> oScoringItem2 = l.stream().filter(item -> item.getName().equals("Scoring Item 2")).findFirst();
        assertTrue(oScoringItem2.isPresent());
        assertEquals(2.0, oScoringItem2.get().getScore(), 0);
    }
}

