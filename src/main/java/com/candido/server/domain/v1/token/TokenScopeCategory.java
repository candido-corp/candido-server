package com.candido.server.domain.v1.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_scope_category")
public class TokenScopeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_scope_category_id")
    private int id;

    @Column(name = "description")
    private String description;

    public TokenScopeCategory(int id) {
        this.id = id;
    }

}
