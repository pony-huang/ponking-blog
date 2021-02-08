package com.ponking.pblog.util;

import com.ponking.pblog.model.vo.ArchiveVO;
import com.ponking.pblog.model.vo.ArchivesContentVO;

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

    public static List<ArchivesContentVO> getArchivesFront(List<ArchiveVO> archiveVOList) {
        List<ArchivesContentVO> archivesContentVOList = new ArrayList<>();
        Date year = archiveVOList.get(0).getDate();
        archivesContentVOList.add(new ArchivesContentVO(year));
        int count = 0;
        for (ArchiveVO archive : archiveVOList) {
            if (year.getYear() == archive.getDate().getYear()) {
                archivesContentVOList.get(count).getArchiveList().add(archive);
            } else {
                year = archive.getDate();
                archivesContentVOList.add(new ArchivesContentVO(year));
                count++;
                archivesContentVOList.get(count).getArchiveList().add(archive);
            }
        }
        return archivesContentVOList;
    }
}
