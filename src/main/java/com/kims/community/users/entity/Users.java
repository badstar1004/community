package com.kims.community.users.entity;

import com.kims.community.baseEntity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {

    /**
     * 유저아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;

    /**
     * 이메일
     */
    @Column(length = 100, nullable = false)
    private String email;

    /**
     * 이름
     */
    @Column(length = 20, nullable = false)
    private String name;

    /**
     * 닉네임
     */
    @Column(length = 50, nullable = false)
    private String nickName;

    /**
     * 비밀번호
     */
    @Column(length = 20, nullable = false)
    private String password;

}
