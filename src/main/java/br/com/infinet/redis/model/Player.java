package br.com.infinet.redis.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Player {
    private String name;
    private Long score;
}
