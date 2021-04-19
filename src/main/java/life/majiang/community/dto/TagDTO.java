package life.majiang.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author songjian
 * @date 2021/4/19 20:55
 */
@Data
public class TagDTO {
    private String categoryName;//变量名不用大写的英文。
    private List<String> tags;
}
