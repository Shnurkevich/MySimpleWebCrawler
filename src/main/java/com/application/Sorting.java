package com.application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Sorting {

    public void viewTopTenResults (Map<Integer, String[]> hashMap) {
        Map<Integer, String[]> collect1 = hashMap.entrySet().stream()
                .sorted((e1, e2) -> {
                    final Integer s1 = Integer.valueOf(e1.getValue()[e1.getValue().length-1]);
                    final Integer s2 = Integer.valueOf(e2.getValue()[e2.getValue().length-1]);
                    return s2.compareTo(s1);
                })
                .limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o1, LinkedHashMap::new));

        for (Map.Entry<Integer, String[]> entry : collect1.entrySet()) {
            String[] strings = entry.getValue();
            System.out.println(strings[0] + " " + strings[1] + " " + strings[2] + " " + strings[3] + " " + strings[4] + " " + strings[5]);
        }
    }
}

