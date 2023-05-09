package ru.kpfu.itis.khabibullin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.khabibullin.models.Address;
import ru.kpfu.itis.khabibullin.models.User;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Khabibullin Alisher
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String streetName;
    private String homeNumber;
    private String city;
    private String country;
    private Long userId;

    public static Address to(AddressDto dto) {
        return Address.builder()
                .id(dto.id)
                .streetName(dto.getStreetName())
                .homeNumber(dto.getHomeNumber())
                .city(dto.getCity())
                .country(dto.getCountry())
                .user(User.builder().id(dto.userId).build())
                .build();
    }

    public static AddressDto from(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .streetName(address.getStreetName())
                .homeNumber(address.getHomeNumber())
                .city(address.getCity())
                .country(address.getCountry())
                .userId(address.getUser().getId())
                .build();
    }

    public static List<Address> to(List<AddressDto> dtos) {
        return dtos.stream()
                .map(AddressDto::to)
                .collect(Collectors.toList());
    }

    public static List<AddressDto> from(List<Address> addresses) {
        return addresses.stream()
                .map(AddressDto::from)
                .collect(Collectors.toList());
    }

}
