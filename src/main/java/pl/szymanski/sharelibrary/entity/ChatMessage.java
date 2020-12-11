package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "roomId")
    private ChatRoom chat;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "recipientId")
    private User recipient;

    private String content;

    private LocalDateTime timestamp;
}
