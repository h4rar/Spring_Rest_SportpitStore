package h4rar.jwt.token.demo.dto.address;

import h4rar.jwt.token.demo.model.Address;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.Data;

@Data
public class AddressResponseDto {

    private Long id;

    private String street;

    private String house;

    private String room;

    private String index;

    private BasicStatus basicStatus;

    public static AddressResponseDto fromAddress(Address address) {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setId(address.getId());
        addressResponseDto.setStreet(address.getStreet());
        addressResponseDto.setRoom(address.getRoom());
        addressResponseDto.setIndex(address.getIndex());
        addressResponseDto.setHouse(address.getHouse());
        addressResponseDto.setBasicStatus(address.getBasicStatus());
        return addressResponseDto;
    }
}
