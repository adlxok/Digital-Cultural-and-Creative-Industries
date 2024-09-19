package com.example.server_forexam.dao;

//import com.example.server_forexam.entity.ExamTask;
import com.example.server_forexam.entity.StudentToChoose;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ReleaseMapper {

    /**
     * 获取学生列表
     * @param identity
     * return
     */
    List<StudentToChoose> getStudentList(String identity);

}
