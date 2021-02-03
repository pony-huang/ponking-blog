package com.ponking.pblog.util;

import com.ponking.pblog.model.vo.ArchiveVo;
import com.ponking.pblog.model.vo.ArchivesContentVo;

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

    public static List<ArchivesContentVo> getArchivesFront(List<ArchiveVo> archiveVoList) {
        List<ArchivesContentVo> archivesContentVoList = new ArrayList<>();
        Date year = archiveVoList.get(0).getDate();
        archivesContentVoList.add(new ArchivesContentVo(year));
        int count = 0;
        for (ArchiveVo archive : archiveVoList) {
            if (year.getYear() == archive.getDate().getYear()) {
                archivesContentVoList.get(count).getArchiveList().add(archive);
            } else {
                year = archive.getDate();
                archivesContentVoList.add(new ArchivesContentVo(year));
                count++;
                archivesContentVoList.get(count).getArchiveList().add(archive);
            }
        }
        return archivesContentVoList;
    }
}
