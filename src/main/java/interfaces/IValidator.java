package interfaces;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public interface IValidator {
    String ValidateTextInput(Scanner scanner,
                             String message);
    int ValidateIntInput(Scanner scanner,int min,int max);
    List<Integer> ValidateMultipleIntInput(Scanner scanner, int min, int max);
    Date ValidateDateInput(Scanner scanner);
}
