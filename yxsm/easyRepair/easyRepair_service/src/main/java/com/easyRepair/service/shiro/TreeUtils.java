package com.easyRepair.service.shiro;

import com.easyRepair.tabEntity.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean on 2017/2/16.
 */
public class TreeUtils {
    static List<Resource> creationTreeNode(List<Resource> menuList, Long rootId) { //获取除根节点外所有子的嵌套列表
        List<Resource> childrenTreeNodes = new ArrayList<Resource>();
        for (Resource resource : menuList) {
            if (resource.getParentId().equals(rootId)) {
                List<Resource> childrenTreeNodeTemps = creationTreeNode(menuList, resource.getId());
                resource.setResources(childrenTreeNodeTemps);
                childrenTreeNodes.add(resource);
            }
        }
        return childrenTreeNodes;
    }

    public static List<Resource> builderTree(List<Resource> resourceList ) { //为了将根节点和子节点合并
        Resource root =new Resource();
        List<Resource> menuList=new ArrayList<Resource>();
        for (Resource resource:resourceList) {
            if (resource.getParentId().equals(0L)) {
                root = resource;
            } else {
                if (resource.getResourceType().equals(0L)) {
                    menuList.add(resource);
                }
            }
        }
        List<Resource> childrenTreeNodes = creationTreeNode(menuList,root.getId());
        return childrenTreeNodes;
    }
}
