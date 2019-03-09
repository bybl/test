package net.huadong.util;


import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by wuph on 2016-10-9.
 * Description
 */
public class ResourceTree {
    private String id;
    private String text;
    private String menuName;
    private String permissionName;
    private String url;
    private String icon;
    private String parentId;
    private Integer seqNum;
    private String remark;
    private boolean checked;
    private List<ResourceTree> children;
    //1禁用  0正常
    private String disabled;

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        if (StringUtils.isNotEmpty(menuName)) {
            return menuName;
        } else {
            return permissionName;
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public List<ResourceTree> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceTree> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "ResourceTree{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", menuName='" + menuName + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", parentId='" + parentId + '\'' +
                ", seqNum=" + seqNum +
                ", remark='" + remark + '\'' +
                ", checked=" + checked +
                ", children=" + children +
                ", disabled='" + disabled + '\'' +
                '}';
    }
}
