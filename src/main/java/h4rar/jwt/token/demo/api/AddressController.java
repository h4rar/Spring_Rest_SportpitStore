package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.address.*;
import h4rar.jwt.token.demo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/")
public class AddressController {

    private final AddressService addressService;

    public AddressController(
            AddressService addressService
    ) {
        this.addressService = addressService;
    }

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseDto createNewAddress(
            @RequestBody AddressRequestDto addressDto,
            HttpServletRequest req
    ) {
        return addressService.createNewAddress(addressDto, req);
    }

    @DeleteMapping("/address/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddress(
            @PathVariable ("id") Long id,
            HttpServletRequest req
    ) {
        addressService.deleteAddress(id, req);
    }
}
