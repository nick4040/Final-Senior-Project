package main.ClassManger3.Service;

import jakarta.annotation.PostConstruct;
import main.ClassManger3.Entity.CourceEntity;
import main.ClassManger3.Repo.CourceRepo;
import main.ClassManger3.config.customLinkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.linkbuilder.ILinkBuilder;
import org.thymeleaf.spring6.context.webmvc.SpringWebMvcThymeleafRequestContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourceService {

    @Autowired
    private CourceRepo courceRepo;

    @Autowired
    private TemplateEngine templateEngine;


    //@PostConstruct
    public void checkAndGenerateC() throws IOException {
        List<String> DefaultCName = new ArrayList<>();
        //List<String> CurrentHtmlFile = new ArrayList<>();
        List<CourceEntity> cources = courceRepo.findAll();

        DefaultCName.add("Grades");
        DefaultCName.add("Announc");
        DefaultCName.add("Help");
        DefaultCName.add("Start");
        DefaultCName.add("Dash");

        for (CourceEntity cource : cources) {

            String currentCource = "course" + cource.getId() + ".html";
            cource.setClass_link(currentCource); // this sends the correct id to link them together
            courceRepo.save(cource);

            if (isNewCourse(cource)) {
                for (String CurrentCName : DefaultCName){
                    generateCourcesFile(cource, CurrentCName);
                }
            }
        }
    }

    private void generateCourcesFile(CourceEntity cource, String CurrentC) throws IOException {
        Context context = new Context();
        context.setVariable("listCource", cource);

        String htmlContent = templateEngine.process("AutoTemp/" + CurrentC, context);

        if (CurrentC.equals("Dash")){//major note this is causing dash not to be created 
            // Save the HTML file to a folder
            String fileName = "course" + cource.getId() + ".html";//this line is to name the file
            String filePath = "src/main/resources/templates/Cources/" + fileName;
            try (Writer writer = new FileWriter(filePath)) {
                writer.write(htmlContent);
            }
        } else {
            // Save the HTML file to a folder
            String fileName = CurrentC + "course" + cource.getId() + ".html";//this line is to name the file
            String filePath = "src/main/resources/templates/Cources/" + fileName;
            try (Writer writer = new FileWriter(filePath)) {
                writer.write(htmlContent);
            }
        }
    }

    private boolean isNewCourse(CourceEntity newCourse) {
        List<CourceEntity> existingCourses = courceRepo.findAll();
        for (CourceEntity existingCourse : existingCourses) {
            if (existingCourse.equals(newCourse)) {
                return false; // Course already exists
            }
        }
        return true; // New course
    }

    // You may need a custom template resolver depending on your project structure
    public TemplateEngine templateEngine(ResourceLoader resourceLoader) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.setLinkBuilder(linkBuilder());

        return engine;
    }

    private ILinkBuilder linkBuilder(){
        return new customLinkHandler();
    }
}

