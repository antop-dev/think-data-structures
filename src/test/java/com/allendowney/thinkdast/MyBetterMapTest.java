/**
 *
 */
package com.allendowney.thinkdast;

import org.junit.Before;

/**
 * @author downey
 *
 */
public class MyBetterMapTest extends MyLinearMapTest {

    /**
     * @throws Exception
     */
    @Before
    public void setUp() {
        map = new MyBetterMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put(null, 0);
    }

}
