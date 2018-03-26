package com.srllimited.srl.data;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

public class ScrollingListTemplate
{

	private String header;

	private String content;

	private String image;

	private int headercolor;

	private int contentcolor;

	public int getHeadercolor()
	{
		return headercolor;
	}

	public void setHeadercolor(final int headercolor)
	{
		this.headercolor = headercolor;
	}

	public int getContentcolor()
	{
		return contentcolor;
	}

	public void setContentcolor(final int contentcolor)
	{
		this.contentcolor = contentcolor;
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(final String header)
	{
		this.header = header;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(final String image)
	{
		this.image = image;
	}

	@Override
	public String toString()
	{
		return "ScrollingListTemplate{" + "contentcolor=" + contentcolor + ", header='" + header + '\'' + ", content='"
				+ content + '\'' + ", image='" + image + '\'' + ", headercolor=" + headercolor + '}';
	}
}
