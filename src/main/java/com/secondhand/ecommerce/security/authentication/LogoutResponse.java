package com.secondhand.ecommerce.security.authentication;

import com.secondhand.ecommerce.models.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * This class models the format of the login response produced.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LogoutResponse implements Serializable {

  private String message = "Logout successful. Tokens are removed from cookie.";

  @NonNull
  private OperationStatus status;
}
