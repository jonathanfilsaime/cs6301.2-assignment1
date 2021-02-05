package com.company;

import java.io.*;
import java.util.*;

public class Search {

    static boolean cost;
    static String inputFile;
    static String goalState;
    static String initialState;
    static String algo;
    static Map<String, String> parentChild;

    public static void main(String[] args) throws IOException {

        cost = false;

        commandLineInputCheck(args); // good for sure

        initialState = readFile(inputFile).toLowerCase(); // good for sure
        goalState = computeGoalState(initialState.length()); // good for sure
        parentChild = new HashMap<>();

        System.out.println("initial state :" + initialState);
        System.out.println("goal State: " + goalState);


        Node currentState = new Node(initialState, 0);
        List<String> visited = new ArrayList<>();
        PriorityQueue<Node> dataStructure = new PriorityQueue<Node>(13, new NodeComparator());

        dataStructure.add(currentState);

        do {
            currentState = dataStructure.poll();
            //goal test
            if (isGoalState(currentState.getTile(), goalState)) {
                foundGoalNode(currentState);
                break;
            } else {
                for(int i = 0; i < initialState.length(); i++) {
                    if (i != currentState.getTile().indexOf('x')) {

                        Node successor = new Node(generateSuccessor(currentState.getTile(), i), 0);

                        if (!isGoalState(successor.getTile(), goalState)) {
                            if(!isVisited(successor.getTile(), visited)) {
                                visited.add(currentState.getTile());
                                visited.add(successor.getTile());
                                dataStructure.add(successor);
                                parentChild.put(currentState.getTile(), successor.getTile());
                            }
                        } else {
                            dataStructure.add(successor);
                        }
                    }
                }
            }
        } while (true);

    }

    private static void foundGoalNode(Node currentState) {
        System.out.println("success current state " + currentState.getTile() + " == " + goalState + " goal State.");
        System.out.println("success nodes at depth : " + parentChild.size());

        for (Map.Entry<String, String> parentChild : parentChild.entrySet()) {
            System.out.println(String.format("Parent: %s -> child: %s", parentChild.getKey(), parentChild.getValue()));
        }
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
            algoSelection(args[2]);
            setInputFile(args[3]);
        } else {
            algoSelection(args[1]);
            setInputFile(args[2]);
        }

    }

    public static void algoSelection(String arg) {
        switch (arg) {
            case "BFS":
                algo = "BFS";
                break;
            case "DFS":
                algo = "DFS";
                break;
            case "UCS":
                algo = "UCS";
                break;
            case "GS":
                algo = "GS";
                break;
            case "A-star":
                algo = "A-star";
                break;
            default:
                throw new IllegalArgumentException("second argument should be -cost or <BFS|DFS|UCS|GS|A-star> ");
        }
    }

    public static void setInputFile(String arg) {
        inputFile = arg;
    }
}
