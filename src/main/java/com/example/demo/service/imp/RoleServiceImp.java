package com.example.demo.service.imp;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
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
import java.util.List;

@Transactional
@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    RoleRepository repository;

    @Autowired
    EntityManager manager;

    @Override
    public RoleDto saveOrUpdate(RoleDto roleDto, Long id) {
        if (roleDto != null) {
            Role role = null;
            if (id != null) {
                role = repository.getById(id);
            }
            if (role == null) {
                role = new Role();
            }
            String name = "ROLE_" + roleDto.getName().trim().toUpperCase();
            role.setName(name);
            role = repository.save(role);
            return new RoleDto(role);
        }
        return null;
    }

    @Override
    public Page<RoleDto> getRoles(SearchDto dto) {
        if (dto == null) {
            return null;
        }
        int pageSize = dto.getPageSize();
        int pageIndex = dto.getPageIndex();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;

        String whereClause = "";
        String sqlCount = "select count(role.id) from Role as role where (1=1)";
        String sql = "select new com.example.demo.dto.RoleDto(role) from Role as role where (1=1)";
        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            whereClause += " AND role.name like :text";
        }
        sql += whereClause;
        sqlCount += whereClause;
        Query query = manager.createQuery(sql, RoleDto.class);
        Query queryCount = manager.createQuery(sqlCount);
        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            query.setParameter("text", '%' + dto.getText() + '%');
            queryCount.setParameter("text", '%' + dto.getText() + '%');
        }

        int startPosition = pageIndex * pageSize;
        query.setFirstResult(startPosition);
        query.setMaxResults(pageSize);
        List<RoleDto> roleDtos = query.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<RoleDto> result = new PageImpl<>(roleDtos, pageable, count);
        return result;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null) {
            Role entity = repository.getOne(id);
            if (entity != null) {
                repository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public RoleDto getOne(Long id) {
        if (id != null) {
            Role role = repository.getOne(id);
            return new RoleDto(role);
        }
        return null;
    }
}
