package org.jinghouyu.json.to;

import java.util.Date;

public class Novel {

	private String name;
	private Date publishDate;
	private String authorName;
	private long wordSize;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public long getWordSize() {
		return wordSize;
	}
	public void setWordSize(long wordSize) {
		this.wordSize = wordSize;
	}
}
