package com.game.itstar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.itstar.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @Author 朱斌
 * @Date 2019/9/29  14:10
 * @Desc 用户战队关联关系表
 */
@Data
@Entity
@Table(name = "user_team")
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
public class UserTeam implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "team_id")
    private Integer teamId;
    @Column(name = "type", columnDefinition = "int default 0")
    private Integer type;// 0-普通队员 1-队长


}
