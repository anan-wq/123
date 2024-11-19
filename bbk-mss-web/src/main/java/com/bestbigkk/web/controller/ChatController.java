package com.bestbigkk.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bestbigkk.common.ListResponse;
import com.bestbigkk.common.Pagination;
import com.bestbigkk.common.exception.BusinessException;
import com.bestbigkk.persistence.entity.ChatPo;
import com.bestbigkk.service.ChatService;
import com.bestbigkk.web.response.annotation.RW;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Date;

@RW
@Api(tags = {"站内消息接口"})
@Slf4j
@RequestMapping(value = "/dev/chat", produces = {"application/json;charset=UTF-8"})
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/getChatList")
    public ListResponse<ChatPo> getChatList(ChatPo chatPo, Pagination<ChatPo> page){
        QueryWrapper<ChatPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper-> wrapper.eq("send_id",chatPo.getSendId()).or().eq("send_id",chatPo.getAcceptId()));
        queryWrapper.and(wrapper-> wrapper.eq("accept_id",chatPo.getSendId()).or().eq("accept_id",chatPo.getAcceptId()));
        queryWrapper.orderByAsc("create_time");
        IPage<ChatPo> record = chatService.page(page.toPage("create_time"), queryWrapper);
        return new ListResponse<>(record.getRecords(), new Pagination<ChatPo>().toPagination(record));
    }

    @PostMapping("/sendChat")
    public ChatPo getChatList(ChatPo chatPo){
        if (chatService.save(chatPo)) {
            chatPo.setCreateTime(LocalDateTime.now());
            return chatPo;
        }
        throw new BusinessException("发送失败");
    }
}
