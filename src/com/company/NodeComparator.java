package com.company;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getOrder() < o2.getOrder()) {
            return 1;
        } else if (o1.getOrder() > o2.getOrder()) {
            return -1;
        }
        return 0;
    }
}