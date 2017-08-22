/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.service.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dh.common.CommonButil;
import com.dh.entity.Group;
import com.dh.dto.TreeNode;
import com.dh.repository.GroupDao;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	public List<Group> findAll() {
		return (List<Group>) groupDao.findAll();
	}
	
	
	/**
	 * 查询树形结构资源菜单
	 * @return
	 */
	public List<TreeNode> findGroups() {
		List<TreeNode> menuList = new ArrayList<TreeNode>();
		List<Group> groups = (List<Group>) groupDao.findAll();
		for (Group group : groups) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(group.getId() == null ? "0" : group.getId().toString());
			treeNode.setName(group.getGroupName());
			treeNode.setpId(group.getParentId() == null ? "0" : group.getParentId().toString());
			treeNode.setOpen(true);
			menuList.add(treeNode);
		}
		TreeNode treeNode = new TreeNode();
		List<TreeNode> treeList = treeNode.builderTree(menuList);
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		CommonButil.deTreeNode(tempList,treeList);
		return tempList;
	}
	
	/**
	 * 修改部门
	 * @param group
	 * @return 
	 */
	public Boolean updateGroup(Group group) {
		if(group == null || group.getId() == null){
			return false;
		}
		Group g = groupDao.findOne(group.getId());
		group.setParentId(g.getParentId());
		group.setUpdateTime(CommonButil.getNowTime());
		groupDao.save(group);
		return true;
	}

	public Long createByPid(Long groupId) {
		Group group = new Group(); 
		group.setParentId(groupId);
		group.setGroupName("请输入部门名称");
		group.setUpdateTime(CommonButil.getNowTime());
		Group newGroup =  groupDao.save(group);
		return newGroup.getId();
	}

	public String delete(Long groupId) {
		List<Group> parentGroup = groupDao.findGroupsByParentId(groupId);
		if(parentGroup.size() > 0){
			return "该目录包含子部门，不能删除";
		}
		groupDao.delete(groupId);
		return "删除成功";
	}
}
