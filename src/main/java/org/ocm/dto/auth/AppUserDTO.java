package org.ocm.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
@Data
@Entity(name = "users")
@Table(name = "application_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDTO {

    @Id
    @Column(name = "username")
    @Size(min=8, max = 255, message = "USERNAME must be between {min} and {max} characters")
    @NotBlank(message = "USERNAME is missing")
    private String username;

    @ToString.Exclude
    @JsonIgnore
    @Column(name = "password")
    @Size(min=8, max = 255, message = "PASSWORD must be between {min} and {max} characters")
    @NotBlank(message = "PASSWORD is missing")
    private String password;

    @Column(name = "first_name")
    @Size(min=0, max = 255, message = "FIRST NAME  cannot have more than 255 characters")
    private String firstName;

    @Column(name = "last_name")
    @Size(min=0, max = 255, message = "LAST NAME  cannot have more than 255 characters")
    private String lastName;

    @Column(name = "email")
    @Email(message = "Provide a valid email")
    private String email;

    @Column(name = "roles")
    private String Roles = "USER";
}
