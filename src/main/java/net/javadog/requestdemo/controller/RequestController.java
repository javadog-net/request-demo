package net.javadog.requestdemo.controller;

import net.javadog.requestdemo.entity.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * 请求管理
 *
 * @author hdx
 * @version V1.0
 * @date 2021年10月18日
 */
@Controller
public class RequestController {
    @GetMapping("/request")
    public String request(){
        return "request/index";
    }

    /**
     * @Description 实体嵌套List提交
     * @param teacher
     * @Return java.lang.String
     */
    @PostMapping("/f1")
    @ResponseBody
    public String f1(@RequestBody Teacher teacher){
        return teacher.toString();
    }

    /**
     * @Description 文件上传
     * @param file
     * @Return java.lang.String
     */
    @PostMapping("/f2")
    @ResponseBody
    public String f2(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        return fileName;
    }

    /**
     * @Description List实体上传
     * @param teachers
     * @Return java.lang.String
     */
    @PostMapping("/f3")
    @ResponseBody
    public String f3(@RequestBody List<Teacher> teachers) {
        return teachers.toString();
    }

    /**
     * @Description 数组上传
     * @param array
     * @Return java.lang.String
     */
    @PostMapping("/f4")
    @ResponseBody
    public int[] f4(@RequestParam(value = "array[]")int[] array) {
        return array;
    }
}
