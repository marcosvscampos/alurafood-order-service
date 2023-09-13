package br.com.alurafood.orderservice.dto;

import br.com.alurafood.orderservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String id;
    private LocalDateTime dateHour;
    private Status status;
    private List<OrderItemDTO> items = new ArrayList<>();



}
