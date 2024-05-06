package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.repository.ProjectRepository;
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

    /* START OF PROJECT MAPPINGS BY STEFFEN */
    //A getmapping that shows all projects based on a workspaceId, puts the objects into an arrayList and into a model that show the projects on the page
    @GetMapping("")
    public String getHomepage(Model model) {
        List<ProjectModel> projectList = projectRepository.findAll(); //TODO Skal opdateres til getAllProjectsByWorkspaceID
        model.addAttribute("projects", projectList);
        return "workspace";
    }
}
