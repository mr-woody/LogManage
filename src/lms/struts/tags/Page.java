package lms.struts.tags;

public class Page {

	/** 是否具有上一页 */
	private boolean hasPrePage;

	/** 是否具有下一页 */
	private boolean hasNextPage;

	/** 每页显示数据条数 */
	private static int everyPage = 10;

	/** 总页数 */
	private int totalPage;

	/** 当前页码 */
	private int currentPage = 1;

	/** 开始页码 */
	private int beginIndex = 1;
	
	/**数据总条数*/
	private int elementTotal = 0;
	
	/**页码显示条数*/
	private static int showPageNum = 4;
	
	public Page() {

	}

	/**
	 * construct the page by everyPage
	 * 
	 * @param everyPage
	 * */
	public Page(int everyPage) {
		Page.everyPage = everyPage;
	}

	/** The whole constructor */
	public Page(boolean hasPrePage, boolean hasNextPage, int everyPage,
			int totalPage, int currentPage, int beginIndex) {
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
		Page.everyPage = everyPage;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
	}

	/**
	 * @return Returns the beginIndex.
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param beginIndex
	 *            The beginIndex to set.
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the everyPage.
	 */
	public static int getEveryPage() {
		return everyPage;
	}

	/**
	 * @param everyPage
	 *            The everyPage to set.
	 */
	public static void setEveryPage(int everyPage) {
		Page.everyPage = everyPage;
	}

	/**
	 * @return Returns the hasNextPage.
	 */
	public boolean getHasNextPage() {
		return hasNextPage;
	}

	/**
	 * @param hasNextPage
	 *            The hasNextPage to set.
	 */
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	/**
	 * @return Returns the hasPrePage.
	 */
	public boolean getHasPrePage() {
		return hasPrePage;
	}

	/**
	 * @param hasPrePage
	 *            The hasPrePage to set.
	 */
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	/**
	 * @return Returns the totalPage.
	 * 
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            The totalPage to set.
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getElementTotal() {
		return elementTotal;
	}

	public void setElementTotal(int elementTotal) {
		this.elementTotal = elementTotal;
	}

	public static int getShowPageNum() {
		return showPageNum;
	}

	public static void setShowPageNum(int showNum) {
		showPageNum = showNum;
	}

}
