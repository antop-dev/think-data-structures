package com.allendowney.thinkdast;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * @author downey
 *
 */
public class WikiPhilosophyTest {


    /**
     * Test method for {@link WikiPhilosophy#main(java.lang.String[])}.
     */
    @Test
    public void testMain() {
        // Because this lab is more open-ended than others, we can't provide unit
        // tests.  Instead, we just check that you've modified WikiPhilosophy.java
        // so it doesn't throw an exception.
        String[] args = {};
        try {
            WikiPhilosophy.main(args);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}
