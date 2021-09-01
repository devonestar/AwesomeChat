package com.awesome.controllers.api.v1;

import com.awesome.applications.service.ProjectTaskUserService;
import com.awesome.domains.projecttask.dtos.ProjectTaskDTO;
import com.awesome.domains.projecttask.services.ProjectTaskService;
import com.awesome.domains.user.dtos.UserDTO;
import com.awesome.dtos.ProjectTaskDetail;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/tasks")
public class ProjectTaskController {
    private final ProjectTaskService projectTaskService;
    private final ProjectTaskUserService projectTaskUserService;

    /**
     * 1. 프로젝트 타스크/이슈 리스트
     * @return
     */
    @GetMapping("/")
    public List<ProjectTaskDTO> projectTaskList() {
        return projectTaskService.getProjectTaskList();
    }

    /**
     * 2. 특정 프로젝트 타스크/이슈
     * @param taskId
     * @return
     */
    @GetMapping("/{taskId}")
    public ProjectTaskDetail projectTaskOne(@PathVariable("taskId") Long taskId) {
        ProjectTaskDTO projectTask = projectTaskService.getProjectTask(taskId);
        List<Long> userIds = projectTaskUserService.getProjectTaskUserIdList(taskId);

        ProjectTaskDetail projectTaskDetail = new ProjectTaskDetail();
        projectTaskDetail.setProjectTaskId(taskId);
        projectTaskDetail.setProjectTaskName(projectTask.getProjectTaskName());
        projectTaskDetail.setProjectId(projectTask.getProjectId());
        projectTaskDetail.setParentTaskId(projectTask.getParentTaskId());
        projectTaskDetail.setTaskPriority(projectTask.getTaskPriority());
        projectTaskDetail.setTaskSummary(projectTask.getSummary());
        projectTaskDetail.setTaskType(projectTask.getType());
        projectTaskDetail.setTaskStartDate(projectTask.getTaskStartDate());
        projectTaskDetail.setTaskEndDate(projectTask.getTaskEndDate());
        projectTaskDetail.setUserIds(userIds);

        return projectTaskDetail;
    }

    /**
     * 3. 특정 프로젝트의 타스크 리스트
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}/tasks")
    public List<ProjectTaskDTO> projectTaskList(@PathVariable("projectId") Long projectId) {
        List<ProjectTaskDTO> projectTaskList = projectTaskService.getProjectTaskListByProjectId(projectId);

        return projectTaskList;
    }

    /**
     * 4. 프로젝트 타스크/이슈 생성
     * @param projectTaskDetail
     * @return
     */
    @PostMapping("/")
    public ProjectTaskDetail createProjectTask(@RequestBody ProjectTaskDetail projectTaskDetail) {
        ProjectTaskDTO projectTaskDTO = getProjectTaskDTO(projectTaskDetail);
        List<Long> userIds = projectTaskDetail.getUserIds();

        ProjectTaskDTO createdProjectTask = projectTaskUserService.createTask(projectTaskDTO, userIds);

        ProjectTaskDetail createProjectTaskDetail = new ProjectTaskDetail();
        createProjectTaskDetail.setProjectTaskId(createdProjectTask.getId());
        createProjectTaskDetail.setProjectTaskName(createdProjectTask.getProjectTaskName());
        createProjectTaskDetail.setProjectId(createdProjectTask.getProjectId());
        createProjectTaskDetail.setParentTaskId(createdProjectTask.getParentTaskId());
        createProjectTaskDetail.setTaskPriority(createdProjectTask.getTaskPriority());
        createProjectTaskDetail.setTaskSummary(createdProjectTask.getSummary());
        createProjectTaskDetail.setTaskType(createdProjectTask.getType());
        createProjectTaskDetail.setTaskStartDate(createdProjectTask.getTaskStartDate());
        createProjectTaskDetail.setTaskEndDate(createdProjectTask.getTaskEndDate());
        createProjectTaskDetail.setUserIds(userIds);

        return createProjectTaskDetail;
    }

    /**
     * 5. 프로젝트 타스크/이슈 수정
     * @param projectTaskDetail
     * @return
     */
    @PutMapping("/")
    public ProjectTaskDTO projectTaskUpdate(@RequestBody ProjectTaskDetail projectTaskDetail) {
        ProjectTaskDTO projectTaskDTO = getProjectTaskDTO(projectTaskDetail);
        List<Long> userIds = projectTaskDetail.getUserIds();

        ProjectTaskDTO updatedProjectTask = projectTaskUserService.updateProjectTask(projectTaskDTO, userIds);

        return updatedProjectTask;
    }

    /**
     * 6. 프로젝트 타스크/이슈 삭제
     * @param taskId
     * @return
     */
    @DeleteMapping("/{taskId}")
    public String projectTaskDelete(@PathVariable("taskId") Long taskId) {
        projectTaskService.deleteProjectTask(taskId);

        return null;
    }

    private ProjectTaskDTO getProjectTaskDTO(ProjectTaskDetail projectTaskDetail) {
        ProjectTaskDTO projectTaskDTO = new ProjectTaskDTO();
        projectTaskDTO.setId(projectTaskDetail.getProjectTaskId());
        projectTaskDTO.setProjectTaskName(projectTaskDetail.getProjectTaskName());
        projectTaskDTO.setProjectId(projectTaskDetail.getProjectId());
        projectTaskDTO.setParentTaskId(projectTaskDetail.getParentTaskId());
        projectTaskDTO.setTaskPriority(projectTaskDetail.getTaskPriority());
        projectTaskDTO.setSummary(projectTaskDetail.getTaskSummary());
        projectTaskDTO.setType(projectTaskDetail.getTaskType());
        projectTaskDTO.setTaskStartDate(projectTaskDetail.getTaskStartDate());
        projectTaskDTO.setTaskEndDate(projectTaskDetail.getTaskEndDate());
        return projectTaskDTO;
    }
}