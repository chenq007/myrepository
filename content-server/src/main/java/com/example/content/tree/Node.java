package com.example.content.tree;

public class Node {
    public int  iData;
    public double dData;
    public Node leftNode;
    public Node rightNode;

    public  void  showNode(){
        System.out.println("{" + iData + "," + dData + "}");
    }
}
