//Dominik Grzeszczyk grupa 2 158794
package Zestaw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("Zadanie2:");
        String pathToDecisionSystem = "D:\\Projekty\\SI\\Zestaw3\\SystemDecyzyjny.txt";
        ArrayList<String> lines = read_lines(pathToDecisionSystem);
        int xSize = lines.get(0).split(" ").length;
        int ySize = lines.size();
        int[][] attributes = new int[ySize][xSize-1];
        int[] decisions = new int[ySize];
        for (int i = 0; i < ySize; ++i) {
            String [] line = lines.get(i).split(" ");
            for (int j = 0; j < xSize-1; ++j)
                attributes[i][j] = Integer.parseInt(line[j]);
            decisions[i] = Integer.parseInt(line[xSize-1]);
        }

        System.out.println("System decyzyjny:");
        drawDecisionSystem(attributes, decisions);
        System.out.println("\nReguly:");
        sequentialCovering(attributes, decisions);
    }

    public static ArrayList<String> read_lines(String path) {
        ArrayList<String> output = new ArrayList<>();
        try {
            File obj = new File(path);
            Scanner myReader = new Scanner(obj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                output.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("blad");
            e.printStackTrace();
        }
        return output;
    }

    public static void drawDecisionSystem(int [][] attributes, int [] decisions) {
        int xSize = attributes[0].length + 1;
        int ySize = attributes.length;
        System.out.print("   ");
        for(int i = 0; i < xSize-1; ++i) {
            System.out.print(" a"+(i+1)+" ");
        }
        System.out.println("  d");
        System.out.print("   ┌");
        for(int i = 0; i < xSize-1; ++i)
            System.out.print("───┬");
        System.out.println("───┐");
        for(int i = 0; i < ySize; ++i) {
            System.out.print("o"+(i+1)+" ");
            for(int j = 0; j < xSize-1; ++j) {
                System.out.print("│ "+attributes[i][j]+" ");
            }
            System.out.print("│ "+decisions[i]+" │");
            System.out.println();
            if(i != ySize-1) {
                System.out.print("   ├");
                for(int j = 0; j < xSize-1; ++j)
                    System.out.print("───┼");
                System.out.println("───┤");
            }
        }

        System.out.print("   └");
        for(int i = 0; i < xSize-1; ++i)
            System.out.print("───┴");
        System.out.println("───┘");
    }

    public static void sequentialCovering(int [][] attributes, int [] decisions) {
        int ySize = attributes.length;

        int numberOfAttributes = attributes[0].length;
        ArrayList<int[]> combinations = new ArrayList<>();
        ArrayList<int[]> iElementsFromN = new ArrayList<>();
        for(int i = 1; i <= numberOfAttributes; ++i) {
            iElementsFromN = generateCombinations(numberOfAttributes, i);
            for(int [] j : iElementsFromN)
                combinations.add(j);
        }

        ArrayList<Integer> considerations = new ArrayList<>();
        for(int i = 0; i < ySize; ++i)
            considerations.add(i);

        for(int combinationsIterator = 0; combinationsIterator < combinations.size() && considerations.size() > 0; ++combinationsIterator) {
            for(int i = 0; i < considerations.size(); ++i) {
                boolean correct = true;
                int obj = considerations.get(i);
                for(int j = 0; j < ySize; ++j) {
                    boolean sameAttributesValues = true;
                    for(int k : combinations.get(combinationsIterator))
                        if(attributes[obj][k] != attributes[j][k])
                            sameAttributesValues = false;
                    if(sameAttributesValues)
                        if(decisions[obj] != decisions[j])
                            correct = false;
                }
                if(correct) {
                    System.out.print("o"+(obj+1)+": ");
                    for(int j = 0; j < combinations.get(combinationsIterator).length; ++j) {
                        int attribute = combinations.get(combinationsIterator)[j];
                        System.out.print("(a"+(attribute+1)+" = "+attributes[obj][attribute]+") ");
                        if(j < combinations.get(combinationsIterator).length-1)
                            System.out.print("AND ");
                    }
                    System.out.print("==> d = "+decisions[obj]);
                    System.out.print(" remove");
                    ArrayList<Integer> toDeletedFromConsiderations = new ArrayList<>();
                    for(int j = 0; j < ySize; ++j) {
                        boolean sameAttributesValues = true;
                        for(int k : combinations.get(combinationsIterator))
                            if(attributes[obj][k] != attributes[j][k])
                                sameAttributesValues = false;
                            if(sameAttributesValues)
                                toDeletedFromConsiderations.add(j);
                    }
                    if(toDeletedFromConsiderations.size() > 1)
                        System.out.print(" objects: ");
                    else
                        System.out.print(" object: ");
                    for(int j : toDeletedFromConsiderations)
                        System.out.print("o"+(j+1)+" ");
                    System.out.println();
                    for(int k = toDeletedFromConsiderations.size()-1; k >= 0; --k) {
                        considerations.remove(toDeletedFromConsiderations.get(k));
                    }
                    if(toDeletedFromConsiderations.size() > 0) {
                        i = -1;
                    }
                }
            }
        }
    }

    private static void generateCombinationsHelper(ArrayList<int[]> combinations, int [] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            generateCombinationsHelper(combinations, data, start + 1, end, index + 1);
            generateCombinationsHelper(combinations, data, start + 1, end, index);
        }
    }

    public static ArrayList<int[]> generateCombinations(int n, int r) {
        ArrayList<int[]> combinations = new ArrayList<>();
        generateCombinationsHelper(combinations, new int[r], 0, n-1, 0);
        return combinations;
    }
}
