package com.allendowney.thinkdast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListClientExample<T> {
    private List<T> list;

    public ListClientExample() {
        // list = new LinkedList<T>();
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

}
