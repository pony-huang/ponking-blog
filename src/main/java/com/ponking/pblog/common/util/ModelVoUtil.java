package com.ponking.pblog.common.util;

import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesFrontVO;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ponking
 * @ClassName ModelVoUtil
 * @date 2020/4/10--0:54
 * @Des 处理归档页view数据
 **/
public class ModelVoUtil {

    public static List<ArchivesFrontVO> getArchivesFront(List<ArchiveVO> archiveVOList) {
        List<ArchivesFrontVO> archivesFrontVOList = new ArrayList<>();
        Date year = archiveVOList.get(0).getDate();
        archivesFrontVOList.add(new ArchivesFrontVO(year));
        int count = 0;
        for (ArchiveVO archive : archiveVOList) {
            if (year.getYear() == archive.getDate().getYear()) {
                archivesFrontVOList.get(count).getArchiveList().add(archive);
            } else {
                year = archive.getDate();
                archivesFrontVOList.add(new ArchivesFrontVO(year));
                count++;
                archivesFrontVOList.get(count).getArchiveList().add(archive);
            }
        }
        return archivesFrontVOList;
    }
}
