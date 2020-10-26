package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

//글쓰기 기능 제공
public class WriteArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	public Integer write(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Article article = toArticle(req); //article 객체 생성
			Article savedArticle = articleDao.insert(conn, article); //insert 메서드 실행
			
			if (savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}
			
			ArticleContent content = new ArticleContent( //ArticleContent 객체 생성
					savedArticle.getNumber(),
					req.getContent());
			ArticleContent savedContent = contentDao.insert(conn, content);
			if (savedContent == null) {
				throw new RuntimeException("fail to insert article_content");
			}

			conn.commit(); //트랜잭션 커밋

			return savedArticle.getNumber(); //새로 추가한 게시글 번호 리턴
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private Article toArticle(WriteRequest req) {
		Date now = new Date();
		return new Article(null, req.getWriter(), req.getTitle(), now, now, 0);
	}
}
