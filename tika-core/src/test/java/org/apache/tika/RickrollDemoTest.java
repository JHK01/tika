package org.apache.tika;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static org.junit.jupiter.api.Assertions.fail;

class RickrollDemoTest {
    @Test
    @EnabledIfEnvironmentVariable(named = "TIKA_RICKROLL_DEMO", matches = "true")
    void controlledFailure() {
        fail("Échec volontaire pour vérifier le Rickroll");
    }
}
