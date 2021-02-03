package com.ponking.pblog.model.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ponking
 * @ClassName ArchiveTableCartVo
 * @date 2020/4/8--12:01
 **/
@Data
public class ArchiveTableCartVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date years;
    private Integer count;
}
