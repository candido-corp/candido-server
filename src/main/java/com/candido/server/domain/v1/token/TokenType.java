package com.candido.server.domain.v1.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_type")
public class TokenType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_type_id")
    private int id;

    @Column(name = "description")
    private String description;

    public TokenType(int id) {
        this.id = id;
    }

}
