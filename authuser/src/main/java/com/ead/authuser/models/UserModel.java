package com.ead.authuser.models;

import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @JsonIgnore
    private String password;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "image_url")
    private String imageUrl;

    @CreationTimestamp
    @Column(name="creation_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(name="last_update_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private OffsetDateTime lastUpdateDate;

    public UserEventDto convertToUserEvetDto() {
        var userEventDto = new UserEventDto();
        BeanUtils.copyProperties(this, userEventDto);
        userEventDto.setUserStatus(this.getUserType().toString());
        userEventDto.setUserType(this.getUserStatus().toString());
        return userEventDto;
    }
}
