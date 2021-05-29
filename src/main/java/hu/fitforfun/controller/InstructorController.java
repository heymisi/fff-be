package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.request.InstructorRegistrationModel;
import hu.fitforfun.model.request.InstructorResponseModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.InstructorRepository;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.InstructorService;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/instructors")
@CacheConfig(cacheNames = {"instructors"})
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "")
    @Cacheable()
    public Page<Instructor> getInstructorsFiltered(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Or({@Spec(path = "user.firstName", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "user.lastName", params = "name", spec = LikeIgnoreCase.class)})
            @And({@Spec(path = "user.shippingAddress.city.cityName", params = "address", spec = EqualIgnoreCase.class)})
                    Specification<Instructor> spec) {
        return instructorService.listInstructorsFiltered(page, limit, spec);
    }

    @GetMapping(value = "/bySport")
    @Cacheable()
    public Page<Instructor> getInstructorsBySport(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sport", defaultValue = "none") String sport) {
        return instructorService.listInstructorsBySport(page, limit, sport);
    }


    @GetMapping("/{id}")
    @Cacheable()
    public Response get(@PathVariable Long id) {
        try {
            return Response.createOKResponse(instructorService.getInstructorById(id));
        } catch (FitforfunException | IOException e) {
            return Response.createErrorResponse("error to get Instructor");
        }
    }


    @GetMapping("/byUser/{userId}")
    @Cacheable()
    public Response getByUser(@PathVariable Long userId) {
        try {
            return Response.createOKResponse(instructorService.getInstructorByUserId(userId));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping("")
    @CacheEvict(allEntries = true)
    public Response createInstructor(@RequestBody InstructorRegistrationModel instructor) {
        try {
            return Response.createOKResponse(instructorService.createInstructor(instructor));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.createErrorResponse("error creating instructor");
        }
    }

    @PutMapping("/{id}")
    @CacheEvict(allEntries = true)
    public Response updateInstructor(@PathVariable Long id, @RequestBody InstructorResponseModel instructor) {
        try {
            instructorService.updateInstructor(id, instructor);
            return Response.createOKResponse("success instructor update");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }


    @PutMapping("/{id}/updateUser")
    @CacheEvict(allEntries = true)
    public Response updateInstructorUser(@PathVariable Long id, @RequestBody User user) {
        try {
            instructorService.updateInstructorUser(id, user);
            return Response.createOKResponse("success instructor user update");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(allEntries = true)
    public Boolean delete(@PathVariable Long id, @RequestParam(value = "pass") String pass) {
        try {
            return instructorService.deleteInstructor(id, pass);
        } catch (FitforfunException e) {
            return false;
        }
    }

    @GetMapping("/availableFacility")
    @Cacheable()
    public List<Instructor> getInstructorsByAvailableFacility() {
        return instructorService.listInstructorsByAvailableFacility();
    }

    @PostMapping("/{id}/addComment")
    @CacheEvict(allEntries = true)
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequestModel massage) {
        try {
            return Response.createOKResponse(instructorService.addComment(id, massage));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping("/{id}/uploadImage")
    public Response uploadImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file) {
        try {
            instructorService.addImage(id, file);
            return Response.createOKResponse("Success Image upload");
        } catch (Exception e) {
            return Response.createErrorResponse("Error during image upload");
        }
    }

}
