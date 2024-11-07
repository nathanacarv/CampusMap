import java.util.ArrayList;
import java.util.Scanner;

public class CampusMap {
    public static void main(String[] args) {

        //declare and initialize scanner object
        Scanner scnr = new Scanner(System.in);

        final int SIZE = 12; //max row and column value
        int[][] buildingsMap = new int[SIZE][SIZE]; //2d map array for the buildings

        final int MAX_BUILDINGS = 144;//max number of buildings that fit in map
        String[] storeBuildingNames = new String[MAX_BUILDINGS]; //array to store building names

        displayEmptyMap(); //call function that displays empty map and give map instructions

        //loop to allow the user to add multiple buildings
        char addAnotherBuilding;
        do {
            //call the addBuilding method to add a new building
            addBuilding(scnr, SIZE, buildingsMap, storeBuildingNames);

            //display the updated map with the new building
            displayMap(buildingsMap, storeBuildingNames);

            //ask if the user wants to add another building
            System.out.println("Would you like to add another building? (y or n)");
            addAnotherBuilding = scnr.next().charAt(0);
            scnr.nextLine(); //delete newline character

        } while (addAnotherBuilding == 'y' || addAnotherBuilding == 'Y');

        displayBuildingList(buildingsMap, storeBuildingNames);

        //allow user to request directions
        char requestDirections;
        do{
            System.out.println("Would you like to get directions between two buildings? (y or n)");
            requestDirections = scnr.next().charAt(0);
            scnr.nextLine(); //delete newline character

            if(requestDirections == 'y' || requestDirections == 'Y'){
                getDirections(scnr, buildingsMap);
            }
        }while(requestDirections == 'y' || requestDirections == 'Y');
        System.out.println("Bye!");
    }

    //######################## *METHODS* #########################

    //method to display empty map
    public static void displayEmptyMap() {
        for (int l = 0; l < 12; l++) {
            for (int m = 0; m < 12; m++) {
                System.out.print("|  "); //2 spaces after |
            }
            System.out.println("|  "); //2 spaces after |
            System.out.println("-------------------------------------");
        }
        System.out.println();

        //inform user how the map and coordinates work
        System.out.println("To add a building to the map, please enter the coordinates for the row and column.");
        System.out.println("The map rows and columns both go from 0 to 11.");
        System.out.println("For example: row = 0 and column  = 0 adds a building to the first position on the map.\n");
    }

    //method that displays populated map and list of buildings
    public static void displayMap(int[][] buildingsMap, String[] storeBuildingNames) {
        System.out.println("\nPopulated Map:");

        for (int row = 0; row < buildingsMap.length; row++) {
            System.out.print("|");
            for (int col = 0; col < buildingsMap[row].length; col++) {
                int buildingNum = buildingsMap[row][col];
                if (buildingNum == 0) {
                    System.out.print("   |"); // Empty space for unoccupied cells
                } else {
                    System.out.print(" " + buildingNum + " |"); // Display the building number
                }
            }
            System.out.println(); // Move to the next row
            System.out.println("----------------------------------------------");
        }

        // Print the building list below the map
        System.out.println("\nList of Building Numbers and Names:");
        for (int i = 0; i < storeBuildingNames.length; i++) {
            if (storeBuildingNames[i] != null) { // Only print if the building name exists
                System.out.println("Building Number: " + (i + 1) + ", Name: " + storeBuildingNames[i]);
            }
        }
    }

    //method that checks for unique building number
    public static int checkBuildingNumber(int[][] buildingsMap, int row, int column, int buildingNum, Scanner scnr) {
        boolean bldgAlreadyExists = false;

        //check if the number is already in use
        for (int bldgNumRow = 0; bldgNumRow < buildingsMap.length; bldgNumRow++) {
            for (int bldgNumCol = 0; bldgNumCol < buildingsMap[bldgNumRow].length; bldgNumCol++) {
                if (buildingsMap[bldgNumRow][bldgNumCol] == buildingNum) {
                    bldgAlreadyExists = true; //building number is already in use
                    break; //exit the loop if building already exists
                }
            }
            if (bldgAlreadyExists) {
                break; //exit the loop if the building already exists
            }
        }

        //if the building number already exists, prompt the user for a new one
        while (bldgAlreadyExists) {
            System.out.println("ERROR: Number already in use. Choose another building number.");
            System.out.print("Enter the building number: ");
            buildingNum = scnr.nextInt();

            //check again if the new number is already in use
            bldgAlreadyExists = false; // Reset the flag
            for (int bldgNumRow = 0; bldgNumRow < buildingsMap.length; bldgNumRow++) {
                for (int bldgNumCol = 0; bldgNumCol < buildingsMap[bldgNumRow].length; bldgNumCol++) {
                    if (buildingsMap[bldgNumRow][bldgNumCol] == buildingNum) {
                        bldgAlreadyExists = true; // Found a duplicate
                        break;
                    }
                }
                if (bldgAlreadyExists) {
                    break;
                }
            }
        }
        return buildingNum;
    }


