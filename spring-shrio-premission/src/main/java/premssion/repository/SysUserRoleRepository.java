package premssion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import premssion.model.SysUserRole;

import java.util.List;

/**
 * author: 小宇宙
 * date: 2018/4/5
 */
public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Integer>{

    List<SysUserRole> findByUserId(Integer id);

    void deleteByUserId(Integer id);
}
