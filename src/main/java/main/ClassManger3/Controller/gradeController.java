package main.ClassManger3.Controller;

import main.ClassManger3.Entity.*;
import main.ClassManger3.Repo.CourceAssignmentRepo;
import main.ClassManger3.Repo.GradeRepo;
import main.ClassManger3.Repo.TeacherGradesRepo;
import main.ClassManger3.Service.GradeService;
import main.ClassManger3.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class gradeController {

    @Autowired
    private CourceAssignmentRepo courceAssignmentRepo;

    @Autowired
    private GradeRepo gradeRepo;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private TeacherGradesRepo teacherGradesRepo;

    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");

    public gradeController() throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    @GetMapping("/adminSubmitAssignment")
    public String showAdminSubmitAssignment(Model model) {

        model.addAttribute("AddSubmissionForm", new GradesEntity());

        List<CourceAssignmentEntity> assignmentRepo1 = courceAssignmentRepo.findAll();
        model.addAttribute("allAssignments", assignmentRepo1);

        return "MediaDash/adminViews/adminSubmitAssignment";
    }


    @PostMapping("/adminSubmitAssignment")
    public String uploadAdminSubmitAssignment(@ModelAttribute("AddSubmissionForm") GradesEntity submittedAssignment, @RequestParam("file") MultipartFile file, Model model) {

        try {
            if (file.isEmpty()) {
                model.addAttribute("message", "Please select a file to upload.");
                return "MediaDash/adminViews/adminSubmitAssignment";
            }

            // Clean filename and save file
            String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
            Path destination = uploadDir.resolve(fileName);
            file.transferTo(destination.toFile());

            // Set the filename into the entity
            submittedAssignment.setSubmissionName(fileName);

            // Save the grade submission to DB after setting the filename
            gradeRepo.save(submittedAssignment);

            // Populate assignment name from related assignment entity
            gradeService.insertAssignmentDetails();

            model.addAttribute("message", "File uploaded successfully: " + fileName);
            return "MediaDash/adminViews/adminSubSuc"; // success page

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
            return "MediaDash/adminViews/adminSubmitAssignment"; // stay on upload page if error
        }
    }


    @GetMapping("/adminGradeAssignment")
    public String listUploadedFiles(Model model) throws IOException {

        List<GradesEntity> allAssignments = gradeRepo.findAll();
        model.addAttribute("listAssignments", allAssignments);
        model.addAttribute("GradeForm", new TeacherGradesEntity());

        try (Stream<Path> paths = Files.list(uploadDir)) {
            model.addAttribute("files", paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList()));
        }
        return "MediaDash/adminViews/adminGradeAssignment";
    }

    @PostMapping("/adminGradeAssignment")
    public String submitGrades(@ModelAttribute TeacherGradesEntity teacherGradesEntity) throws IOException{

        teacherGradesRepo.save(teacherGradesEntity);
        gradeService.insertSubmissionName();

        return "MediaDash/adminViews/adminSubSuc";
    }

    // Download a file
    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path file = uploadDir.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

