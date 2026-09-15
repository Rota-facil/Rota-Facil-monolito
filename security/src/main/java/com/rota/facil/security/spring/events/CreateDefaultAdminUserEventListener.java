package com.rota.facil.security.spring.events;

import com.rota.facil.security.spring.events.dto.receive.CreateDefaultAdminUserEventReceive;
import com.rota.facil.security.spring.events.mappers.SecurityUserEventMapper;
import com.rota.facil.users.domain.Role;
import com.rota.facil.users.entities.UserEntity;
import com.rota.facil.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDefaultAdminUserEventListener {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserEventMapper securityUserEventMapper;

    @EventListener
    public void execute(CreateDefaultAdminUserEventReceive event) {
        UserEntity newAdminPrefecture = securityUserEventMapper.map(event);
        newAdminPrefecture.setPassword(passwordEncoder.encode(newAdminPrefecture.getPassword()));
        newAdminPrefecture.setRole(Role.ADMIN);
        userRepository.save(newAdminPrefecture);
    }
}