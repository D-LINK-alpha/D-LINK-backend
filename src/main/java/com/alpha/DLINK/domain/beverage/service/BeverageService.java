package com.alpha.DLINK.domain.beverage.service;


import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.domain.Nutrition;
import com.alpha.DLINK.domain.beverage.repository.BeverageRepository;
import com.alpha.DLINK.domain.cafe.domain.Cafe;
import com.alpha.DLINK.setting.S3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BeverageService {

    private final BeverageRepository beverageRepository;
    private final S3FileService s3FileService;


    // 음료 데이터 생성
    public Beverage create(String name, Cafe cafe, Nutrition nutrition, String type ,MultipartFile file) throws IOException {
        Beverage beverage = Beverage.create(name, cafe, nutrition, type);
        String url = s3FileService.createPostImageFile("beverage_image", file);
        beverage.setPhoto(url);
        beverageRepository.save(beverage);
        return beverage;
    }

    // 음료 데이터 저장
    public void save(Beverage beverage) {
        beverageRepository.save(beverage);
    }

    // 음료 조회
    public List<Beverage> findAll() {
        return beverageRepository.findAll();
    }
}