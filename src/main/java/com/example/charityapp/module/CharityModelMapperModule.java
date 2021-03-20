package com.example.charityapp.module;

import com.example.charityapp.dto.CartDto;
import com.example.charityapp.dto.ProductLineItemDto;
import com.example.charityapp.model.Cart;
import com.example.charityapp.model.ProductLineItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.Module;
import org.springframework.stereotype.Component;

@Component
public class CharityModelMapperModule implements Module {

  @Override
  public void setupModule(ModelMapper modelMapper) {
    updateProductLineItemMapping(modelMapper);
    updateCartMapping(modelMapper);
  }

  private void updateProductLineItemMapping(ModelMapper mapper) {
    var typeMap = mapper.createTypeMap(ProductLineItem.class, ProductLineItemDto.class);
    typeMap.addMapping(src -> src.getProduct().getName(), ProductLineItemDto::setProductName);
    typeMap.addMapping(src -> src.getProduct().getPrice(), ProductLineItemDto::setPrice);
  }

  private void updateCartMapping(ModelMapper mapper) {
    var typeMap = mapper.createTypeMap(Cart.class, CartDto.class);
    typeMap.addMapping(
        src -> {
          var receipt = src.getReceipt();
          return receipt == null ? null : receipt.getPaidAmount();
        },
        CartDto::setPaidAmount);
    typeMap.addMapping(
        src -> {
          var receipt = src.getReceipt();
          return receipt == null ? null : receipt.getPaidTime();
        },
        CartDto::setPaidTime);
  }
}
