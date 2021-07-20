package com.sai.ecommerce.controller;

import com.sai.ecommerce.global.GlobalData;
import com.sai.ecommerce.model.Product;
import com.sai.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable long id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGetDetails(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        //mapToDouble : sum of the cart
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getProductPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeCartItem(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getProductPrice).sum());
        return "checkout";
    }
}
