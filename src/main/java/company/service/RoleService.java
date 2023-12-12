package company.service;

import company.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAllRoles();

    RoleDTO findById(Long id);


}
