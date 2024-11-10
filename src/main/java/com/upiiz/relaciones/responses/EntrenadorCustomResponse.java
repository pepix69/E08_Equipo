package com.upiiz.relaciones.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenadorCustomResponse<T> {
    private int estado;
    private String msg;
    private T entrenadores;
    private List<Link> links;
}
