package company.mapper;

import company.dto.UserDTO;
import company.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper mapper;


    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


//    Convert to entity
    public User convertToEntity(UserDTO userDTO){

      return mapper.map(userDTO, User.class);
    }

//    Convert to DTO

    public UserDTO convertToDTO(User user){

        return mapper.map(user,UserDTO.class);
    }


}
