package com.example.demo.service.imp;

import com.example.demo.dto.NewsDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.model.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.FileService;
import com.example.demo.service.NewsService;
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
public class NewsServiceImp implements NewsService {

    @Autowired
    NewsRepository repository;

    @Autowired
    EntityManager manager;

    @Autowired
    FileService fileService;

    @Override
    public NewsDto saveOrUpdate(NewsDto dto, Long id) {
        try {
            if (dto != null) {
                News news = null;
                if (id != null) {
                    news = repository.getById(id);
                }
                if (news == null) {
                    news = new News();
                }
                news.setTitle(dto.getTitle());
                news.setContent(dto.getContent());
                news.setThumbnail(dto.getThumbnail());
                news = repository.save(news);
                return new NewsDto(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public NewsDto getOne(Long id) {
        try {
            if (id != null) {
                News news = repository.getById(id);
                return new NewsDto(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<NewsDto> getAll(SearchDto dto) {
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
            String sqlCount = "select count(entity.id) from News as entity where (1=1)  ";
            String sql = "select new com.example.demo.dto.NewsDto(entity) from News as entity where (1=1)  ";
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                whereClause += " AND (entity.title like :text ) ";
            }
            sql += whereClause;
            sql += order;
            sqlCount += whereClause;
            Query query = manager.createQuery(sql, NewsDto.class);
            Query queryCount = manager.createQuery(sqlCount);
            if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
                query.setParameter("text", '%' + dto.getText().trim() + '%');
                queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
            }

            int startPosition = pageIndex * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
            List<NewsDto> dtos = query.getResultList();
            long count = (long) queryCount.getSingleResult();

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<NewsDto> result = new PageImpl<>(dtos, pageable, count);
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
