package com.dh.service.system;

import com.dh.common.CommonButil;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.Resource;
import com.dh.entity.Role;
import com.dh.dto.TreeNode;
import com.dh.entity.User;
import com.dh.repository.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ResourceService {
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 根据类型查询菜单
	 * @param type
	 * @return
	 */
	@SystemServiceLog(description = "根据分类查询菜单")
	public List<Resource> findResourcesByType(String type){
		return resourceDao.findResourcesByType(type);
	}

	/**
	 * 根据角色id查询出树形结构的资源
	 * @return
	 */
	@SystemServiceLog(description = "查询角色菜单列表")
	public List<TreeNode> findRoleResources(Long roleId) {
		List<TreeNode> menuList = new ArrayList<TreeNode>();
		/*找到所有的功能*/
		List<Resource> listRes = resourceDao.findAllResources();
		/*找到角色以及对应的功能*/
		Role role = roleService.findRoleById(roleId);
		Set<Resource> roleResourceids = role.getResources();
	/*	List<String> stringList=new ArrayList<String>();
		for (Resource resource:roleResourceids){
			stringList.add(resource.getId().toString());
		}*/
		for (Resource res : listRes) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(res.getId() == null ? "0" : res.getId().toString());
			treeNode.setName(res.getName());
			treeNode.setpId(res.getParentId() == null ? "0" : res.getParentId().toString());
			if("1".equals(res.getHasChild())){
				treeNode.setOpen(true);
			}else{
				treeNode.setOpen(false);
			}
			treeNode.setChecked(roleResourceids.contains(res));
			menuList.add(treeNode);
		}
		TreeNode treeNode = new TreeNode();
		List<TreeNode> treeList = treeNode.builderTree(menuList);
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		CommonButil.deTreeNode(tempList,treeList);
		return tempList;
	}
	/**
	 * 查询树形结构资源菜单
	 * @return
	 */
	@SystemServiceLog(description = "查询菜单列表")
	public List<TreeNode> findRoleResources() {
		List<TreeNode> menuList = new ArrayList<TreeNode>();
		List<Resource> listRes = resourceDao.findAllResources();
		for (Resource res : listRes) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(res.getId() == null ? "0" : res.getId().toString());
			treeNode.setName(res.getName());
			treeNode.setpId(res.getParentId() == null ? "0" : res.getParentId().toString());
			if("1".equals(res.getHasChild())){
				treeNode.setOpen(true);
			}else{
				treeNode.setOpen(false);
			}
			treeNode.setChecked(false);
			menuList.add(treeNode);
		}
		TreeNode treeNode = new TreeNode();
		List<TreeNode> treeList = treeNode.builderTree(menuList);
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		CommonButil.deTreeNode(tempList,treeList);
		return tempList;
	}

	/**
	 * 修改菜单
	 * @param request 
	 * @param entity
	 */
	public void updateResource(HttpServletRequest request, Resource entity) {
		Resource resource = resourceDao.findOne(entity.getId());
		entity.setParentId(resource.getParentId());
		entity.setLevel(resource.getLevel());
		entity.setHasChild(resource.getHasChild());
//		entity.setRoles(resource.getRoles());
		resourceDao.save(entity);
		/*如果与修改的菜单先关联的角色被当前用户拥有*/
		User user = (User) request.getSession().getAttribute("currentUser");
		List<Resource> resourceList=finMenuByResourceIds(roleService.findRoleById(user.getRoleId()).getResources());
		request.getSession().setAttribute("userMenu",resourceList);
	}
	
	/**
	 * 新增菜单 level+1
	 * @param entity
	 */
	public void saveResource(Resource entity) {
		if(null == entity.getParentId() || "".equals(entity.getParentId())){
			entity.setParentId((long) 1);
		}
		Resource resourceParent = resourceDao.findOne(entity.getParentId());
		//若新增的是菜单  则给父级菜单 hasChild：1
		if(entity.getResourceType() == 0){
			//修改新增菜单的父级目录 HasChild：1
			if("0".equals(resourceParent.getHasChild())){
				resourceParent.setHasChild("1");
				resourceDao.save(resourceParent);	
			}
		}
		entity.setLevel(resourceParent.getLevel()+1);
		entity.setHasChild("0");
		resourceDao.save(entity);
	}

	public List<Resource> findAll() {
		return (List<Resource>) resourceDao.findAll();
	}

	/**
	 * 删除菜单
	 * @param request 
	 * @param id
	 * @return
	 */
	public String deleteResource(HttpServletRequest request, Long id) {
		//查询该对象是否有子目录
		List<Resource> resources = resourceDao.findResourcesByParentId(id);
		if(resources.size() > 0){
			return "该目录包含子项，不能删除";
		}
		//获取删除对象
		Resource delResource = resourceDao.findOne(id);
		//获取删除对象的父级目录
		Resource delResourceParent = resourceDao.findOne(delResource.getParentId());
		//删除对象
		resourceDao.delete(id);
		//查询删除对象父级是否有子目录 若没有 则修改 该父级目录hasChild：0
		List<Resource> rs = resourceDao.findMenuResourcesByParentId(delResourceParent.getId());
		if(rs.size() == 0){
			delResourceParent.setHasChild("0");
			resourceDao.save(delResourceParent);
		}
		User user = (User) request.getSession().getAttribute("currentUser");
		request.getSession().setAttribute("userMenu",finMenuByResourceIds(roleService.findRoleById(user.getRoleId()).getResources()));
		return "删除成功";
	}
	
	/**
	 * 根据资源ids 查询权限
	 * @param resourceSet
	 * @return
	 */
	public Set<String> findPermissions(Set<Resource> resourceSet) {
		Set<String> permissions = new HashSet<String>();
        for(Resource resource : resourceSet) {
            if(resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }
	/**
	 * 根据资源ids 获取资源
	 * @param resourceIdList
	 * @return
	 */
	public List<Resource> findResourcesByIds(List<String> resourceIdList) {
		List<Long> list = new ArrayList<Long>();
		for(String resourceId : resourceIdList) {
			list.add(Long.parseLong(resourceId));
		}
		return resourceDao.findResourcesByIds(list);
	}
	/**
	 * 根据资源ids 获取菜单
	 * @param resourceSet
	 * @return
	 */
	public List<Resource> finMenuByResourceIds(Set<Resource> resourceSet) {
		List<Resource> resourceSetTo=new ArrayList<Resource>();
		for (Resource resource:resourceSet){
			if (resource.getResourceType()==0){
				resourceSetTo.add(resource);
			}
		}
		return resourceSetTo;
	}

	public Integer findTabeType(String url) {
		return resourceDao.findTabeType(url);
	}

}
