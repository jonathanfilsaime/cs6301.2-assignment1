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
    static List<String> path;
    static int numberOfMoves;
    static int moveLength;

    public static void main(String[] args) throws IOException {

        commandLineInputCheck(args);

        cost = false;
        path = new ArrayList<>();
        initialState = readFile(inputFile).toLowerCase();
        goalState = computeGoalState(initialState.length());
        parentChild = new HashMap<>();

//        System.out.println(initialState);
        Node currentState = new Node(initialState, 0);
        List<String> visited = new ArrayList<>();
        PriorityQueue<Node> dataStructure = new PriorityQueue<Node>(13, new NodeComparator());

        dataStructure.add(currentState);

        do {
            currentState = dataStructure.poll();
            //goal test
            if (isGoalState(currentState.getTile(), goalState)) {
                foundGoalNode();
                break;
            } else {
                for(int i = 0; i < initialState.length(); i++) {
                    if (i != currentState.getTile().indexOf('x')) {

                        String successor = generateSuccessor(currentState.getTile(), i);

                        if (!isVisited(successor, visited)) {
                            parentChild.put(successor, currentState.getTile());
                            int depth = computeDepthFunction(successor, parentChild);
                            int cost = computeCostFunction(depth, successor);
                            Node successorNode = new Node(successor, cost);
                            dataStructure.add(successorNode);
                        }
                    }
                }
                visited.add(currentState.getTile());
            }
        } while (true);
    }

    private static void foundGoalNode() {
        System.out.println("Final Result for " + algo + ":");
        computeDepthFunction(goalState, parentChild);
        Collections.reverse(path);

        for(int i = 0; i < path.size(); i++) {
            if (i == 0){
                System.out.println("Step " + i + ": " + path.get(i));
            } else {
                System.out.println("Step " + i + ": move " + path.get(i).indexOf('x') + " " + path.get(i));
            }
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
        numberOfMoves++;
        moveLength = Math.abs(xIndex - nodeIndex);
        return successorNode;
    }

    public static int computeNumberOfTilesOutOfPlace(String successor) {
        int numberOfTilesOutOfPlace = 0;
        for (int i = 0; i < successor.length(); i++ ) {
            if (successor.charAt(i) != goalState.charAt(i)) {
                numberOfTilesOutOfPlace++;
            }
        }
        return numberOfTilesOutOfPlace;
    }

    private static int computeCostFunction(int depth, String successor) {
        if (algo == "BFS") {
           return depth;
        } else if (algo == "DFS") {
            return 1/depth;
        } else if (algo == "UCS" && !cost) {
            return numberOfMoves;
        } else if (algo == "UCS" && cost) {
            return moveLength;   //modify this
        } else if (algo == "GS") {
            return computeNumberOfTilesOutOfPlace(successor);
        } else if (algo == "A-star" && !cost) {
            return numberOfMoves + computeNumberOfTilesOutOfPlace(successor);
        } else if (algo == "A-star" && cost) {
            return moveLength + computeNumberOfTilesOutOfPlace(successor);
        }
        return 0;
    }

    private static int computeDepthFunction(String successor, Map<String, String> parentChild) {
        path.clear();
        int depth = 0;
        String node = successor;
        while(true) {
            path.add(node);
            node = parentChild.get(node);
            depth++;
            if(node == initialState) {
                path.add(node);
                break;
            }
        }
        return depth;
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
