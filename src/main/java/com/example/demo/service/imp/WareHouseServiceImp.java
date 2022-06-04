package com.example.demo.service.imp;

import com.example.demo.dto.SearchDto;
import com.example.demo.dto.WareHouseDto;
import com.example.demo.model.WareHouse;
import com.example.demo.repository.WareHouseRepository;
import com.example.demo.service.WareHouseService;
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
public class WareHouseServiceImp implements WareHouseService {

    @Autowired
    EntityManager manager;


    @Autowired
    WareHouseRepository repository;

    @Override
    public WareHouseDto saveOrUpdate(WareHouseDto dto, Long id) {
        try {
            if (dto != null) {
                WareHouse wareHouse = null;
                if (id != null) {
                    wareHouse = repository.getById(id);
                }
                if (wareHouse == null) {
                    wareHouse = new WareHouse();
                }
                wareHouse.setCode(dto.getCode());
                wareHouse.setName(dto.getName());
                wareHouse.setMaxSize(dto.getMaxSize());
                wareHouse = repository.save(wareHouse);
                return new WareHouseDto(wareHouse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<WareHouseDto> getAll(SearchDto dto) {
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
            String sqlCount = "select count(entity.id) from WareHouse as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.WareHouseDto(entity) from WareHouse as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.name like :text OR entity.code like :text) " ;
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, WareHouseDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<WareHouseDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<WareHouseDto> result = new PageImpl<>(dtos, pageable, count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WareHouseDto getOne(Long id) {
        try {
            WareHouse wareHouse = repository.getById(id);
            return new WareHouseDto(wareHouse);
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
}
