package com.dh.dto;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private String id;
	private boolean checked;
	private String pId;
	private boolean open;
	private List<TreeNode> children;
	private String name;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private List<TreeNode> getRootTreeNode(List<TreeNode> menuList) {
		List<TreeNode> roots = new ArrayList<TreeNode>();
		int number = 0;
		for (TreeNode treeNodeI : menuList) {
			your: for (TreeNode treeNodeJ : menuList) {
				if (treeNodeI.getpId().equals(treeNodeJ.getId())) {
					number += 1;
					break your;
				}
			}
			if (number == 0) {
				roots.add(treeNodeI);
			}
		}
		return roots;
	}
	
	private List<TreeNode> creationTreeNode(List<TreeNode> menuList,String pId){
		List<TreeNode> childrenTreeNodes = new ArrayList<TreeNode>();
		
		for (TreeNode treeNode : menuList){
			String parentId = treeNode.getpId();
			String id = treeNode.getId();
			if (parentId.equals(pId)){
				List<TreeNode> childrenTreeNodeTemps = creationTreeNode(menuList,id);
				treeNode.setChildren(childrenTreeNodeTemps);
				childrenTreeNodes.add(treeNode);
			}
		}
		return childrenTreeNodes;
	}
	
	public List<TreeNode> builderTree(List<TreeNode> menuList){
		List<TreeNode> roots = new ArrayList<TreeNode>();
		List<TreeNode> rootsTemp = getRootTreeNode(menuList);
		for (TreeNode root : rootsTemp){
			List<TreeNode> childrenTreeNodes = creationTreeNode(menuList,root.getId());
			root.setChildren(childrenTreeNodes);
			roots.add(root);
		}
		return roots;
	}
	
}
