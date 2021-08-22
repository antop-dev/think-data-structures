package com.allendowney.thinkdast;

import org.junit.Test;

import java.util.Map;

public class SillyArrayTest {

    @Test
    public void hashing() {
        Map<SillyArray, Integer> map = new MyBetterMap<>();

        SillyArray array = new SillyArray("Word1".toCharArray()); // hahsing = 461
        map.put(array, 1);

        // what happens if we mutate a key while it's in the Map?
        array.setChar(0, 'C'); // "Cord1" → hashing = 441

        Integer value = map.get(array);
        // 버킷의 크기가 2이고
        // 461이나 441 이나 hashCode % 2 를 하면 인덱스는 1이 나온다.
        // 1번 배열의 LinearMap 에서 key 값를 비교하면서 찾게 되는데..
        // 먼저 map에 들어갔있던 SillyArray 인스턴스의 내부 배열 값이 바뀐 것이므로 "Cord1" 이다.
        // 그래서 값을 찾게 된다.
        // 만약 배열의 값을 다른 것으로 바꿔서 해시의 값이 짝수라면 못 찾는다.
        System.out.println(value); // 1
    }
}
