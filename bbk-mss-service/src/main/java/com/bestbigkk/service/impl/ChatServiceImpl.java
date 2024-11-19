package com.bestbigkk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bestbigkk.persistence.dao.ChatDao;
import com.bestbigkk.persistence.entity.ChatPo;
import com.bestbigkk.service.ChatService;
import org.springframework.stereotype.Service;

@Service("chatService")
public class ChatServiceImpl  extends ServiceImpl<ChatDao, ChatPo> implements ChatService {


}
