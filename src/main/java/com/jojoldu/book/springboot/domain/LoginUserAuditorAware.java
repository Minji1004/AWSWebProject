package com.jojoldu.book.springboot.domain;

import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginUserAuditorAware implements AuditorAware<Long> {

    private final HttpSession httpSession;

    @Override
    public Optional<Long> getCurrentAuditor() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user == null)
            return null;

        return Optional.ofNullable(user.getId());
    }
}
