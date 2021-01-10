package h4rar.jwt.token.demo.dto.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import h4rar.jwt.token.demo.dto.address.AddressResponseDto;
import h4rar.jwt.token.demo.model.*;
import lombok.Data;

import java.util.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private List<AddressResponseDto> address;

    public static UserResponseDto fromUser(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        List<Address> userAddress = user.getAddress();
        if (userAddress != null) {
            List<AddressResponseDto> userAddressResponseDto = new ArrayList<>();
            for (Address ua : userAddress
            ) {
                userAddressResponseDto.add(AddressResponseDto.fromAddress(ua));
            }
            userResponseDto.setAddress(userAddressResponseDto);
        }

        return userResponseDto;
    }
}