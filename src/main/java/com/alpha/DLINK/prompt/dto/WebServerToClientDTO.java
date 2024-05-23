package com.alpha.DLINK.prompt.dto;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.beverage.domain.Nutrition;
import com.alpha.DLINK.domain.beverage.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class WebServerToClientDTO {
    private Double similarity;
    private Long beverageId;
    private String name;
    private Nutrition nutrition;
    private String photo;
    private Type type;
    private Integer price;

    public WebServerToClientDTO(Double similarity, Beverage beverage) {
        this.similarity = similarity;
        this.beverageId = beverage.getId();
        this.name = beverage.getName();
        this.nutrition = beverage.getNutrition();
        this.photo = beverage.getPhoto();
        this.type = beverage.getType();
        this.price = beverage.getPrice();
    }
}
