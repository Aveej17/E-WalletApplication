package com.Jeeva.UserService.request;


import com.Jeeva.UserService.customAnnotation.AgeLimit;
import com.Jeeva.UserService.model.User;
import com.Jeeva.Utils.UserIdentifier;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String name;
    @NotBlank(message = "Email Should not be blank")
    private String email;

    @NotBlank(message = "PhoneNo Should not be blank")
    private String phoneNo;

    private String address;

    private String password;

    @AgeLimit(message = "Age Should be greater than 18")
    private String dob;

    private UserIdentifier userIdentifier;

    private  String userIdentifierValue;

    public User toUser(){
        return User.builder().
                name(this.name).
                email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                password(this.password).
                dob(this.dob).
                userIdentifier(this.userIdentifier).
                userIdentifierValue(this.userIdentifierValue).
                build();
    }
}

