package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.request.FacilityRequestModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.facility.SportFacility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SportFacilityService {
    SportFacility getSportFacilityById(Long id) throws FitforfunException, IOException;

    Page<SportFacility> listSportFacilities(int page, int limit, Specification<SportFacility> spec);

    Page<SportFacility> listSportFacilitiesBySport(int page, int limit, String sport);

    SportFacility createSportFacility(FacilityRequestModel sportFacility) throws FitforfunException;

    void deleteSportFacility(Long id) throws FitforfunException;

    SportFacility updateSportFacility(Long id, FacilityRequestModel sportFacility) throws FitforfunException;

    SportFacility commentSportFacility(User user, Long facilityId, Comment comment) throws FitforfunException;

    Page<SportFacility> findByNameContaining(String keyword, Pageable pageable);

    Page<SportFacility> findByCity(String city, Pageable pageable);

    Page<SportFacility> listFacilitiesBySportId(Long id, int page, int limit);

    void addImage(Long id, MultipartFile multipartFile, String type) throws Exception;

    void addInstructor(Long id, Instructor instructor) throws Exception;

    Comment addComment(Long id, CommentRequestModel comment) throws FitforfunException;

}
