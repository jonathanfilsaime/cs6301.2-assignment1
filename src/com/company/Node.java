package com.company;

public class Node {
    String tile;
    int order;

    public Node(String tile, int order) {
        this.tile = tile;
        this.order = order;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
