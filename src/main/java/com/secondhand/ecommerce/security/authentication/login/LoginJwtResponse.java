package com.secondhand.ecommerce.security.authentication.login;

import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.entity.Address;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.utils.DateUtilConverter;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.secondhand.ecommerce.utils.SecondHandConst.TOKEN_PREFIX;


@Data
@Builder
public class LoginJwtResponse implements Serializable {

    private Long id;
    private String accessToken;
    private String fullName;
    private String email;
    private String type;
    private String joinDate;
    private Address address;
    private String phoneNumber;
    private String imageUrl;

    /**
     * Builds jwtResponseBuilder object from the specified userDetails.
     *
     * @param jwtToken the jwtToken
     * @return the jwtResponse
     */
    public static LoginJwtResponse buildJwtResponse(final String jwtToken) {
        return buildJwtResponse(jwtToken, null);
    }

    /**
     * Build jwtResponse object from the specified userDetails.
     *
     * @param jwToken     the jwToken.
     * @param userDetails the userDetails.
     * @return the jwtResponse object.
     */
    public static LoginJwtResponse buildJwtResponse(String jwToken,
                                                    AppUserBuilder userDetails) {

        AppUserBuilder localUserDetails = userDetails;

        if (Objects.isNull(localUserDetails)) {
            localUserDetails = SecurityUtils.getAuthenticatedUserDetails();
        }

        if (Objects.nonNull(localUserDetails)) {
            Date date = localUserDetails.getJoinDate();
            LocalDateTime localDateTime = DateUtilConverter.toLocalDate(date);

            return LoginJwtResponse.builder()
                    .accessToken(jwToken)
                    .id(localUserDetails.getUserId())
                    .email(localUserDetails.getEmail())
                    .type(TOKEN_PREFIX)
                    .fullName(localUserDetails.getFullName())
                    .address(localUserDetails.getAddress())
                    .phoneNumber(localUserDetails.getPhoneNumber())
                    .imageUrl(localUserDetails.getImageUrl())
                    .joinDate(localDateTime.toString())
                    .build();
        }
        return LoginJwtResponse.builder().build();
    }
}
