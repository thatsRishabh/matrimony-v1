package com.matrimony.response;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.User;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserFriendRequestResponse {

    private User user;
    private FriendRequest friendRequest;
}
