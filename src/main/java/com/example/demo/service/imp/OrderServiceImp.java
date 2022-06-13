package com.example.demo.service.imp;

import com.example.demo.Constants;
import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    EntityManager manager;


    @Override
    public Page<OrderDto> getAllOrder(SearchOrderDto dto) {
        if (dto == null) {
            return null;
        }
        try {
            int pageSize = dto.getPageSize();
            int pageIndex = dto.getPageIndex();
            if (pageIndex > 0)
                pageIndex--;
            else
                pageIndex = 0;

            String order = " ORDER BY entity.createdAt DESC";
            String whereClause = "";
            String sqlCount = "select count(entity.id) from Order as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.OrderDto(entity) from Order as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.code like :text ) ";
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, OrderDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<OrderDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            return new PageImpl<>(dtos, pageable, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderDto getById(Long id) {
        if (id != null) {
            Order order = orderRepository.getById(id);
            return new OrderDto(order);
        }
        return null;
    }

    @Override
    public List<OrderDto> getByUserId(Long id) {
        try {
            List<Order> orders = orderRepository.getOrderByUser(id);
            return orders.stream().map(OrderDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public OrderDto addOrder(CartDto cartDto) {
        if (cartDto != null && cartDto.getId() != null) {
            try {
                // TODO Lay ra cac san pham trong gio hang
                Order order = new Order();
                order.setCode(String.valueOf(UUID.randomUUID()).toUpperCase());
                order.setStatus(Constants.CREATED);
                order.setIncome(cartDto.getSubtotal());
                order.setDescription(cartDto.getDescription());
                orderRepository.save(order);
                Set<OrderProduct> orderProducts = new HashSet<>();
                if (cartDto.getCartProducts() != null && !cartDto.getCartProducts().isEmpty()) {
                    for (CartProductDto cartProductDto: cartDto.getCartProducts()) {
                        if (cartProductDto.getProduct() != null && cartProductDto.getProduct().getId() != null) {
                            OrderProduct orderProduct = new OrderProduct();
                            Product product = productRepository.getById(cartProductDto.getProduct().getId());
                            // TODO: Tao thi phai tru so luong san pham
                            orderProduct.setProduct(product);
                            orderProduct.setAmount(cartProductDto.getAmount());
                            orderProduct.setOrder(order);
                            orderProductRepository.save(orderProduct);
                            orderProducts.add(orderProduct);
                            product.setQuantity((int) (product.getQuantity() - cartProductDto.getAmount()));
                            product.setSold(product.getSold() != null ? product.getSold() + cartProductDto.getAmount() : cartProductDto.getAmount());
                                productRepository.save(product);
                            CartProduct cartProduct = cartProductRepository.getById(cartProductDto.getId());
                            cartProduct.setIsDelete(2L);
                            cartProductRepository.save(cartProduct);
                        }
                    }
                    order.setOrderProducts(orderProducts);
                }
                if (cartDto.getUser() != null && cartDto.getUser().getId() != null) {
                    User user = userRepository.getById(cartDto.getUser().getId());
                    order.setUser(user);
                }
                orderRepository.save(order);
                return new OrderDto(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public OrderDto changeStatus(Long id, Long status, String desc) {
        if (id != null) {
            Order order = orderRepository.getById(id);
            order.setStatus(status);
            order.setDescription(desc);
            orderRepository.save(order);
            if (status == 6) {
                Set<OrderProduct> orderProducts = order.getOrderProducts();
                for (OrderProduct orderProduct : orderProducts) {
                    Product product = orderProduct.getProduct();
                    product.setQuantity((int) (product.getQuantity() + orderProduct.getAmount()));
                    product.setSold(product.getSold() - orderProduct.getAmount());
                    productRepository.save(product);
                }
            }
            return new OrderDto(order);
        }
        return null;
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long id) {
        if (id != null) {
            try {
                Order order = orderRepository.getById(id);
                order.setStatus(order.getStatus());
                order.setDescription(orderDto.getDescription());
                order.setCode(orderDto.getCode());
                orderRepository.save(order);
                return new OrderDto(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
