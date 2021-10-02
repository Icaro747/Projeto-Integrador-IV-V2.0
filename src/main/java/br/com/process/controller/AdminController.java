package br.com.process.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller
public class AdminController {
    
    @RequestMapping("admin")
    public String admin(){
        return "AdminHome";
    }
}
