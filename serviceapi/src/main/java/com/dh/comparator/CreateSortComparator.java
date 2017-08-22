package com.dh.comparator;

import com.dh.entity.RepairOrder;

import java.util.Comparator;

/**
 * Created by gaoll on 2015/10/26.
 */
public class CreateSortComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        RepairOrder p1 = (RepairOrder) o1;
        RepairOrder p2 = (RepairOrder) o2;
        if (p1 == null || p1.getSort() == null) {
            return 1;
        }
        if (p2 == null || p2.getSort() == null) {
            return -1;
        }
        return -p1.getSort().compareTo(p2.getSort());
    }
}
