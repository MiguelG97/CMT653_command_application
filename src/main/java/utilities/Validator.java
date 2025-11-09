package utilities;

import interfaces.IValidator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Validator implements IValidator {

    /**
     * It validates user input is non-empty text.
     * @param scanner
     * @param message e.g: Input can not be empty. Please enter a valid text:
     * @return
     */
    public String ValidateTextInput(Scanner scanner,
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
    public int ValidateIntInput(Scanner scanner,int min,int max){
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
    public List<Integer> ValidateMultipleIntInput(Scanner scanner,int min,int max){
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

    public Date ValidateDateInput(Scanner scanner){
        System.out.println("Enter the appointment date (Format: yyyy-mm-dd, e.g. 2025-10-17): ");
        LocalDate appointmentDate = null;
        while (appointmentDate == null){
            String dateInput = scanner.nextLine().trim();
            try{
                appointmentDate = LocalDate.parse(dateInput);
            }catch(DateTimeParseException e){
                System.out.print("Invalid date format. Please enter a valid date: ");
            }
        }
        return Date.from(appointmentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
