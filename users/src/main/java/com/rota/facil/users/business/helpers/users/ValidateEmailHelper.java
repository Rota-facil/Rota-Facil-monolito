package com.rota.facil.users.business.helpers.users;

import com.rota.facil.users.exceptions.EmailAlreadyExistsException;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateEmailHelper {
    private final UserRepository userRepository;

    public void execute(String email) {
        UserEntity userFound = this.userRepository.findByEmailAndActiveTrue(email)
                .orElse(null);

        if (userFound != null) throw new EmailAlreadyExistsException();
    }
}
