package com.sai.ecommerce.controller;

import com.sai.ecommerce.dto.ProductDTO;
import com.sai.ecommerce.model.Category;
import com.sai.ecommerce.model.Product;
import com.sai.ecommerce.service.CategoryService;
import com.sai.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        //category is the key and value is complete Category object
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategories(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategories(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategoriesAdd(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "categoriesAdd";
        }
        else {
            return "404";
        }

    }

    //product Information
    @GetMapping("/admin/products")
    public String getProducts(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
                               @RequestParam("productImage")MultipartFile file,
                               @RequestParam("ProductImageName") String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductWeight(productDTO.getProductWeight());
        product.setProductDescription(productDTO.getProductDescription());
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else {
            imageUUID = imgName;
        }
        product.setProductImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";

    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProducts(@PathVariable long id){
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductDetails(@PathVariable long id, Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setProductWeight(product.getProductWeight());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setProductImageName(product.getProductImageName());

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("productDTO", productDTO);

        return "productsAdd";
    }


}
