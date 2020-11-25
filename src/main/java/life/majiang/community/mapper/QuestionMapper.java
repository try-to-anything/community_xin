package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author songjian
 * @date 2020/11/16 15:44
 */
@Component
@Mapper
public interface QuestionMapper {
    /***
     * 插入问题的命令，这里需要时接口命令
     * @param question
     */
    @Insert("insert into question (title,description,gmt_create," +
            "gmt_modified,creator,tag) values (#{title}," +
            "#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void crete(Question question);
}
