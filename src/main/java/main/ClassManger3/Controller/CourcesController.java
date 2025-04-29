package main.ClassManger3.Controller;

import main.ClassManger3.Entity.CourceAssignmentEntity;
import main.ClassManger3.Entity.CourceNavInfoEntity;
import main.ClassManger3.Repo.CourceAssignmentRepo;
import main.ClassManger3.Repo.CourceNavRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class CourcesController {

    @Autowired
    private CourceNavRepo courceNavRepo;

    @Autowired
    private CourceAssignmentRepo courceAssignmentRepo;

    private static final String HTML_DIRECTORY = "C:\\Users\\ps4mi\\Desktop\\code\\Class Manger Project\\ClassManger3\\src\\main\\resources\\templates\\Cources\\";

    @GetMapping("/courses/{fileName}")
    @ResponseBody
    public byte[] getCourseHTML(@PathVariable String fileName, Model model) throws IOException {
        // Load the HTML file from the specified directory
        Resource resource = new FileSystemResource(HTML_DIRECTORY + fileName);

        List<CourceAssignmentEntity> courceA = courceAssignmentRepo.findAll();
        List<CourceNavInfoEntity> courceN = courceNavRepo.findAll();

        model.addAttribute("CourceA", courceA);
        model.addAttribute("CourceN", courceN);


        // Check if the file exists
        if (resource.exists()) {
            // Read the file content as bytes
            Path path = Paths.get(resource.getURI());
            return Files.readAllBytes(path);
        } else {
            // Return an error message if the file doesn't exist
            return ("File not found: " + fileName).getBytes();
        }
    }
}

