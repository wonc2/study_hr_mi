package dao;

import connect.JDBCConnectionPool;
import dto.TimeAttendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AttendanceDao extends JDBCConnectionPool {

    public AttendanceDao(String url, String user, String password) {
        super(url, user, password);
    }



    // 근태 수정
    public void updateAttendance(TimeAttendance attendance, String Emp_FK) {
        String sql = "UPDATE TimeAttendance SET Status = ? WHERE Workday = ? and Emp_FK = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance.getStatus());
            pstmt.setString(2, attendance.getWorkday());
            pstmt.setString(3, Emp_FK);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 근태 삭제
    public void deleteAttenddance(String workday) {}
}
