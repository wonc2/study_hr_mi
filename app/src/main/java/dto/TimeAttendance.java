package dto;

public class TimeAttendance {
    private String Emp_FK;
    private String Workday;
    private String Status;

    public TimeAttendance(String empfk, String workday, String status) {
        this.Emp_FK = empfk;
        this.Workday = workday;
        this.Status = status;
    }

    public String getEmp_FK() {
        return Emp_FK;
    }

    public String getWorkday() {
        return Workday;
    }

    public String getStatus() {
        return Status;
    }

    // 업데이트용
    public void setStatus(String status) {
        this.Status = status;
    }
}
