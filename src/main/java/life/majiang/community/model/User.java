package life.majiang.community.model;

import lombok.Data;

/**
 * @author songjian
 * @date 2020/11/14 11:26
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAccountId(String accountId) {
//        this.accountId = accountId;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void setGmtCreate(Long gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public void setGmtModified(Long gmtModified) {
//        this.gmtModified = gmtModified;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getAccountId() {
//        return accountId;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public Long getGmtCreate() {
//        return gmtCreate;
//    }
//
//    public Long getGmtModified() {
//        return gmtModified;
//    }
}
