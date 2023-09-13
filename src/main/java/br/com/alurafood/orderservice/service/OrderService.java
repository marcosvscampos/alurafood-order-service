package br.com.alurafood.orderservice.service;

import br.com.alurafood.orderservice.dto.OrderDTO;
import br.com.alurafood.orderservice.dto.StatusDTO;
import br.com.alurafood.orderservice.model.Order;
import br.com.alurafood.orderservice.model.Status;
import br.com.alurafood.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository repository;

    private ModelMapper modelMapper;


    public List<OrderDTO> getAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO getById(String id) {
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO create(OrderDTO dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setDateHour(LocalDateTime.now());
        order.setStatus(Status.REALIZADO);
        order.getItems().forEach(item -> item.setOrder(order));
        Order salvo = repository.save(order);

        return modelMapper.map(salvo, OrderDTO.class);
    }

    public OrderDTO updateStatus(String id, StatusDTO dto) {

        Order order = repository.byIdWithItens(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDTO.class);
    }

    public void approveOrderPayment(String id) {

        Order order = repository.byIdWithItens(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAGO);
        repository.updateStatus(Status.PAGO, order);
    }
}
