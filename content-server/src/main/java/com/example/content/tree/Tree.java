package com.example.content.tree;

public class Tree {
    private Node root;

    //插入
    public void insert(int iData,double dData){

        //创建node节点
        Node newNode = new Node();
        newNode.iData = iData;
        newNode.dData = dData;

        if (root == null){
            root = newNode;
        }else {
            Node current = root;
            Node parent;
            while (true){
                parent = current;
                if (iData < current.iData){
                    current = current.leftNode;
                    if (current == null){
                        parent.leftNode = newNode;
                        return;
                    }
                }else {
                    current = current.rightNode;
                    if (current == null){
                        parent.rightNode = newNode;
                        return;
                    }
                }
            }
        }
    }

    public Node find(int key){
        Node current = root;
        while (current.iData != key){
            if (current.iData>key){
                current = current.leftNode;
            }else {
                current = current.rightNode;
            }
            if (current == null){
                return null;
            }
        }
        return current;
    }


    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(3,3.666);
        tree.insert(1,1.111);
        tree.insert(2,2.366);
        tree.insert(4,4.626);
        tree.insert(5,5.856);
        tree.insert(6,6.616);

        Node node = tree.find(6);
        if (node != null){
            node.showNode();
        }
    }
}
