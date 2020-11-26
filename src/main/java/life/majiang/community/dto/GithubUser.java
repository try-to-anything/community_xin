package life.majiang.community.dto;

import lombok.Data;

/**
 * @author songjian
 * @date 2020/11/13 10:59
 */


@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setBio(String bio) {
//        this.bio = bio;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getBio() {
//        return bio;
//    }
}
