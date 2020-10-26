package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//게시글 수정 기능 제공
public class ModifyArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//게시글 번호에 해당하는 Article 객체 구하기
			Article article = articleDao.selectById(conn, 
					modReq.getArticleNumber());
			if (article == null) {
				throw new ArticleNotFoundException();
			} //수정할 수 있는지 -> 없으면 익셉션 발생시킴.
			if (!canModify(modReq.getUserId(), article)) {
				throw new PermissionDeniedException();
			}
			//제목과 내용 변경
			articleDao.update(conn, 
					modReq.getArticleNumber(), modReq.getTitle());
			contentDao.update(conn, 
					modReq.getArticleNumber(), modReq.getContent());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean canModify(String modfyingUserId, Article article) {
		return article.getWriter().getId().equals(modfyingUserId);
	}
}
