package controller;

import java.util.Arrays;
import java.util.List;

public class HrCategory {

    public List<String> getMainCategory() {
        return List.of("조직/직무 관리", "인사행정", "근태 관리 *", "급여/정산", "사회보험",
                "평가 관리", "연말정산", "승진 관리", "핵심 인재 관리", "월별 종합 현황 보기");
    }

    public List<String> getAttendanceCategory() {
        return List.of("근태 입력 (option)", "근태 수정 *", "근태 삭제 *",
                "직원별 월별 근태 현황 보기 (option)", "부서별 월별 근태 현황 보기 *");
    }
}