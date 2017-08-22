package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@SqlResultSetMapping(name="resourcePageMapping",
        entities={@EntityResult(entityClass=Resource.class)},
                columns={@ColumnResult(name="res")})
@Table(name = "t_resource")
public class Resource extends IdEntity {
    //菜单名称
    private String name;
    //菜单链接
    @Column(name = "res_url")
    private String resUrl;
    //父id
    @Column(name = "parent_id")
    private Long parentId;
    //类型 1、2、3、4
    private String type;

    //几级菜单 0为根目录 1为1级目录 以此类推
    private Long level;

    @Column(name = "icon_cls")
    private String iconCls;
    //描述
    private String description;
    //排序
    @Column(name = "sort")
    @OrderBy("level,sort DESC")
    private Long sort;
    //权限字符串
    private String permission;
    //是否有子目录
    private String hasChild;
    //0菜单  1按钮
    private Long resourceType;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_resource", joinColumns = {@JoinColumn(name = "resource_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    /*模块功能集合*/
    private Set<Role> roles = new HashSet<Role>();

    @Transient
    private List<Resource> resources = new ArrayList<Resource>();
    @Transient
    private String showName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public Long getResourceType() {
        return resourceType;
    }

    public void setResourceType(Long resourceType) {
        this.resourceType = resourceType;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
