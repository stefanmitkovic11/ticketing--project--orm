package company.mapper;

import company.dto.RoleDTO;
import company.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


//    Convert to Entity

    public Role convertToEntity(RoleDTO roleDTO){

        return modelMapper.map(roleDTO, Role.class);
    }

//    Convert to DTO

    public RoleDTO convertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
