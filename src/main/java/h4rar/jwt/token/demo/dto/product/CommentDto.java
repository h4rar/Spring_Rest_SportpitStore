package h4rar.jwt.token.demo.dto.product;

import h4rar.jwt.token.demo.dto.user.response.UserResponseDto;
import h4rar.jwt.token.demo.model.*;
import h4rar.jwt.token.demo.model.statuses.BasicStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String text;

    private BasicStatus basicStatus;

    private UserResponseDto user;



    public CommentDto(Comment comm) {
        this.id = comm.getId();
        this.created = comm.getCreated();
        this.updated = comm.getUpdated();
        this.text = comm.getText();
        this.user = UserResponseDto.fromUser(comm.getUser());
        this.basicStatus = comm.getBasicStatus();
    }
}
