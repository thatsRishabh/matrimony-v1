package com.matrimony.response;

import com.matrimony.entity.Profile;
import com.matrimony.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userToken;
    private LocalDate loginDate;
//    private int userId;
    private Optional<Profile> profile;
    private User user;
}
