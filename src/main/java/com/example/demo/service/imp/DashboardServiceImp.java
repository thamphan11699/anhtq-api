package com.example.demo.service.imp;

import com.example.demo.dto.DashboardDto;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DashboardServiceImp implements DashboardService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public DashboardDto getDashboard() {
        try {
            Object list = orderRepository.getDashboard();
            Object[] objects = (Object[]) list;
            List<Product>  products = productRepository.getProductsBySold();
            Long revenue = 0L;
            for (Product product: products) {
                if (product.getPriceInput() != null) {
                    revenue += (product.getPrice() - product.getPriceInput());
                } else {
                    revenue += product.getPrice();
                }
            }
            return new DashboardDto(String.valueOf(objects[0]), String.valueOf(objects[1]), String.valueOf(revenue), String.valueOf(objects[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
