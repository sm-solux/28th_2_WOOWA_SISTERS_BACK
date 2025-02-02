package com.woowaSisters.woowaSisters.domain.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwt_token")
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_uuid", columnDefinition = "BINARY(16)")
    private UUID tokenUuid;

    @Column(name = "token_value", nullable = false, unique = true, columnDefinition = "TEXT")
    private String tokenValue;

    @JsonProperty("tokenValue")
    public String getTokenValue() {return tokenValue;}
}
