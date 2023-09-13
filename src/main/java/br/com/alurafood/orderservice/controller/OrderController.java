package br.com.alurafood.orderservice.controller;

import br.com.alurafood.orderservice.dto.OrderDTO;
import br.com.alurafood.orderservice.dto.StatusDTO;
import br.com.alurafood.orderservice.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

        private OrderService service;

        @GetMapping("/port")
        public ResponseEntity<String> getInstancePort(@Value("${local.server.port}") String port){
            Map<String, String> response = new HashMap<>();
            System.out.println(port);
            return ResponseEntity.ok("Current Port: " + port);
        }

        @GetMapping
        public List<OrderDTO> getAll() {
            return service.getAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> getById(@PathVariable @NotNull String id) {
            OrderDTO dto = service.getById(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping
        public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO request, UriComponentsBuilder uriBuilder) {
            OrderDTO pedidoRealizado = service.create(request);

            URI endereco = uriBuilder.path("/orders/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable String id, @RequestBody StatusDTO status){
           OrderDTO dto = service.updateStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/pay")
        public ResponseEntity<Void> approvePayment(@PathVariable @NotNull String id) {
            service.approveOrderPayment(id);

            return ResponseEntity.ok().build();

        }
}
