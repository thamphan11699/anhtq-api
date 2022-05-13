package com.example.demo.service.imp;

import com.example.demo.dto.ContextProviderDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.model.ContextProvider;
import com.example.demo.repository.ContextProviderRepository;
import com.example.demo.service.ContextProviderService;
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
public class ContextProviderServiceImp implements ContextProviderService {

    @Autowired
    ContextProviderRepository repository;

    @Autowired
    EntityManager manager;

    @Override
    public ContextProviderDto saveOrUpdate(ContextProviderDto dto, Long id) {
        try {
            if (dto != null) {
                ContextProvider contextProvider = null;
                if (id != null) {
                    contextProvider = repository.getById(id);
                }
                if (contextProvider == null) {
                    contextProvider = new ContextProvider();
                }
                contextProvider.setCode(dto.getCode());
                contextProvider.setName(dto.getName());
                contextProvider.setAddress(dto.getAddress());
                contextProvider.setDescription(dto.getDescription());
                contextProvider.setPhoneNumber(dto.getPhoneNumber());
                contextProvider.setRepresentative(dto.getRepresentative());
                contextProvider = repository.save(contextProvider);
                return new ContextProviderDto(contextProvider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ContextProviderDto getOne(Long id) {
        if (id != null) {
            try {
                ContextProvider contextProvider = repository.getById(id);
                return new ContextProviderDto(contextProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Page<ContextProviderDto> getAll(SearchDto dto) {
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
            String sqlCount = "select count(entity.id) from ContextProvider as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.ContextProviderDto(entity) from ContextProvider as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.name like :text OR entity.code like :text OR entity.description) ";
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, ContextProviderDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<ContextProviderDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<ContextProviderDto> result = new PageImpl<>(dtos, pageable, count);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
