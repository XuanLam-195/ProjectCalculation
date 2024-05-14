package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.repository.ProjectRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("project")
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/overview/{id}")
    public String overview(@PathVariable("id") Long projectID, HttpSession session, Model model) {
        ProjectModel currentproject = projectRepository.findProjectByID(projectID);
        currentproject.setId(projectID);
        model.addAttribute("currentproject",currentproject);
        session.setAttribute("currentproject",currentproject);
        return "project/overview";
    }

    //A getmapping that shows the create project page
    @GetMapping("/create")
    public String getCreateproject() {
        return "project/create";
    }

    //A postmapping that request user input and creates a project object with that information. Return project page based on a workspaceId
    @PostMapping("/save")
    public String createProject(ProjectModel project) {
        projectRepository.createProject(project);
        return "redirect:/workspace";
    }

    //A getmapping that finds a project by a projectId and puts it into a model, to be able to show it on the page
    @GetMapping("/edit/{id}")
    public String showUpdateProject(@PathVariable("id") Long id, Model model, HttpSession session){
        ProjectModel updateProject = projectRepository.findProjectByID(id);
        model.addAttribute("project", updateProject);
        session.setAttribute("currentproject", updateProject);
        return "project/edit";
    }

    //A postmapping that request user input and shoots the updated version of the project to the database. Return appfrontpage based on a workspaceId
    @PostMapping("/update")
    public String updateProject(ProjectModel updateProject) {
        projectRepository.updateProject(updateProject);
        return "redirect:/workspace";
    }

    @GetMapping("project/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        projectRepository.deleteProjectById(id);
        return "redirect:/workspace";
    }
}

