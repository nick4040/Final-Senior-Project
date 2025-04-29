package main.ClassManger3.Service;

import main.ClassManger3.Entity.EnrollmentEntity;
import main.ClassManger3.Entity.UserEntity;
import main.ClassManger3.Repo.EnrollmentRepo;
import main.ClassManger3.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Override
    public String getUsername() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                username = userDetails.getUsername();
            }
        }
        return username;
    }

    @Override
    public String getRoleByUsername() {
        UserEntity user = userRepo.findByUsername(getUsername());
        return (user != null) ? user.getRole() : null;
    }

    @Override
    public List<Long> getCourceIDs() {
        Long userID;
        List<Long> courseIDs = new ArrayList<>();

        UserEntity user = userRepo.findByUsername(getUsername());
        if (user != null) {
            userID = user.getStudentID();
        } else {
            return courseIDs;
        }

        List<EnrollmentEntity> enrollmentEntities = enrollmentRepo.findByStudentID(userID);
        for (EnrollmentEntity enrollmentEntity : enrollmentEntities) {
            Long courseID = enrollmentEntity.getCourceID();
            if (courseID != null) {
                courseIDs.add(courseID);
            }
        }
        return courseIDs;
    }
}
