/* 
    Name: Muhamad Elassar
    Course: CNT 4714 Spring 2020
    Assignment title: Project 2 – Multi-threaded programming in Java
    Date: February 12, 2020
    Class: ConveyerBelt
*/

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConveyerBelt {

    private final static File configFile = new File("config.txt");
    private final static File outputFile = new File("simulation-output.txt");
    public static ArrayList<Station> stations = new ArrayList<>();
    public static int numStations;
    public static String newLine = System.getProperty("line.separator");

    // parse the config file and extract its information
    private static void parseConfig(File configFile) throws FileNotFoundException {
        Scanner reader = new Scanner(configFile);
        int i = 0;
        int prevStationID;

        // set first line of file as number of stations
        numStations = Integer.valueOf(reader.nextLine());

        // scan the file and add the stations and their info
        while (reader.hasNextLine()) {
            if (i == 0)
                prevStationID = numStations - 1;
            else
                prevStationID = i - 1;

            Station station = new Station(i, prevStationID, Integer.valueOf(reader.nextLine()));
            stations.add(station);
            i++;
        }

        // close reader when file has no more lines to read
        reader.close();
    }

    // output statements to output file
    public static void outputToFile(String output) {
        try {
            FileWriter fileWriter = new FileWriter(outputFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(output);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // start all the threads for each station
    public static void startThreads() throws FileNotFoundException {
        System.out.println(newLine + "* * * SIMULATION BEGINS * * *" + newLine);
        outputToFile(newLine + "* * * SIMULATION BEGINS * * *" + newLine);
        parseConfig(configFile);
        for (Station station : stations) {
            WorkLoad workLoad = new WorkLoad(station);
            Thread thread = new Thread(workLoad);
            thread.start();
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        // start the simulation
        startThreads();
    }

}
