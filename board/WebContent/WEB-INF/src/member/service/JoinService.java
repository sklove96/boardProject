package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class JoinService {

	private MemberDao memberDao = new MemberDao();
	
	public void join(JoinRequest joinReq) {
		Connection conn = null;
		try { 
			conn = ConnectionProvider.getConnection(); //DB 커넥션 구하기
			conn.setAutoCommit(false); //트랜잭션 시작
			
			//selectById 이용하여 joinReq.getId()에 대핟ㅇ하는 회원 데이터 구하기
			Member member = memberDao.selectById(conn, joinReq.getId());
			if (member != null) {
				JdbcUtil.rollback(conn); //데이터 이미 존재하면 트랜잭션 롤백
				throw new DuplicateIdException(); //예외 처리
			}
			
			//joinReq 통해서 Member객체 생성
			//insert 실행하여 회원 데이터 테이블에 삽입
			memberDao.insert(conn, 
					new Member(
							joinReq.getId(), 
							joinReq.getName(), 
							joinReq.getPassword(), 
							new Date())
					);
			conn.commit(); //트랜잭션 커밋
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn); //커넥션 종료
		}
	}
}
