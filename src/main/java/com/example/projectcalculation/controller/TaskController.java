package com.example.projectcalculation.controller;

import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.model.TaskModel;
import com.example.projectcalculation.service.AccountService;
import com.example.projectcalculation.service.ProjectService;
import com.example.projectcalculation.service.SubProjectService;
import com.example.projectcalculation.service.TaskService;
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
@RequestMapping("task")
public class TaskController {
    @Autowired
    ProjectService projectService;

    @Autowired
    SubProjectService subProjectService;

    @Autowired
    TaskService taskService;

    @Autowired
    AccountService accountService;

    @GetMapping("/create/{subProjectId}")
    public String getCreateProject(@PathVariable Long subProjectId, Model model, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        SubProjectModel subProjectModel = subProjectService.findProjectByID(subProjectId);
        model.addAttribute("subProjectModel", subProjectModel);
        model.addAttribute("accounts", accountService.getAllUserEmployee());
        return "task/create";
    }

    @PostMapping("/save")
    public String createProject(TaskModel taskModel, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        taskService.create(taskModel);
        SubProjectModel projectModel = subProjectService.findProjectByID(taskModel.getSubProjectId());
        redirectAttributes.addFlashAttribute("message", "Create task project " + taskModel.getTaskName() + " is succeed!");
        return "redirect:/subproject/overview/" + projectModel.getProjectId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateProject(@PathVariable("id") Long id, Model model, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        TaskModel taskModel = taskService.findByID(id);
        model.addAttribute("accounts",accountService.getAllUserEmployee());
        model.addAttribute("task", taskModel);
        return "task/edit";
    }

    @PostMapping("/update")
    public String updateProject(TaskModel taskModel, RedirectAttributes redirectAttributes, HttpSession session){
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        taskService.update(taskModel);
        SubProjectModel projectModel = subProjectService.findProjectByID(taskModel.getSubProjectId());
        redirectAttributes.addFlashAttribute("message", "Update task" + taskModel.getTaskName() + " is success!");
        return "redirect:/subproject/overview/" + projectModel.getProjectId();
    }

    @GetMapping("/delete/{id}/{projectId}")
    public String delete( @PathVariable("id") Long id, @PathVariable("projectId") Long projectId,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        String result = taskService.delete(id);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/project/overview/" + projectId;
    }

    @GetMapping("/list")
    public String overview(HttpSession session, Model model) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;

        Long userId = ((AccountModel)session.getAttribute(Constant.CURRENT_USER)).getId();
        model.addAttribute("tasks", taskService.findAllByCurrentUser(userId));
        return "task/list";
    }

}
