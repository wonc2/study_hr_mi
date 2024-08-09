import connect.JDBCConnectionPool;
import controller.HrService;
import dao.AttendanceDao;

public class Main {
    public static void main(String[] args) {
        AttendanceDao attendanceDao = new AttendanceDao();
        HrService hrService = new HrService(attendanceDao);
        hrService.runHrService();
    }
    // hi
}