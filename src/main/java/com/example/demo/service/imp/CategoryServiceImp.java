package com.example.demo.service.imp;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    EntityManager manager;

    @Autowired
    CategoryRepository repository;


    @Override
    public CategoryDto saveOrUpdate(CategoryDto dto, Long id) {
        try {
            if (dto != null) {
                Category category = null;
                if (id != null) {
                    category = repository.getById(id);
                }
                if (category == null) {
                    category = new Category();
                }
                category.setCode(dto.getCode());
                category.setName(dto.getName());
                category.setDescription(dto.getDescription());
                category.setThumbnail(dto.getThumbnail());
                if (dto.getParent() != null && dto.getParent().getId() != null && dto.getParent().getId() != 0) {
                    category.setParent(repository.getById(dto.getParent().getId()));
                }
                category = repository.save(category);
                return new CategoryDto(category, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<CategoryDto> searchByPage(SearchDto dto) {
        try {
            if (dto == null) {
                return null;
            }
            int pageSize = dto.getPageSize();
            int pageIndex = dto.getPageIndex();
            if (pageIndex > 0)
                pageIndex--;
            else
                pageIndex = 0;

            String order = " ORDER BY entity.updatedBy DESC";
            String whereClause = "";
            String sqlCount = "select count(entity.id) from Category as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.CategoryDto(entity) from Category as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.name like :text OR entity.code like :text) " ;
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, CategoryDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<CategoryDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<CategoryDto> result = new PageImpl<>(dtos, pageable, count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CategoryDto getOne(Long id) {
        try {
            Category category = repository.getById(id);
            return new CategoryDto(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CategoryDto> getByParentId(Long id) {
        try {
            String sql = "";
            if (id != null) {
                sql = "SELECT new com.example.demo.dto.CategoryDto(entity, false ) FROM Category as entity WHERE (1=1) AND entity.parent.id =:id";
            } else {
                sql = "SELECT new com.example.demo.dto.CategoryDto(entity, false ) FROM Category as entity WHERE (1=1) AND entity.parent is NULL ";
            }
            Query query = manager.createQuery(sql, CategoryDto.class);
            if (id != null) {
                query.setParameter("id", id);
            }
            return (List<CategoryDto>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
