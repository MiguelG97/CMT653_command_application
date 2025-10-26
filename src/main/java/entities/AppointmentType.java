package entities;

import java.util.HashSet;
import java.util.UUID;


public class AppointmentType extends BaseClass {
    //final since we do not plan to reassign the hashset, but just add or remove values from the set.
    //increase performance.
    private final HashSet<UUID> appointmentIds= new HashSet<UUID>();
    private final HashSet<UUID> instructorsIds= new HashSet<UUID>();

    public HashSet<UUID> getInstructorsIds() {
        return instructorsIds;
    }
    public void addInstructorsId(UUID instructorsId) {
        this.instructorsIds.add(instructorsId);
    }
    public void removeInstructorsId(UUID instructorsId) {
        this.instructorsIds.remove(instructorsId);
    }

    public HashSet<UUID> getAppointmentIds() {
        return appointmentIds;
    }
    public void addAppointmentId(UUID appointmentId) {
        this.appointmentIds.add(appointmentId);
    }
    public void removeAppointmentId(UUID appointmentId) {
        this.appointmentIds.remove(appointmentId);
    }
    public Integer getAppointmentsLength() {
        return appointmentIds.size();
    }
}
