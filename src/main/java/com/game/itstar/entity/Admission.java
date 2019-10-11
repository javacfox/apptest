package com.game.itstar.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.itstar.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Table(name = "admission")
@Entity
@Getter
@Setter
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties({"createdAt", "updatedAt"})
public class Admission implements BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer status;
    private Integer teamId;//战队id
    private Timestamp operatedAt = new Timestamp(System.currentTimeMillis());
    private Integer operaterId;
    private String message;

    @NotBlank(message = "战队邀请码不能为空")
    @Transient
    private String teamCode;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
