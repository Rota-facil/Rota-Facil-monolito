package com.rota.facil.security.business.auth.helpers;

import com.rota.facil.security.exceptions.CpfAlreadyExistsExceptions;
import com.rota.facil.security.exceptions.EmailAlreadyExistsExceptions;
import com.rota.facil.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationCreateAccountHelper {
    private final UserRepository userRepository;

    public void execute(String cpf, String email) {
        if (userRepository.countUsersByCpf(cpf) > 0) throw new CpfAlreadyExistsExceptions();
        if (userRepository.countUsersByEmail(email) > 0) throw new EmailAlreadyExistsExceptions();
    }
}
