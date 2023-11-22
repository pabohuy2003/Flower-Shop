/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author DELL
 */
@Builder
@Getter
@Setter
@ToString
public class Cart {
    private int productId;
    private String orderDetailProductImg;
    private String orderDetailProductName;
    private int orderDetailPriceProduct;
    private String orderDetailColorValue;
    private int orderDetailQuantity;
}
