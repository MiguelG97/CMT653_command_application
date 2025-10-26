package entities;

import java.util.HashSet;
import java.util.UUID;

public class Instructor extends BaseClass {
    private final HashSet<UUID> appointmentTypeIds = new HashSet<UUID>();

    public HashSet<UUID> getAppointmentTypeIds() {
        return appointmentTypeIds;
    }
    public void addAppointmentTypeId(UUID appointmentTypeId) {
        this.appointmentTypeIds.add(appointmentTypeId);
    }
    public void removeAppointmentTypeId(UUID appointmentTypeId) {
        this.appointmentTypeIds.remove(appointmentTypeId);
    }
}
