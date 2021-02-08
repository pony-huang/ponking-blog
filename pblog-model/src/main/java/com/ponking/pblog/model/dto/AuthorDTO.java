package com.ponking.pblog.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ponking
 * @ClassName AuthorDto
 * @date 2020/4/3--23:49
 **/
@Data
public class AuthorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer id;
}
