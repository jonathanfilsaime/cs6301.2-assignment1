
import java.io.*;
import java.util.*;

public class Search {

    static boolean cost;
    static String inputFile;

    public static void main(String[] args) throws IOException {

        cost = false;

        commandLineInputCheck(args);

        String initialState = readFile("tile1.txt").toLowerCase();
        String goalState = computeGoalState(initialState.length());
        System.out.println("goal State: " + goalState);

        System.out.println("initial state :" + initialState);

        String currentState = initialState;
        List<String> visited = new ArrayList<>();
        Queue<String> dataStructure = new PriorityQueue<>();
        List<String> path = new ArrayList<>();

        dataStructure.add(currentState);
        int numberOfNodeExplored = 0;
        int numberOfSuccessorGenerated = 0;

        do {
            currentState = dataStructure.poll();

            //goal test
            if (isGoalState(currentState, goalState)) {
                System.out.println("number of successor generated : " + numberOfSuccessorGenerated);
                System.out.println("number of node explored : " + numberOfNodeExplored);
                System.out.println("success current state " + currentState + " == " + goalState + " goalState.");
                break;
            } else if (isVisited(currentState, visited)) {
                //if visited already do nothing
            } else {
                //generate successor
                visited.add(currentState);
                for(int i = 0; i < initialState.length(); i++) {
                    dataStructure.add(generateSuccessor(currentState, i));
                    numberOfSuccessorGenerated++;
                }
            }
            numberOfNodeExplored++;
        } while (true);

    }

    public static String computeGoalState(int length) {
        int half = length / 2;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < half; i++) { stringBuilder.append("b"); }
        stringBuilder.append("x");
        for(int i = 0; i < half; i++) { stringBuilder.append("w"); }

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
                break;
            case "DFS":
                System.out.println(arg);
                break;
            case "UCS":
                System.out.println(arg);
                break;
            case "GS":
                System.out.println(arg);
                break;
            case "A-star":
                System.out.println(arg);
                break;
            default:
                System.out.println(arg);
                throw new IllegalArgumentException("second argument should be -cost or <BFS|DFS|UCS|GS|A-star> ");
        }
    }

    public static void setInputFile(String arg) {
        System.out.println("input file " + arg);
    }
}
