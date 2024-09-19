package com.example.server_forexam.service.impl;


//import com.example.server_forexam.entity.ExamTask;
import com.example.server_forexam.entity.StudentToChoose;
import com.example.server_forexam.dao.ReleaseMapper;
import com.example.server_forexam.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReleaseImpl implements ReleaseService {

    @Autowired
    private ReleaseMapper releaseMapper;

    @Override
    public List<StudentToChoose> getStudentList(String identity) {
        List<StudentToChoose> studentList = releaseMapper.getStudentList(identity);
        return studentList;
    }
}

