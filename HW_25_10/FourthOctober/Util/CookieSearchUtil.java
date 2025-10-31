package org.example.FourthOctober.Util;


import jakarta.servlet.http.Cookie;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class CookieSearchUtil {

    public Optional<String> findCookieByName(Cookie[] cookies, String name) {
        if(Objects.isNull(cookies)) return Optional.empty();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(name)) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }

}
