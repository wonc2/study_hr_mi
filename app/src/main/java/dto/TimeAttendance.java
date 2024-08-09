package dto;

public class TimeAttendance {
    private String tapk;
    private String Workday;
    private String Status;

    public TimeAttendance(String tapk, String workday, String status) {
        this.tapk = tapk;
        this.Workday = workday;
        this.Status = status;
    }
    public TimeAttendance(String tapk,  String status) {
        this.tapk = tapk;
        this.Status = status;
    }

    public String getPk() {return tapk;}

    public String getWorkday() {
        return Workday;
    }

    public String getStatus() {
        return Status;
    }

//    // 업데이트용
//    public void setStatus(String status) {
//        this.Status = status;
//    }
}
