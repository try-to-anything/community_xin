package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    Integer count();
}
