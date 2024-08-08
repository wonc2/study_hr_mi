package dto;

public class TimeAttendance {
    private String Workday;
    private String Status;

    public TimeAttendance(String workday, String status) {
        this.Workday = workday;
        this.Status = status;
    }

    public String getWorkday() {
        return Workday;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
