package com.maiko.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiko.usercenterdemo.mapper.ChatMapper;
import com.maiko.usercenterdemo.model.domain.Chat;
import com.maiko.usercenterdemo.service.ChatService;
import org.springframework.stereotype.Service;

/**
* @author Maiko7
* @description 针对表【chat(消息表)】的数据库操作Service实现
* @createDate 2023-11-19 14:08:20
*/
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat>
    implements ChatService {

}




