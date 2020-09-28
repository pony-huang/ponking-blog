package com.ponking.pblog.model.params;

import lombok.Data;

/**
 * @author Ponking
 * @ClassName UserInfo
 * @date 2020/3/16--18:24
 **/
@Data
public class UserInfo {
    private String [] roles;
    private String introduction;
    private String avatar;
    private String name;
}
