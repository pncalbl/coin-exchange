package com.pncalbl.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.mapper.UserAddressMapper;
import com.pncalbl.domain.UserAddress;
import com.pncalbl.service.UserAddressService;
/**
*   @author pncalbl
*   @date 2021/10/21 20:01
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService{

}
