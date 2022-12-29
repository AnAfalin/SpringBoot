package ru.lazarenko.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lazarenko.springboot.entity.Cart;
import ru.lazarenko.springboot.entity.Client;
import ru.lazarenko.springboot.entity.Product;
import ru.lazarenko.springboot.service.CartService;
import ru.lazarenko.springboot.service.ClientService;
import ru.lazarenko.springboot.service.OrderService;
import ru.lazarenko.springboot.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("/reg")
    public String getPageRegistration(Model model){
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/new-client")
    public String createNewClient(@ModelAttribute Client client){
        clientService.createClient(client);
        return "redirect:/clients/all";
    }

    @GetMapping("/all")
    public String getPageAllClients(Model model){
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/{clientId}/select-product")
    public String getPageBuyProducts(@PathVariable Integer clientId, Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products-list";
    }

    @PostMapping("/{clientId}/add-product-in-cart/{productId}")
    public String addProductInCart(@PathVariable Integer clientId, @PathVariable Integer productId,
                                   @RequestParam Integer count){
        clientService.addProductInCart(clientId, productId, count);
        return "redirect:/clients/{clientId}/cart";
    }

    @GetMapping("/{clientId}/cart")
    public String getPageCartClient(@PathVariable Integer clientId, Model model){
        Cart cart = cartService.getCartWithCartRowsByClientId(clientId);
        model.addAttribute("cartRows", cart.getCartRows());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/{clientId}/orders/change-row/{cartRowId}")
    public String changeCartRow(@PathVariable Integer clientId, @PathVariable Integer cartRowId,
                                @RequestParam Integer newCount){
        cartService.changeCountRowById(clientId, cartRowId, newCount);
        return "redirect:/clients/{clientId}/cart";
    }

    @GetMapping("/{clientId}/orders/delete-row/{cartRowId}")
    public String deleteCartRow(@PathVariable Integer clientId, @PathVariable Integer cartRowId){
        cartService.deleteRowById(clientId, cartRowId);
        return "redirect:/clients/{clientId}/cart";
    }

    @GetMapping("/{clientId}/order/create-order")
    public String createOrder(@PathVariable Integer clientId){
        orderService.createOrderByClientId(clientId);
        return "redirect:/clients/all";
    }
}
