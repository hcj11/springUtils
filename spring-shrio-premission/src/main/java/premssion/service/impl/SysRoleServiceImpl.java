package premssion.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import premssion.from.SysRoleFrom;
import premssion.model.SysResource;
import premssion.model.SysRole;
import premssion.model.SysRoleResource;
import premssion.repository.SysResourceRepository;
import premssion.repository.SysRoleRepository;
import premssion.repository.SysRoleResourceRepository;
import premssion.service.SysRoleService;
import premssion.utils.JPAUtil;
import premssion.utils.RUtil;
import premssion.vo.R;
import premssion.vo.SysRoleVo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * author: 小宇宙
 * date: 2018/4/5
 */
@Service
@Transactional
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleRepository sysRoleRepository;

    @Autowired
    SysRoleResourceRepository sysRoleResourceRepository;

    @Autowired
    SysResourceRepository sysResourceRepository;
    /**
     * 新增角色
     * @param sysRoleFrom
     * @return
     */
    @Override
    public R saveRole(SysRoleFrom sysRoleFrom) {

        /*分离对象并保存*/
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleFrom,sysRole);
        SysRole sysRoleSave = sysRoleRepository.save(sysRole);
        log.info("角色基本信息保存：sysRoleSave = {}"+sysRoleSave);

        /*构建该角色的资源并保存*/
        List<SysRoleResource> sysRoleResources = new ArrayList<>();
        sysRoleFrom.getSysResources().forEach(o->{
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(sysRoleSave.getId());
            sysRoleResource.setResourceId(o.getId());
            sysRoleResources.add(sysRoleResourceRepository.save(sysRoleResource));
        });
        log.info("角色资源保存：sysRoleResourcesSave = {}"+sysRoleResources);

        return RUtil.success();
    }

    /**
     * 查询角色列表
     * @param pageable
     * @return
     */
    public R selectRoleList(String name,Pageable pageable){
        Specification<SysRole> specification = new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNoneBlank(name)){
                    predicate.add(criteriaBuilder.like(root.get("name").as(String.class), JPAUtil.like(name)));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return criteriaQuery.where(predicate.toArray(pre)).getRestriction();
            }
        };
        return RUtil.success(sysRoleRepository.findAll(specification,pageable));
    }

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @Override
    public R selectRoleDetail(Integer id) {

        /*查询角色基本信息*/
        SysRoleVo sysRoleVo = new SysRoleVo();
        SysRole sysRole = sysRoleRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(sysRole,sysRoleVo);

        /*取出resourceId*/
        List<Integer> sysResourceIds = new ArrayList<>();
        List<SysRoleResource> sysRoleResources = sysRoleResourceRepository.findByRoleId(id);
        sysRoleResources.forEach(o->{
            sysResourceIds.add(o.getResourceId());
        });

        /*查询该角色拥有资源*/
        List<SysResource> sysResources = sysResourceRepository.findAllById(sysResourceIds);
        sysRoleVo.setSysResources(sysResources);

        log.info("角色详情：sysRoleVo = {}"+sysRoleVo);
        return RUtil.success(sysRoleVo);
    }

    /**
     * 更新角色
     * @param sysRoleFrom
     * @return
     */
    @Override
    public R updateRole(SysRoleFrom sysRoleFrom){

        /*分离角色与拥有资源*/
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleFrom,sysRole);

        /*初始化角色资源*/
        sysRoleResourceRepository.deleteByRoleId(sysRoleFrom.getId());

        /*角色基本信息更新*/
        SysRole sysRoleSave = sysRoleRepository.save(sysRole);
        log.info("角色基本信息更新：sysRoleSave = {}"+sysRoleSave);

        /*更新角色资源*/
        List<SysRoleResource> sysRoleResources = new ArrayList<>();
        sysRoleFrom.getSysResources().forEach(o->{
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setResourceId(o.getId());
            sysRoleResource.setRoleId(sysRoleFrom.getId());
            sysRoleResources.add(sysRoleResourceRepository.save(sysRoleResource));
        });
        log.info("资源更新：sysRoleResourcesSave = {}"+sysRoleResources);

        return RUtil.success();
    }

    /**
     * 删除角色接口
     * @param id
     * @return
     */
    @Override
    public R deleteRole(Integer id){
        sysRoleRepository.deleteById(id);

        /*资源删除*/
        sysRoleResourceRepository.deleteByRoleId(id);
        return RUtil.success();
    }
}
