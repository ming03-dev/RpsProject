package RpsProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordServiceImpl implements RecordService {
	private final RecordRepository recordRepo;

	public RecordServiceImpl(RecordRepository recordRepo) {
		this.recordRepo = recordRepo;
	}

	@Override
	public void updateRecord(User currentUser, int win, int lose, int draw) {
		if (currentUser == null)
			return;

		User saved = recordRepo.findByEmail(currentUser.getEmail());
		if (saved == null)
			return;

		saved.setWin(saved.getWin() + win);
		saved.setLose(saved.getLose() + lose);
		saved.setDraw(saved.getDraw() + draw);

		recordRepo.saveAllRecords(); // 파일 반영
	}

	@Override
	public void showMyRecord(User currentUser) {
		if (currentUser == null)
			return;
		User saved = recordRepo.findByEmail(currentUser.getEmail());
		if (saved == null) {
			System.out.println("전적이 없습니다.");
			return;
		}
		System.out.println("승: " + saved.getWin() + " 무: " + saved.getDraw() + " 패: " + saved.getLose());
	}

	@Override
	public void showRanking(boolean asc) {
		Map<String, User> usersMap = recordRepo.getUsers();

		// 가입된 유저 자체가 없는 경우
		if (usersMap.isEmpty()) {
			System.out.println("아직 등록된 유저가 없습니다.");
			return;
		}

		// Map → List
		List<User> users = new ArrayList<>(usersMap.values());

		// 전적 있는 유저가 하나도 없는 경우
		boolean hasRecord = users.stream().anyMatch(u -> (u.getWin() + u.getLose() + u.getDraw()) > 0);

		if (!hasRecord) {
			System.out.println("아직 게임을 진행한 유저가 없습니다.");
			return;
		}

		// ⭐ 승률 기준 정렬
		List<User> ranked = RankingCalculator.sortByWinRate(users, asc);

		// 출력
		System.out.println("========== 전체 랭킹 (승률 기준) ==========");

		int rank = 1;
		for (User u : ranked) {
			double rate = RankingCalculator.winRate(u) * 100;

			System.out.printf("%d위 | %s | 승:%d 패:%d 무:%d | 승률: %.2f%%%n", rank++, u.getEmail(), u.getWin(), u.getLose(),
					u.getDraw(), rate);
		}

		System.out.println("=========================================");
	}
}
