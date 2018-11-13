package com.example.demo.domain;

import com.example.demo.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * pojo Created by hcj on 18-7-22
 */
@ToString
@Builder
@Setter
@Getter
@Entity
@Table(name = "jhi_user")
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 0, max = 60)
    @Column(name = "password_hash", length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    // 明确定义长度
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    // 一对多级联查询 , 或者查询两次?
//  private List<Role> roleList;
    @Column(name = "role_id", length = 20)
    private Long roleId;

    public User() {
    }

    public User(Long id, @NotNull @Pattern(regexp = Constants.LOGIN_REGEX) @Size(min = 1, max = 50) String login, @NotNull @Size(min = 0, max = 60) String password, @Size(max = 50) String firstName, @Size(max = 50) String lastName, Long roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }

    public User(Long id , String firstName, String lastName) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
//        System.out.println("equals.."+user.getId());
        return Objects.equals(id, user.id);
//        return true;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(id);
        System.out.println("hashCode"+hash+"..");
        return hash;
    }
}
