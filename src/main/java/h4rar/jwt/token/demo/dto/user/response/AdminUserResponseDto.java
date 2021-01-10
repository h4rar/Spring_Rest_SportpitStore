package h4rar.jwt.token.demo.dto.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import h4rar.jwt.token.demo.dto.address.AddressResponseDto;
import h4rar.jwt.token.demo.model.*;
import lombok.Data;

import java.util.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserResponseDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String status;

    private String phone;

    private List<AddressResponseDto> address;

    public static AdminUserResponseDto fromUser(User user) {
        AdminUserResponseDto adminUserResponseDto = new AdminUserResponseDto();
        adminUserResponseDto.setId(user.getId());
        adminUserResponseDto.setUsername(user.getUsername());
        adminUserResponseDto.setFirstName(user.getFirstName());
        adminUserResponseDto.setLastName(user.getLastName());
        adminUserResponseDto.setEmail(user.getEmail());
        adminUserResponseDto.setStatus(user.getBasicStatus().name());
        adminUserResponseDto.setPhone(user.getPhone());
        List<Address> userAddress = user.getAddress();
        if (userAddress != null) {
            List<AddressResponseDto> userAddressResponseDto = new ArrayList<>();
            for (Address ua : userAddress
            ) {
                userAddressResponseDto.add(AddressResponseDto.fromAddress(ua));
            }
            adminUserResponseDto.setAddress(userAddressResponseDto);
        }
        return adminUserResponseDto;
    }
}
