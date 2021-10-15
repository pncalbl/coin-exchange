package com.pncalbl.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.Notice;
import com.pncalbl.mapper.NoticeMapper;
import com.pncalbl.service.NoticeService;
/**
*   @author pncalbl
*   @date 2021/10/15 20:37
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

}
