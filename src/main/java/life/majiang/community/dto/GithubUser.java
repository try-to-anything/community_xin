package life.majiang.community.dto;

/**
 * @author songjian
 * @date 2020/11/13 10:59
 */
public class GithubUser {
    private String name;
    private Long id;
    private String bio;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }
}
