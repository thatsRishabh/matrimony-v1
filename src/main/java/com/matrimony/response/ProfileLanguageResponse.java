package com.matrimony.response;

import com.matrimony.entity.FriendRequest;
import com.matrimony.entity.LanguageSelected;
import com.matrimony.entity.Profile;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileLanguageResponse {
    private List<LanguageSelected> languageSelected;
    private Profile profile;
}
