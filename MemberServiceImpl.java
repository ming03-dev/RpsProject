package RpsProject;

// 아이디

public class MemberServiceImpl implements MemberService {
	RecordRepository recordRepo = new RecordRepository();

	@Override
	public boolean signUp(User newUser) {
		// 이메일을 저장된 파일에 있는지 중복 체크
		// T면 "이미 가입한 회원" 출력 후 false 리턴, F면 파일에 회원 정보 저장 요청 후 true를 반환
		if(recordRepo.isDupl(newUser) == true) {
			System.out.println("이미 가입된 이메일입니다.");
			return false;
		} else {
			recordRepo.save(newUser);
			return true;
		}
		
	}

	

	@Override
	public boolean login(User newUser) {
		// 1. 로그인 2. 비밀번호 변경
		
    	return true;

	}

	@Override
	public void logout() {
		// 로그아웃
		System.out.println("로그아웃");

	}

	@Override
	public void changePassword() {
		// 비밀번호 변경
	}
}
