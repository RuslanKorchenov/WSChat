package ru.itis.models;

import lombok.*;
import ru.itis.dto.MessageDto;

import javax.persistence.*;
import java.util.Stack;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"user", "room"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String text;

    @ManyToOne
    private Room room;

}
