package life.majiang.community.service;

import life.majiang.community.dto.PageinationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.dto.QuestionQueryDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songjian
 * @date 2020/11/25 11:08
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    /***
     * 返回一个搜索后的问题标题结果
     * @param page
     * @param size
     * @param search
     * @return
     */
    public PageinationDTO list(Integer page, Integer size,String search) {

        // 将search分片
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);


        Integer totalPage;
        PageinationDTO pageinationDTO = new PageinationDTO();

        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        if (page < 1) {
            page = 1;
        }

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageinationDTO.setPagination(totalPage, page);
        Integer offset = page < 1 ? 0 : size * (page - 1);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageinationDTO.setData(questionDTOList);
        return pageinationDTO;
    }


//    /***
//     *
//     * @param page
//     * @param size
//     * @return 返回一个主页面的所有最新提问的问题
//     */
//    public PageinationDTO list(Integer page, Integer size) {
//        Integer totalPage;
//        PageinationDTO pageinationDTO = new PageinationDTO();
//        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
////        Integer count = questionMapper.count();
//        if (page < 1) {
//            page = 1;
//        }
//
//        if (totalCount % size == 0) {
//            totalPage = totalCount / size;
//        } else {
//            totalPage = totalCount / size + 1;
//        }
//
//        if (page < 1) {
//            page = 1;
//        }
//        if (page > totalPage) {
//            page = totalPage;
//        }
//
//        pageinationDTO.setPagination(totalPage, page);//这是得到pages，还有一些初始值的
//        Integer offset = size * (page - 1);
//
//
//        QuestionExample example = new QuestionExample();
//        example.setOrderByClause("gmt_create desc");
//        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
//        List<QuestionDTO> questionDTOList = new ArrayList<>();
//        for (Question question : questions) {
//            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setId((long) question.getId());
//            questionDTO.setUser(user);
//            questionDTOList.add(questionDTO);
//        }
//        pageinationDTO.setData(questionDTOList);
//        return pageinationDTO;
//    }

    /***
     *
     * @param userId
     * @param page
     * @param size
     * @return 返回userId的所有问题。作用于，用户本身的询问问题
     */
    public PageinationDTO list(Long userId, Integer page, Integer size) {
        PageinationDTO pageinationDTO = new PageinationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        Integer totalPage;
//        Integer count = questionMapper.countByUserId(userId);
        if (page < 1) {
            page = 1;
        }

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageinationDTO.setPagination(totalPage, page);//这是得到pages，还有一些初始值的
        Integer offset = size * (page - 1);
        questionExample.setOrderByClause("gmt_create desc");
//        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample
                ,new RowBounds(offset, size));
//        List<Question> questions = questionMapper.listByUserID(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageinationDTO.setData(questionDTOList);
        return pageinationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            //如果表里查不到相应的question，要抛出这个异常才可以。
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(questionDTO.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void CreateOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
//            questionMapper.crete(question);
        } else {
//            question.setGmtModified(System.currentTimeMillis());
            Question dbquestion = questionMapper.selectByPrimaryKey(question.getId());
            if (dbquestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                //更新之前应该也看看是否可以更新才可以。
            }
            if (dbquestion.getCreator().intValue() != question.getCreator().intValue()) {
                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
                //更新时候发现，两者的标签有变化。创始人的id应该创建Long的类型才对。
            }

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            //第一个参数是需要更新的内容，第二个是查找的条件
//            question.setGmtModified(System.currentTimeMillis());  这个一会试试
//            questionMapper.update(question);
        }
    }

    public void incView(Long id) {
        Question question = new Question();//这个是读取出question，然后修改一部分数据
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO1);//将question转化为questionDTO
            return questionDTO1;
        }).collect(Collectors.toList());

        return questionDTOS ;
    }
}
