package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * @author songjian
 * @date 2020/11/25 11:04
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private String description;
    private User user;
}
