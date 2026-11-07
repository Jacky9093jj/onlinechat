package com.homelab.onlinechat;
import com.homelab.onlinechat.User;
import com.homelab.onlinechat.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDto) {
        Optional<User> checkExistingUser = userRepository.findByUserId(userDto.getUserId());
        if (checkExistingUser.isPresent()) {
            return "Registration Failed: User already exist";
        }

        User newUser = new User();
        newUser.setUserId(userDto.getUserId());
        newUser.setPassword(userDto.getPassword());

        userRepository.save(newUser);
        return "Registration success, your userID is：" + newUser.getUserId();
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDto) {
        Optional<User> checkLoginUser = userRepository.findByUserId(userDto.getUserId());
        if (checkLoginUser.isPresent()) {
           if (checkLoginUser.get().getPassword().equals(userDto.getPassword())){
               return "Login success, welcome back: " + userDto.getUserId();
           } else {
               return "Wrong password, try again";
           }
        }


        return "Login failed, no such user";
    }
}
