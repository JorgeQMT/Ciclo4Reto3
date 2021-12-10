package com.ciclo4.reto3.service;

import com.ciclo4.reto3.model.Order;
import com.ciclo4.reto3.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jorge Quesada
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepositorio;
    
    public List<Order> getAll() {
        return orderRepositorio.getAll();
    }
    
    public Optional<Order> getOrder(int id) {
        return orderRepositorio.getOrder(id);
    }


    public Order create(Order order){ 
        //obtiene el maximo id existente ne la coleccion
        Optional<Order> orderIdMaximo = orderRepositorio.lastOrderId();
        // Si el id del Usuario que se recibe como parametro es nulo, entonces valida el maximo id
        if (order.getId() == null) {
            //Valida el maximo Id generado, si no hay ninguno aun el primer Id sera 1
            if (orderIdMaximo.isEmpty()) {
                order.setId(1);
            }else {
                order.setId(orderIdMaximo.get().getId()+ 1);
            }
        }
        Optional<Order> e = orderRepositorio.getOrder(order.getId());
        if (e.isEmpty()) {
            return orderRepositorio.create(order);
        } else {
            return order;
        }
    }

    public Order update(Order order) {

        if (order.getId() != null) {
            Optional<Order> orderDb = orderRepositorio.getOrder(order.getId());
            if (!orderDb.isEmpty()) {
                if (order.getStatus()!= null) {
                    orderDb.get().setStatus(order.getStatus());
                }
                orderRepositorio.update(orderDb.get());
                return orderDb.get();
            } else {
                return order;
            }
        } else {
            return order;
        }
    }

    public boolean delete(int orderId) {
        /*Optional<User> usuario = getUser(userId);
        
        if (usuario.isEmpty()){
            return false;
        }else{
            userRepositorio.delete(usuario.get());
            return true;
        }
        */
        Boolean aBoolean = getOrder(orderId).map(order -> {
            orderRepositorio.delete(order);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}
