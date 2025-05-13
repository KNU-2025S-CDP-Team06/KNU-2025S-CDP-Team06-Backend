package knu.knu2025scdpteam06backend.dto.auth;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    private String mbId;
    private String password;

}
