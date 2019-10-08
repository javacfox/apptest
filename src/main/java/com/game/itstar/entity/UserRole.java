package com.game.itstar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.itstar.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRole implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "role_id")
    private Integer roleId;
}
