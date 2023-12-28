package heesu.websocketchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @GetMapping("/")
    public String home(){
        return "login";
    }
    @PostMapping("/room")
    public String enterRoom(@RequestParam(name = "nickname") String nickname, HttpSession httpSession, Model model) {
        model.addAttribute("name", nickname);
        return "room";
    }
}
