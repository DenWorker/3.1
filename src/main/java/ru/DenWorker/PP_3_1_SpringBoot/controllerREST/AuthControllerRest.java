package ru.DenWorker.PP_3_1_SpringBoot.controllerREST;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.DenWorker.PP_3_1_SpringBoot.dto.UserDto;
import ru.DenWorker.PP_3_1_SpringBoot.model.User;
import ru.DenWorker.PP_3_1_SpringBoot.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerRest {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthControllerRest(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("get_current_user")
    public UserDto getCurrentUser() {
        return convertToUserDto(userService.getAuthenticatedUser().getUser());
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
