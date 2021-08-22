package com.allendowney.thinkdast;

import org.junit.Test;

import java.util.Map;

public class SillyStringTest {

    @Test
    public void hashing() {
        Map<SillyString, Integer> map = new MyBetterMap<>();

        map.put(new SillyString("Word1"), 1);
        map.put(new SillyString("Word2"), 2);

        Integer value = map.get(new SillyString("Word1"));
        System.out.println(value);
    }
}
