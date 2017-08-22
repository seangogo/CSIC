/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.service.account;

import com.dh.common.CommonButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.Resource;
import com.dh.entity.Role;
import com.dh.entity.User;
import com.dh.repository.UserDao;
import com.dh.service.ServiceException;
import com.dh.service.account.ShiroDbRealm.ShiroUser;
import com.dh.service.order.OrderService;
import com.dh.service.system.ResourceService;
import com.dh.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import java.util.*;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;

//	private GroupDao groupDao;

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private OrderService orderService;
	

	private Clock clock = Clock.DEFAULT;
	
	@SystemServiceLog(description = "查询用户列表")
	public Page<User> getAllUser(int pageNumber, Integer pageSize, String loginName, String userName, Integer userType,
			Integer groupId) {
		// 设置查询排序 和分页
		PageRequest pageRequest = buildUserListPageRequest(pageNumber, pageSize);
		Specification<User> spe = buildUserListSpecification(loginName, userName, userType, groupId);
		return userDao.findAll(spe, pageRequest);
	}
	
	private Specification<User> buildUserListSpecification(String loginName, String userName, Integer userType,
			Integer groupId) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		if (!CommonButil.isEmpty(loginName)) {
			filters.put("loginName", new SearchFilter("loginName", Operator.LIKE, loginName));
		}
		if (!CommonButil.isEmpty(userName)) {
			filters.put("userName", new SearchFilter("userName", Operator.LIKE, userName));
		}
		if (!CommonButil.isEmpty(userType)) {
			filters.put("userType", new SearchFilter("userType", Operator.EQ, userType));
		}
		if (!CommonButil.isEmpty(groupId)) {
			filters.put("groupId", new SearchFilter("groupId", Operator.EQ, groupId));
		}
		// 条件判断没能找到 不等于 的判断， 故而使用 小于3 来剔除掉 用户列表管理员的查询
		filters.put("userType2", new SearchFilter("userType", Operator.LT, 3));
		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
		return spec;
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildUserListPageRequest(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.DESC, "isLogin").and(new Sort(Sort.Direction.ASC, "loginName"));
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}
	
	/**
	 * 查询用户详情
	 * @param id
	 * @return
	 */
	@SystemServiceLog(description = "查询用户详情")
	public User getUser(Long id) {
		return userDao.findOne(id);
	}
	/**
	 * 根据用户名查询用户
	 * @param loginName
	 * @return
	 */
	@SystemServiceLog(description = "根据用户名查询用户")
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	public void registerUser(User user) {
		entryptPassword(user);
		Role role = new Role();
		role.setId((long) 2);
		user.setRole(role);
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
	}

	/**
	 * 新建用户
	 * @param user
	 * @return
	 */
	@SystemServiceLog(description = "新增用户")
	public User createUser(User user) {
		user.setPlainPassword(Consts.DEFAULT_PASSWORD);
		entryptPassword(user);
	/*	if(user.getUserType() == null){
			user.setRole(roleService.findRoleByName(USER_ROLE));
		}else if(user.getUserType() == 1){
			user.setRole(roleService.findRoleByName(SERVICE_ROLE));
		}else if(user.getUserType() == 2){
			user.setRole(roleService.findRoleByName(MANAGER_ROLE));
		}else{
			user.setRole(roleService.findRoleByName(USER_ROLE));
		}*/
		user.setIsLocked(0);
		user.setLoginCount(0L);
		user.setUserState(1);
		user.setRegisterDate(CommonButil.getNowTime());
		user.setIsLogin(0);
		return userDao.save(user);
	}

	/**
	 * 修改用户信息
	 * @param user
	 */
	@SystemServiceLog(description = "修改用户信息")
	public void updateUser(User user) {
		if (user.getId() != null) {
			User newUser = userDao.findOne(user.getId());
			if (StringUtils.isNotBlank(user.getLoginName())) {
				newUser.setLoginName(user.getLoginName());
			}
			if (StringUtils.isNotBlank(user.getUserName())) {
				newUser.setUserName(user.getUserName());
			}
			newUser.setUserType(user.getUserType());
			newUser.setRepairType(user.getRepairType());
			if (StringUtils.isNotBlank(user.getUserSex())) {
				newUser.setUserSex(user.getUserSex());
			}
			if (StringUtils.isNotBlank(user.getUserAddress())) {
				newUser.setUserAddress(user.getUserAddress());
			}
			if (StringUtils.isNotBlank(user.getUserCompany())) {
				newUser.setUserCompany(user.getUserCompany());
			}
			if (StringUtils.isNotBlank(user.getUserMail())) {
				newUser.setUserMail(user.getUserMail());
			}
			if (user.getUserType() == 1) {
				newUser.setRole(roleService.findByRoleName(Consts.SERVICE_ROLE));
			} else if (user.getUserType() == 2) {
				newUser.setRole(roleService.findByRoleName(Consts.MANAGER_ROLE));
			} else {
				newUser.setRole(roleService.findByRoleName(Consts.USER_ROLE));
			}
		}

		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 重置密码
	 * @param user
	 */
	@SystemServiceLog(description = "重置密码")
	public void resetPwd(User user) {
		user.setPlainPassword(Consts.DEFAULT_PASSWORD);
		entryptPassword(user);
		userDao.save(user);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
	
	@SystemServiceLog(description = "查询用户的角色")
	public Set<String> findRoles(User user) {
		if (user == null || null == user.getRole()) {
			return new HashSet<String>();
		}
		Set<String> rolesName = new HashSet<String>();
		rolesName.add(user.getRole().getRoleName());
		return rolesName;
	}
	@SystemServiceLog(description = "查询用户的权限")
	public Set<String> findPermissions(User user) {
		if (user == null || null == user.getRole()) {
			return new HashSet<String>();
		}
		return resourceService.findPermissions(user.getRole().getResources());
	}
	
	@SystemServiceLog(description = "查询用户的菜单")
	public List<Resource> findMenu(User user) {
		if (user == null || null == user.getRole()) {
			return new ArrayList<Resource>();
		}
		return resourceService.finMenuByResourceIds(user.getRole().getResources());
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	@SystemServiceLog(description = "解锁/锁定用户")
	public User isLocked(User user, Integer type) {
		if (type == 1) {
			user.setIsLocked(1);
			user.setIsLogin(0);
			user.setDeviceId("");
			user.setDeviceOs("");
		} else {
			user.setIsLocked(0);
		}
		return userDao.save(user);
	}

	/**
	 * 获取工程师和经理列表
	 * @return
	 */
	@SystemServiceLog(description = "查询工程师列表")
	public List<User> getRepairList() {
		return userDao.getRepairList();
	}

	public String findUserDeviceId(Long userId) {
		return this.userDao.findUserDeviceId(userId);
	}

	public Long countByRoldId(Long roleId) {
		return userDao.countByRoleId(roleId);
	}

	/**
	 * 判断当前用户的最新订单数
	 * @param user
	 * @return
     */
	public int findNewOrderNum(User user) {
		return orderService.findNewOrderNum(user);
	}

	/**
	 * 查询所有工程师最近4个月的接单情况
	 * @return
     */
	public List<Map<String,String>> findTop4OrderSumList() {
		//查询出所有发布时间在最近四个月的所有订单并分组
			return orderService.findAllRepairCountBy4Month();

	}

	public Map<String,String> findWeekByType() {
		return orderService.findWeekByType();
	}
}
