package RpsProject;

import java.util.List;
import java.util.stream.Collectors;

public class RankingCalculator {
	// asc = true → 오름차순
	// asc = false → 내림차순
	public static List<User> sortByWinRate(List<User> users, boolean asc) {

		return users.stream()
				// 전적 없는 유저 제외
				.filter(u -> (u.getWin() + u.getLose() + u.getDraw()) > 0)

				.sorted((u1, u2) -> {
					double r1 = winRate(u1);
					double r2 = winRate(u2);

					// 1) 승률
					int cmp = Double.compare(r2, r1); // 기본: 내림차순
					if (cmp != 0)
						return asc ? -cmp : cmp;

					// 2) 승 수 (승률 같으면 승 많은 사람이 위)
					cmp = Integer.compare(u2.getWin(), u1.getWin());
					if (cmp != 0)
						return asc ? -cmp : cmp;

					// 3) 총 판 수 (선택: 승/패/무 합)
					int t1 = u1.getWin() + u1.getLose() + u1.getDraw();
					int t2 = u2.getWin() + u2.getLose() + u2.getDraw();
					cmp = Integer.compare(t2, t1); // 기본: 내림차순
					if (cmp != 0)
						return asc ? -cmp : cmp;

					// 4) 완전 동률이면 이메일로 고정 정렬(안정성)
					return u1.getEmail().compareTo(u2.getEmail());
				}).collect(Collectors.toList());
	}

	public static double winRate(User u) {
		int total = u.getWin() + u.getLose() + u.getDraw();
		if (total == 0)
			return 0.0;
		return (double) u.getWin() / total;
	}
}