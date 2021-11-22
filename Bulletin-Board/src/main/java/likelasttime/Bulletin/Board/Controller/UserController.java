package likelasttime.Bulletin.Board.Controller;

import likelasttime.Bulletin.Board.Repository.UserRepository;
import likelasttime.Bulletin.Board.domain.posts.Role;
import likelasttime.Bulletin.Board.domain.posts.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @PostMapping("/user/joinForm")
    public String join(User user){
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role=new Role();
        role.setId(1l);
        user.getRoles().add(role);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/user/list")
    public String list(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/user/{id}/form")
    public String updateForm(@PathVariable Long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/user/{id}")
    public String update(@PathVariable Long id, User newUser){
        User user=userRepository.findById(id).get();
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/login")
    public String loginForm(){
        return "/user/login";
    }

}
