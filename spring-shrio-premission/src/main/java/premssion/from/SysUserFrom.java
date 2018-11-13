package premssion.from;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import premssion.model.SysRole;

import javax.validation.constraints.Digits;
import java.util.List;

/**
 * author: 小宇宙
 * date: 2018/4/7
 */
@Data
@Builder
public class SysUserFrom {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 姓名
     */
    @Length(max = 32,message = "长度不能超过32位")
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 账号
     */
    @Length(max = 32,message = "长度不能超过32位")
    @NotEmpty(message = "账号名称不能为空")
    private String account;

    /**
     * 密码
     */
    @Length(max = 64,message = "长度不能超过64位")
    @NotEmpty(message = "密码名称不能为空")
    private String password;


    /**
     * 是否禁用 0：否；1：是
     */
    @Digits(integer=1,fraction=0,message = "必须为整数")
    @Range(min = 0,max = 1,message = "等级系数在1或2之间")
    private String forbidden;

    /**
     * 用户角色
     */
    private List<SysRole> sysRoles;
    public SysUserFrom(){}

    public SysUserFrom(Integer id, @Length(max = 32, message = "长度不能超过32位") @NotEmpty(message = "名称不能为空") String name, @Length(max = 32, message = "长度不能超过32位") @NotEmpty(message = "账号名称不能为空") String account, @Length(max = 64, message = "长度不能超过64位") @NotEmpty(message = "密码名称不能为空") String password, @Digits(integer = 1, fraction = 0, message = "必须为整数") @Range(min = 0, max = 1, message = "等级系数在1或2之间") String forbidden, List<SysRole> sysRoles) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.forbidden = forbidden;
        this.sysRoles = sysRoles;
    }
}
