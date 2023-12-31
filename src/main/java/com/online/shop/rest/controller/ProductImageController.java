package com.online.shop.rest.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online.shop.dto.ProductImageDto;
import com.online.shop.enums.ImageFormate;
import com.online.shop.error_response.EShopError;
import com.online.shop.error_response.EShopException;
import com.online.shop.service.ProductImageService;
import com.online.shop.utility.EShopConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product/image")
@RequiredArgsConstructor

@Validated

public class ProductImageController {

	private final ProductImageService productImageService;

	@PostMapping
	public ResponseEntity<String> insertProductImage(@RequestParam String id, @RequestPart MultipartFile imageFile)
			throws IOException {
		if (!(imageFile.getContentType().equalsIgnoreCase(ImageFormate.PNG.getImgFormate())
				|| imageFile.getContentType().equalsIgnoreCase(ImageFormate.JPG.getImgFormate())
				|| imageFile.getContentType().equalsIgnoreCase(ImageFormate.JPEG.getImgFormate()))) {

			throw new EShopException(EShopError.IMAGE_FORMAT_INVALID.getErroCode(),
					EShopError.IMAGE_FORMAT_INVALID.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productImageService.insertProductImage(id, imageFile));
	}



	@GetMapping
	public ResponseEntity<byte[]> getProductImageById(@RequestParam String productImageid) {
		ProductImageDto resourceImage = productImageService.getProductImageById(productImageid);
       String contentType=EShopConstants.FILE_CONTENT_TYPE;
       String headerValue=EShopConstants.FILE_NAME_TYPE+ resourceImage.getImageName();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resourceImage.getImage());
	}

	@DeleteMapping
	public ResponseEntity<Boolean> removeProductImageById(@RequestParam String id) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productImageService.removeProductImageById(id));
	}
}
