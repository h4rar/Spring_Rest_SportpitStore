package h4rar.jwt.token.demo.api;

import h4rar.jwt.token.demo.dto.address.AddressRequestDto;
import h4rar.jwt.token.demo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
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
    public void createNewAddress(
            @RequestBody AddressRequestDto addressDto,
            HttpServletRequest req
    ) {
        addressService.createNewAddress(addressDto, req);
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
