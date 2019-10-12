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
import java.util.List;

@Data
@Entity
@Table(name = "menu")
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties({"createdAt", "updatedAt"})
public class Menu implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "menu_name", length = 20)
    private String menuName;
    @Column(name = "menu_url", length = 200)
    private String menuUrl;
    @Column(name = "is_moudle", length = 1)
    private Boolean isMoudle;
    @Column(name = "parent_id", length = 11)
    private Integer parentId;
    @Column(name = "order_no", length = 11)
    private Integer orderNo;
    @Column(name = "icons", length = 200)
    private String icons;

    @Transient
    private List<Menu> children;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
}
