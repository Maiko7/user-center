package com.maiko.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiko.usercenterdemo.mapper.VisitorMapper;
import com.maiko.usercenterdemo.model.domain.Visitor;
import com.maiko.usercenterdemo.service.VisitorService;
import org.springframework.stereotype.Service;

/**
* @author Maiko7
* @description 针对表【visitor(访客表)】的数据库操作Service实现
* @createDate 2023-11-19 14:08:20
*/
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor>
    implements VisitorService {

}




