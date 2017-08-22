package com.easyRepair.service.impl;

import com.easyRepair.dao.ResourceDao;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.ResourceService;
import com.easyRepair.service.RoleService;
import com.easyRepair.tabEntity.Resource;
import com.easyRepair.tabEntity.Role;
import common.core.bean.PageParam;
import common.core.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sean on 2016/12/22.
 */
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RoleService roleService;
    @PersistenceContext
    private EntityManager em;

    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    public Page<Resource> find(int i, int i1) {
        return null;
    }

    public Page<Resource> find(int i) {
        return null;
    }

    public Resource getById(long l) {
        return resourceDao.findOne(l);
    }

    public Resource deleteById(long l) {
        return null;
    }

    public Resource create(Resource resource) {
        return null;
    }

    public Resource update(Resource resource) {
        return null;
    }

    public void deleteAll(long[] longs) {

    }

    /**
     * 根据资源ids 查询权限
     *
     * @param resouces
     * @return
     */
    /*public Set<String> findPermissions(Set<Resource> resouces) {
        Set<String> permissions = new HashSet<String>();
        for(Resource resource : resouces) {
            if(resource != null && ! StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }*/
    public List<String> findPermissions(List<Resource> resouces) {
        List<String> permissions = new ArrayList<String>();
        for (Resource resource : resouces) {
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    public List<TreeNode> findRoleResources() {
        List<TreeNode> menuList = new ArrayList<TreeNode>();
        List<Resource> listRes = resourceDao.findAll();
        for (Resource res : listRes) {
            TreeNode treeNode = new TreeNode();
            treeNode.setName(res.getName());//名称
            treeNode.setType(res.getHasChild().equals("1") ? "folder" : "item");
            treeNode.getAdditionalParameters().setId(res.getId());
            treeNode.getAdditionalParameters().setItemSelected(false);
            treeNode.getAdditionalParameters().setPid(res.getParentId());
            menuList.add(treeNode);
        }
        TreeNode treeNode = new TreeNode();
        List<TreeNode> treeList = treeNode.builderTree(menuList);
        return treeList;
    }

    public List<Resource> findRoleResourcesByRoleId(Long id) {
        Role role = roleService.getById(id);
        return role.getResouces();
    }

    /**
     * 根据类型查询菜单
     *
     * @param
     * @return //@SystemServiceLog(description = "根据分类查询菜单")
     * public List<Resource> findResourcesByType(String type) {
     * return resourceDao.findResourcesByType(type);
     * }
     */

    public Integer findTabeType(String s) {
        return resourceDao.findTabeType(s);
    }

    public Page<Resource> page(PageRequest pageRequest, String type) {
       /* String sql = "select resource from Resource resource where resource.type = ?1 order by resource.level, resource.sort desc,resource.resourceType";
        Query query = em.createQuery(sql.toString(), Resource.class);
        query.setParameter(1, type);
        List<Resource> queryResultList = query.getResultList();
        List<Resource> resourceList = new ArrayList<Resource>();
        for (Resource resource : queryResultList) {
            if (resource.getLevel().equals(1L)) {
                resourceList.add(resource);
                for (Resource res : queryResultList) {
                    if (res.getParentId().equals(resource.getId())) {
                        resourceList.add(res);
                        if (res.getHasChild().equals("1") && res.getResourceType().equals(0L)) {
                            for (Resource resou : queryResultList) {
                                if (resou.getParentId().equals(res.getId())) {
                                    resourceList.add(resou);
                                }
                            }
                        }
                    }
                }
            }
        }

        int total = queryResultList.size();
        PageParam pageParam = new PageParam();
        pageParam.setPageNum(pageRequest.getPageNumber() + 1);
        pageParam.setLength(pageRequest.getPageSize());
        List<Resource> PageList = new ArrayList<Resource>();
        for (int i = 0; i < total; i++) {
            if (i >= pageParam.getStart() && i <= pageParam.getStart() + pageParam.getLength()) {
                PageList.add(resourceList.get(i));
            }
        }
        Page<Resource> page = new PageImpl<Resource>(PageList, pageRequest, total);*/
        //调用返回部分列的存储过程
        Query query = em.createNativeQuery("{call showTreeNodes(?,?,?)}","resourcePageMapping");//,concat(SPACE(B.nLevel*2),'+--',A.`name`)
        query.setParameter(1,type);
        query.setParameter(2,pageRequest.getOffset());
        query.setParameter(3,pageRequest.getPageSize());
        List<Object[]> result = query.getResultList();
        List<Resource> resourceList=new ArrayList<Resource>();
        for (Object[] obj:result){
            Resource resource=(Resource) obj[0];
            StringBuilder s=new StringBuilder();
            s.append(obj[1].toString()).append("+--").append(resource.getName());
            resource.setShowName(s.toString());
            resourceList.add(resource);
        }
        return new PageImpl(resourceList,pageRequest,resourceDao.countByType(type));
    }

    /**
     * 新增菜单 level+1
     *
     * @param resource
     */
    @Transactional
    public void saveResource(Resource resource) {
        if (null == resource.getParentId() || "".equals(resource.getParentId())) {
            resource.setParentId(1L);
        }
        Resource resourceParent = resourceDao.findOne(resource.getParentId());
        //修改新增菜单的父级目录 HasChild：1
        if ("0".equals(resourceParent.getHasChild())) {
            resourceParent.setHasChild("1");
            resourceDao.save(resourceParent);
        }
        resource.setLevel(resourceParent.getLevel() + 1);
        if (resource.getId() == null) {
            resource.setHasChild("0");
        }
        if (!resourceParent.getType().equals("0")){
            resource.setType(resourceParent.getType());
        }
        resourceDao.save(resource);
    }
    /*批量删除*/
    @Transactional
    public Result batchDelete(int[] arrayId) {
        List<Long> idList=new ArrayList<Long>();
        for (int i=0;i<arrayId.length;i++){
            idList.add((long) arrayId[i]);
        }
        //判断是否绑定角色
        String sql = "SELECT COUNT(*) FROM t_role_resource t WHERE t.resource_id IN (?1)";
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter(1, idList);
        BigInteger count = (BigInteger)query.getSingleResult();
        if (count.intValue()>0){
            return new Result(false).msg("删除失败,菜单已绑定角色");
        }
        //判断是否有子菜单
        List<Resource> resourceList=resourceDao.findByIdIn(idList);
        for (Resource resource:resourceList){
            if (resource.getHasChild().equals("1")){
                return new Result(false).msg("删除失败,请先删除子菜单");
            }
        }
        //修改hasChild
        List<Resource> resourceDaoByIdIn=resourceDao.findByIdIn(idList);
        for (Resource resource:resourceDaoByIdIn){
            if(resourceDao.countByParentId(resource.getParentId())==1){
                Resource parent=getById(resource.getParentId());
                parent.setHasChild("0");
                update(parent);
            }
        }
        resourceDao.deleteBatch(idList);
        return new Result(true).msg("删除成功");
    }
    /**
     * 根据资源ids 获取菜单
     * @param resourceSet
     * @return
     *//*
    public List<Resource> finMenuByResourceIds(Set<Resource> resourceSet) {
        List<Resource> resourceSetTo=new ArrayList<Resource>();
        for (Resource resource:resourceSet){
            if (resource.getType()==0){
                resourceSetTo.add(resource);
            }
        }
        return resourceSetTo;
    }*/
}
