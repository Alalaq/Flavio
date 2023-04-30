package ru.kpfu.itis.khabibullin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Address;
import ru.kpfu.itis.khabibullin.models.Order;
import ru.kpfu.itis.khabibullin.models.User;
import ru.kpfu.itis.khabibullin.utils.Role;
import ru.kpfu.itis.khabibullin.utils.State;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    @JsonManagedReference
    @JsonIgnore
    private List<Address> addresses;
    @JsonManagedReference
    @JsonIgnore
    private List<Order> orders;
    private Role role;
    private State state;

    public static User to(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .birthday(dto.getBirthday())
                .orders(dto.getOrders())
                .addresses(dto.getAddresses())
                .state(dto.getState())
                .role(dto.getRole())
                .build();
    }

    public static List<User> to(List<UserDto> dtos) {
        return dtos.stream()
                .map(UserDto::to)
                .collect(Collectors.toList());
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .orders(user.getOrders())
                .addresses(user.getAddresses())
                .state(user.getState())
                .role(user.getRole())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
