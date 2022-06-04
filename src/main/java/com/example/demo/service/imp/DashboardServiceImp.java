package com.example.demo.service.imp;

import com.example.demo.dto.DashboardDto;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DashboardServiceImp implements DashboardService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public DashboardDto getDashboard() {
        try {
            Object list = orderRepository.getDashboard();
            Object[] objects = (Object[]) list;
            return new DashboardDto(String.valueOf(objects[0]), String.valueOf(objects[1]), String.valueOf(objects[3]), String.valueOf(objects[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
