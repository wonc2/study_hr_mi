package controller;

import dao.AttendanceDao;
import dto.TimeAttendance;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Scanner;

public class HrService {
    private AttendanceDao attendanceDao;
    HrCategory hrCategory;
    Scanner sc;

    public HrService(AttendanceDao attendanceDao) {
        hrCategory = new HrCategory();
        sc = new Scanner(System.in);
        this.attendanceDao = attendanceDao;
    }



    public void runHrService() {

        while (true) {
            int choice = displayMain();
            if (choice == 0) break;
            else if (choice == 3) {
                while (true) {
                    choice = displayAttendance();
                    if (choice == 0) break;
                    else if (choice == 2) {
                        displayAttendanceUpdate();
                    } else if (choice == 3) {
                        displayAttendanceDelete();
                    } else if (choice == 5) {
                        displayAttendanceDepartmentMonthly();
                    }
                }
            }
        }
    }

    // 공통으로 사용하는 카테고리 출력 로직
    public int displayMenu(String title, List<String> categoryList, String exitOption) {
        StringBuilder stringBuilder = new StringBuilder("\n==== " + title + " ====\n");
        for (int i = 0; i < categoryList.size(); i++) {
            stringBuilder.append((i + 1)).append(". ").append(categoryList.get(i)).append("\n");
        }
        stringBuilder.append("0. ").append(exitOption).append("\n").append("선택하세요 : ");
        System.out.print(stringBuilder);
        return sc.nextInt();
    }

    // 메인 카테고리 출력 메서드
    public int displayMain() {
        return displayMenu("인적 자원 관리 시스템", hrCategory.getMainCategory(), "종료");
    }

    // 근태 관리 카테고리 출력 메서드
    public int displayAttendance() {
        return displayMenu("근태 관리", hrCategory.getAttendanceCategory(), "메인 메뉴로 돌아가기 *");
    }


    public void displayAttendanceUpdate() {
        sc.nextLine(); // 에러 때문에 추가했습니다
        // 직원 ID 입력 ->
        // 기존 날짜 출력 -> 수정할 날짜 입력
        // 기존 근무 상태 출력 -> 수정할 근무 상태 입력
        // 직원 ID 입력 ->
        System.out.println("\n==== 근태 수정 ====\n");
        attendanceDao.readEmployees();
        System.out.print("직원 ID 입력하세여 : ");
        String empPk = sc.nextLine();
        if (empPk.equals("0")) return;
        attendanceDao.readEmployeeAttendance(empPk);
        System.out.print(" - 수정할 날짜의 근태 코드를 입력하세요: ");
        String ta_pk = sc.nextLine();
        if (ta_pk.equals("0")) return;
        System.out.print("수정할 근무 상태를 입력하세요: ");
        String status = sc.nextLine();
        if (status.equals("0")) return;

        TimeAttendance attendance = new TimeAttendance(ta_pk, status);
        attendanceDao.updateAttendance(attendance);
        System.out.println("업데이트 완료");
    }

    public void displayAttendanceDelete() {
        sc.nextLine();
        // 직원 ID 입력 ->
        // 기존 날짜 출력 -> 삭제할 날짜 입력
        System.out.println("\n==== 근태 삭제 ====\n");
        attendanceDao.readEmployees();
        System.out.print("직원 ID 입력하세여 : ");
        String empPk = sc.nextLine();
        if (empPk.equals("0")) return;
        attendanceDao.readEmployeeAttendance(empPk);
        System.out.print(" - 삭제할 날짜의 근태 코드를 입력하세요: ");
        String ta_pk = sc.nextLine();
        if (ta_pk.equals("0")) return;

        TimeAttendance attendance = new TimeAttendance(ta_pk);
        attendanceDao.deleteAttenddance(attendance);
        System.out.println("삭제 완료");
    }

    public int displayAttendanceDepartmentMonthly() {
        System.out.println("\n==== 근태 관리 ====\n");
        // 리스트로 쫙
        // 직원 아이디 이름:
        // 출근율 = 24 - ~
        // 출근일 = 24-휴가 -결근, 결근일, 휴가

        System.out.print("선택하세요 : ");
        return sc.nextInt();
    }
}