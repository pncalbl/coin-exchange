package com.pncalbl.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.User;
import com.pncalbl.mapper.UserMapper;
import com.pncalbl.service.UserService;
/**
*   @author pncalbl
*   @date 2021/10/21 20:01
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
