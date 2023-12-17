package company.service.impl;

import company.dto.ProjectDTO;
import company.dto.UserDTO;
import company.entity.Project;
import company.entity.User;
import company.enums.Status;
import company.mapper.MapperUtil;
import company.repository.ProjectRepository;
import company.service.ProjectService;
import company.service.TaskService;
import company.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final MapperUtil mapperUtil;

    public ProjectServiceImpl(ProjectRepository projectRepository,@Lazy UserService userService, TaskService taskService, MapperUtil mapperUtil) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.taskService = taskService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
//        return projectMapper.convertToDto(project);
        return mapperUtil.convert(project, new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        List<Project> list = projectRepository.findAll();
//        return list.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
        return list.stream().map(project -> mapperUtil.convert(project,new ProjectDTO())).collect(Collectors.toList());

    }

    @Override
    public void save(ProjectDTO dto) {

        dto.setProjectStatus(Status.OPEN);

//        Project project = projectMapper.convertToEntity(dto);
        Project project = mapperUtil.convert(dto, new Project());
        projectRepository.save(project);


    }

    @Override
    public void update(ProjectDTO dto) {

        Project project = projectRepository.findByProjectCode(dto.getProjectCode());
//        Project convertedProject = projectMapper.convertToEntity(dto);
        Project convertedProject = mapperUtil.convert(dto, new Project());
        convertedProject.setId(project.getId());
        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);



    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        projectRepository.save(project);
//        taskService.deleteByProject(projectMapper.convertToDto(project));
        taskService.deleteByProject(mapperUtil.convert(project, new ProjectDTO()));

    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);

        projectRepository.save(project);
//        taskService.completeByProject(projectMapper.convertToDto(project));
        taskService.completeByProject(mapperUtil.convert(project, new ProjectDTO()));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDTO currentUserDto = userService.findByUserName(username);

//        User user = userMapper.convertToEntity(currentUserDto);
        User user = mapperUtil.convert(currentUserDto, new User());

        List<Project> list = projectRepository.findAllByAssignedManager(user);


        return list.stream().map(project -> {
//            ProjectDTO obj = projectMapper.convertToDto(project);
            ProjectDTO obj = mapperUtil.convert(project, new ProjectDTO());

            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));


            return obj;

        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllByAssignedManager(UserDTO assignedManager) {
//        List<Project> list = projectRepository.findAllByAssignedManager(userMapper.convertToEntity(assignedManager));
//        return list.stream().map(projectMapper::convertToDto).collect(Collectors.toList());

        List<Project> list = projectRepository.findAllByAssignedManager(mapperUtil.convert(assignedManager, new User()));
        return list.stream().map(project -> mapperUtil.convert(project, new ProjectDTO())).collect(Collectors.toList());
    }

}
