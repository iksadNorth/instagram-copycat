package me.iksadnorth.insta.utils;

import java.util.ArrayList;
import java.util.List;

public class Zip {
    public static List<List<Object>> zip(List<?>... lists) {
        List<List<Object>> zipped = new ArrayList<List<Object>>();
        for (List<?> list : lists) {
            for (int i = 0, listSize = list.size(); i < listSize; i++) {
                List<Object> list2;
                if (i >= zipped.size())
                    zipped.add(list2 = new ArrayList<Object>());
                else
                    list2 = zipped.get(i);
                list2.add(list.get(i));
            }
        }
        return zipped;
    }
}
