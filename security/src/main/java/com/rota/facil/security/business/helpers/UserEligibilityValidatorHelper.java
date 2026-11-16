package com.rota.facil.security.business.helpers;

import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.files.persistence.repositories.FileWithValidationRepository;
import com.rota.facil.security.http.exceptions.UserDocumentationAnalysisException;
import com.rota.facil.security.http.exceptions.UserDocumentationRejectException;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEligibilityValidatorHelper {
    private final FileWithValidationRepository fileWithValidationRepository;

    public void execute(UserEntity currentUser) {
        if (currentUser.isDriver()) {
            this.validateDriver(currentUser);
            return;
        }

        if (currentUser.isStudent()) this.validateStudent(currentUser);
    }

    private void validateStudent(UserEntity student) {
        FileWithValidationEntity mostRecentEnrollmentCertificate = this.fileWithValidationRepository.findEnrollmentCertificateByUserId(student.getId())
                .orElseThrow(() -> new FileNotFoundException("Etapa de upload de comprovante de matrícula é necessária para liberar esse recurso"));

        if (mostRecentEnrollmentCertificate.isAnalysis()) throw new UserDocumentationAnalysisException("Não é possível realizar essa ação porque seu comprovante de matrícula inda está sob análise");
        if (mostRecentEnrollmentCertificate.isReject()) throw new UserDocumentationRejectException("Não é possível realizar essa ação porque seu comprovante de matrícula foi rejeitado. Envie novamente para ser analisado mais uma vez");
    }

    private void validateDriver(UserEntity driver) {
        FileWithValidationEntity mostRecentEnrollmentCertificate = this.fileWithValidationRepository.findCNHByUserId(driver.getId())
                .orElseThrow(() -> new FileNotFoundException("Etapa de upload de CNH é necessária para liberar esse recurso"));

        if (mostRecentEnrollmentCertificate.isAnalysis()) throw new UserDocumentationAnalysisException("Não é possível realizar essa ação porque sua CNH inda está sob análise");
        if (mostRecentEnrollmentCertificate.isReject()) throw new UserDocumentationRejectException("Não é possível realizar essa ação porque sua CNH foi rejeitada. Envie novamente para ser analisada mais uma vez");
    }
}
