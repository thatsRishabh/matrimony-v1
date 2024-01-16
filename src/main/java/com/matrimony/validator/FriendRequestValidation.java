package com.matrimony.validator;

import com.matrimony.entity.User;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FriendRequestValidation {

    private User sender_id;
    private User receiver_id;

    @NotNull(message = "Please enter a valid status type")
    @Digits(integer = 1, fraction=2, message ="INT COMMENT '1: Accepted, 2: Pending, 3: Declined'")
    private Integer status;
}
