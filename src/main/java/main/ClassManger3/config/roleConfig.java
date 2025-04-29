package main.ClassManger3.config;

import main.ClassManger3.Repo.UserRepo;
import main.ClassManger3.Service.RoleService;
import main.ClassManger3.Service.RoleServiceImpl;
import main.ClassManger3.Service.UpdateClassService;
import main.ClassManger3.Service.UpdateClassServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = "main.ClassManger3")
public class roleConfig {

    // Define a bean for RoleService
    @Bean
    public RoleService roleService() {
        return new RoleServiceImpl();
    }

    @Bean
    public UpdateClassService updateClassService(){
        return new UpdateClassServiceImpl();
    }
}

