package com.app.manager.controller;

import com.app.manager.model.midware_model.ListProduct;
import com.app.manager.model.midware_model.ProductModel;
import com.app.manager.model.midware_model.SellModel;
import com.app.manager.model.returnResult.JsonResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    private static final String host = "http://localhost:8080/api/product";


    @GetMapping({"/","/list"})
    public String list(Model model) {
        var entity = new HttpEntity<>("", new HttpHeaders());
        var restTemplate = new RestTemplate();

        try {
            var data = restTemplate
                    .exchange(host, HttpMethod.GET, entity, ListProduct.class).getBody();
            if (data != null) model.addAttribute("products", data.getProductModels());
        } catch (RestClientException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return "list";
    }


    @GetMapping({"/add"})
    public String add(Model model) {
        model.addAttribute("productModel", new ProductModel());
        return "add";
    }

    @PostMapping({"/add"})
    public String add(@Validated @ModelAttribute ProductModel productModel,
                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            for (var item :
                    bindingResult.getAllErrors()) {
                System.out.println(item.getDefaultMessage());
            }
            return "add";
        }

        var entity = new HttpEntity<>(productModel, new HttpHeaders());
        var restTemplate = new RestTemplate();

        try {
            var data = restTemplate
                    .exchange(host + "/add", HttpMethod.POST, entity, JsonResult.class).getBody();
            if (data != null && data.isSuccess()){
                return "redirect:/";
            }else {
                return "add";
            }

        } catch (RestClientException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "add";
        }
    }

    @GetMapping({"/sell"})
    public String sell(Model model, @RequestParam(value = "id") String id) {
        model.addAttribute("sellModel", new SellModel(id));

        var entity = new HttpEntity<>("", new HttpHeaders());
        var restTemplate = new RestTemplate();

        try {
            var data = restTemplate
                    .exchange(host + "/detail?id=" + id,
                            HttpMethod.GET, entity, String.class).getBody();
            if (data != null) {
                if(data.equals("ResourceNotFoundException"))
                    throw new ResourceNotFoundException();

                ObjectMapper mapper = new ObjectMapper();
                try {
                    var dataDeserialize  = mapper.readValue(data, ProductModel.class);
                    model.addAttribute("name", dataDeserialize.getName());
                    if(dataDeserialize.getAmount() <= 0)  return "redirect:/";
                    return "sell";
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new InternalServerErrorException();
                }
            }else {
                throw new InternalServerErrorException();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new InternalServerErrorException();
        }
    }

    @PostMapping({"/sell"})
    public String sell(@Validated @ModelAttribute SellModel sellModel,
                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            for (var item :
                    bindingResult.getAllErrors()) {
                System.out.println(item.getDefaultMessage());
            }
            return "sell";
        }
        var entity = new HttpEntity<>(sellModel, new HttpHeaders());
        var restTemplate = new RestTemplate();

        try {
            var data = restTemplate
                    .exchange(host + "/sell",
                            HttpMethod.POST, entity, JsonResult.class).getBody();
            if (data != null && data.isSuccess()){
                return "redirect:/";
            }else {
                return "sell";
            }

        } catch (RestClientException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "sell";
        }
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {

    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class InternalServerErrorException extends RuntimeException {

    }

}
