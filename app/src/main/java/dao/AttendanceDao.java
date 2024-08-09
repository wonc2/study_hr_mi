package dao;

import connect.JDBCConnectionPool;
import dto.TimeAttendance;

import java.sql.*;

public class AttendanceDao extends JDBCConnectionPool {
    // 근태 수정
    public void updateAttendance(TimeAttendance attendance) {
        String sql = "UPDATE TimeAttendance SET Status = ? WHERE Workday = ? and Emp_FK = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance.getStatus());
            pstmt.setString(2, attendance.getWorkday());
            pstmt.setString(3, attendance.getEmp_FK());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 근태 삭제
    public void deleteAttenddance(String workday) {}

    // 직원 명단 출력
    public void readEmployees() {
        String sql = "SELECT * FROM Employees e JOIN Department d ON e.dep_FK = d.dep_PK";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()){
                String emppk = rs.getString("emp_PK");
                String dname= rs.getString("d.Name");
                String name = rs.getString("Name");

                System.out.println("[사원 번호 : " + emppk + "] [이름 :  " + name + "] [부서명 : " + dname + "]");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    // 직원 근태기록 조회
    public void readEmployeeAttendance(String id) {
        String sql = "SELECT * FROM TimeAttendance t " +
                "INNER JOIN Employees e ON t.Emp_FK = e.Emp_PK " +
                "WHERE t.Emp_FK = ? " +  // 직원 번호를 ?로 바꿈
                "ORDER BY t.Workday, t.Status ASC";  // t.Workday와 t.Status를 사용

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  // PreparedStatement 사용

            pstmt.setString(1, id);  // 첫 번째 ?에 id 값 설정

            try (ResultSet rs = pstmt.executeQuery()) {

                // 결과가 없으면 처리하지 않고 종료
                if (!rs.next()) {
                    System.out.println("No records found.");
                    return;
                }

                // 첫 번째 레코드에서 이름 가져오기
                String name = rs.getString("Name");
                System.out.println("\n" + name + "의 2024년 08월 근태 기록");

                // 첫 번째 레코드의 나머지 데이터 처리
                do {
                    String taPk = rs.getString("TA_PK");
                    String workday = rs.getString("Workday");
                    String status = rs.getString("Status");
                    System.out.println("[근태 코드 : " + taPk + "] " + workday + " : " + status);
                } while (rs.next());
                System.out.print(name + "의 ");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
