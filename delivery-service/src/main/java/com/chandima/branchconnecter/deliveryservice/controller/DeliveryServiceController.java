package com.chandima.branchconnecter.deliveryservice.controller;

import com.chandima.branchconnecter.deliveryservice.service.DeliveryService;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class DeliveryServiceController {

    @Autowired
    DeliveryService deliveryService;

    @RequestMapping(value = "/getAllDeliverys", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Delivery> getDeliverys(){
        return  deliveryService.getAllDeliverys();
    }

    @RequestMapping(value = "/getDeliveryByID/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Delivery getDeliveryByID(@PathVariable int id){
        Delivery checkDelivery = deliveryService.getDeliveryByID(id);
        if(checkDelivery.equals(null)){
            return null;
        }
        return checkDelivery;
    }

    @RequestMapping(value = "/getDeliveryByOrderID/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Delivery getDeliveryByOrderID(@PathVariable int id){
        Delivery checkDelivery = deliveryService.getDeliveryByOrderID(id);
        if(checkDelivery==null){
            return null;
        }
        return checkDelivery;
    }


    @RequestMapping(value = "/addDelivery", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Delivery addDelivery(@RequestBody Delivery delivery){
//        //check orderid is valid
//        Order order = validateOrder(delivery.getOrderID());
//        if (order == null){
//            return null;
//        }
        Delivery checkDelivery = deliveryService.addDelivery(delivery);
        if(checkDelivery.equals(null)){
            return null;
        }
        return checkDelivery;
    }

    @RequestMapping(value = "/updateDelivery", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Delivery updateDeliveryByID(@RequestBody Delivery delivery){
        if (delivery.getOrderID()>0){
            if(validateOrder(delivery.getOrderID())==null){
                return null;
            }
        }
        Delivery checkDelivery = deliveryService.updateDeliveryByID(delivery);
        if(checkDelivery.equals(null)){
            return null;
        }
        return checkDelivery;
    }


    @RequestMapping(value = "/deleteDelivery/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Delivery deleteDelivery(@PathVariable int id){
        Delivery delivery = deliveryService.deleteDelivery(id);
        return delivery;
    }


    public Order validateOrder(int id){
        String baseURL = "http://orderservice/services/getOrderByID/"+id;
        RestTemplate restTemplate = new RestTemplate();
        Order order =  restTemplate.getForObject(baseURL, Order.class);
        if(order!=null){
            return order;
        }
        return null;
    }

}
