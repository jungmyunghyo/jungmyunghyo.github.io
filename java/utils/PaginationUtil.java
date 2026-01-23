public class PaginationUtil {
	public static final int PAGE_DEFAULT = 1;
	public static final int PAGE_SIZE_DEFAULT = 10;
	
	public static final int getStartRow(int page, int pageSize) {
		return (getPage(page) - PAGE_DEFAULT) * getPageSize(pageSize);
	}
	public static final int getEndRow(int totalCount, int page, int pageSize) {
		int lastRow = getPage(page) * getPageSize(pageSize);
		return lastRow > totalCount ? totalCount : lastRow;
	}
	public static final boolean hasNextPage(int totalCount, int page, int pageSize) {
		return getPageCount(totalCount, getPageSize(pageSize)) >= getPage(page);
	}
	public static final boolean hasMorePage(int page, int lastPage) {
		return getPage(lastPage) > getPage(page);
	}
	private static final int getPage(int page) {
		return page > PAGE_DEFAULT ? page : PAGE_DEFAULT;
	}
	private static final int getPageSize(int pageSize) {
		return pageSize > 0 ? pageSize : PAGE_SIZE_DEFAULT;
	}
	private static final int getPageCount(int totalCount, int pageSize) {
		return PAGE_DEFAULT > totalCount ? 0 : (int) Math.ceil((double) totalCount / getPageSize(pageSize));
	}
}