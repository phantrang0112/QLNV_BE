package com.trang.QuanLyNhanVien.PageHelper;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class PageInfo implements Serializable {
	 private static final long serialVersionUID = 1L;
	    
	    private int pageNum;

	    private int pageSize;

	    private int size;

	    private int startRow;

	    private int endRow;

	    private long total;

	    private int pages;

	    private List list;

	    private int firstPage;

	    private int prePage;

	    private int nextPage;

	    private int lastPage;

	    private boolean isFirstPage = false;

	    private boolean isLastPage = false;

	    private boolean hasPreviousPage = false;

	    private boolean hasNextPage = false;

	    private int navigatePages;

	    private int[] navigatepageNums;

	    public PageInfo(List list) {
	        this(list, 8);
	    }

	    public PageInfo(List list, int navigatePages) {
	        if (list instanceof Page) {
	            Page page = (Page) list;
	            this.pageNum = page.getPageNum();
	            this.pageSize = page.getPageSize();

	            this.total = page.getTotal();
	            this.pages = page.getPages();
	            this.list = page;
	            this.size = page.size();

	            if (this.size == 0) {
	                this.startRow = 0;
	                this.endRow = 0;
	            } else {
	                this.startRow = page.getStartRow() + 1;
	    
	                this.endRow = this.startRow - 1 + this.size;
	            }
	            this.navigatePages = navigatePages;

	            calcNavigatepageNums();

	            calcPage();

	            judgePageBoudary();
	        }
	    }

	    private void calcNavigatepageNums() {
	        if (pages <= navigatePages) {
	            navigatepageNums = new int[pages];
	            for (int i = 0; i < pages; i++) {
	                navigatepageNums[i] = i + 1;
	            }
	        } else {
	            navigatepageNums = new int[navigatePages];
	            int startNum = pageNum - navigatePages / 2;
	            int endNum = pageNum + navigatePages / 2;

	            if (startNum < 1) {
	                startNum = 1;

	                for (int i = 0; i < navigatePages; i++) {
	                    navigatepageNums[i] = startNum++;
	                }
	            } else if (endNum > pages) {
	                endNum = pages;

	                for (int i = navigatePages - 1; i >= 0; i--) {
	                    navigatepageNums[i] = endNum--;
	                }
	            } else {

	                for (int i = 0; i < navigatePages; i++) {
	                    navigatepageNums[i] = startNum++;
	                }
	            }
	        }
	    }

	    private void calcPage() {
	        if (navigatepageNums != null && navigatepageNums.length > 0) {
	            firstPage = navigatepageNums[0];
	            lastPage = navigatepageNums[navigatepageNums.length - 1];
	            if (pageNum > 1) {
	                prePage = pageNum - 1;
	            }
	            if (pageNum < pages) {
	                nextPage = pageNum + 1;
	            }
	        }
	    }

	    private void judgePageBoudary() {
	        isFirstPage = pageNum == 1;
	        isLastPage = pageNum == pages;
	        hasPreviousPage = pageNum > 1;
	        hasNextPage = pageNum < pages;
	    }

	    public void setPageNum(int pageNum) {
	        this.pageNum = pageNum;
	    }

	    public int getPageNum() {
	        return pageNum;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }

	    public int getSize() {
	        return size;
	    }

	    public int getStartRow() {
	        return startRow;
	    }

	    public int getEndRow() {
	        return endRow;
	    }

	    public long getTotal() {
	        return total;
	    }

	    public int getPages() {
	        return pages;
	    }

	    public List getList() {
	        return list;
	    }

	    public int getFirstPage() {
	        return firstPage;
	    }

	    public int getPrePage() {
	        return prePage;
	    }

	    public int getNextPage() {
	        return nextPage;
	    }

	    public int getLastPage() {
	        return lastPage;
	    }

	    public boolean isIsFirstPage() {
	        return isFirstPage;
	    }

	    public boolean isIsLastPage() {
	        return isLastPage;
	    }

	    public boolean isHasPreviousPage() {
	        return hasPreviousPage;
	    }

	    public boolean isHasNextPage() {
	        return hasNextPage;
	    }

	    public int getNavigatePages() {
	        return navigatePages;
	    }

	    public int[] getNavigatepageNums() {
	        return navigatepageNums;
	    }

	    @Override
	    public String toString() {
	        final StringBuffer sb = new StringBuffer("PageInfo{");
	        sb.append("pageNum=").append(pageNum);
	        sb.append(", pageSize=").append(pageSize);
	        sb.append(", size=").append(size);
	        sb.append(", startRow=").append(startRow);
	        sb.append(", endRow=").append(endRow);
	        sb.append(", total=").append(total);
	        sb.append(", pages=").append(pages);
	        sb.append(", list=").append(list);
	        sb.append(", firstPage=").append(firstPage);
	        sb.append(", prePage=").append(prePage);
	        sb.append(", nextPage=").append(nextPage);
	        sb.append(", lastPage=").append(lastPage);
	        sb.append(", isFirstPage=").append(isFirstPage);
	        sb.append(", isLastPage=").append(isLastPage);
	        sb.append(", hasPreviousPage=").append(hasPreviousPage);
	        sb.append(", hasNextPage=").append(hasNextPage);
	        sb.append(", navigatePages=").append(navigatePages);
	        sb.append(", navigatepageNums=");
	        if (navigatepageNums == null) sb.append("null");
	        else {
	            sb.append('[');
	            for (int i = 0; i < navigatepageNums.length; ++i)
	                sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
	            sb.append(']');
	        }
	        sb.append('}');
	        return sb.toString();
	    }
}
