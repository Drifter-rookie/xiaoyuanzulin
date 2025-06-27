package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.Notice;
import com.idleItem.tradeSystem.mapper.NoticeMapper;
import com.idleItem.tradeSystem.service.NoticeService;
import com.idleItem.tradeSystem.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    public boolean addNotice(Notice notice) {
        return noticeMapper.insert(notice) == 1;
    }
    public PageVo<Notice> getAllNotice(int page, int nums){
        List<Notice> list=noticeMapper.getAllNotice((page-1)*nums,nums);
        int count=noticeMapper.getCount();
        return new PageVo<>(list,count);
    }
    public PageVo<Notice> getNotice(){
        List<Notice> list=noticeMapper.getNotice();
        int count=noticeMapper.getCount();
        return new PageVo<>(list,count);
    }
    public boolean deleteNotice(long id){
        return noticeMapper.deleteByPrimaryKey(id)==1;
    }
}
