package com.iim.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iim.projet.Animal;
import com.iim.projet.service.CourseService;

@RestController
@RequestMapping("/classement")
public class ClassementController {

    private final CourseService courseService;

    public ClassementController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Animal> classement() {
        return courseService.classement();
    }
}
