package hu.fitforfun.services.impl;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.InstructorRepository;
import hu.fitforfun.repositories.TrainingSessionRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.EmailService;
import hu.fitforfun.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingSessionServiceImpl implements TrainingSessionService {
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    EmailService emailService;

    @Override
    public TrainingSession addTrainingSession(Long instructorId,TrainingSession session) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        if (!isSessionTimeFreeForInstructor(instructor.getId(), session.getDay(), session.getSessionStart(), session.getSessionEnd())) {
            throw new FitforfunException(ErrorCode.SESSION_TIME_ALREADY_OCCUPIED);
        }
        instructor.addTrainingSession(session);
        return trainingSessionRepository.save(session);

    }

    @Override
    public List<TrainingSession> getInstructorTrainingSessionsByDay(Long instructorId, WeekDays day) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();

        return trainingSessionRepository.findByInstructorAndDay(instructor, day);
    }
    @Override
    public List<TrainingSession> getTrainingSessionsByAvailableForClients(Long userId) throws FitforfunException{
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        User user = optionalUser.get();
        List<TrainingSession> returnValue = new ArrayList<>();

        trainingSessionRepository.findAll().forEach(trainingSession -> {
            if(trainingSession.getTrainingSessionType().equals("Csoportos edzés")){
                if(trainingSession.getClient().size() < trainingSession.getMaxClientNumber() && !trainingSession.getClient().contains(user)){
                    returnValue.add(trainingSession);
                }
            }else if(trainingSession.getTrainingSessionType().equals("Személyi edzés")) {
                if (trainingSession.getClient().size() == 0) {
                    returnValue.add(trainingSession);
                }
            }
        });
        return returnValue;
    }
    @Override
    public List<TrainingSession> getTrainingSessionsByAvailable() throws FitforfunException{
        List<TrainingSession> returnValue = new ArrayList<>();

        trainingSessionRepository.findAll().forEach(trainingSession -> {
            if(trainingSession.getTrainingSessionType().equals("Csoportos edzés")){
                if(trainingSession.getClient().size() < trainingSession.getMaxClientNumber()){
                    returnValue.add(trainingSession);
                }
            }else if(trainingSession.getTrainingSessionType().equals("Személyi edzés")) {
                if (trainingSession.getClient().size() == 0) {
                    returnValue.add(trainingSession);
                }
            }
        });
        return returnValue;
    }
    @Override
    public List<TrainingSession> getTrainingSessionsByInstructorAndAvailability(Long instructorId) throws FitforfunException{
        List<TrainingSession> returnValue = new ArrayList<>();
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        List<TrainingSession> sessionsByInstructor = trainingSessionRepository.findByInstructor(instructor);
        if(sessionsByInstructor.size() != 0){
            sessionsByInstructor.forEach(trainingSession->{
                if(trainingSession.getTrainingSessionType().equals("Csoportos edzés")){
                    if(trainingSession.getClient().size() < trainingSession.getMaxClientNumber()){
                        returnValue.add(trainingSession);
                    }
                }else if(trainingSession.getTrainingSessionType().equals("Személyi edzés")) {
                    if (trainingSession.getClient().size() == 0) {
                        returnValue.add(trainingSession);
                    }
                }
            });
        }
        return returnValue;
    }

    @Override
    public List<TrainingSession> getTrainingSessionsByUser(Long userId) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        User user = optionalUser.get();
        List<TrainingSession> returnList = new ArrayList<>();
        trainingSessionRepository.findByClientIdIn(Arrays.asList(userId)).forEach(returnList::add);
        return returnList;
    }

    @Override
    public List<TrainingSession> getTrainingSessionsByInstructor(Long instructorId) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        List<TrainingSession> returnList = new ArrayList<>();
        trainingSessionRepository.findByInstructor(instructor).forEach(returnList::add);
        return returnList;
    }

    @Override
    public List<TrainingSession> getClientTrainingSessionsByDay(Long clientId, WeekDays day) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(clientId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        User user = optionalUser.get();

        return trainingSessionRepository.findByClientAndDay(user, day);
    }

    public User addTrainingSessionToClient(Long clientId, Long sessionId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(clientId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        Optional<TrainingSession> optionalTrainingSession = trainingSessionRepository.findById(sessionId);
        if (!optionalTrainingSession.isPresent()) {
            throw new FitforfunException("Invalid training session");
        }
        TrainingSession session = optionalTrainingSession.get();
        User user = optionalUser.get();

        if (!isSessionTimeFreeForClient(user,session.getInstructor(), session.getDay(), session.getSessionStart(), session.getSessionEnd())) {
            throw new FitforfunException(ErrorCode.SESSION_TIME_ALREADY_OCCUPIED);
        }
        session.getClient().add(user);
        emailService.sendApplyForTrainingSession(user,session);
        trainingSessionRepository.save(session);
        return user;
    }

    public void deleteTrainingSessionToClient(Long clientId, Long sessionId) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(clientId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        Optional<TrainingSession> optionalTrainingSession = trainingSessionRepository.findById(sessionId);
        if (!optionalTrainingSession.isPresent()) {
            throw new FitforfunException("Invalid training session");
        }
        TrainingSession session = optionalTrainingSession.get();
        User user = optionalUser.get();

        session.getClient().remove(user);
        trainingSessionRepository.save(session);
    }


    @Override
    public void deleteTrainingSession(Long trainingSessionId) throws FitforfunException{
        Optional<TrainingSession> optionalTrainingSession = trainingSessionRepository.findById(trainingSessionId);
        if (!optionalTrainingSession.isPresent()) {
            throw new FitforfunException(ErrorCode.TRAINING_SESSION_NOT_EXISTS);
        }
        TrainingSession trainingSession = optionalTrainingSession.get();
        trainingSessionRepository.delete(trainingSession);
    }

    private boolean isSessionTimeValid(Double sessionStart, Double sessionEnd) {
        if (sessionEnd > sessionStart) return true;
        return false;
    }

    public boolean isSessionTimeFreeForClient(User client, Instructor instructor, WeekDays day, Double sessionStart, Double sessionEnd) throws FitforfunException {
        if (!isSessionTimeValid(sessionStart, sessionEnd)) {
            return false;
        }
        if (trainingSessionRepository.findByClientAndDayAndSessionStart(client, day, sessionStart).isPresent() ||
                trainingSessionRepository.findByClientAndDayAndSessionEnd(client, day, sessionEnd).isPresent()) {
            return false;
        }
        List<TrainingSession> trainingSessionsThisDay = getClientTrainingSessionsByDay(client.getId(), day);
        for (TrainingSession session : trainingSessionsThisDay) {
            if ((sessionStart < session.getSessionStart() && session.getSessionStart() < sessionEnd) ||
                    (sessionStart < session.getSessionEnd() && session.getSessionEnd() < sessionEnd)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSessionTimeFreeForInstructor(Long instructorId, WeekDays day, Double sessionStart, Double sessionEnd) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        if (trainingSessionRepository.findByInstructorAndDayAndSessionStart(instructor, day, sessionStart).isPresent() ||
                trainingSessionRepository.findByInstructorAndDayAndSessionEnd(instructor, day, sessionEnd).isPresent()) {
            return false;
        }
        List<TrainingSession> trainingSessionsThisDay = getInstructorTrainingSessionsByDay(instructor.getId(), day);
        for (TrainingSession session : trainingSessionsThisDay) {
            if ((sessionStart < session.getSessionStart() && session.getSessionStart() < sessionEnd) ||
                    (sessionStart < session.getSessionEnd() && session.getSessionEnd() < sessionEnd)) {
                return false;
            }
        }
        return true;
    }
}
