package com.rota.facil.interactions.business.feedbacks;

import com.rota.facil.interactions.exceptions.EvaluateYourSelfException;
import com.rota.facil.interactions.exceptions.EvaluationSameRoleException;
import com.rota.facil.interactions.http.dto.request.feedbacks.SendFeedBackRequest;
import com.rota.facil.interactions.http.dto.response.feedbacks.FeedBackResponse;
import com.rota.facil.interactions.persistence.entities.FeedBackEntity;
import com.rota.facil.interactions.persistence.entities.FeedBackRepository;
import com.rota.facil.interactions.persistence.mappers.FeedBackMapper;
import com.rota.facil.users.exceptions.UserNotFoundExceptions;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendFeedbackToUserUseCase {
    private final UserRepository userRepository;
    private final FeedBackRepository feedBackRepository;
    private final FeedBackMapper feedBackMapper;

    public FeedBackResponse execute(UserEntity currentUser, SendFeedBackRequest request) {
        if (currentUser.getId().equals(request.receiverId())) throw new EvaluateYourSelfException();

        UserEntity receiver = this.userRepository.findByIdAndPrefectureIdActive(request.receiverId(), currentUser.getPrefectureId())
                .orElseThrow(UserNotFoundExceptions::new);

        if (currentUser.getRole().equals(receiver.getRole())) throw new EvaluationSameRoleException("Apenas motoristas e alunos podem se avaliar entre si");

        FeedBackEntity newFeedback = FeedBackEntity.builder()
                .sender(currentUser)
                .receiver(receiver)
                .feedback(request.feedback())
                .note(request.note())
                .build();

        FeedBackEntity savedFeedback = this.feedBackRepository.save(newFeedback);

        receiver.setScore(this.feedBackRepository.calculateMediaOfReceiverByUserId(receiver.getId()));
        this.userRepository.save(receiver);

        return this.feedBackMapper.map(savedFeedback);
    }
}
