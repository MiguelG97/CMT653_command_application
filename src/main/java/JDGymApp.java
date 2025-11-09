import domain.Application;
import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;
import interfaces.IApplication;
import interfaces.IGenerator;
import interfaces.IValidator;
import utilities.Generator;
import utilities.Validator;

import java.util.HashSet;
import java.util.Scanner;

public class JDGymApp {

    //Core entites as variables
    private final static HashSet<AppointmentType> _appointmentTypes = new  HashSet<AppointmentType>();
    private final static HashSet<Appointment> _appointments = new HashSet<>();
    private final static HashSet<Instructor> _instructors = new  HashSet<Instructor>();

    //Business logic
    private final static IApplication _application = new Application();

    //helpers
    private final static IGenerator _generator = new Generator();
    private final static IValidator _validator = new Validator();

    //App entry point
    public static  void  main(String[] args){

        //step 1: Generate dummy data
        _generator.GenerateInstructors(_instructors);
        _generator.GenerateInitialAppointmentTypes(_appointmentTypes,_appointments,_instructors);

        //step 2: Scoped variables
        Scanner scanner = new Scanner(System.in);
        boolean runningApp = true;

        //step 3: Handle user interaction
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
                6. Create a new instructor´
                7. Exit
                "Enter your choice: "
                """);
                int mainChoice = _validator.ValidateIntInput(scanner,1,7);
                scanner.nextLine();

                switch (mainChoice) {
                    case 1:
                        _application.ListAndViewAllAppointmentTypes(scanner,
                                _appointmentTypes,_appointments,_instructors);
                        break;
                    case 2:
                        _application.CreateAppointmentType(scanner,_appointmentTypes,
                                _instructors);
                        break;
                    case 3:
                        _application.DeleteAppointmentType(scanner,_appointmentTypes);
                        break;
                    case 4:
                        _application.CreateAppointment(scanner, _appointmentTypes, _instructors,_appointments);
                        break;
                    case 5:
                        _application.ViewInstructors(scanner,_instructors);
                        break;
                    case 6:
                        _application.CreateInstructor(scanner,_instructors,_appointmentTypes);
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

        //4) Dispose variables
        _appointmentTypes.clear();
        _appointments.clear();
        _instructors.clear();
        scanner.close();
    }
}
