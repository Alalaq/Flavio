package ru.kpfu.itis.khabibullin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.khabibullin.dto.AddressDto;
import ru.kpfu.itis.khabibullin.exceptions.NotFoundException;
import ru.kpfu.itis.khabibullin.repositories.AddressRepository;
import ru.kpfu.itis.khabibullin.services.AddressService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddressByUsersId(Long id) {
        return AddressDto.from(addressRepository.findByUserId(id));
    }

    @Override
    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        return AddressDto.from(addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address with id: <" + id + "> not found")));
    }

    @Override
    public void saveAddress(AddressDto addressDto) {
        addressRepository.save(AddressDto.to(addressDto));
    }
}
