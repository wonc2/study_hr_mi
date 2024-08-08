package controller;

import java.util.Scanner;

public class HrService {

    public HrService() {
    }

    public void runHrService() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int choice = displayMain(sc);
            if (choice == 0) break;
            else if (choice == 3) {
                while (true) {
                    choice = displayAttendance(sc);
                    if (choice == 0) break;
                    else if (choice == 2) {
                        displayAttendanceUpdate(sc);
                    } else if (choice == 3) {
                        displayAttendanceDelete(sc);
                    }
                }
            }
        }
        // Map<"사전이름 ? (Main, Attendance 등)", List<Integer, <그안의 옵션 목록>>

    }

    public int displayMain(Scanner sc) {
        System.out.println("\n==== 인적 자원 관리 시스템 ====\n");
        System.out.println("1. 조직/직무 관리\n" + "2. 인사행정\n" + "3. 근태 관리 *\n" + "4. 급여/정산\n" + "5. 사회보험\n" +
                "6. 평가 관리\n" + "7. 연말정산\n" + "8. 승진 관리\n" + "9. 핵심 인재 관리\n" + "10. 월별 종합 현황 보기\n" + "0. 종료 *\n");

        System.out.print("선택하세요 : ");
        return sc.nextInt();
    }

    public int displayAttendance(Scanner sc) {
        System.out.println("\n==== 근태 관리 ====\n");
        System.out.println("1. 근태 입력 (option)\n" + "2. 근태 수정 *\n" + "3. 근태 삭제 *\n" +
                "4. 직원별 월별 근태 현황 보기 (option)\n" + "5. 부서별 월별 근태 현황 보기 *\n" + "0. 메인 메뉴로 돌아가기 *\n");

        System.out.print("선택하세요 : ");
        return sc.nextInt();
    }

//    public String displayAttendance(){
//
//    }

    public void displayAttendanceUpdate(Scanner sc) {
        System.out.println("\n==== 근태 수정 ====\n");
        // 직원 ID 입력 ->
        // 기존 날짜 출력 -> 수정할 날짜 입력
        // 기존 근무 상태 출력 -> 수정할 근무 상태 입력
        System.out.println("직원 ID 입력하세여 : ");
        String id = sc.nextLine();
        String date;
        String status;
    }

    public void displayAttendanceDelete(Scanner sc) {
        System.out.println("\n==== 근태 삭제 ====\n");
        // 직원 ID 입력 ->
        // 기존 날짜 출력 -> 삭제할 날짜 입력
        // 삭제 확인? 출력 -> 삭제 확인 yes : no 입력
        System.out.println("(삭제하려면 yes 아니면 no 입력)\n삭제 확인 : ");

        // return sc.nextLine().equals("yes") ? true : false;
        // true -> DB 에서 해당 근태 삭제
    }

    public int displayAttendanceDepartmentMonthly(Scanner sc) {
        System.out.println("\n==== 근태 관리 ====\n");
        // 리스트로 쫙
        // 직원 아이디 이름:
        // 출근율 = 24 - ~
        // 출근일 = 24-휴가 -결근, 결근일, 휴가

        System.out.print("선택하세요 : ");
        return sc.nextInt();
    }
}