package com.app.manager.controller;

import com.app.manager.model.midware_model.ListProduct;
import com.app.manager.model.midware_model.ProductModel;
import com.app.manager.model.returnResult.JsonResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    @RequestMapping({"/home", "/"})
    public String index() {
        var headers = new HttpHeaders();
        var productAdd = new ProductModel("new product", 100, 100);
        var entity = new HttpEntity<>(productAdd, headers);
        var restTemplate = new RestTemplate();

        var response = restTemplate
                .exchange("http://localhost:8080/api/product/add",
                        HttpMethod.POST, entity, String.class);



        var data = response.getBody();

        if (data != null) {
            var xmlMapper = new XmlMapper();
            System.out.println(data);
            try {
                var result = xmlMapper.readValue(data, JsonResult.class);
                System.out.println(result.getDescription());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }else {
            System.out.println("error");
        }

        return "home";
    }
    @RequestMapping({"/list"})
    public String list() {
        var headers = new HttpHeaders();
        var entity = new HttpEntity<>("", headers);
        var restTemplate = new RestTemplate();

        var response = restTemplate
                .exchange("http://localhost:8080/api/product",
                        HttpMethod.GET, entity, String.class);
        var data = response.getBody();

        if (data != null) {
            var xmlMapper = new XmlMapper();
            System.out.println(data);
            try {
                var result = xmlMapper.readValue(data, ListProduct.class);
                var list = result.getProductModels();
                for (ProductModel productModel: list
                     ) {
                    System.out.println(productModel.getName());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }else {
            System.out.println("error");
        }

        return "list";
    }


}
