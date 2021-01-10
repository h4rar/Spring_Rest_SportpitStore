package h4rar.jwt.token.demo.service.impl;

import h4rar.jwt.token.demo.dto.address.AddressRequestDto;
import h4rar.jwt.token.demo.exception.BadRequestException;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import h4rar.jwt.token.demo.repository.*;
import h4rar.jwt.token.demo.security.jwt.JwtTokenProvider;
import h4rar.jwt.token.demo.service.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AddressServiceImpl(
            AddressRepository addressRepository, UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void createNewAddress(AddressRequestDto addressDto, HttpServletRequest req) {
        User user = jwtTokenProvider.getUserFromHttpServletRequest(req);
        Address address = new Address();
        address.setBasicStatus(BasicStatus.ACTIVE);
        address.setStreet(addressDto.getStreet());
        address.setHouse(addressDto.getHouse());
        address.setRoom(addressDto.getRoom());
        address.setIndex(addressDto.getIndex());
        address.setUser(user);
        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id, HttpServletRequest req) {
        User user = jwtTokenProvider.getUserFromHttpServletRequest(req);
        Address address = addressRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new BadRequestException("Такого адреса не существует"));
        address.setBasicStatus(BasicStatus.DELETED);
        addressRepository.save(address);
    }
}
