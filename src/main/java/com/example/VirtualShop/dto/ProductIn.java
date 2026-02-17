package com.example.VirtualShop.dto;

import com.example.VirtualShop.model.Product;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;

public class ProductIn implements Serializable {

    @NotBlank(message = "Product name required")
    @Length(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Description required")
    @Length(max = 1000)
    private String description;

    @NotNull(message = "Price required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @Min(value = 0)
    private Integer stockQuantity;

    @NotBlank(message = "Category required")
    private String category;

    @Length(max = 500)
    private String imageUrl;


    public ProductIn() {}


    public Product toProduct() {
        return new Product(
                this.name,
                this.description,
                this.price,
                this.stockQuantity,
                this.category,
                this.imageUrl
        );
    }

    public void updateProduct(Product product) {
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setStockQuantity(this.stockQuantity);
        product.setCategory(this.category);
        product.setImageUrl(this.imageUrl);
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
