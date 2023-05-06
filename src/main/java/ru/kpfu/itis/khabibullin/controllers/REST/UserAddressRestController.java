package ru.kpfu.itis.khabibullin.controllers.REST;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.khabibullin.dto.AddressDto;
import ru.kpfu.itis.khabibullin.dto.OrderDto;
import ru.kpfu.itis.khabibullin.dto.UserDto;
import ru.kpfu.itis.khabibullin.services.AddressService;
import ru.kpfu.itis.khabibullin.services.OrderService;
import ru.kpfu.itis.khabibullin.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAddressRestController {
    private final AddressService addressService;
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/orders/add")
    public void addOrderByUser(@RequestBody OrderDto order, Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        order.setUserId(user.getId());
        orderService.saveOrder(order);
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrdersByUserId(Principal principal) {
        return orderService.getOrdersByUserId(userService.getUserByEmail(principal.getName()).getId());
    }


    @GetMapping("/addresses")
    public List<AddressDto> getAddressesByUserId(Principal principal) {
        return addressService.getAddressByUsersId(userService.getUserByEmail(principal.getName()).getId());
    }

    @PostMapping("/addresses/add")
    public void addAddressByUser(@RequestBody AddressDto addressDto, Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        //userService.updateUser(); TODO:add address to user somehow
        addressDto.setUserId(user.getId());
        addressService.saveAddress(addressDto);
    }

    @DeleteMapping("/addresses/delete")
    public void deleteAddressById(@RequestParam(name = "address_id") Long addressId,
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
