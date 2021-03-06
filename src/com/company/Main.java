package com.company;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String initialState = readFile("tile1.txt").toLowerCase();
        String goalState = computeGoalState(initialState.length());
        System.out.println("goal State: " + goalState);

        String currentState = initialState;
        List<String> visited = new ArrayList<>();
        Stack<String> dataStructure = new Stack<>();
        List<String> path = new ArrayList<>();

        dataStructure.add(currentState);
        int numberOfNodeExplored = 0;
        int numberOfSuccessorGenerated = 0;

        do {
            currentState = dataStructure.pop();

            if (isGoalState(currentState, goalState)) {
                System.out.println("number of successor generated : " + numberOfSuccessorGenerated);
                System.out.println("number of node explored : " + numberOfNodeExplored);
                System.out.println("success current state " + currentState + " == " + goalState + " goalState.");
                break;
            } else if (isVisited(currentState, visited)) {
                //do nothing
            } else {
                visited.add(currentState);
                for(int i = 0; i < initialState.length(); i++) {
                    dataStructure.push(generateSuccessor(currentState, i));
                    numberOfSuccessorGenerated++;
                }
            }
            numberOfNodeExplored++;
        } while (true);

    }

    public static String computeGoalState(int length) {
        int half = length / 2;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < half; i++) { stringBuilder.append("w"); }
        stringBuilder.append("x");
        for(int i = 0; i < half; i++) { stringBuilder.append("b"); }

        return stringBuilder.toString();
    }

    public static boolean isVisited(String currentState, List<String> visited) {
        return visited.contains(currentState);
    }

    public static boolean isGoalState(String currentState, String goalState){
        return goalState.equals(currentState);
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

        return successorNode;
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
}
