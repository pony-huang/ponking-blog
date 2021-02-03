package com.ponking.pblog.model.dto;

import lombok.Data;

/**
 * @author Ponking
 * @ClassName UserInfoDTO
 * @date 2020/3/16--18:24
 **/
@Data
public class UserInfoDTO {
    private String[] roles;
    private String introduction;
    private String avatar;
    private String name;
}
