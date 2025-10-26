package domain;

import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;

import java.text.SimpleDateFormat;
import java.util.*;

public class Application {
    /**
     *
     * @param scanner
     * @param _appointmentTypes
     * @param _appointments
     */
    public static void ListAndViewAllAppointmentTypes(Scanner scanner,
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
            int appointmentTypeSelected = scanner.nextInt();
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
    private static void ViewAppointmentsByType(UUID appointmentTypeId,
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
     * It creates new {@code AppointmentType} and prompts user to select instructors
     * @param scanner
     * @param _appointmentTypes
     */
    public static void CreateAppointmentType(Scanner scanner,
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
        newAppointmentType.setName(scanner.nextLine());

        //assign instructors
        List<Instructor> instructorsSorted = _instructors.stream().sorted(Comparator.comparing(Instructor::getName)).toList();
        sb.append("Select instructors for this appointment type (use commas to separate multiple choices):\n");
        for (int i = 0; i < instructorsSorted.size(); i++) {
            Instructor instructor = instructorsSorted.get(i);
            sb.append(String.format("%s. %s\n", i+1, instructor.getName()));
        }
        sb.append(String.format("%s. Include All\n", instructorsSorted.size()+1));
        System.out.println(sb);

        String indexesSelectedInput = scanner.nextLine();
        List<Integer> selectedIndexes= Arrays.stream(indexesSelectedInput.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
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
    public static void DeleteAppointmentType(Scanner scanner,
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
        int appointmentTypeSelected = scanner.nextInt();
        scanner.nextLine();
        AppointmentType typeSelected = sortedAppointments.get(appointmentTypeSelected-1);
        _appointmentTypes.remove(typeSelected);
    }
}
