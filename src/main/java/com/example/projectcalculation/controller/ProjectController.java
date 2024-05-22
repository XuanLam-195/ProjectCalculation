package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.service.ProjectService;
import com.example.projectcalculation.utilities.Constant;
import com.example.projectcalculation.utilities.Utils;
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
    ProjectService projectService;

    @GetMapping("/overview/{id}")
    public String overview(@PathVariable("id") Long projectID, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        ProjectModel currentproject = projectService.findProjectByID(projectID);
        currentproject.setId(projectID);
        return "project/overview";
    }

    @GetMapping("/create")
    public String getCreateProject(HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        return "project/create";
    }


    @PostMapping("/save")
    public String createProject(ProjectModel project, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        projectService.createProject(project);
        redirectAttributes.addFlashAttribute("message", "Create " + project.getProjectName() + " is success");
        return "redirect:/workspace";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateProject(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        ProjectModel updateProject = projectService.findProjectByID(id);
        model.addAttribute("project", updateProject);
        session.setAttribute("currentproject", updateProject);
        return "project/edit";
    }


    @PostMapping("/update")
    public String updateProject(ProjectModel updateProject, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        projectService.updateProject(updateProject);
        redirectAttributes.addFlashAttribute("message", "Update " + updateProject.getProjectName() + " is success");
        return "redirect:/workspace";
    }

    @GetMapping("/delete/{id}")
    public String getDeletePage(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        String result = projectService.delete(id);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/workspace";
    }


}

