package com.online.shop.serviceImpl;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online.shop.dto.ProductImageDto;
import com.online.shop.error_response.EShopException;
import com.online.shop.model.ProductImage;
import com.online.shop.model.Products;
import com.online.shop.repository.ProductImageRepo;
import com.online.shop.repository.ProductRepo;
import com.online.shop.service.ProductImageService;
import com.online.shop.utility.EShopUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

	private final ProductImageRepo productImageRepo;

	private final ProductRepo productRepo;

	private final EShopUtility utility;

	private final ModelMapper modelMap;

	@Override
	public String insertProductImage(String id, MultipartFile imageFile) throws IOException {
		Products existingproduct = productRepo.findById(id)
				.orElseThrow(() -> new EShopException(404, "Product doesn't exists!... - " + id));
		ProductImage image = new ProductImage().setImageId(utility.getImageId())
				.setImageName(imageFile.getOriginalFilename()).setImageFormat(imageFile.getContentType())
				.setImage(imageFile.getBytes());
		productImageRepo.save(image);
		existingproduct.setProductImage(image);
		Products imageInsertedProduct = productRepo.save(existingproduct);
		return imageInsertedProduct.getProductImage().getId();
	}

	@Override
	public ProductImageDto getProductImageById(String productImageid) {
		ProductImage existingProductImage = productImageRepo.findById(productImageid)
				.orElseThrow(() -> new EShopException(404, "Product image doesn't exists!... - " + productImageid));
		ProductImageDto productImageDto = utility.toConvert(existingProductImage, ProductImageDto.class);
//		ProductImageDto productImageDto=modelMap.map(existingProductImage, ProductImageDto.class);
		return productImageDto;
	}

	@Override
	public Boolean removeProductImageById(String id) {
		try {
			productImageRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			throw new EShopException(500,
					"Something went wrong while removing the product image!... - " + e.getLocalizedMessage());
		}
	}

}
