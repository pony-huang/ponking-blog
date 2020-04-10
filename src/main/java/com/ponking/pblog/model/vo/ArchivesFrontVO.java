package com.ponking.pblog.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ponking.pblog.model.entity.Category;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ArchivesFrontVO
 * @date 2020/4/10--0:28
 * @Des 归档页内容
 **/
@Data
public class ArchivesFrontVO {
    /**
     * 归档时间
     */
    private Date year;
    @JsonProperty("children")
    private List<ArchiveVO> archiveList = new ArrayList<>();

    public ArchivesFrontVO() {
    }

    public ArchivesFrontVO(Date year) {
        this.year = year;
    }
}
