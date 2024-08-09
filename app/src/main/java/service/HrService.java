package service;

import dao.AttendanceDao;
import dto.TimeAttendance;

import java.util.*;

public class HrService {
    private AttendanceDao attendanceDao;
    private HrCategory hrCategory;
    private Scanner sc;

    public HrService() {
        hrCategory = new HrCategory();
        sc = new Scanner(System.in);
        this.attendanceDao = new AttendanceDao();
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

    // Year 및 Month 리턴
    public String displayYearMonth(String title, List<String> categoryList, String exitOption) {
        StringBuilder stringBuilder = new StringBuilder("\n==== " + title + " ====\n");
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).equals("08월") || categoryList.get(i).equals("2024")) {
                stringBuilder.append((i + 1)).append(". ").append(categoryList.get(i)).append(" *\n");
            } else stringBuilder.append((i + 1)).append(". ").append(categoryList.get(i)).append("\n");
        }
        stringBuilder.append("0. ").append(exitOption).append("\n").append("선택하세요 : ");
        System.out.print(stringBuilder);
        return categoryList.get(sc.nextInt() - 1);
    }

    // 메인 카테고리 출력 메서드
    public int displayMain() {
        return displayMenu("인적 자원 관리 시스템", hrCategory.getMainCategory(), "종료");
    }

    // 근태 관리 카테고리 출력 메서드
    public int displayAttendance() {
        return displayMenu("근태 관리", hrCategory.getAttendanceCategory(), "메인 메뉴로 돌아가기 *");
    }

    public String displayYear() {
        return displayYearMonth("연도 선택", hrCategory.getYearCategory(), "메인 메뉴로 돌아가기 *");
    }

    public String displayMonth() {
        return displayYearMonth("월 선택", hrCategory.getMonthCategory(), "메인 메뉴로 돌아가기 *");
    }

    public void displayAttendanceUpdate() {
        sc.nextLine(); // 입력 안받고 넘어가지는? 에러 때문에 추가했습니다

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

    public void displayAttendanceDepartmentMonthly() {
        System.out.println("\n==== 부서별 월별 근태 현황 ====\n");

        String yearMonth = "";
        yearMonth += displayYear()+"-";
        yearMonth += displayMonth();
        yearMonth = yearMonth.substring(0, yearMonth.length() - 1);

        List<Map<String, String>> employeeList = attendanceDao.monthlyAttendanceRateEmpList(yearMonth);
        if (employeeList.size() == 0) {
            System.out.println(yearMonth + "의 근태 기록이 없습니다!");
            System.out.print("\n이전으로 돌아가려면 아무 숫자 입력 : ");
            sc.nextInt();
            return;
        }

        Map<String, List<Map<String, String>>> departmentMap = new LinkedHashMap<>();

        // 직원 목록을 부서별로 그룹화
        for (Map<String, String> employee : employeeList) {
            String department = employee.get("부서");

            // 부서가 departmentMap에 없으면 새로운 리스트를 생성
            if (!departmentMap.containsKey(department)) {
                departmentMap.put(department, new ArrayList<>());
            }

            // 현재 부서의 직원 리스트에 직원 정보를 추가
            departmentMap.get(department).add(employee);
        }

        // 부서별로 출력
        for (Map.Entry<String, List<Map<String, String>>> entry : departmentMap.entrySet()) {
            String department = entry.getKey();
            List<Map<String, String>> employees = entry.getValue();

            System.out.println("부서: " + department);
            System.out.print("2024년 8월 근태 현황:\n");

            for (Map<String, String> employee : employees) {
                System.out.println(employee);
            }
            System.out.println(); // 한줄띄우기
        }

        System.out.print("\n이전으로 돌아가려면 아무 숫자 입력 : ");
        sc.nextInt();
    }
}