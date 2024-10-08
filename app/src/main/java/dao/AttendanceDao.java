package dao;

import connect.JDBCConnectionPool;
import dto.TimeAttendance;

import java.sql.*;
import java.util.*;

public class AttendanceDao extends JDBCConnectionPool {
    // 근태 수정
    public void updateAttendance(TimeAttendance attendance) {
        String sql = "UPDATE TimeAttendance SET Status=? WHERE TA_PK=?";
        System.out.println(attendance.getStatus());
        System.out.println(attendance.getWorkday());
        System.out.println(attendance.getPk());
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance.getStatus());
            pstmt.setString(2, attendance.getPk());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 근태 삭제
    public void deleteAttenddance(TimeAttendance attendance) {
        String sql = "DELETE FROM TimeAttendance WHERE TA_PK = ?";
        System.out.println(attendance.getWorkday());
        System.out.println(attendance.getPk());
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance.getPk());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 직원 명단 출력
    public void readEmployees() {
        String sql = "SELECT * FROM Employees e JOIN Department d ON e.dep_FK = d.dep_PK";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String emppk = rs.getString("emp_PK");
                String dname = rs.getString("d.Name");
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
                "WHERE t.Emp_FK = ? " +
                "ORDER BY t.Workday, t.Status ASC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                // 결과가 없으면 처리하지 않고 종료
                if (!rs.next()) {
                    System.out.println("No records found.");
                    return;
                }
                String name = rs.getString("Name");
                System.out.println("\n" + name + "의 2024년 08월 근태 기록");
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

    // 부서별 직원 출근율 리스트로 조회
    public List<Map<String, String>> monthlyAttendanceRateEmpList(String yearMonth) {
        List<Map<String, String>> employeeList = new ArrayList<>();
        String sql = "SELECT e.Emp_PK, e.Name, d.Name, " +
                "Round((COUNT(CASE WHEN t.Status = '출근' THEN 1 END)/24)*100, 1) AS 'attendanceRate', " +
                "COUNT(CASE WHEN t.Status = '출근' THEN 1 END) AS 'attendDays', " +
                "COUNT(CASE WHEN t.Status = '결근' THEN 1 END) AS 'absenceDays', " +
                "COUNT(CASE WHEN t.Status = '휴가' THEN 1 END) AS 'vacationDays' " +
                "FROM (select * from TimeAttendance where workday like ?) t " +
                "JOIN Employees e ON e.Emp_PK = t.Emp_FK " +
                "JOIN Department d ON e.dep_FK = d.dep_PK " +
                "GROUP BY e.Emp_PK, e.Name, d.Name";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + yearMonth + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> employeeData = new LinkedHashMap<>();
                    employeeData.put("직원 ID", rs.getString("Emp_PK"));
                    employeeData.put("이름", rs.getString("e.Name"));
                    employeeData.put("부서", rs.getString("d.Name"));
                    employeeData.put("출근율", rs.getString("attendanceRate") + "%");
                    employeeData.put("총 출근일", rs.getString("attendDays"));
                    employeeData.put("총 결근일", rs.getString("absenceDays"));
                    employeeData.put("총 휴가일", rs.getString("vacationDays"));
                    employeeList.add(employeeData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    // 근태 번호 최댓값 가져옴 - 입력시 +1 해서 PK로 사용
    public String getTaPk() {
        String taPk = "";
        String sql = "SELECT TA_PK FROM TimeAttendance ORDER BY length(TA_PK) desc ,TA_PK desc limit 1 ";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                taPk = rs.getString("TA_PK");
                String[] empPkSplit = taPk.split("_");
                taPk = empPkSplit[0] + "_" + (Integer.parseInt(empPkSplit[1]) + 1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return taPk;
    }

    // 근태 입력
    public void insertAttendance(TimeAttendance attendance, String empPk) {
        String sql = "INSERT INTO TimeAttendance (TA_PK, Emp_FK, Dep_FK, Workday, Status) VALUES (?, ?, ?, ?, ?) ";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String taPk = getTaPk();
            String depFk = getDepFk(empPk);
            String workday = attendance.getWorkday();
            String status = attendance.getStatus();

            pstmt.setString(1, taPk);
            pstmt.setString(2, empPk);
            pstmt.setString(3, depFk);
            pstmt.setString(4, workday);
            pstmt.setString(5, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 직원 정보 조회
    public String getDepFk(String emppk) {
        String name = "";
        String sql = "SELECT Dep_FK from Employees where Emp_PK = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emppk);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    name = rs.getString("Dep_FK");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return name;
    }
}
