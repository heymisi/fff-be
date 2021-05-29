package hu.fitforfun.services.impl;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.*;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.request.InstructorRegistrationModel;
import hu.fitforfun.model.request.InstructorResponseModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.InstructorService;
import hu.fitforfun.services.TrainingSessionService;
import hu.fitforfun.services.UserService;
import hu.fitforfun.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    TrainingSessionService trainingSessionService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SportFacilityRepository sportFacilityRepository;

    @Autowired
    private SportTypeRepository sportTypeRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public Instructor getInstructorById(Long id) throws FitforfunException, IOException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        if (optionalInstructor.get().getImage() != null) {
            Image image = imageService.getImage(optionalInstructor.get().getImage().getName());
            optionalInstructor.get().setImageString("data:" + image.getType() + ";base64," + Base64.getEncoder().encodeToString(image.getPicByte()));
        }

        return optionalInstructor.get();
    }

    @Override
    public Instructor getInstructorByUserId(Long userId) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }

        return instructorRepository.findByUser(optionalUser.get()).get();
    }

    @Override
    public Page<Instructor> listInstructors(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Instructor> instructors = instructorRepository.findAll(pageableRequest).map(this::setImage);
        return instructors;
    }

    @Override
    public Page<Instructor> listInstructorsFiltered(int page, int limit, Specification<Instructor> spec) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        return instructorRepository.findAll(spec, pageableRequest).map(instructor -> setImage(instructor));
    }


    public Page<Instructor> listInstructorsBySport(int page, int limit, String sport){
        if (page > 0) page--;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Optional<SportType> byNameIgnoreCase = sportTypeRepository.findByNameIgnoreCase(sport);
        return instructorRepository.findByKnownSportsIdIn(Arrays.asList(byNameIgnoreCase.get().getId()), pageableRequest)
                .map(instructor -> this.setImage(instructor));
    }


    @Override
    public Instructor createInstructor(InstructorRegistrationModel instructor) throws Exception {
        if (userRepository.findByContactDataEmail(instructor.getUser().getEmail()).isPresent()) {
            throw new FitforfunException("INSTRUCTOR_ALREADY_EXIST");
        }

        Instructor createdInstructor = new Instructor();
        createdInstructor.setUser(userService.createUser(instructor.getUser(), Roles.ROLE_INSTRUCTOR.name()));

        createdInstructor.setTitle(instructor.getTitle());
        createdInstructor.setBio(instructor.getBio());
        if (instructor.getFacilityId() != null) {
            Optional<SportFacility> facility = sportFacilityRepository.findById(instructor.getFacilityId());
            if (!facility.isPresent()) {
                throw new FitforfunException("WRONG FACILITY ID");
            }
            createdInstructor.setSportFacility(facility.get());
        }

        createdInstructor.setKnownSports(new ArrayList<>());

        if (instructor.getSportIds() != null) {
            instructor.getSportIds().forEach(sportId -> {
                Optional<SportType> sport = sportTypeRepository.findById(sportId);
                if (sport.isPresent()) {
                    createdInstructor.addSport(sport.get());
                }
            });
        }

        createdInstructor.setRating(new Rating());
        createdInstructor.setComments(new ArrayList<>());
        createdInstructor.setTrainingSessions(new ArrayList<>());
        return instructorRepository.save(createdInstructor);
    }

    @Override
    public Boolean deleteInstructor(Long id, String password) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent())
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);

        Optional<User> userToDelete = userRepository.findById(optionalInstructor.get().getUser().getId());

        if (!userToDelete.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);

        if (!bCryptPasswordEncoder.matches(password, userToDelete.get().getPassword()))
            return false;

        instructorRepository.delete(optionalInstructor.get());
        return true;
    }

    @Override
    public Instructor updateInstructor(Long id, InstructorResponseModel instructor) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructorToUpdate = optionalInstructor.get();
        if (instructor.getBio() != null && instructorToUpdate.getBio() != instructor.getBio()) {
            instructorToUpdate.setBio(instructor.getBio());
        }
        if (instructor.getTitle() != null && instructorToUpdate.getTitle() != instructor.getTitle()) {
            instructorToUpdate.setTitle(instructor.getTitle());
        }
        if (instructor.getSportFacility() != null) {
            instructorToUpdate.setSportFacility(sportFacilityRepository.findById(instructor.getSportFacility()).get());
        }
        if (instructor.getKnownSports() != null) {
            instructorToUpdate.setKnownSports(new ArrayList<>());
            instructor.getKnownSports().forEach(sport -> {
                instructorToUpdate.addSport(sportTypeRepository.findById(sport).get());
            });
        }
        return instructorRepository.save(instructorToUpdate);
    }


    @Override
    public void updateInstructorUser(Long id, User user) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        User instructorUser = instructor.getUser();
        userService.updateUser(instructorUser.getId(), user);
    }

    @Override
    public Comment addComment(Long id, CommentRequestModel comment) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        Optional<User> optionalUser = userRepository.findById((long) comment.getUserId());
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        List<Comment> optionalComment = commentRepository.findByCommenterAndInstructor(optionalUser.get(), instructor);
        if (optionalComment.size() != 0) {
            throw new FitforfunException(ErrorCode.ALREADY_COMMENTED);
        }

        instructor.getRating().setValue((instructor.getRating().getValue() * instructor.getRating().getCounter()
                + comment.getRate()) / (instructor.getRating().getCounter() + 1));

        instructor.getRating().setCounter(instructor.getRating().getCounter() + 1);

        Comment commentToAdd = new Comment();
        commentToAdd.setText(comment.getMessage());
        commentToAdd.setRate(comment.getRate());
        commentToAdd.setCommenter(optionalUser.get());
        commentRepository.save(commentToAdd);

        instructor.addComment(commentToAdd);
        instructorRepository.save(instructor);
        return commentToAdd;
    }

    @Override
    public Instructor commentInstructor(User user, Long instructorId, Comment comment) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        comment.setCreated(java.sql.Date.valueOf(LocalDate.now()));
        instructor.addComment(comment);

        commentRepository.save(comment);
        return instructor;
    }

    @Override
    public List<Instructor> listInstructorsByAvailableFacility() {
        List<Instructor> returnValue = new ArrayList<>();
        instructorRepository.findAll().forEach(instructor -> {
                    if (instructor.getSportFacility() == null) {
                        returnValue.add(instructor);
                    }
                }
        );
        return returnValue;
    }


    @Override
    public void addImage(Long id, MultipartFile multipartFile) throws Exception {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        instructor.setImage(new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                ImageUtils.compressBytes(multipartFile.getBytes())));
        instructorRepository.save(instructor);
    }

    private Instructor setImage(Instructor instructor) {
        if (instructor.getImage() != null) {
            try {
                Image image = imageService.getImageById(instructor.getImage().getId());
                instructor.setImageString("data:" + image.getType() + ";base64," + Base64.getEncoder().encodeToString(image.getPicByte()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instructor;
    }
}