    //method to add buildings
    public static void addBuilding(Scanner scnr, final int SIZE, int buildingsMap[][], String storeBuildingNames[]) {
        //ask for user input on where to put the building
        System.out.println("Enter a row number (0 - 11): ");
        int row = scnr.nextInt();
        //input validation
        while (row < 0 || row >= SIZE) {
            System.out.println("ERROR: Invalid value. Enter a number for the row (0 - 11): ");
            row = scnr.nextInt();
        }
        System.out.println("Enter a column number (0 - 11): ");
        int column = scnr.nextInt();
        //input validation
        while (column < 0 || column >= SIZE) {
            System.out.println("ERROR: Invalid value. Enter a number for the column (0 - 11): ");
            column = scnr.nextInt();
        }

        //ask user to enter the building number
        System.out.println("Enter the building number: ");
        int buildingNum = scnr.nextInt();

        //check for a unique building number
        buildingNum = checkBuildingNumber(buildingsMap, row, column, buildingNum, scnr);

        buildingsMap[row][column] = buildingNum; //adds number to display in map

        scnr.nextLine(); //delete newline character

        System.out.println("Enter the building name: ");
        String buildingName = scnr.nextLine();

        //store the building name in the array at the index corresponding to the building number
        storeBuildingNames[buildingNum - 1] = buildingName; //assuming building numbers start from 1
    }


    //method to display list of populated building numbers and their names
//    public static void displayBuildingList(int[][] buildingsMap, String[] storeBuildingNames) {
//        System.out.println("\nList of Building Numbers and Names:");
//        for (int row = 0; row < buildingsMap.length; row++) {
//            for (int col = 0; col < buildingsMap[row].length; col++) {
//                int buildingNum = buildingsMap[row][col];
//                if (buildingNum != 0) { //only display if there is a building
//                    //find the index of the building name
//                    int index = -1;
//                    for (int i = 0; i < storeBuildingNames.length; i++) {
//                        //assuming building numbers are unique
//                        if (storeBuildingNames[i] != null && buildingNum == buildingsMap[row][col]) {
//                            index = i;
//                            break;
//                        }
//                    }
//                    //print the building number and name
//                    if (index != -1) {
//                        System.out.println("Building Number: " + buildingNum + ", Name: " + storeBuildingNames[index]);
//                    }
//                }
//            }
//        }
//    }
    public static void displayBuildingList(int[][] buildingsMap, String[] storeBuildingNames) {
        System.out.println("\nList of Building Numbers and Names:");
        for (int row = 0; row < buildingsMap.length; row++) {
            for (int col = 0; col < buildingsMap[row].length; col++) {
                int buildingNum = buildingsMap[row][col];
                if (buildingNum != 0) { //only display if there is a building
                    //print the building number and name directly using the building number as the index
                    System.out.println("Building Number: " + buildingNum + ", Name: " + storeBuildingNames[buildingNum - 1]);
                }
            }
        }
    }


    //method to get and print directions between two buildings
    public static void getDirections(Scanner scnr, int[][] buildingsMap){
        System.out.println("Enter the building number where you are at: ");
        int currentBuilding = scnr.nextInt();
        System.out.println("Enter the building number that you want to go to: ");
        int destinationBuilding = scnr.nextInt();

        //variables that hold the coordinates for current and destination buildings
        //initialized to -1 because they haven't been assigned yet
        int currentRow = -1, currentCol = -1, destinationRow = -1, destinationCol = -1;

        //find coordinates of current and destionations buildings
        for(int row = 0; row < buildingsMap.length; row++){
            for(int col = 0; col < buildingsMap[row].length; col++)
            {
                if (buildingsMap[row][col] == currentBuilding){
                    currentRow = row;
                    currentCol = col;
                }
                if (buildingsMap[row][col] == destinationBuilding){
                    destinationRow = row;
                    destinationCol = col;
                }
            }
        }

        String directions = "Start at Building " + currentBuilding + "\n";

        //north-south direction
        int northSouthDistance = (destinationRow - currentRow) * 100;
        if(northSouthDistance>0){
            directions += "Walk South " + northSouthDistance + " Yards.\n";
        }else if(northSouthDistance<0){
            directions += "Walk North " + (-northSouthDistance) + " Yards.\n";
        }

        //west-east direction
        int westEastDistance = (destinationCol - currentCol) * 100;
        if(westEastDistance>0){
            directions += "Then East " + westEastDistance + " Yards.\n";
        }else if(westEastDistance<0){
            directions += "Then West " + (-westEastDistance) + " Yards.\n";
        }

        directions += "End at Building " + destinationBuilding;

        //print directions
        System.out.println(directions);

    }

}

