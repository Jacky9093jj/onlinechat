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
    public String register(@RequestBody User user) {
        Optional<User> checkExistingUser = userRepository.findByUserId(user.getUserId());
        if (checkExistingUser.isPresent()) {
            return "Registration Failed: User already exist";
        }


        userRepository.save(user);
        return "Registration success, your userID is：" + user.getUserId();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> checkLoginUser = userRepository.findByUserId(user.getUserId());
        if (checkLoginUser.isPresent()) {
           if (checkLoginUser.get().getPassword().equals(user.getPassword())){
               return "Login success, welcome back: " + user.getUserId();
           } else {
               return "Wrong password, try again";
           }
        }


        return "Login failed, no such user";
    }
}
