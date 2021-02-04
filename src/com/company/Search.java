package com.company;

import java.io.*;
import java.util.*;

public class Search {

    static boolean cost;
    static String inputFile;
    static String goalState;
    static String initialState;
    static String algo;
    static List<ParentChild> parentChild;

    public static void main(String[] args) throws IOException {

        cost = false;

        commandLineInputCheck(args);

        initialState = readFile(inputFile).toLowerCase();
        goalState = computeGoalState(initialState.length());
        parentChild = new ArrayList<>();

        System.out.println("goal State: " + goalState);
        System.out.println("initial state :" + initialState);

        Node currentState = new Node(initialState, 0);
        List<String> visited = new ArrayList<>();
        PriorityQueue<Node> dataStructure = new PriorityQueue<Node>(11, new NodeComparator());

        dataStructure.add(currentState);

        do {
            currentState = dataStructure.poll();

            //goal test
            if (isGoalState(currentState.getTile(), goalState)) {
                System.out.println("success current state " + currentState.getTile() + " == " + goalState + " goalState.");
                System.out.println("depth : " + parentChild.size());
                parentChild.forEach(p -> System.out.println(p.getParent() + " " + p.getChild()));
                break;
            } else if (isVisited(currentState.getTile(), visited)) {
                //if visited already do nothing
            } else {
                //generate successor
                visited.add(currentState.getTile());
                for(int i = 0; i < initialState.length(); i++) {
                    if (i != currentState.getTile().indexOf('x'))
                    {
                        Node successor = new Node(generateSuccessor(currentState.getTile(), i), computeCostFunction());
                        dataStructure.add(successor);
                    }
                }
            }
        } while (true);

    }

    public static boolean isVisited(String currentState, List<String> visited) { return visited.contains(currentState); }

    public static boolean isGoalState(String currentState, String goalState){
        return goalState.equals(currentState);
    }

    public static String computeGoalState(int length) {
        int half = length / 2;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < half; i++) { stringBuilder.append("b"); }
        stringBuilder.append("x");
        for(int i = 0; i < half; i++) { stringBuilder.append("w"); }

        return stringBuilder.toString();
    }

    public static String generateSuccessor(String currentState, int nodeIndex) {
        int xIndex = currentState.indexOf('x');

        List<Character> successor = new ArrayList<>();
        for (int i = 0; i < currentState.length(); i++) {
            successor.add(currentState.charAt(i));
        }

        Collections.swap(successor, xIndex, nodeIndex);
        StringBuilder stringBuilder = new StringBuilder();
        successor.forEach(v -> {stringBuilder.append(v);});
        String successorNode = stringBuilder.toString();

        System.out.println("move: " + nodeIndex + " "+ successorNode);

        computeDepth(currentState, successorNode);

        return successorNode;
    }

    private static int computeCostFunction() {
        if (algo.equalsIgnoreCase("BFS")){
            return parentChild.size();
        }
        if (algo.equalsIgnoreCase("DFS")){
            return 1/parentChild.size();
        }
        return 0;
    }

    private static void computeDepth(String currentState, String successorNode) {

        boolean flag = false;
        for (int i = 0; i < parentChild.size() ; i++) {
            if (parentChild.get(i).getParent().equalsIgnoreCase(currentState)) {
                flag = true;
            }
        }

        if (!flag) {
            parentChild.add(new ParentChild(currentState, successorNode));
        }
    }

    public static String readFile(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            break;
        }
        bufferedReader.close();
        return line;
    }

    public static void commandLineInputCheck(String[] args) {
        if(args.length > 4) {
            throw new IllegalArgumentException("too many arguments");
        }

        if(args.length < 3) {
            throw new IllegalArgumentException("too few arguments");
        }

        if(!args[0].equalsIgnoreCase("search")) {
            throw new IllegalArgumentException("first argument should be search");
        }

        if(args[1].equalsIgnoreCase("-cost")) {
            cost = true;
            System.out.println("cost " + args[1]);
            algoSelection(args[2]);
            setInputFile(args[3]);
        } else {
            algoSelection(args[1]);
            setInputFile(args[2]);
        }

    }

    public static void algoSelection(String arg) {
        System.out.println("algo selection: " + arg);
        switch (arg) {
            case "BFS":
                System.out.println(arg);
                algo = "BFS";
                break;
            case "DFS":
                System.out.println(arg);
                algo = "DFS";
                break;
            case "UCS":
                System.out.println(arg);
                algo = "UCS";
                break;
            case "GS":
                System.out.println(arg);
                algo = "GS";
                break;
            case "A-star":
                System.out.println(arg);
                algo = "A-star";
                break;
            default:
                System.out.println(arg);
                throw new IllegalArgumentException("second argument should be -cost or <BFS|DFS|UCS|GS|A-star> ");
        }
    }

    public static void setInputFile(String arg) {
        System.out.println("input file " + arg);
        inputFile = arg;
    }
}
