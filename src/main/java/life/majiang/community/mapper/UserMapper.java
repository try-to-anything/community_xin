package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author songjian
 * @date 2020/11/14 11:25
 */

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//这里是返回一个user的变量

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);//这里是返回一个user的变量

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token= #{token}, gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id=#{id}")
    void update(User dbUser);
}
