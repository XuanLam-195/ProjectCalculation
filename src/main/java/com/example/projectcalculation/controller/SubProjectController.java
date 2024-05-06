package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.repository.ProjectRepository;
import com.example.projectcalculation.repository.SubProjectRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("subproject")
public class SubProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SubProjectRepository subProjectRepository;

    @GetMapping("/overview/{id}")
    public String overview(@PathVariable("id") Long projectID, HttpSession session, Model model) {
        ProjectModel currentproject = projectRepository.findProjectByID(projectID);
        currentproject.setId(projectID);
        model.addAttribute("currentproject", currentproject);
        session.setAttribute("currentproject", currentproject);

        List<SubProjectModel> subProjectModelList = subProjectRepository.findAllByProjectId(projectID);
        model.addAttribute("subProjectModelList", subProjectModelList);
        return "subproject/overview";
    }

    @GetMapping("/create/{projectId}")
    public String getCreateproject(@PathVariable Long projectId, Model model) {
        ProjectModel currentproject = projectRepository.findProjectByID(projectId);
        model.addAttribute("currentproject", currentproject);
        return "subproject/create";
    }

    @PostMapping("/save")
    public String createProject(SubProjectModel project, RedirectAttributes redirectAttributes) {
        subProjectRepository.createProject(project);
        redirectAttributes.addFlashAttribute("message", "Create sub project " + project.getProjectName() + " is success!");
        return "redirect:/subproject/overview/" + project.getProjectId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateProject(@PathVariable("id") Long id, Model model, HttpSession session) {
        SubProjectModel updateProject = subProjectRepository.findProjectByID(id);
        model.addAttribute("project", updateProject);
        return "subproject/edit";
    }

    @PostMapping("/update")
    public String updateProject(SubProjectModel updateProject, RedirectAttributes redirectAttributes) {
        subProjectRepository.updateProject(updateProject);
        redirectAttributes.addFlashAttribute("message", "Update sub project " + updateProject.getProjectName() + " is success!");
        return "redirect:/subproject/overview/" + updateProject.getProjectId();
    }


}

