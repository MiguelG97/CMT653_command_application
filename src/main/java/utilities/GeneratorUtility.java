package utilities;

import entities.Appointment;
import entities.AppointmentType;
import entities.Instructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;

public class GeneratorUtility
{
    /**
     * This method populates the {@code _instructors} variable with {@code 2} initial instructors
     */
    public static  void GenerateInstructors(HashSet<Instructor>  _instructors){
        Instructor instructor1 = new Instructor();
        instructor1.setName("Sam Altman");
        _instructors.add(instructor1);

        Instructor instructor2 = new Instructor();
        instructor2.setName("Anne Hathaway");
        _instructors.add(instructor2);
    }
    /**
     * This method populates the {@code _appointmentTypes} variable with {@code 2} initial appointment types
     * and 2 booked appointments each.
     */
    public static void GenerateInitialAppointmentTypes(HashSet<AppointmentType> _appointmentTypes,
                                                       HashSet<Appointment> _appointments,
                                                        HashSet<Instructor>  _instructors){
        //To not throw error when querying an empty hashset
        if(_instructors.isEmpty()) return ;

        //Appointment type 1
        AppointmentType appointmentType1  = new AppointmentType();
        appointmentType1.setName("Personal Training Sessions");
        _instructors.forEach(i -> appointmentType1.addInstructorsId(i.getId()));

        //Assigning Appointments
        Appointment appointment1 = new Appointment();
        appointment1.setCustomerName("Satya Nadella");
        appointment1.setAppointmentTypeId(appointmentType1.getId());
        appointment1.setInstructorId(_instructors.stream().toList().getFirst().getId());
        LocalDateTime ldt1 = LocalDateTime.of(2025, 12, 2, 12, 0, 0);
        Date date1 = Date.from(ldt1.atZone(ZoneId.of("UTC")).toInstant());
        appointment1.setDate(date1);

        appointmentType1.addAppointmentId(appointment1.getId());


        Appointment appointment2 = new Appointment();
        appointment2.setCustomerName("Tim Cook");
        appointment2.setAppointmentTypeId(appointmentType1.getId());
        appointment2.setInstructorId(_instructors.stream().toList().getLast().getId());
        LocalDateTime ldt2 = LocalDateTime.of(2025, 12, 3, 12, 0, 0);
        Date date2 = Date.from(ldt2.atZone(ZoneId.of("UTC")).toInstant());
        appointment2.setDate(date2);

        appointmentType1.addAppointmentId(appointment2.getId());

        //Appointment type 2
        AppointmentType appointmentType2  = new AppointmentType();
        appointmentType2.setName("Yoga Sessions");
        _instructors.forEach(i -> appointmentType2.addInstructorsId(i.getId()));

        //Assigning Appointments
        Appointment appointment3 = new Appointment();
        appointment3.setCustomerName("Satoshi Nakamoto");
        appointment3.setAppointmentTypeId(appointmentType2.getId());
        appointment3.setInstructorId(_instructors.stream().toList().getFirst().getId());
        LocalDateTime ldt3 = LocalDateTime.of(2025, 12, 4, 12, 0, 0);
        Date date3 = Date.from(ldt3.atZone(ZoneId.of("UTC")).toInstant());
        appointment3.setDate(date3);

        appointmentType2.addAppointmentId(appointment3.getId());


        Appointment appointment4 = new Appointment();
        appointment4.setCustomerName("Michael Saylor");
        appointment4.setAppointmentTypeId(appointmentType2.getId());
        appointment4.setInstructorId(_instructors.stream().toList().getLast().getId());
        LocalDateTime ldt4 = LocalDateTime.of(2025, 12, 5, 12, 0, 0);
        Date date4 = Date.from(ldt4.atZone(ZoneId.of("UTC")).toInstant());
        appointment4.setDate(date4);

        appointmentType2.addAppointmentId(appointment4.getId());


        //add objects to the hash sets
        _appointmentTypes.add(appointmentType1);
        _appointmentTypes.add(appointmentType2);

        _appointments.add(appointment1);
        _appointments.add(appointment2);
        _appointments.add(appointment3);
        _appointments.add(appointment4);
    }
}
