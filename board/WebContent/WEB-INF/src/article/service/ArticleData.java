package article.service;

import article.model.Article;
import article.model.ArticleContent;

//ReadArticleService 에 구현할 getArticle() 메서드 리턴 타입
//Article과 ArticleContent 객체를 한 객체에 담기 위해 생성
public class ArticleData {

	private Article article;
	private ArticleContent content;

	public ArticleData(Article article, ArticleContent content) {
		this.article = article;
		this.content = content;
	}

	public Article getArticle() {
		return article;
	}

	public String getContent() {
		return content.getContent();
	}

}
