package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.Notice;
import com.idleItem.tradeSystem.vo.PageVo;

import java.util.List;

public interface NoticeService {
    boolean addNotice(Notice notice);
    PageVo<Notice> getAllNotice(int page, int nums);
    PageVo<Notice> getNotice();
    boolean deleteNotice(long id);
}
