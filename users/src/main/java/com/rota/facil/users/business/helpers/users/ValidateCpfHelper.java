package com.rota.facil.users.business.helpers.users;

import com.rota.facil.users.exceptions.CpfAlreadyExistsException;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateCpfHelper {
    private final UserRepository userRepository;

    public void execute(String cpf) {
        UserEntity userFound = this.userRepository.findByCpfAndActive(cpf)
                .orElse(null);

        if (userFound != null) throw new CpfAlreadyExistsException();
    }
}
