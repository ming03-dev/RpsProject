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

		int total = saved.getWin() + saved.getLose() + saved.getDraw();
		double rate = (total == 0) ? 0.0 : (double) saved.getWin() / total;

		System.out.printf("승:%d 무:%d 패:%d | 승률: %.2f%%%n", saved.getWin(), saved.getDraw(), saved.getLose(),
				rate * 100);
	}

	  @Override
	    public void showRanking(boolean asc) {
	        Map<String, User> map = recordRepo.getUsers();

	        // 1) 유저 자체가 없음
	        if (map == null || map.isEmpty()) {
	            System.out.println("아직 등록된 유저가 없습니다.");
	            return;
	        }

	        // 2) List로 변환
	        List<User> all = new ArrayList<>(map.values());

	        // 3) 전적 있는 유저만 + 승률 기준 정렬(동률이면 승 수, 그 다음 총판수)
	        List<User> ranked = RankingCalculator.sortByWinRate(all, asc);

	        // 4) 전적 있는 사람이 0명
	        if (ranked.isEmpty()) {
	            System.out.println("아직 전적이 있는 유저가 없습니다. (게임을 한 유저가 없음)");
	            return;
	        }

	        System.out.println("========== 전체 랭킹 (승률 기준) ==========");

	        int rank = 1;
	        for (User u : ranked) {
	            double rate = RankingCalculator.winRate(u) * 100.0;

	            System.out.printf("%d위 | %s | 승:%d 패:%d 무:%d | 승률: %.2f%%%n",
	                    rank++, u.getEmail(), u.getWin(), u.getLose(), u.getDraw(), rate);
	        }

	        System.out.println("=======================================");
	    }
	}