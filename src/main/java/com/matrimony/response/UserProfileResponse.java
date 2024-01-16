package com.matrimony.response;

import com.matrimony.entity.Menu;
import com.matrimony.entity.Profile;
import com.matrimony.entity.User;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfileResponse {
    private Optional<User> user;
    private Profile profile;

}
