package com.pncalbl.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.UserAuthAuditRecord;
import com.pncalbl.mapper.UserAuthAuditRecordMapper;
import com.pncalbl.service.UserAuthAuditRecordService;
/**
*   @author pncalbl
*   @date 2021/10/21 20:01
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
@Service
public class UserAuthAuditRecordServiceImpl extends ServiceImpl<UserAuthAuditRecordMapper, UserAuthAuditRecord> implements UserAuthAuditRecordService{

}
