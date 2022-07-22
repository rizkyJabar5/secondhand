package com.secondhand.ecommerce.login;

import com.secondhand.ecommerce.models.dto.offers.OfferMapper;
import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.offers.OfferUpdate;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.security.authentication.register.RegisterRequest;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class RequestResponseAndMapperTest {

    @Test
    void authenticationResponseAndRequestEquals() {
        assertThat(LoginRequest.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
        assertThat(RegisterRequest.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
    }

    @Test
    void offerMapperEquals() {
        assertThat(OfferMapper.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
        assertThat(OfferSave.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
        assertThat(OfferUpdate.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
    }
}