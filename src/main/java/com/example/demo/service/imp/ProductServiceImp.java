package com.example.demo.service.imp;

import com.example.demo.dto.*;
import com.example.demo.model.Category;
import com.example.demo.model.Image;
import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.payload.ApiResponse;
import com.example.demo.repository.*;
import com.example.demo.service.FileService;
import com.example.demo.service.ImageService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
@Transactional
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    ImageService imageService;

    @Autowired
    FileService fileService;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    EntityManager manager;

    @Autowired
    ContextProviderRepository contextProviderRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public ProductDto saveOrUpdate(ProductDto dto, Long id) {
        try {
            if (dto != null) {
                Product product = null;
                if (id != null) {
                    product = repository.getById(id);
                }
                if (product == null) {
                    product = new Product();
                }
                product.setCode(dto.getCode());
                product.setName(dto.getName());
                product.setTitle(dto.getTitle());
                product.setDescription(dto.getDescription());
                product.setSize(dto.getSize());
                if (dto.getColor() != null && dto.getColor().getId() != null) {
                    product.setColor(colorRepository.getById(dto.getColor().getId()));
                }

                if (dto.getContextProvider() != null && dto.getContextProvider().getId() != null) {
                    product.setContextProvider(contextProviderRepository.getById(dto.getContextProvider().getId()));
                }

                if (dto.getWareHouse() != null && dto.getWareHouse().getId() != null) {
                    product.setWareHouse(wareHouseRepository.getById(dto.getWareHouse().getId()));
                }

                product.setQuantity(dto.getQuantity());
                product.setPrice(dto.getPrice());

                if (dto.getParent() != null && dto.getParent().getId() != null) {
                    Product parent = repository.getById(dto.getParent().getId());
                    // Check co hay chua
                    if (Objects.equals(dto.getColor().getId(), product.getColor().getId()) && Objects.equals(dto.getSize(), product.getSize()) && id == null) {
                        return new ProductDto();
                    }
                    product.setParent(parent);
                    if (dto.getCategories() == null || dto.getCategories().size() == 0) {
                        product.getCategories().clear();
                        product.getCategories().addAll(parent.getCategories());
                    }
                    // Lay ten code, theo cha
                    product.setName(parent.getName());
                    product.setCode(("C-" + parent.getCode() + "-" + UUID.randomUUID()).toUpperCase());
                    product.setTitle(parent.getTitle());
                    if (parent.getContextProvider() != null && parent.getContextProvider().getId() != null) {
                        product.setContextProvider(parent.getContextProvider());
                    }
                    if (parent.getWareHouse() != null && parent.getWareHouse().getId() != null) {
                        product.setWareHouse(parent.getWareHouse());
                    }
                }

                if (dto.getCategories() != null && dto.getCategories().size() > 0) {
                    Set<Category> categories = new HashSet<>();
                    for (CategoryDto categoryDto : dto.getCategories()) {
                        Category category = categoryRepository.getById(categoryDto.getId());
                        categories.add(category);
                    }
                    product.getCategories().clear();
                    product.getCategories().addAll(categories);
                }
                product.setThumbnail(dto.getThumbnail());
                if (dto.getImages() != null && dto.getImages().size() > 0) {
                    Set<Image> images = new HashSet<>();
                    for (ImageDto imageDto : dto.getImages()) {
                        Image image = imageRepository.getById(imageDto.getId());
                        images.add(image);
                    }
                    if (product.getImages() != null) {
                        product.getImages().clear();
                    }
                    product.getImages().addAll(images);

                }
                product = repository.save(product);

                return new ProductDto(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductDto getOne(Long id) {
        try {
            if (id != null) {
                Product product = repository.getById(id);
                return new ProductDto(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<ProductDto> getAll(SearchProductDto dto) {
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

            String order = " ORDER BY entity.updatedBy DESC";
            String whereClause = "";
            String sqlCount = "select count(entity.id) from Product as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.ProductDto(entity, true) from Product as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.name like :text OR entity.code like :text OR entity.title :text) ";
            }
            if (dto.getUseParent() != null && StringUtils.hasText(dto.getUseParent())) {
                whereClause += " AND entity.parent is NULL ";
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, ProductDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<ProductDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<ProductDto> result = new PageImpl<>(dtos, pageable, count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            if (id != null) {
                repository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProductDto updateThumbnail(MultipartFile multipartFile, Long id) {
        try {
            Product product = repository.getById(id);
            if (product != null) {
                String thumbnail = fileService.uploadFile(multipartFile);
                product.setThumbnail(thumbnail);
                product = repository.save(product);
                return new ProductDto(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductDto updateImage(MultipartFile[] multipartFiles, Long id) {
        try {
            Product product = repository.getById(id);
            product.getImages().clear();
            List<Image> image = imageService.save(multipartFiles);
            Set<Image> images = new HashSet<>(image);
            product.getImages().addAll(images);
            product = repository.save(product);
            return new ProductDto(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProductDto> getByParentId(Long id) {
        try {
            String sql = "";
            if (id != null) {
                sql = "SELECT new com.example.demo.dto.ProductDto(entity ) FROM Product as entity WHERE (1=1) AND entity.parent.id =:id";
            } else {
                sql = "SELECT new com.example.demo.dto.ProductDto(entity) FROM Product as entity WHERE (1=1) AND entity.parent is NULL ";
            }
            Query query = manager.createQuery(sql, ProductDto.class);
            if (id != null) {
                query.setParameter("id", id);
            }
            return (List<ProductDto>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProductDto> getByCategory(Long cateId) {
        List<Product> products;
        try {
            if (cateId != null) {
                products = repository.getProductsByCategory(cateId);
            } else {
                products = repository.getProductsByAllCategory();
            }
            List<ProductDto> result = new ArrayList<>();
            for (Product product : products) {
                ProductDto productDto = new ProductDto(product);
                result.add(productDto);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductDto getOneByUser(Long id) {
        try {
            if (id != null) {
                Product product = repository.getById(id);
                return new ProductDto(product, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductDto findByColorAndSize(Long parentId, ColorDto color, String size) {
        try {
            String sql = "";
            sql = "SELECT new com.example.demo.dto.ProductDto(entity ) FROM Product as entity WHERE (1=1) AND entity.parent.id =:id " +
                    " AND entity.color.id =:colorId AND entity.size =:size";
            Query query = manager.createQuery(sql, ProductDto.class);
            query.setParameter("id", parentId);
            query.setParameter("colorId", color.getId());
            query.setParameter("size", size);
            List<ProductDto> list = (List<ProductDto>) query.getResultList();
            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
