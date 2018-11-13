package premssion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户
 * author: 小宇宙
 * date: 2018/4/5
 */
@Builder
@Data
@Entity
public class SysUser {
    public SysUser(){}

    public SysUser(String name, String account, String password, String salt, String forbidden) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.salt = salt;
        this.forbidden = forbidden;
    }
    public SysUser(Integer id,String name, String account, String password, String salt, String forbidden) {
        this.id=id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.salt = salt;
        this.forbidden = forbidden;
    }

    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 是否禁用 0：否；1：是
     */
    private String forbidden;
}
