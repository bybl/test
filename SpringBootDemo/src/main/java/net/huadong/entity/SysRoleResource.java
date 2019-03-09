package net.huadong.entity;

import javax.persistence.*;

@Table(name = "SYS_ROLE_RESOURCE")
public class SysRoleResource {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "RESOURCE_ID")
    private String resourceId;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return ROLE_ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * @return RESOURCE_ID
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}