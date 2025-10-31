import domain.Application;
import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;
import utilities.GeneratorUtility;
import java.util.HashSet;
import java.util.Scanner;

public class JDGymApp {
    //performance: We plan to list the appointment in alphabetical order, so there is no need to store them in a list
    private final static HashSet<AppointmentType> _appointmentTypes = new  HashSet<AppointmentType>();
    private final static HashSet<Appointment> _appointments = new HashSet<>();
    private final static HashSet<Instructor> _instructors = new  HashSet<Instructor>();

    public static  void  main(String[] args){

        //Generators
        GeneratorUtility.GenerateInstructors(_instructors);
        GeneratorUtility.GenerateInitialAppointmentTypes(_appointmentTypes,_appointments,_instructors);

        //Variables
        Scanner scanner = new Scanner(System.in);
        boolean runningApp = true;

        //Main app
        try {
            while (runningApp) {
                System.out.println("""
                Welcome to JD Gyms!
                -------------------
                Main Dashboard:
                1. List appointments types and view appointments
                2. Create a new appointment type
                3. Delete an appointment type
                4. Create a new appointment
                5. List all instructors
                6. Create a new instructor
                7. Exit
                "Enter your choice: "
                """);
                int mainChoice = scanner.nextInt();
                scanner.nextLine();//handle text errors here?? nextInt perhaps?

                switch (mainChoice) {
                    case 1:
                        Application.ListAndViewAllAppointmentTypes(scanner,
                                _appointmentTypes,_appointments,_instructors);
                        break;
                    case 2:
                        Application.CreateAppointmentType(scanner,_appointmentTypes,
                                _instructors);
                        break;
                    case 3:
                        Application.DeleteAppointmentType(scanner,_appointmentTypes);
                        break;
                    case 4:
                        Application.CreateAppointment(scanner, _appointmentTypes, _instructors,_appointments);
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        runningApp = false;
                        break;
                    default:
                        break;
                }
            }
            System.out.println("\nThank you for using JD Gyms. Goodbye!");
        }
        catch (Exception e) {
            String msg = e.getMessage();
            System.out.println("Something went wrong: " + msg);
        }

        //Disposing variables
        _appointmentTypes.clear();
        _appointments.clear();
        _instructors.clear();
        scanner.close();
    }
}
