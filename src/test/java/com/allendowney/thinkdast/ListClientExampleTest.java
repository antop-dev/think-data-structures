package com.allendowney.thinkdast;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ListClientExampleTest {

    /**
     * Test method for {@link ListClientExample}.
     */
    @Test
    public void testListClientExample() {
        ListClientExample<Integer> lce = new ListClientExample<>();
        List<Integer> list = lce.getList();
        assertThat(list, Matchers.instanceOf(ArrayList.class));
    }
}
