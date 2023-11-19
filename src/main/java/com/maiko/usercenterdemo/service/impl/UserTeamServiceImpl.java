package com.maiko.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiko.usercenterdemo.mapper.UserTeamMapper;
import com.maiko.usercenterdemo.model.domain.UserTeam;
import com.maiko.usercenterdemo.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author Maiko7
* @description 针对表【user_team(用户队伍关系表)】的数据库操作Service实现
* @createDate 2023-11-19 14:08:20
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




