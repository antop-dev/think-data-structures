package com.allendowney.thinkdast;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Encapsulates a map from search term to frequency (count).
 *
 * @author downey
 */
public class TermCounter {

    private final Map<String, Integer> map;
    private final String label;

    public TermCounter(String label) {
        this.label = label;
        this.map = new HashMap<>();
    }

    public String getLabel() {
        return label;
    }

    /**
     * Returns the total of all counts.
     *
     * @return total of all counts
     */
    public int size() {
        int total = 0;
        for (int v : map.values()) {
            total += v;
        }
        return total;
    }

    /**
     * Takes a collection of Elements and counts their words.
     */
    public void processElements(Elements paragraphs) {
        for (Node node : paragraphs) {
            processTree(node);
        }
    }

    /**
     * Finds TextNodes in a DOM tree and counts their words.
     */
    public void processTree(Node root) {
        // NOTE: we could use select to find the TextNodes, but since
        // we already have a tree iterator, let's use it.
        for (Node node : new WikiNodeIterable(root)) {
            if (node instanceof TextNode) {
                processText(((TextNode) node).text());
            }
        }
    }

    /**
     * Splits `text` into words and counts them.
     *
     * @param text The text to process.
     */
    public void processText(String text) {
        // replace punctuation with spaces, convert to lower case, and split on whitespace
        String[] array = text
                .replaceAll("\\pP", " ").
                toLowerCase().
                split("\\s+");

        for (String term : array) {
            incrementTermCount(term);
        }
    }

    /**
     * Increments the counter associated with `term`.
     */
    public void incrementTermCount(String term) {
        // System.out.println(term);
        put(term, get(term) + 1);
    }

    /**
     * Adds a term to the map with a given count.
     */
    public void put(String term, int count) {
        map.put(term, count);
    }

    /**
     * Returns the count associated with this term, or 0 if it is unseen.
     */
    public Integer get(String term) {
        Integer count = map.get(term);
        return count == null ? 0 : count;
    }

    /**
     * Returns the set of terms that have been counted.
     */
    public Set<String> keySet() {
        return map.keySet();
    }

    /**
     * Print the terms and their counts in arbitrary order.
     */
    public void printCounts() {
        for (String key : keySet()) {
            Integer count = get(key);
            System.out.println(key + ", " + count);
        }
        System.out.println("Total of all counts = " + size());
    }

    public static void main(String[] args) throws IOException {
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        WikiFetcher wf = new WikiFetcher();
        Elements paragraphs = wf.fetchWikipedia(url);

        TermCounter counter = new TermCounter(url);
        counter.processElements(paragraphs);
        counter.printCounts();
    }
}
