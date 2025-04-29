package main.ClassManger3.Controller;

import main.ClassManger3.Entity.CourceEntity;
import main.ClassManger3.Entity.UserEntity;
import main.ClassManger3.Repo.CourceRepo;
import main.ClassManger3.Repo.UserRepo;
import main.ClassManger3.Service.CourceService;
import main.ClassManger3.Service.RoleService;
import main.ClassManger3.Service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;


@Controller
public class NavbarController {

    @Autowired
    private CourceRepo courceRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleService roleService;


    @GetMapping("/")
    public String index(Model model){
        String currentRole = roleService.getRoleByUsername();

        if (currentRole.equals("admin")){
            return "MediaDash/adminViews/adminDashboerd";
        } else if (currentRole.equals("student")) {
            return "MediaDash/Dashboerd";
        } else {
            //error page
            return "MediaMain/errorPage";
        }

    }
    @GetMapping("/Dashboerd")
    public String Dashboerd(Model model) {
        // You can add any attributes you need to your model
        return "MediaDash/Dashboerd"; // This will return the "index.html" template
    }

    @GetMapping("/Assignment")
    public String Assignment(Model model) {
        // You can add any attributes you need to your model
        return "MediaDash/ActiveAssignment"; // This will return the "about.html" template
    }

    @GetMapping("/Cources")
    public String Cources(Model model) throws IOException {

        //Makes a list of cource Id's for that user
        List CourceIds = roleService.getCourceIDs();

        //finds the cources and outputs them
        List<CourceEntity> listCource = courceRepo.findAllById(CourceIds);
        model.addAttribute("listCource", listCource);
        
        return "MediaDash/Cources";
    }

//    @GetMapping("/{CurrentCourceUrl}")
//    public String CurrentCource(@PathVariable String CurrentCourceUrl){
//        CurrentCourceUrl = courceService.
//        return "MediaDash/Cources" + CurrentCourceUrl;
//    }

    @GetMapping("/Schedule")
    public String Schedule(Model model) {
        // You can add any attributes you need to your model
        return "MediaDash/MySch"; // This will return the "about.html" template
    }

    @GetMapping("/Grades")
    public String Grades(Model model) {
        // You can add any attributes you need to your model
        return "MediaDash/Grades"; // This will return the "contact.html" template
    }

    @GetMapping("/Account")
    public String Account(Model model) {
        // You can add any attributes you need to your model
        return "MediaDash/Account"; // This will return the "contact.html" template
    }

    @GetMapping("/LogOut")
    public String LogOut(Model model) {
        // You can add any attributes you need to your model
        return "MediaMain/LogOut"; // This will return the "contact.html" template
    }
}
