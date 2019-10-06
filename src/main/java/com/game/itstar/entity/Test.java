package com.game.itstar.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    @Column(name = "user_name",length = 50)
    private String userName;
    @Column(name = "gender",length = 2)
    private Integer gender;
    @Column(name = "age")
    private Integer age;
}
