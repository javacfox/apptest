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

@Data
@Entity
@Table(name = "role")
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties({"createdAt", "updatedAt"})
public class Role implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "role_name", length = 30)
    private String roleName;
    @Column(name = "type", columnDefinition = "int default 0")
    private Integer type;//暂定两种角色0-默认角色 99-管理员

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
