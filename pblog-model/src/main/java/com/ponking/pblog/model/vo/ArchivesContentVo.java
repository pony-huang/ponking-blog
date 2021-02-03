package com.ponking.pblog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;;
import java.util.Date;
import java.util.List;

/**
 * @author Ponking
 * @date 2020/4/10--0:28
 * @Des 归档页内容
 **/
@Data
public class ArchivesContentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 归档时间
     */
    private Date date;

    private List<ArchiveVo> archiveList = new ArrayList<>();

    public ArchivesContentVo() {
    }

    public ArchivesContentVo(Date date) {
        this.date = date;
    }
}
