package ru.kpfu.itis.khabibullin.services;

import ru.kpfu.itis.khabibullin.dto.AddressDto;

import java.util.List;
/**
 * @author Khabibullin Alisher
 */
public interface AddressService {
    List<AddressDto> getAddressByUsersId(Long id);

    void deleteAddressById(Long id);

    AddressDto getAddressById(Long id);

    void saveAddress(AddressDto addressDto);
}
