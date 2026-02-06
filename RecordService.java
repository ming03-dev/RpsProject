package RpsProject;

public interface RecordService {
	void showMyRecord(User currentUser); // 내 전적 보기
    void showRanking(boolean asc); // 전체 랭킹 보기(파라미터는 랭킹 방향(오름/내림))
    void updateRecord(User user, int win, int lose, int draw);

}
