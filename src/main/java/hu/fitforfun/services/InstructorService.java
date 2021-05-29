package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.request.InstructorRegistrationModel;
import hu.fitforfun.model.request.InstructorResponseModel;
import hu.fitforfun.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstructorService {
    Instructor getInstructorById(Long id) throws FitforfunException, IOException;

    Instructor getInstructorByUserId(Long userId) throws FitforfunException;

    Page<Instructor> listInstructors(int page, int limit);

    Page<Instructor> listInstructorsBySport(int page, int limit, String sport);

    Page<Instructor> listInstructorsFiltered(int page, int limit, Specification<Instructor> spec);

    Instructor createInstructor(InstructorRegistrationModel instructor) throws Exception;

    Boolean deleteInstructor(Long id, String password) throws FitforfunException;

    Instructor updateInstructor(Long id, InstructorResponseModel instructor) throws FitforfunException;

    Instructor commentInstructor(User user, Long instructorId, Comment comment) throws FitforfunException;

    List<Instructor> listInstructorsByAvailableFacility();

    void updateInstructorUser(Long id, User user) throws FitforfunException;

    Comment addComment(Long id, CommentRequestModel massage) throws FitforfunException;

    void addImage(Long id, MultipartFile multipartFile) throws Exception;

}
