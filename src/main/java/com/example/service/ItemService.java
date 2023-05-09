package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Item;
import com.example.dto.ItemImage;

// 컨트롤러에서 실행하는 클래스
@Service
public interface ItemService {
    // 물품 전체 조회
    public List<Item> selectItemList();

    // 물품 이미지 등록
    public int insertItemImageOne(ItemImage obj);
}
