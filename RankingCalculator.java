package RpsProject;

import java.util.List;
import java.util.stream.Collectors;

public class RankingCalculator {
	// 랭킹 정렬된 유저 리스트 반환
    // asc = true  → 오름차순
    // asc = false → 내림차순
    public static List<User> sortByWinRate(List<User> users, boolean asc) {

        return users.stream()
            // 전적 없는 유저 제외
            .filter(u -> (u.getWin() + u.getLose() + u.getDraw()) > 0)

            .sorted((u1, u2) -> {
                double r1 = winRate(u1);
                double r2 = winRate(u2);
                return asc
                        ? Double.compare(r1, r2)
                        : Double.compare(r2, r1);
            })
            .collect(Collectors.toList());
    }

    // 승률 계산
    public static double winRate(User u) {
        int total = u.getWin() + u.getLose() + u.getDraw();
        if (total == 0) return 0.0;
        return (double) u.getWin() / total;
    }
}
