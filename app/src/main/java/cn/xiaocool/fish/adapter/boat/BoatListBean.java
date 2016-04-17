package cn.xiaocool.fish.adapter.boat;

public class BoatListBean {
	private String newsIconUrl; // 图片的网址即picSmall
	private String newsTitle; // 图片的标题即json中的name属性
	private String newsContent; // 图片的内容即json中的description

	public BoatListBean(String newsIconUrl, String newsTitle, String newsContent)
	{
		this.newsIconUrl = newsIconUrl;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
	}
	public String getNewsIconUrl() {
		return newsIconUrl;
	}
	public void setNewsIconUrl(String newsIconUrl) {
		this.newsIconUrl = newsIconUrl;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

}
