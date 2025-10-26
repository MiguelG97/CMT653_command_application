package entities;

import java.util.Date;
import java.util.UUID;

public class Appointment extends BaseClass {
    private String customerName;
    private Date date;
    private UUID appointmentTypeId;
    private UUID instructorId;

    public UUID getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
    }
    public UUID getAppointmentTypeId() {
        return appointmentTypeId;
    }
    public void setAppointmentTypeId(UUID appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
