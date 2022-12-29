package ru.lazarenko.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lazarenko.springboot.entity.Order;
import ru.lazarenko.springboot.entity.OrderRow;
import ru.lazarenko.springboot.model.OrderStatus;
import ru.lazarenko.springboot.service.OrderRowService;
import ru.lazarenko.springboot.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;
    private final OrderRowService orderRowService;

    @GetMapping("orders/all")
    public String getPageAllOrders(Model model){
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        model.addAttribute("typeOrder", "All");
        return "orders";
    }

    @GetMapping("orders/new")
    public String getPageNewOrders(Model model){
        List<Order> newOrders = orderService.getAllOrdersByOrderStatus(OrderStatus.NEW);
        model.addAttribute("orders", newOrders);
        model.addAttribute("isNewOrders", true);
        model.addAttribute("typeOrder", "New");
        return "orders";
    }

    @GetMapping("orders/accepted")
    public String getPageAcceptedOrders(Model model){
        List<Order> acceptedOrders = orderService.getAllOrdersByOrderStatus(OrderStatus.ACCEPTED);
        model.addAttribute("orders", acceptedOrders);
        model.addAttribute("isNewOrders", false);
        model.addAttribute("isAcceptedOrders", true);
        model.addAttribute("typeOrder", "Accepted");
        return "orders";
    }

    @GetMapping("orders/rejected")
    public String getPageRejectedOrders(Model model){
        List<Order> rejectedOrders = orderService.getAllOrdersByOrderStatus(OrderStatus.REJECTED);
        model.addAttribute("orders", rejectedOrders);
        model.addAttribute("isNewOrders", false);
        model.addAttribute("typeOrder", "Rejected");
        return "orders";
    }

    @GetMapping("orders/finished")
    public String getPageFinishedOrders(Model model){
        List<Order> finishedOrders = orderService.getAllOrdersByOrderStatus(OrderStatus.FINISHED);
        model.addAttribute("orders", finishedOrders);
        model.addAttribute("isNewOrders", false);
        model.addAttribute("typeOrder", "Finished");
        return "orders";
    }

    @GetMapping("/orders/{orderId}/accept")
    public String acceptOrder(@PathVariable Integer orderId){
        orderService.updateOrderStatusById(OrderStatus.ACCEPTED, orderId);
        return "redirect:/admin/orders/all";
    }

    @GetMapping("/orders/{orderId}/reject")
    public String rejectOrder(@PathVariable Integer orderId){
        orderService.updateOrderStatusById(OrderStatus.REJECTED, orderId);
        return "redirect:/admin/orders/all";
    }

    @GetMapping("/orders/{orderId}/finished")
    public String finishOrder(@PathVariable Integer orderId){
        orderService.updateOrderStatusById(OrderStatus.FINISHED, orderId);
        return "redirect:/admin/orders/all";
    }

    @GetMapping("/orders/{orderId}/show")
    public String getPageOrder(@PathVariable Integer orderId, Model model){
        System.out.println(orderId);
        Order order = orderService.getOrderById(orderId);
        List<OrderRow> orderRows = orderRowService.getRowsByOrderId(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderRows", orderRows);
        return "order";
    }
}
