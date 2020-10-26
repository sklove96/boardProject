package article.model;

import java.util.Date;

public class Article {

	private Integer number;
	private Writer writer; //Writer에 정의한 필드 사용
	private String title;
	private Date regDate;
	private Date modifiedDate;
	private int readCount;

	public Article(Integer number, Writer writer, String title, 
			Date regDate, Date modifiedDate, int readCount) {
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
	}

	public Integer getNumber() {
		return number;
	}

	public Writer getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public int getReadCount() {
		return readCount;
	}

}
