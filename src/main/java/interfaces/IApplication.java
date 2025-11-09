package interfaces;

import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;

import java.util.HashSet;
import java.util.Scanner;

public interface IApplication {
    void ListAndViewAllAppointmentTypes(Scanner scanner,
                                        HashSet<AppointmentType> _appointmentTypes,
                                        HashSet<Appointment> _appointments,
                                        HashSet<Instructor> _instructors);
    void CreateAppointmentType(Scanner scanner,
                                      HashSet<AppointmentType> _appointmentTypes,
                                      HashSet<Instructor> _instructors);
    void DeleteAppointmentType(Scanner scanner,
                               HashSet<AppointmentType> _appointmentTypes);
    void CreateAppointment(Scanner scanner,
                                  HashSet<AppointmentType> _appointmentTypes,
                                  HashSet<Instructor> _instructors,
                                  HashSet<Appointment> _appointments);
    void ViewInstructors(Scanner scanner,
                         HashSet<Instructor> _instructors);
    void CreateInstructor(Scanner scanner,
                                 HashSet<Instructor> _instructors,
                                 HashSet<AppointmentType> _appointmentTypes);
}
