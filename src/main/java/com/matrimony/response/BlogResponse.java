package com.matrimony.response;

import com.matrimony.entity.Blog;
import com.matrimony.entity.Profile;
import com.matrimony.entity.User;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogResponse {
    private Optional<User> bride;
    private Optional<User> groom;
    private Blog blog;
}
