package com.allendowney.thinkdast;


import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author downey
 */
public class TermCounterTest {

    private TermCounter counter;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        WikiFetcher wf = new WikiFetcher();
        Elements paragraphs = wf.readWikipedia(url);

        counter = new TermCounter(url);
        counter.processElements(paragraphs);
    }

    @Test
    public void testSize() {
        assertThat(counter.size(), is(4798));
    }
}
