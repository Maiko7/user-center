package com.maiko.usercenterdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiko.usercenterdemo.mapper.TeamMapper;
import com.maiko.usercenterdemo.model.domain.Team;
import com.maiko.usercenterdemo.service.TeamService;
import org.springframework.stereotype.Service;

/**
* @author Maiko7
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2023-11-19 14:08:20
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService {

}




