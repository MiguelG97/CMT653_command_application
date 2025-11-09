package interfaces;

import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;

import java.util.HashSet;

public interface IGenerator {
    void GenerateInstructors(HashSet<Instructor> _instructors);
    void GenerateInitialAppointmentTypes(HashSet<AppointmentType> _appointmentTypes,
                                         HashSet<Appointment> _appointments,
                                         HashSet<Instructor>  _instructors);
}
