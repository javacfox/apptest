package com.game.itstar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.game.itstar.base.entity.BaseEntity;
import com.game.itstar.utile.RegexpProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user")
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @NotBlank(message = "用户名称不能为空")
    @Column(name = "user_name", length = 20)
    private String userName;
    @NotBlank(message = "登录密码不能为空")
    @Column(name = "password", length = 50)
    private String password;
    @Pattern(regexp = RegexpProperties.MOBILE_PATTERN, message = "电话格式有误")
    @Column(name = "phone_number", length = 11)
    private String phoneNumber;//手机号
    @Column(name = "pic", length = 100)
    private String pic;//图像地址
    @Column(name = "salt", length = 100)
    private String salt;
    @ApiModelProperty("状态：0-禁用；1-启用")
    @Column(name = "active")
    private Boolean active;
    @Column(name = "user_type", columnDefinition = "int default 0")
    private Integer userType;//99超级管理员
    @Pattern(regexp = RegexpProperties.EMAIL_PATTERN, message = "邮箱格式有误")
    @Column(name = "email", length = 50)
    private String email;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @NotBlank(message = "确认密码不能为空")
    @Transient
    private String password1;
    @Transient
    private Boolean isEffective = false;//是否调用服务器图片,默认否
    @Transient
    private Integer type;//注册类型1-普通用户 2-管理员
}
