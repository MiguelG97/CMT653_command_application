package utilities;

import entities.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Validator {

    /**
     * It validates user input is non-empty text.
     * @param scanner
     * @param message e.g: Input can not be empty. Please enter a valid text:
     * @return
     */
    public static String ValidateTextInput(Scanner scanner,
                                           String message){
        String inputString= scanner.nextLine().trim();
        while(inputString.isEmpty()){
            System.out.print(message+"\n");
            inputString = scanner.nextLine().trim();
        }
        return inputString;
    }

    /**
     * It validates user input a valid integer within a tolerant range of values.
     * @param scanner
     * @param min
     * @param max
     * @return
     */
    public static int ValidateIntInput(Scanner scanner,int min,int max){
        int inputInt =0;
        boolean validInt = false;
        while (!validInt) {
            if (scanner.hasNextInt()) {
                inputInt = scanner.nextInt();
                if(inputInt < min || inputInt > max){
                    System.out.printf("Please enter a valid integer between %d and %d%n",min,max);
                    continue;
                }
                validInt = true;
            } else {
                System.out.println("Please enter a valid integer: ");
                scanner.next();
            }
        }
        return inputInt;
    }

    /**
     * It validates user selects multiple integers by using comma-separated values.
     * It also checks whether the numbers fall within the given range of values.
     * @param scanner
     * @param min
     * @param max
     * @return
     */
    public static List<Integer> ValidateMultipleIntInput(Scanner scanner,int min,int max){
        List<Integer> selectedIndexes = new ArrayList<>();
        boolean validInput = false;
        while (!validInput) {
            String inputLine = scanner.nextLine().trim();
            if (inputLine.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter at least one number.");
                continue;
            }

            String[] parts = inputLine.split(",");
            selectedIndexes.clear();
            boolean allValid = true;
            for (String part : parts){
                part = part.trim();
                try{
                    int indexSelected = Integer.parseInt(part);
                    if (indexSelected < min || indexSelected > max) {
                        System.out.printf("Number %d is out of range (%d-%d).%n", indexSelected, min, max);
                        allValid = false;
                        break;
                    }
                    selectedIndexes.add(indexSelected);
                }
                catch (NumberFormatException e){
                    System.out.printf("'%s' is not a valid integer.%n. Please try again: ", part);
                    allValid = false;
                    break;
                }
            }
            if (allValid) {
                validInput = true;
            }
        }

        return selectedIndexes;
    }
}
