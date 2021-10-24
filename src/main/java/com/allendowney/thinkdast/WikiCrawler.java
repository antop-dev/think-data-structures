package com.allendowney.thinkdast;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;


public class WikiCrawler {
    // keeps track of where we started
    @SuppressWarnings("unused")
    private final String source;

    // the index where the results go
    private JedisIndex index;

    // queue of URLs to be indexed
    private Queue<String> queue = new LinkedList<>();

    // fetcher used to get pages from Wikipedia
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Constructor.
     *
     * @param source
     * @param index
     */
    public WikiCrawler(String source, JedisIndex index) {
        this.source = source;
        this.index = index;
        queue.offer(source);
    }

    /**
     * Returns the number of URLs in the queue.
     *
     * @return
     */
    public int queueSize() {
        return queue.size();
    }

    /**
     * Gets a URL from the queue and indexes it.
     *
     * @param testing
     * @return URL of page indexed.
     * @throws IOException
     */
    public String crawl(boolean testing) throws IOException {
        if (queue.isEmpty()) return null;

        String url = queue.poll();
        System.out.println("Crawling " + url);

        if (!testing && index.isIndexed(url)) {
            System.out.println("Already indexed.");
            return null;
        }

        Elements paragraphs = (testing) ? wf.readWikipedia(url) : wf.fetchWikipedia(url);
        index.indexPage(url, paragraphs);
        queueInternalLinks(paragraphs);
        return url;
    }

    /**
     * Parses paragraphs and adds internal links to the queue.
     *
     * @param paragraphs
     */
    // NOTE: absence of access level modifier means package-level
    void queueInternalLinks(Elements paragraphs) {
        for (Element paragraph : paragraphs) {
            queueInternalLinks(paragraph);
        }
    }

    private void queueInternalLinks(Element paragraph) {
        Elements elements = paragraph.select("a[href]");
        for (Element e : elements) {
            String relURL = e.attr("href");
            if (relURL.startsWith("/wiki/")) {
                String absURL = e.absUrl("href");
                queue.offer(absURL);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // make a WikiCrawler
        Jedis jedis = JedisMaker.make();
        JedisIndex index = new JedisIndex(jedis);
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        WikiCrawler wc = new WikiCrawler(source, index);

        // for testing purposes, load up the queue
        Elements paragraphs = wf.fetchWikipedia(source);
        wc.queueInternalLinks(paragraphs);

        // loop until we index a new page
        String res;
        do {
            res = wc.crawl(false);

            // REMOVE THIS BREAK STATEMENT WHEN crawl() IS WORKING
            break;
        } while (res == null);

        Map<String, Integer> map = index.getCounts("the");
        for (Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
