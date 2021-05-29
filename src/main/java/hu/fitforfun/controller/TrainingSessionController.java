package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.repositories.TrainingSessionRepository;
import hu.fitforfun.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainingSessions")
public class TrainingSessionController {

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    TrainingSessionService trainingSessionService;

    @GetMapping("")
    public List<TrainingSession> getTrainingSessions() {
        List<TrainingSession> returnValue = new ArrayList<>();
        trainingSessionRepository.findAll().iterator().forEachRemaining(returnValue::add);
        return returnValue;
    }

    @GetMapping("/{userId}/byAvailableForClient")
    public List<TrainingSession> getTrainingSessionsByAvailableForClient(@PathVariable Long userId) {
        try {
            return trainingSessionService.getTrainingSessionsByAvailableForClients(userId);
        } catch (FitforfunException e) {
            return null;
        }
    }

    @GetMapping("/byAvailable")
    public List<TrainingSession> getTrainingSessionsByAvailable() {
        try {
            return trainingSessionService.getTrainingSessionsByAvailable();
        } catch (FitforfunException e) {
            return null;
        }
    }

    @GetMapping("/{instructorId}/byInstructorAndAvailable")
    public List<TrainingSession> getTrainingSessionsByInstructorAndAvailability(@PathVariable Long instructorId) {
        try {
            return trainingSessionService.getTrainingSessionsByInstructorAndAvailability(instructorId);
        } catch (FitforfunException e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public TrainingSession getSessionById(@PathVariable Long id) {
        return trainingSessionRepository.findById(id).get();
    }

    @GetMapping("/user/{id}")
    public List<TrainingSession> getSessionByUser(@PathVariable Long id) {
        try {
            return trainingSessionService.getTrainingSessionsByUser(id);
        } catch (FitforfunException e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/instructor/{id}")
    public List<TrainingSession> getSessionsByInstructor(@PathVariable Long id) {
        try {
            return trainingSessionService.getTrainingSessionsByInstructor(id);
        } catch (FitforfunException e) {
            return new ArrayList<>();
        }
    }


    @GetMapping("/user/{userId}/deleteSession/{sessionId}")
    @CacheEvict(cacheNames = "instructors",allEntries = true)
    public Response deleteSessionForUser(@PathVariable Long userId, @PathVariable Long sessionId) {
        try {
            trainingSessionService.deleteTrainingSessionToClient(userId, sessionId);
            return Response.createOKResponse("success delete for user");
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "instructors",allEntries = true)
    public Response deleteSession(@PathVariable Long id) {
        try {
            trainingSessionService.deleteTrainingSession(id);
            return Response.createOKResponse("Training session success delete");
        } catch (FitforfunException e) {
            return Response.createOKResponse("error during training session delete");
        }
    }

    @PostMapping("")
    @CacheEvict(cacheNames = "instructors",allEntries = true)
    public Response addSession(@RequestParam(value = "instructorId") Long instructorId, @RequestBody TrainingSession session) {
        try {
            return Response.createOKResponse(trainingSessionService.addTrainingSession(instructorId, session));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }
}
