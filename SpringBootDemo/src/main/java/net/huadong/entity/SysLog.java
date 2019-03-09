package net.huadong.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "SYS_LOG")
public class SysLog {
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select sys_guid() from dual")
    private String id;

    @Column(name = "CLASS_NAME")
    private String className;

    @Column(name = "METHOD_NAME")
    private String methodName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "OPER_REMARK")
    private String operRemark;

    @Column(name = "CHANGE_TXT")
    private String changeTxt;

    @Column(name = "IP")
    private String ip;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "OPER_TYPE")
    private String operType;

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
     * @return CLASS_NAME
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return METHOD_NAME
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return USER_ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return USER_NAME
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return OPER_REMARK
     */
    public String getOperRemark() {
        return operRemark;
    }

    /**
     * @param operRemark
     */
    public void setOperRemark(String operRemark) {
        this.operRemark = operRemark;
    }

    /**
     * @return CHANGE_TXT
     */
    public String getChangeTxt() {
        return changeTxt;
    }

    /**
     * @param changeTxt
     */
    public void setChangeTxt(String changeTxt) {
        this.changeTxt = changeTxt;
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return OPER_TYPE
     */
    public String getOperType() {
        return operType;
    }

    /**
     * @param operType
     */
    public void setOperType(String operType) {
        this.operType = operType;
    }


    @Override
    public String toString() {
        return "SysLog{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", operRemark='" + operRemark + '\'' +
                ", changeTxt='" + changeTxt + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", operType='" + operType + '\'' +
                '}';
    }
}