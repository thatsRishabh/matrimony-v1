package com.matrimony.response;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.Profile;
import com.matrimony.entity.User;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfileFriendRequestResponse {

    private User user;
    private FriendRequest friendRequest;
    private Optional<Profile> profile;
}
