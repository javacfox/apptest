package com.game.itstar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.itstar.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

/**
 * @Author 朱斌
 * @Date 2019/9/29  10:52
 * @Desc
 */
@Data
@Entity
@Table(name = "team")
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
public class Team implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "team_name", length = 30)
    private String teamName;//战队名
    @Column(name = "team_pic", length = 70)
    private String teamPic;//队徽
    @Column(name = "team_code", length = 100)
    private String teamCode;//战队唯一代码,用于申请加入战队(用于邀请好友加入战队)
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "count", columnDefinition = "int default 0")
    private Integer count;//战队人数
    @Column(name = "remark", length = 200)
    private String remark;//备注

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
    @Transient
    private Boolean isEffective;//是否调用服务器图片
}
