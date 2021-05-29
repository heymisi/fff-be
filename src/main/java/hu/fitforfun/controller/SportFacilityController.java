package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.request.FacilityRequestModel;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.SportFacilityService;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/facilities")
@CacheConfig(cacheNames = {"facilities"})
public class SportFacilityController {

    @Autowired
    private SportFacilityService sportFacilityService;
    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    @Cacheable()
    public Response getSportFacilityById(@PathVariable Long id) {
        try {
            return Response.createOKResponse(sportFacilityService.getSportFacilityById(id));
        } catch (FitforfunException | IOException e) {
            return Response.createErrorResponse("error to get Facility");
        }
    }

    @PostMapping({"", "/"})
    @CacheEvict(allEntries = true)
    public Response saveSportFacility(@RequestBody FacilityRequestModel sportFacility) {
        try {
            return Response.createOKResponse(sportFacilityService.createSportFacility(sportFacility));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PutMapping("/{id}")
    @CacheEvict(allEntries = true)
    public Response updateSportFacility(@PathVariable Long id, @RequestBody FacilityRequestModel sportFacility) {
        try {
            return Response.createOKResponse(sportFacilityService.updateSportFacility(id, sportFacility));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(allEntries = true)
    public Response deleteSportFacility(@PathVariable Long id) {
        try {
            sportFacilityService.deleteSportFacility(id);
            return Response.createOKResponse("Successful delete");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @GetMapping(value = "")
    @Cacheable()
    public Page<SportFacility> getFacilitiesFiltered(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @And({@Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "address.city.cityName", params = "address", spec = EqualIgnoreCase.class)})
                    Specification<SportFacility> spec) {
        return sportFacilityService.listSportFacilities(page, limit, spec);
    }

    @GetMapping(value = "/bySport")
    @Cacheable()
    public Page<SportFacility> getFacilitiesBySport(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "sport", defaultValue = "none") String sport) {
        return sportFacilityService.listSportFacilitiesBySport(page, limit, sport);
    }

    @GetMapping("/search/{keyword}")
    @Cacheable()
    public Page<SportFacility> searchFacilityByNameContaining
            (@PathVariable String keyword,
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return sportFacilityService.findByNameContaining(keyword, PageRequest.of(page, limit));
    }

    @GetMapping("/search/city/{city}")
    @Cacheable()
    public Page<SportFacility> searchFacilityByCity
            (@PathVariable String city,
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return sportFacilityService.findByCity(city, PageRequest.of(page, limit));
    }

    @GetMapping("/sport/{id}")
    @Cacheable()
    public Page<SportFacility> getFacilityBySportId(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return sportFacilityService.listFacilitiesBySportId(id, page, limit);
    }

    @PostMapping("/{id}/uploadImage/{type}")
    @CacheEvict(allEntries = true)
    public Response uploadImage(@PathVariable Long id, @PathVariable String type, @RequestParam("imageFile") MultipartFile file) {
        try {
            sportFacilityService.addImage(id, file, type);
            return Response.createOKResponse("Success Image upload");
        } catch (Exception e) {
            return Response.createErrorResponse("Error during image upload");
        }
    }

    @PostMapping("/{id}/addInstructor")
    @CacheEvict(allEntries = true)
    public Response addInstructor(@PathVariable Long id, @RequestParam("Instructor") Instructor instructor) {
        try {
            sportFacilityService.addInstructor(id, instructor);
            return Response.createOKResponse("Success Instructor added");
        } catch (Exception e) {
            return Response.createErrorResponse("Error during Instructor added");
        }
    }

    @PostMapping("/{id}/addComment")
    @CacheEvict(allEntries = true)
    public Response addComment(@PathVariable Long id, @RequestBody CommentRequestModel massage) {
        try {
            return Response.createOKResponse(sportFacilityService.addComment(id, massage));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

}


