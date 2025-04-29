package main.ClassManger3.Service;

import jakarta.annotation.PostConstruct;
import main.ClassManger3.Entity.CourceAssignmentEntity;
import main.ClassManger3.Entity.CourceEntity;
import main.ClassManger3.Entity.CourceNavInfoEntity;
import main.ClassManger3.Repo.CourceAssignmentRepo;
import main.ClassManger3.Repo.CourceNavRepo;
import main.ClassManger3.Repo.CourceRepo;
import main.ClassManger3.config.customLinkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.linkbuilder.ILinkBuilder;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateClassServiceImpl implements UpdateClassService {

    @Autowired
    private CourceRepo courceRepo;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private CourceNavRepo courceNavRepo;

    @Autowired
    private CourceAssignmentRepo courceAssignmentRepo;

    @Override
    public void createAssignmentPage() throws IOException {
        List<CourceAssignmentEntity> asignments = courceAssignmentRepo.findAll();

        for (CourceAssignmentEntity assignment : asignments){
            String asignmentLink = assignment.getAssignmentName() + assignment.getCategory() + assignment.getCourceID() + ".html";
            String cleanAsignmentLink = asignmentLink.replaceAll("\\s", "");
            assignment.setLinkToAssignment(cleanAsignmentLink);
            courceAssignmentRepo.save(assignment);
            generateAsignment(assignment, cleanAsignmentLink);
        }
    }

    private void generateAsignment(CourceAssignmentEntity assignment, String cleanAsignmentLink) throws IOException {

        Context context = new Context();
        context.setVariable("assignment", assignment);

        String htmlContent = templateEngine.process("AutoTemp/BlankAssignment.html", context);
        String filePath = "src/main/resources/templates/Cources/" + cleanAsignmentLink;

        try(Writer writer = new FileWriter(filePath)) {
            writer.write(htmlContent);
        }
    }

    @Override
    //@PostConstruct
    public void createCategoryPage() throws IOException {
        List<CourceNavInfoEntity>  categorys = courceNavRepo.findAll();

        for (CourceNavInfoEntity category : categorys){
            String CurrentCategoryLink = category.getCourceID() + category.getCatogory() + ".html";
            String CleanLink = CurrentCategoryLink.replaceAll("\\s", "");
            category.setCatogoryLink(CleanLink);
            courceNavRepo.save(category);

//            if (isNewCategory(currentCategory)){ //again not working how its supposed too edit method
            generateCategoryFile(category, CleanLink);
//            } else {
//                System.out.println("error creating cat file already exist");
//            }
        }
    }

    @Override
    public void generateCategoryFile(CourceNavInfoEntity courceNavInfoEntity, String filename) throws IOException{

        List assignmentIds = getAssignmentIDs(courceNavInfoEntity.getCourceID());
        List<CourceAssignmentEntity> courceList = courceAssignmentRepo.findAllById(assignmentIds);

        Context context = new Context();
        context.setVariable("category", courceNavInfoEntity);
        context.setVariable("assignment", courceList);

        String htmlContent = templateEngine.process("AutoTemp/BlankCategory.html", context);

        String filePath = "src/main/resources/templates/Cources/" + filename;
        try (Writer writer = new FileWriter(filePath)) {
            writer.write(htmlContent);
        }
    }
    @Override
    public Boolean updateDefaultCategory(String categoryName, String courceId){
        String currentDefaultFilename = courceId + categoryName;
        String cleanfile = currentDefaultFilename.replaceAll("\\s", "");
        String filePath = "src/main/resources/templates/Cources/" + cleanfile + ".html";

        File file = new File(filePath);

        if (file.exists()){
            file.delete();
        } else {
            return false;
        }

        try {
            createCategoryPage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean updateDefaultClasses(String currentClassLink) throws IOException {

        if (deleteDefaultClasses(currentClassLink)){

            checkAndGenerateC();

        } else {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteDefaultClasses(String currentClassLink) {
        List<String> DefaultCName = new ArrayList<>();
        int i = 0;
        // create a list with default html templates
        DefaultCName.add("Grades");
        DefaultCName.add("Announc");
        DefaultCName.add("Help");
        DefaultCName.add("Start");
        //DefaultCName.add("Dash");//major note cause dash does not exist
        DefaultCName.add("");//this is to handle the index of the class

        while (i < DefaultCName.size()){

            String currentDefaultFilename = DefaultCName.get(i) + "course" + currentClassLink + ".html";
            String filePath = "src/main/resources/templates/Cources/" + currentDefaultFilename;

            File file = new File(filePath);

            if (file.exists()){
                file.delete();
            } else {
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
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

//            if (isNewCourse(cource)) { when we check if it a new cource it breaks it so fix isnewCource
                for (String CurrentCName : DefaultCName){
                    generateCourcesFile(cource, CurrentCName);
                }
//            }
        }
    }

    @Override
    public void generateCourcesFile(CourceEntity cource, String CurrentC) throws IOException {
        Context context = new Context();

        List categoryIds = getCategoryIDs(cource.getId());
        List<CourceNavInfoEntity> categoryList = courceNavRepo.findAllById(categoryIds);
        // newly added to add correct categorys to the right class
        context.setVariable("listCategory", categoryList);
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

    @Override
    public Boolean isNewCourse(CourceEntity newCourse) {
        List<CourceEntity> existingCourses = courceRepo.findAll();
        for (CourceEntity existingCourse : existingCourses) {
            if (existingCourse.equals(newCourse)) {
                return false; // Course already exists
            }
        }
        return true; // New course
    }

    @Override
    public Boolean isNewCategory(String courceNavInfoEntity){
        List<CourceNavInfoEntity> existingCategorys = courceNavRepo.findAll();
        for (CourceNavInfoEntity existingCategory : existingCategorys){
            if (existingCategory.equals(courceNavInfoEntity)) {
                return true;
            }
        }
        return false;
    }

    ///Helper Classes below///

    @Override
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

    @Override
    public ILinkBuilder linkBuilder() {
        return new customLinkHandler();
    }

    @Override
    public List<Long> getCategoryIDs(Long courceID) {
        List<Long> categoryIds = new ArrayList<>();
        List<CourceNavInfoEntity> courceNavInfoEntities = courceNavRepo.findAll();

        for (CourceNavInfoEntity cNav : courceNavInfoEntities) {
            if (cNav != null){
                if (cNav.getCourceID().equals(courceID)) {
                    categoryIds.add(cNav.getCourceNavID());
                }
            }
        }

        return categoryIds;
    }

    @Override
    public List<Long> getAssignmentIDs(Long CourceID) {
        List<Long> assignmentList = new ArrayList<>();
        List<CourceAssignmentEntity> courceAssignmentEntities = courceAssignmentRepo.findAll();

        for (CourceAssignmentEntity cAssignment : courceAssignmentEntities){
            if (cAssignment != null){
                if (cAssignment.getCourceID().equals(CourceID)){
                    assignmentList.add(cAssignment.getAssignmentID());
                }
            }
        }
        return assignmentList;
    }


}
