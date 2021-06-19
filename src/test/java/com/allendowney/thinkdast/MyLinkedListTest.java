package com.allendowney.thinkdast;

import org.junit.Before;

import java.util.ArrayList;


/**
 * @author downey
 */
public class MyLinkedListTest extends MyArrayListTest {

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        mylist = new MyLinkedList<>();
        mylist.addAll(list);
    }

}
