package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.repository.ProjectRepository;

import com.example.projectcalculation.utilities.Constant;
import com.example.projectcalculation.utilities.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"workspace", ""})
public class WorkspaceController {
    @Autowired
    ProjectRepository projectRepository;


    @GetMapping("")
    public String getHomepage(Model model, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        List<ProjectModel> projectList = projectRepository.findAll();
        model.addAttribute("projects", projectList);
        return "workspace";
    }
}
