package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

//MemberDao를 이용하여 아이디에 해당 회원 데이터가 존재하는지 확인
//존재하면 User 객체 생성하여 리턴
public class LoginService {

	private MemberDao memberDao = new MemberDao();

	public User login(String id, String password) { //아이디와 암호를  파라미터로 전달
		try (Connection conn = ConnectionProvider.getConnection()) {
			Member member = memberDao.selectById(conn, id);
			if (member == null) {
				throw new LoginFailException();
			}
			if (!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new User(member.getId(), member.getName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
