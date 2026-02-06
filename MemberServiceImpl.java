package RpsProject;

public class MemberServiceImpl implements MemberService {

	private final RecordRepository recordRepo;
	private String lastLoginBeforeUpdate = "없음";

	public MemberServiceImpl() {
		this.recordRepo = new RecordRepository();
		recordRepo.membersLoad();
		recordRepo.recordsLoad();
	}
	
	public MemberServiceImpl(RecordRepository recordRepo) {
		 this.recordRepo = recordRepo;
	}


	public String getLastLoginBeforeUpdate() {
		return lastLoginBeforeUpdate;
	}

	@Override
	public boolean signUp(User newUser) {
		// 현재 시간 가져오기

		// 가입된 이메일인지 체크
		// T면 "이미 가입한 회원" 출력 후 false 리턴, F면 파일에 회원 정보 저장 요청 후 true를 반환
		if (recordRepo.isregisteredEmail(newUser)) {
			System.out.println("이미 가입된 이메일입니다.");
			return false;
		} else {
			// records.txt에 입력한 이메일, 비번, 현재 시간 저장
			recordRepo.memberSave(newUser);
			recordRepo.recordSave(newUser);
			System.out.println("회원 가입 되었습니다^-^");
			return true;
		}

	}

	@Override
	public boolean login(User loginUser) {
		if (!recordRepo.isRightEmailPassword(loginUser)) {
			return false;
		}

		// 저장된 유저(전적, lastLogin 들어있는 것) 가져오기
		User saved = recordRepo.findByEmail(loginUser.getEmail());
		if (saved == null)
			return false;

		// 지난 로그인 시간 백업
		String prev = saved.getLastLogin();
		lastLoginBeforeUpdate = (prev == null || prev.isBlank()) ? "없음" : prev;

		// “이번 로그인 시간”으로 saved lastLogin 갱신 + 파일 반영
		recordRepo.currentTimeSave(saved);
		recordRepo.saveAllRecords();

		return true;

	}

	@Override
	public void logout() {
		recordRepo.saveAllRecords();
		System.out.println("게임을 종료합니다.");

	}

	@Override
	public void changePassword(User currentUser, UserInput userInput) {
		if (currentUser == null) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}

		// 1. 현재 비번 입력
		String currentPw = userInput.inputCurrentPassword();

		// 2. 현재 비번과 저장된 비번 같은지 확인
		User saved = recordRepo.findByEmail(currentUser.getEmail());
		if (saved == null) {
			System.out.println("유저 정보를 찾을 수 없습니다.");
			return;
		}

		if (!saved.getPassword().equals(currentPw)) {
			System.out.println("현재 비밀번호가 틀렸습니다.");
			return;
		}

		// 3. 새 비밀번호 입력
		String newPw = userInput.inputNewPassword();

		// 4. 새 비번이 기존 비번과 같은지 확인
		if (saved.getPassword().equals(newPw)) {
			System.out.println("기존 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.");
			return;
		}

		// 5. 변경 + 파일 반영
		recordRepo.updatePassword(saved.getEmail(), newPw);
		recordRepo.saveAllMembers();

		System.out.println("비밀번호가 변경되었습니다.");

	}

	public User getUser(String email) {
		return recordRepo.getUser(email);
	}

}
