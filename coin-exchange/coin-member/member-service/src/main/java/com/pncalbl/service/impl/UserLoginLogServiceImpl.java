package com.pncalbl.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.mapper.UserLoginLogMapper;
import com.pncalbl.domain.UserLoginLog;
import com.pncalbl.service.UserLoginLogService;
/**
*   @author pncalbl
*   @date 2021/10/21 20:01
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService{

}
