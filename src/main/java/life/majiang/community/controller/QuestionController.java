package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.WebParam.Mode;

/**
 * @author songjian
 * @date 2020/12/8 15:09
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id
    , Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        questionDTO.setId((long)id);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";//保证样式不变，每次返回的都是question页面。
    }
}
