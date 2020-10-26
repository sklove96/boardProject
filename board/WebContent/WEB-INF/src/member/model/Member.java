package member.model;

import java.util.Date;

//member 테이블과 관련된 클래스 - 회원 데이터 담음
public class Member {

	private String id;
	private String name;
	private String password;
	private Date regDate;

	public Member(String id, String name, String password, Date regDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public boolean matchPassword(String pwd) { //암호 변경 기능 구현할 때 사용
		return password.equals(pwd);
	}

	public void changePassword(String newPwd) { //암호 변경
		this.password = newPwd;
	}

}
