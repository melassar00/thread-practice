/* 
    Name: Muhamad Elassar
    Course: CNT 4714 Spring 2020
    Assignment title: Project 2 – Multi-threaded programming in Java
    Date: February 12, 2020
    Class: WorkLoad
*/

import java.util.Random;

public class WorkLoad extends ConveyerBelt implements Runnable {

    Station currentStation;
    Station previousStation;
    public static int numStationsDone = 0;
    public String newLine = System.getProperty("line.separator");


    public WorkLoad(Station station) {
        // intialize current station
        this.currentStation = station;
        int currentStationPosition = ConveyerBelt.stations.indexOf(station);

        // make sure right stations are assinged
        if (currentStationPosition != 0)
            previousStation = ConveyerBelt.stations.get(currentStationPosition - 1);
        else
            previousStation = ConveyerBelt.stations.get(stations.size() - 1);
    }

    @Override
    public void run() {
        // print initial statements
        printIntial();

        // put th the thread to sleep initially and evaluate the station lock 
        // while there are still packages
        while (currentStation.workLoad != 0) {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                System.out.println("Error putting thread to sleep");
            }

            try {
                currentStation.evaluateLock(previousStation);
            } catch (InterruptedException e) {
                System.out.println("Error putting thread to sleep");
            }
        }

        // increment number of stations done count if the current workload is 0
        if (currentStation.workLoad == 0)
            numStationsDone++;

        // if all the stations are done, print statements
        if (numStationsDone == ConveyerBelt.numStations) {
            System.out.println(newLine + "* * * * ALL WORKLOADS COMPLETE * * * SIMULATION ENDS * * * *" + newLine);
            outputToFile(newLine + "* * * * ALL WORKLOADS COMPLETE * * * SIMULATION ENDS * * * *" + newLine);
        }

    }

    // initial print statements when simulation begins
    public void printIntial() {
        System.out.println("Station " + this.currentStation.stationID + ": In-Connection set to conveyor "
                + this.currentStation.conveyorInID);
        System.out.println("Station " + this.currentStation.stationID + ": Out-Connection set to conveyor "
                + this.currentStation.conveyorOutID);
        System.out.println("Station " + this.currentStation.stationID + ": Workload set. Station "
                + this.currentStation.stationID + " has " + this.currentStation.workLoad + " package groups to move");

        ConveyerBelt.outputToFile("Station " + this.currentStation.stationID + ": In-Connection set to conveyor "
                + this.currentStation.conveyorInID);
        ConveyerBelt.outputToFile("Station " + this.currentStation.stationID + ": Out-Connection set to conveyor "
                + this.currentStation.conveyorOutID);
        ConveyerBelt.outputToFile("Station " + this.currentStation.stationID + ": Workload set. Station "
                + this.currentStation.stationID + " has " + this.currentStation.workLoad + " package groups to move");
    }

}