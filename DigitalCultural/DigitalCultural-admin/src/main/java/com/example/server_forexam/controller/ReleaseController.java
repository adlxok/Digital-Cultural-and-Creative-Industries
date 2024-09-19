package com.example.server_forexam.controller;

;
//import com.example.server_forexam.entity.ExamTask;
import com.example.server_forexam.entity.StudentToChoose;
import com.example.server_forexam.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/release")
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    /**
     * 获取学生列表
     * @@param identity
     * @return
     */
    @GetMapping("/studentlist")
    public List<StudentToChoose> getStudentList(@RequestParam String identity){

        return releaseService.getStudentList(identity);
    }
}
