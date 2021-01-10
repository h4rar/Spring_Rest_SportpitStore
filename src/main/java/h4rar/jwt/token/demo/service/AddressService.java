package h4rar.jwt.token.demo.service;

import h4rar.jwt.token.demo.dto.address.AddressRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface AddressService {
    void createNewAddress(AddressRequestDto addressDto, HttpServletRequest req);
    void deleteAddress(Long id, HttpServletRequest req);
}
