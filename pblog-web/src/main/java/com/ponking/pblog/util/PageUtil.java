package com.ponking.pblog.util;

import com.baomidou.mybatisplus.core.metadata.IPage;


import java.io.Serializable;
import java.util.List;

/**
 * @author Peng
 * @date 2020/9/1--13:18
 **/
public class PageUtil {

    public static <T> BlogSysPage<T> getPage(IPage<T> iPage) {
        long total = iPage.getTotal();
        List<T> items = iPage.getRecords();
        return new BlogSysPage<T>(total, items);
    }

    public static class BlogSysPage<T> implements Serializable {

        private static final long serialVersionUID = 42L;

        private final Long total;

        private final List<T> items;

        public BlogSysPage(Long total, List<T> items) {
            this.total = total;
            this.items = items;
        }

        public Long getTotal() {
            return total;
        }

        public List<T> getItems() {
            return items;
        }
    }
}
