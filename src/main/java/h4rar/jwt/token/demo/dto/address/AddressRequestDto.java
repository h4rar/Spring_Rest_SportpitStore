package h4rar.jwt.token.demo.dto.address;

import lombok.Data;

@Data
public class AddressRequestDto {

    private String street;

    private String house;

    private String room;

    private String index;
}
