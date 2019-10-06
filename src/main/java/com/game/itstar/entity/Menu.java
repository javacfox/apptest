package com.game.itstar.entity;

import lombok.Data;

@Data
public class Menu {
    private Integer id;
    private String menuName;
    private String menuUrl;
    private Integer isMoudle;
    private Integer parentId;
    private Integer orderNo;
    private String icons;
}
