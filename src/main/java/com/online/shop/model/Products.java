package com.online.shop.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(content = Include.NON_NULL)
@Document(collection = "app_products")
public class Products {

	@Id
	private String id = UUID.randomUUID().toString();

	@Field(name = "product_name")
	private String productName;

	@Indexed
	@Field(name = "product_id")
	private String productId;

	@Field(name = "quantity")
	private Double quantity;

	@Field(name = "price")
	private BigDecimal price;

	private ProductImage productImage;
}
