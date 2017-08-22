package com.easyRepair.pojo.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private AdditionalParameters additionalParameters=new AdditionalParameters();
	private String name;
	private String type;

	public AdditionalParameters getAdditionalParameters() {
		return additionalParameters;
	}

	public void setAdditionalParameters(AdditionalParameters additionalParameters) {
		this.additionalParameters = additionalParameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	private List<TreeNode> getRootTreeNode(List<TreeNode> menuList) {
		List<TreeNode> roots = new ArrayList<TreeNode>();
		/*int number = 0;*/
		for (TreeNode treeNodeI : menuList) {
			/*ok: for (TreeNode treeNodeJ : menuList) {
				if (treeNodeI.getAdditionalParameters().getPid().equals(treeNodeJ.getAdditionalParameters().getId())) {
					number += 1;
					break ok;
				}
			}*/
			if (treeNodeI.getAdditionalParameters().getPid() == 0) {
				roots.add(treeNodeI);
			}
		}
		return roots;
	}

	private List<TreeNode> creationTreeNode(List<TreeNode> menuList,Long pId){
		List<TreeNode> childrenTreeNodes = new ArrayList<TreeNode>();
		for (TreeNode treeNode : menuList){
			Long id = treeNode.getAdditionalParameters().getId();
			if (treeNode.getAdditionalParameters().getPid().equals(pId)){
				List<TreeNode> childrenTreeNodeTemps = creationTreeNode(menuList,id);
				treeNode.getAdditionalParameters().setChildren(childrenTreeNodeTemps);
				childrenTreeNodes.add(treeNode);
			}
		}
		return childrenTreeNodes;
	}


	public List<TreeNode> builderTree(List<TreeNode> menuList){
		List<TreeNode> roots = new ArrayList<TreeNode>();
		List<TreeNode> rootsTemp = getRootTreeNode(menuList);
		for (TreeNode root : rootsTemp){
			List<TreeNode> childrenTreeNodes = creationTreeNode(menuList,root.getAdditionalParameters().getId());
			root.getAdditionalParameters().setChildren(childrenTreeNodes);
			roots.add(root);
		}
		return roots;
	}
}
