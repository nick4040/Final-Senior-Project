package main.ClassManger3.Service;

import main.ClassManger3.Entity.CourceEntity;
import main.ClassManger3.Entity.CourceNavInfoEntity;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.linkbuilder.ILinkBuilder;

import java.io.IOException;
import java.util.List;

public interface UpdateClassService {

    void createCategoryPage() throws IOException;
    void createAssignmentPage() throws IOException;

    Boolean updateDefaultCategory(String categoryName, String courceId);

    Boolean updateDefaultClasses(String currentClassLink) throws IOException;

    Boolean deleteDefaultClasses(String currentClassLink);

    void checkAndGenerateC() throws IOException;

    void generateCourcesFile(CourceEntity cource, String CurrentC) throws IOException;

    void generateCategoryFile(CourceNavInfoEntity courceNavInfoEntity, String fileName) throws IOException;

    Boolean isNewCourse(CourceEntity newCourse);

    Boolean isNewCategory(String courceNavInfoEntity);

    TemplateEngine templateEngine(ResourceLoader resourceLoader);

    ILinkBuilder linkBuilder();

    List<Long> getCategoryIDs(Long CourceID);

    List<Long> getAssignmentIDs(Long CourceID);
}
