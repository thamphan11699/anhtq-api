package com.example.demo.service.imp;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EntityManager manager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto getOne(Long id) {
        try {
            User user = userRepository.getOne(id);
            return new UserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDto saveOrUpdate(UserDto userDto, Long id) {
        try {
            User user = null;
            if (id != null) {
                user = userRepository.getById(id);
            }

            if (user == null) {
                user = new User();
            }
            user.setAvatar(userDto.getAvatar());
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setEmail(userDto.getEmail());
            user.setStatus(1);
            if (userDto.getUserInfo() != null) {
                UserInfo userInfo = new UserInfo();
                userInfo.setAddress(userDto.getUserInfo().getAddress());
                userInfo.setBirthOfDate(userDto.getUserInfo().getBirthOfDate());
                userInfo.setFullName(userDto.getUserInfo().getFullName());
                userInfo.setGender(userDto.getUserInfo().getGender());
                userInfo.setPhoneNumber(userDto.getUserInfo().getPhoneNumber());
                userInfo = userInfoRepository.save(userInfo);
                user.setUserInfo(userInfo);
            }
            if (userDto.getRoles() != null && userDto.getRoles().size() > 0) {

                Set<Role> roles = new HashSet<>();
                for (RoleDto roleDto: userDto.getRoles()) {
                    Role role = roleRepository.getById(roleDto.getId());
                    if (role != null) {
                        roles.add(role);
                    }
                }
                user.getRoles().clear();
                user.getRoles().addAll(roles);
            }
            user = userRepository.save(user);
            return new UserDto(user);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (id != null) {
            User user = userRepository.getById(id);
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public Page<UserDto> getAll(SearchDto dto) {
        if (dto == null) {
            return null;
        }
        int pageSize = dto.getPageSize();
        int pageIndex = dto.getPageIndex();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;

        String order = " ORDER BY entity.updatedAt DESC";
        String whereClause = "";
        String sqlCount = "select count(entity.id) from User as entity where (1=1) AND entity.status = 1 ";
        String sql = "select new com.example.demo.dto.UserDto(entity) from User as entity where (1=1) AND entity.status = 1 ";
        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            whereClause += " AND (entity.username like :text OR entity.email like :text) " ;
        }
        sql += whereClause + " ";
        sql += order;
        sqlCount += whereClause;
        Query query = manager.createQuery(sql, UserDto.class);
        Query queryCount = manager.createQuery(sqlCount);
        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            query.setParameter("text", '%' + dto.getText().trim() + '%');
            queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
        }

        int startPosition = pageIndex * pageSize;
        query.setFirstResult(startPosition);
        query.setMaxResults(pageSize);
        List<UserDto> roleDtos = query.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<UserDto> result = new PageImpl<>(roleDtos, pageable, count);
        return result;
    }


    private SimpleMailMessage createMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("tranquocanh11061999@gmail.com");
        mailMessage.setTo(user.getEmail());
        String mailSubject = "Well Come to CoaCa ";
        String mailContent = "Xin chào " + " " + user.getUserInfo().getFullName() + "\n";
        mailContent += "Chào mừng bạn đến với gian hàng của chúng tôi";
        mailContent += "Hãy lựa chọn cho mình những sản phẩm tốt nhất!!!!";
        mailMessage.setSubject(mailSubject);
        mailMessage.setText(mailContent);
        return mailMessage;
    }
}
