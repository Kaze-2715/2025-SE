package com.agri.platform.service;

import com.agri.platform.entity.Contact;
import com.agri.platform.mapper.ContactMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class ContactService {

    // 注入自定义的ContactMapper（不是BaseMapper）
    @Resource
    private ContactMapper contactMapper;

    // 保存咨询信息（调用自定义的insertContact方法）
    public boolean saveContact(Contact contact) {
        contact.setCreateTime(LocalDateTime.now());  // 设置创建时间
        int rows = contactMapper.insertContact(contact);  // 调用自定义插入方法
        return rows > 0;  // 返回是否插入成功（rows=1表示成功）
    }

    // （可选）根据ID查询（调用自定义方法）
    public Contact getContactById(Long id) {
        return contactMapper.selectContactById(id);
    }

    // （可选）根据ID删除（调用自定义方法）
    public boolean removeContactById(Long id) {
        int rows = contactMapper.deleteContactById(id);
        return rows > 0;
    }
}