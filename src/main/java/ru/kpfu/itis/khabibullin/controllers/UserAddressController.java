package ru.kpfu.itis.khabibullin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.AddressDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.services.AddressService;
import ru.kpfu.itis.khabibullin.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAddressController {
    private final AddressService addressService;
    private final UserService userService;

    @GetMapping("/addresses")
    public List<AddressDto> getAddressesByUserId(Principal principal) {
        return addressService.getAddressByUsersId(userService.getUserByEmail(principal.getName()).getId());
    }

    @PostMapping("/addresses/add")
    public void addAddress(@RequestBody AddressDto addressDto, Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        //userService.updateUser(); TODO:add address to user somehow
        addressDto.setUserId(user.getId());
        System.out.println(addressDto);
        addressService.saveAddress(addressDto);
    }

    @DeleteMapping("/addresses/delete")
    public void deleteAdressById(@RequestParam(name = "address_id") Long addressId,
                                 Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        Long currentUserId = user.getId();

        AddressDto addressToDelete = addressService.getAddressById(addressId);
        if (!addressToDelete.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not authorized to delete this address");
        }


        addressService.deleteAddressById(addressId);

    }
}
