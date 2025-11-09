package domain;

import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;
import interfaces.IApplication;
import interfaces.IValidator;
import utilities.Validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Application implements IApplication {
    private final static IValidator _validator = new Validator();


    /**
     * It lists all available {@code AppointmentType} and
     * prompts user to select one of them to view all its appointments booked.
     * @param scanner
     * @param _appointmentTypes
     * @param _appointments
     */
    public void ListAndViewAllAppointmentTypes(Scanner scanner,
                                                      HashSet<AppointmentType> _appointmentTypes,
                                                      HashSet<Appointment> _appointments,
                                                      HashSet<Instructor> _instructors) {
        //variables
        StringBuilder sb = new StringBuilder();
        String title = "-------------------\nAppointmentTypes: \n";
        String instructionMessage = "Select a type to visualize the available bookings: ";
        boolean backToMenu = false;

        //List appointments
        List<AppointmentType> sortedAppointments = _appointmentTypes.stream()
                .sorted(Comparator.comparing(AppointmentType::getName))
                .toList();
        int typesSize = sortedAppointments.size();
        sb.append(title);
        for (int i = 0; i < typesSize; i++) {
            AppointmentType appointmentType = sortedAppointments.get(i);
            sb.append(String.format("%s. %s (%s)\n", i+1, appointmentType.getName(),
                    appointmentType.getAppointmentsLength()));
        }
        sb.append(String.format("%s. Return to main menu\n", typesSize+1))
                .append(instructionMessage);

        while (!backToMenu) {
            //print appointment types
            System.out.println(sb);

            //list appointments booked
            int appointmentTypeSelected = _validator.ValidateIntInput(scanner,1,typesSize+1);
            scanner.nextLine();

            if(appointmentTypeSelected == typesSize+1)backToMenu=true;
            else{
                AppointmentType typeSelected = sortedAppointments.get(appointmentTypeSelected-1);
                ViewAppointmentsByType(typeSelected.getId(),_appointments,_instructors);
            }
        }
    }

    /**
     * This method list all the appointments booked for a particular appointmentType
     * @param
     * @return
     */
    private void ViewAppointmentsByType(UUID appointmentTypeId,
                                               HashSet<Appointment> _appointments,
                                               HashSet<Instructor> _instructors) {
        //variables
        StringBuilder sb = new StringBuilder();
        String title = "-------------------\nAppointments: \n";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<Appointment> filteredAppointments = _appointments.stream()
                .filter(at->at.getAppointmentTypeId().equals(appointmentTypeId))
                .toList();
        sb.append(title);
        //list appointments booked
        for (int i = 1; i <= filteredAppointments.size(); i++) {
            Appointment appointment = filteredAppointments.get(i - 1);
            UUID instructorId = appointment.getInstructorId();
            Optional<Instructor> instructorFound = _instructors.stream().filter(instructor -> instructor.getId().equals(instructorId)).findFirst();
            if(instructorFound.isEmpty())continue;
            sb.append(String.format("customer: %s; date: %s; instructor: %s\n",
                    appointment.getCustomerName(),formatter.format(appointment.getDate()),
                    instructorFound.get().getName()));
        }
        //print result
        System.out.println(sb);
    }

    /**
     * It creates new {@code AppointmentType} and assigns instructors to it
     * @param scanner
     * @param _appointmentTypes
     */
    public void CreateAppointmentType(Scanner scanner,
                                             HashSet<AppointmentType> _appointmentTypes,
                                             HashSet<Instructor> _instructors) {
        //variables
        StringBuilder sb = new StringBuilder();
        AppointmentType newAppointmentType = new AppointmentType();

        //assign name
        System.out.println("""
        ------------------- \n Creating new appointment type: \n
            - Please enter the name of the appointment type:
        """);
        String typeName = _validator.ValidateTextInput(scanner,"Name can not be empty. Please enter a valid name: ");
        newAppointmentType.setName(typeName);

        //assign instructors
        List<Instructor> instructorsSorted = _instructors.stream().sorted(Comparator.comparing(Instructor::getName)).toList();
        sb.append("Select instructors for this appointment type (use commas to separate multiple choices):\n");
        for (int i = 0; i < instructorsSorted.size(); i++) {
            Instructor instructor = instructorsSorted.get(i);
            sb.append(String.format("%s. %s\n", i+1, instructor.getName()));
        }
        sb.append(String.format("%s. Include All\n", instructorsSorted.size()+1));
        System.out.println(sb);

        List<Integer> selectedIndexes = _validator.ValidateMultipleIntInput(scanner,1,instructorsSorted.size()+1);
        if(selectedIndexes.contains(instructorsSorted.size()+1)){
            //Include all
            instructorsSorted.forEach(instructor ->
                    newAppointmentType.addInstructorsId(instructor.getId()));
        }
        else {
            //Only include selected indexes
            for (int i = 0; i < instructorsSorted.size(); i++) {
                if(!selectedIndexes.contains(i+1)) continue;
                Instructor instructor = instructorsSorted.get(i);
                newAppointmentType.addInstructorsId(instructor.getId());
            }
        }

        //Update the list
        _appointmentTypes.add(newAppointmentType);
    }

    /**
     * It deletes an {@code AppointmentType}
     * @param scanner
     * @param _appointmentTypes
     */
    public void DeleteAppointmentType(Scanner scanner,
                                             HashSet<AppointmentType> _appointmentTypes) {
        //variables
        StringBuilder sb = new StringBuilder();
        String title = "-------------------\nDelete An Appointment Type: \n";
        String instructionMessage = "Select a type to delete: ";

        //List appointments
        List<AppointmentType> sortedAppointments = _appointmentTypes.stream()
                .sorted(Comparator.comparing(AppointmentType::getName))
                .toList();
        int typesSize = sortedAppointments.size();
        sb.append(title);
        for (int i = 0; i < typesSize; i++) {
            AppointmentType appointmentType = sortedAppointments.get(i);
            sb.append(String.format("%s. %s\n", i+1, appointmentType.getName()));
        }
        sb.append(instructionMessage);
        System.out.println(sb);

        //Delete appointment type
        int appointmentTypeSelected = _validator.ValidateIntInput(scanner,1,typesSize);
        scanner.nextLine();
        AppointmentType typeSelected = sortedAppointments.get(appointmentTypeSelected-1);
        _appointmentTypes.remove(typeSelected);
    }

    /**
     * It creates a new {@code Appointment}
     * @param scanner
     * @param _appointmentTypes
     * @param _instructors
     * @param _appointments
     */
    public void CreateAppointment(Scanner scanner,
                                         HashSet<AppointmentType> _appointmentTypes,
                                         HashSet<Instructor> _instructors,
                                         HashSet<Appointment> _appointments){
        //variables
        StringBuilder sb = new StringBuilder();
        String title = "-------------------\nCreate New Appointment: \n";
        String instructionMessage = "Please enter the customer's name: ";

        //Customer name & Date
        sb.append(title).append(instructionMessage);
        System.out.println(sb);
        String customerName = _validator.ValidateTextInput(scanner,"Name can not be empty. Please enter a valid name: ");
        Date dateValue = _validator.ValidateDateInput(scanner);

        //Assign appointment type
        sb.setLength(0);
        sb.append("Select the appointment type :\n");
        List<AppointmentType> appointmentTypesSorted = _appointmentTypes.stream()
                .sorted(Comparator.comparing(AppointmentType::getName)).toList();
        for (int i = 0; i < appointmentTypesSorted.size(); i++) {
            AppointmentType appointmentType = appointmentTypesSorted.get(i);
            sb.append(String.format("%s. %s\n", i+1, appointmentType.getName()));
        }
        System.out.println(sb);

        int appointmentTypeIndex = _validator.ValidateIntInput(scanner,1,_appointmentTypes.size());
        scanner.nextLine();
        AppointmentType appointmentTypeSelected = appointmentTypesSorted.get(appointmentTypeIndex-1);

        //Assign instructor
        sb.setLength(0);
        sb.append("Select an instructor:\n");
        List<Instructor> instructorsSorted = _instructors.stream()
                .sorted(Comparator.comparing(Instructor::getName)).toList();
        for (int i = 0; i < instructorsSorted.size(); i++) {
            Instructor instructor = instructorsSorted.get(i);
            sb.append(String.format("%s. %s\n", i+1, instructor.getName()));
        }
        System.out.println(sb);

        int instructorIndex = _validator.ValidateIntInput(scanner,1,instructorsSorted.size());
        Instructor instructorSelected = instructorsSorted.get(instructorIndex-1);

        //Assigning appointment to the respective appointment type
        Appointment newAppointment = new Appointment();
        newAppointment.setCustomerName(customerName);
        newAppointment.setDate(dateValue);
        newAppointment.setInstructorId(instructorSelected.getId());
        newAppointment.setAppointmentTypeId(appointmentTypeSelected.getId());

        appointmentTypeSelected.addAppointmentId(newAppointment.getId());
        _appointments.add(newAppointment);
    }

    /**
     * It lists all existing {@code Instructors}
     * @param scanner
     * @param _instructors
     */
    public void ViewInstructors(Scanner scanner,
                                       HashSet<Instructor> _instructors){
        //variables
        StringBuilder sb = new StringBuilder();
        String title = "-------------------\nInstructors: \n";
        List<Instructor> instructorsSorted = _instructors.stream()
                .sorted(Comparator.comparing(Instructor::getName))
                .toList();
        sb.append(title);
        //list instructors
        for (int i = 0; i < instructorsSorted.size(); i++) {
            Instructor instructor = instructorsSorted.get(i);
            sb.append(String.format("%s. %s\n",i+1,instructor.getName()));
        }
        //print result
        System.out.println(sb);
    }

    /**
     * It creates a new {@code Instructor}
     * @param scanner
     * @param _instructors
     * @param _appointmentTypes
     */
    public void CreateInstructor(Scanner scanner,
                                        HashSet<Instructor> _instructors,
                                        HashSet<AppointmentType> _appointmentTypes){
        //variables
        StringBuilder sb = new StringBuilder();
        String title = "-------------------\nCreate Instructor: \n";
        String instructionMessage = "Please enter the instructor's name: ";
        sb.append(title).append(instructionMessage);

        //Input fields
        System.out.println(sb);
        String inputName = _validator.ValidateTextInput(scanner
                ,"Name can not be empty. Please enter a valid name: ");

        //New instance
        Instructor newInstructor = new Instructor();
        newInstructor.setName(inputName);
        _instructors.add(newInstructor);

        //Assign its area of expertise
        sb.setLength(0);
        sb.append("Select the area(s) of expertize (use commas to separate multiple choices):\n");
        List<AppointmentType> appointmentTypesSorted = _appointmentTypes.stream()
                .sorted(Comparator.comparing(AppointmentType::getName)).toList();
        for (int i = 0; i < appointmentTypesSorted.size(); i++) {
            AppointmentType appointmentType = appointmentTypesSorted.get(i);
            sb.append(String.format("%s. %s\n", i+1, appointmentType.getName()));
        }
        sb.append(String.format("%s. All of them",appointmentTypesSorted.size()+1));
        System.out.println(sb);

        List<Integer> selectedIndexes = _validator.ValidateMultipleIntInput(scanner,1,appointmentTypesSorted.size()+1);
        if(selectedIndexes.contains(appointmentTypesSorted.size()+1)){
            //Include all
            appointmentTypesSorted.forEach(appointmentType ->
                    appointmentType.addInstructorsId(newInstructor.getId()));
        }
        else {
            //Only include selected indexes
            for (int i = 0; i < appointmentTypesSorted.size(); i++) {
                if(!selectedIndexes.contains(i+1)) continue;
                AppointmentType appointmentType = appointmentTypesSorted.get(i);
                appointmentType.addInstructorsId(newInstructor.getId());
            }
        }

        //Confirmation Status
        System.out.println("Instructor was successfully created!");
    }
}
