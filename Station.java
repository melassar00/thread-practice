/* 
    Name: Muhamad Elassar
    Course: CNT 4714 Spring 2020
    Assignment title: Project 2 – Multi-threaded programming in Java
    Date: February 12, 2020
    Class: Station
*/

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Station {

    public int stationID;
    public int workLoad;
    public int conveyorInID;
    public int conveyorOutID;
    private final Lock lock = new ReentrantLock();
    public String newLine = System.getProperty("line.separator");

    public Station(int stationID, int conveyorInID, int workLoad) {
        this.conveyorInID = conveyorInID;
        this.conveyorOutID = stationID;
        this.stationID = stationID;
        this.workLoad = workLoad;
    }

    // gives the lock status
    public boolean lockStatus(Station adjacentStation) {
        boolean inputLock = false;
        boolean outputLock = false;

        // if the lock is in place for the input, print the status
        if (inputLock = this.lock.tryLock()) {
            printLockInStatus();
        }

        // if the lock is in place for the outpute, print the status
        if (outputLock = adjacentStation.lock.tryLock()) {
            printLockOutStatus();
        }

        // if at least one of the locks aren't in place, print the necessary unlock statuses 
        if (!(inputLock && outputLock)) {
            if (inputLock) {
                this.lock.unlock();
                printUnlockInStatus();
            }
            if (outputLock) {
                adjacentStation.lock.unlock();
                printUnlockOutStatus();
            }
        }

        // return false if one of the locks aren't in place
        return inputLock && outputLock;
    }

    // checks the lock status
    public void evaluateLock(Station adjacentStation) throws InterruptedException {
        // if one of the conveyer locks in the adjacent station isn't in place
        if (lockStatus(adjacentStation)) {
            // move a package off the workload
            System.out.println("Station " + stationID + ": successfully moves packages on conveyor " + conveyorOutID);
            ConveyerBelt.outputToFile(
                    "Station " + stationID + ": successfully moves packages on conveyor " + conveyorOutID);

            // thread is put to sleep after executing a flow and releasing its conveyors
            Random random = new Random();
            Thread.sleep(random.nextInt(10));

            // decrement the workload
            workLoad--;

            System.out.println("Station " + this.stationID + ": has " + this.workLoad + " package groups to move"
                    + newLine + newLine);
            ConveyerBelt.outputToFile("Station " + this.stationID + ": has " + this.workLoad + " package groups to move"
                    + newLine + newLine);
            
            // if the workload is 0, the station is done so print the final statement
            if (workLoad == 0)
                printFinal();

            // unlock the locks and print the statuses
            this.lock.unlock();
            adjacentStation.lock.unlock();
            printUnlockInStatus();
            printUnlockOutStatus();
        }

    }

    public void printLockInStatus() {
        System.out
                .println("Station " + stationID + ": holds lock on (granted access) to conveyor " + this.conveyorInID);
        ConveyerBelt.outputToFile(
                "Station " + stationID + ": holds lock on (granted access) to conveyor " + this.conveyorInID);
    }

    public void printLockOutStatus() {
        System.out
                .println("Station " + stationID + ": holds lock on (granted access) to conveyor " + this.conveyorOutID);
        ConveyerBelt.outputToFile(
                "Station " + stationID + ": holds lock on (granted access) to conveyor " + this.conveyorOutID);
    }

    public void printUnlockInStatus() {
        System.out.println("Station " + stationID + ": unlocks (released access) to conveyor " + this.conveyorInID);
        ConveyerBelt.outputToFile("Station " + stationID + ": unlocks (released access) to conveyor " + this.conveyorInID);
    }

    public void printUnlockOutStatus() {
        System.out.println("Station " + stationID + ": unlocks (released access) to conveyor " + this.conveyorOutID);
        ConveyerBelt.outputToFile("Station " + stationID + ": unlocks (released access) to conveyor " + this.conveyorOutID);
    }

    public void printFinal() {
        System.out.println(newLine + "* * Station " + this.stationID + ": Workload successfully completed. * *"
                + newLine + newLine);
        ConveyerBelt.outputToFile(newLine + "* * Station " + this.stationID + ": Workload successfully completed. * *"
                + newLine + newLine);
    }
}
