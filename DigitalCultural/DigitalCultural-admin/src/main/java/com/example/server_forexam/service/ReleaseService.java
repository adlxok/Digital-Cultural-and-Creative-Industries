package com.example.server_forexam.service;

//import com.example.server_forexam.entity.ExamTask;
import com.example.server_forexam.entity.StudentToChoose;

import java.util.List;
public interface ReleaseService {
    public List<StudentToChoose> getStudentList(String identity);
}
