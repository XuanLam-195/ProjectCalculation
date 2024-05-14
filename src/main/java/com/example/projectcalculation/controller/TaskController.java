package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.model.TaskModel;
import com.example.projectcalculation.repository.ProjectRepository;
import com.example.projectcalculation.repository.SubProjectRepository;
import com.example.projectcalculation.repository.TaskRepository;
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
@RequestMapping("task")
public class TaskController {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SubProjectRepository subProjectRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/create/{subProjectId}")
    public String getCreateproject(@PathVariable Long subProjectId, Model model){
        SubProjectModel subProjectModel = subProjectRepository.findProjectByID(subProjectId);
        model.addAttribute("subProjectModel", subProjectModel);
        return "task/create";
    }

    @PostMapping("/save")
    public String createProject(TaskModel taskModel, RedirectAttributes redirectAttributes) {
        taskRepository.create(taskModel);
        SubProjectModel projectModel = subProjectRepository.findProjectByID(taskModel.getSubProjectId());
        redirectAttributes.addFlashAttribute("message", "Create task project " + taskModel.getTaskName() + " is succeed!");
        return "redirect:/subproject/overview/" + projectModel.getProjectId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateProject(@PathVariable("id") Long id, Model model, HttpSession session){
        SubProjectModel updateProject = subProjectRepository.findProjectByID(id);
        model.addAttribute("project", updateProject);
        return "subproject/edit";
    }

    @PostMapping("/update")
    public String updateProject(SubProjectModel updateProject, RedirectAttributes redirectAttributes){
        subProjectRepository.updateProject(updateProject);
        redirectAttributes.addFlashAttribute("message", "Update task" + updateProject.getProjectName() + " is succeed");
        return "redirect:/subproject/overview" + updateProject.getProjectId();
    }

}
