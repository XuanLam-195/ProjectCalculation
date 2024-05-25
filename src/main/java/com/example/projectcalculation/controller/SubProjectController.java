package com.example.projectcalculation.controller;


import com.example.projectcalculation.dto.ResponseTaskDto;
import com.example.projectcalculation.dto.TaskDto;
import com.example.projectcalculation.model.ProjectModel;
import com.example.projectcalculation.model.SubProjectModel;
import com.example.projectcalculation.model.TaskModel;
import com.example.projectcalculation.service.ProjectService;
import com.example.projectcalculation.service.SubProjectService;
import com.example.projectcalculation.service.TaskService;
import com.example.projectcalculation.utilities.Constant;
import com.example.projectcalculation.utilities.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("subproject")
public class SubProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    SubProjectService subProjectService;

    @Autowired
    TaskService taskService;

    @GetMapping("/overview/{id}")
    public String overview(@PathVariable("id") Long projectID, HttpSession session, Model model) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        ProjectModel currentproject = projectService.findProjectByID(projectID);
        currentproject.setId(projectID);
        model.addAttribute("currentproject", currentproject);
        session.setAttribute("currentproject", currentproject);

        List<SubProjectModel> subProjectModelList = subProjectService.finAllSubProject(projectID);
        model.addAttribute("subProjectModelList", subProjectModelList);
        return "subproject/overview";
    }

    @GetMapping("/create/{projectId}")
    public String getCreateproject(@PathVariable Long projectId, Model model, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        ProjectModel currentproject = projectService.findProjectByID(projectId);
        model.addAttribute("currentproject", currentproject);
        return "subproject/create";
    }

    @PostMapping("/save")
    public String createProject(SubProjectModel project, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        subProjectService.createProject(project);
        redirectAttributes.addFlashAttribute("message", "Create sub project " + project.getProjectName() + " is success!");
        return "redirect:/subproject/overview/" + project.getProjectId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateProject(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        SubProjectModel updateProject = subProjectService.findProjectByID(id);
        model.addAttribute("project", updateProject);
        return "subproject/edit";
    }

    @PostMapping("/update")
    public String updateProject(SubProjectModel updateProject, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        subProjectService.updateProject(updateProject);
        redirectAttributes.addFlashAttribute("message", "Update subproject " + updateProject.getProjectName() + " is success!");
        return "redirect:/subproject/overview/" + updateProject.getProjectId();
    }

    @GetMapping("/getTaskBySubProjectId/{id}")
    @ResponseBody
    public ResponseTaskDto getTasksBySubProjectId(@PathVariable("id") Long id) {
        ResponseTaskDto responseTaskDto = new ResponseTaskDto();
        List<TaskDto> taskDtos = new ArrayList<>();
        SubProjectModel subProjectModel = subProjectService.findProjectByID(id);
        responseTaskDto.setTasksHeader("Tasks of " + subProjectModel.getProjectName());
        List<TaskModel> taskModelList = taskService.findAllBySubProject(id);
        for (TaskModel taskModel : taskModelList) {
            taskDtos.add(taskModel.toTaskDto());
        }
        responseTaskDto.setTasks(taskDtos);
        return responseTaskDto;
    }

    @GetMapping("/delete/{id}/{projectId}")
    public String delete(@PathVariable("id") Long id, @PathVariable("projectId") Long prjectId, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!Utils.validSession(session))
            return Constant.RETURN_LOGIN;
        String result = subProjectService.delete(id);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/subproject/overview/" + prjectId;
    }


}

