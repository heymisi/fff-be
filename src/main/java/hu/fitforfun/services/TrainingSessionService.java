package hu.fitforfun.services;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.user.User;

import java.util.List;

public interface TrainingSessionService {

    TrainingSession addTrainingSession(Long instructorId, TrainingSession session) throws FitforfunException;

    List<TrainingSession> getInstructorTrainingSessionsByDay(Long instructorId, WeekDays day) throws FitforfunException;

    List<TrainingSession> getClientTrainingSessionsByDay(Long clientId, WeekDays day) throws FitforfunException;

    User addTrainingSessionToClient(Long clientId, Long sessionId) throws Exception;

    void deleteTrainingSession(Long trainingSessionId) throws FitforfunException;

    List<TrainingSession> getTrainingSessionsByAvailableForClients(Long userId) throws FitforfunException;

    List<TrainingSession> getTrainingSessionsByAvailable() throws FitforfunException;

    List<TrainingSession> getTrainingSessionsByInstructorAndAvailability(Long instructorId) throws FitforfunException;

    List<TrainingSession> getTrainingSessionsByUser(Long userId) throws FitforfunException;

    List<TrainingSession> getTrainingSessionsByInstructor(Long instructorId) throws FitforfunException;

    void deleteTrainingSessionToClient(Long clientId, Long sessionId) throws FitforfunException;

}
