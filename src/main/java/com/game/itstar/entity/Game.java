package com.game.itstar.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.itstar.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author 朱斌
 * @Date 2019/10/12  9:09
 * @Desc 比赛
 */
@Table(name = "game")
@Entity
@Data
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties({"createdAt", "updatedAt"})
public class Game implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "name", length = 30)
    private String name;//比赛名
    @Column(name = "type", length = 1)
    private Integer type;//比赛类型 1-和平精英 2-王者荣耀 3-英雄联盟
    @Column(name = "model", length = 1)
    private Integer model;// 比赛模式 1-战队模式
    @Column(name = "division", length = 1)
    private Integer division;// 赛区 1-QQ赛区 2-微信赛区
    @Column(name = "begin_at")
    private Timestamp beginAt;//开始时间
    @Column(name = "end_at")
    private Timestamp endAt;//结束时间
    @Column(name = "rules", length = 200)
    private String rules;//比赛规则

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
