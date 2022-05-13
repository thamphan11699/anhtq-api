package com.example.demo.service.imp;

import com.example.demo.dto.ColorDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.model.Color;
import com.example.demo.repository.ColorRepository;
import com.example.demo.service.ColorService;
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
public class ColorServiceImp implements ColorService {

    @Autowired
    ColorRepository repository;

    @Autowired
    EntityManager manager;

    @Override
    public ColorDto saveOrUpdate(ColorDto dto, Long id) {
        try {
            if (dto != null) {
                Color color = null;
                if (id != null) {
                    color = repository.getById(id);
                }
                if (color == null) {
                    color = new Color();
                }
                color.setCode(dto.getCode());
                color.setName(dto.getName());
                color = repository.save(color);
                return new ColorDto(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ColorDto getOne(Long id) {
        try {
            if (id != null) {
                Color color = repository.getById(id);
                return new ColorDto(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<ColorDto> getAll(SearchDto dto) {
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
            String sqlCount = "select count(entity.id) from Color as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.ColorDto(entity) from Color as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.name like :text OR entity.code like :text ) ";
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, ColorDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<ColorDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<ColorDto> result = new PageImpl<>(dtos, pageable, count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null) {
            try {
                repository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
