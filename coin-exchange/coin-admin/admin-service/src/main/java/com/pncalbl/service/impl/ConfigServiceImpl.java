package com.pncalbl.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.Config;
import com.pncalbl.mapper.ConfigMapper;
import com.pncalbl.service.ConfigService;
/**
*   @author pncalbl
*   @date 2021/10/15 20:37
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{

}
