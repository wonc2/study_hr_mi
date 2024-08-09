import service.HrService;
import dao.AttendanceDao;

public class Main {
    public static void main(String[] args) {
        HrService hrService = new HrService();
        hrService.runHrService();
    }
}