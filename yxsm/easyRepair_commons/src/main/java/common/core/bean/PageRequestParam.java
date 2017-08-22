package common.core.bean;

import common.core.Constant;

/**
 * Created by sean on 2016/11/15.
 */
public class PageRequestParam {
    private int start;
    private int length;
    private int pageNum;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPageNum() {
        if (start == 0) {
            start = 0;
        }
        if (length == 0) {
            length = Constant.PAGE_DEF_SZIE;
        }
        int pageNum = (start / length) + 1;
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
