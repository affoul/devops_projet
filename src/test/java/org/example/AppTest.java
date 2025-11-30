package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void testAddition() {
        App app = new App();
        assertEquals(5, app.addition(2, 3));
    }
}
