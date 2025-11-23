package service.custom.impl;

import dto.Product;
import entity.ProductEntity;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.ProductDao;
import service.custom.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Inject
    ProductDao productDao;

    @Override
    public boolean add(Product product) throws SQLException {
        ModelMapper modelMapper = new ModelMapper();
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);

        return productDao.add(productEntity);
    }

    @Override
    public List<Product> getAll() throws SQLException {
        List<ProductEntity> productEntityList = productDao.getAll();
        List<Product> productList = new ArrayList<>();
        for (ProductEntity productEntity:productEntityList){
            Product product = new Product();
            product.setCode(productEntity.getCode());
            product.setDescription(productEntity.getDescription());
            product.setCategory(productEntity.getCategory());
            product.setSize(productEntity.getSize());
            product.setUnitPrice(productEntity.getUnitPrice());
            product.setQuantityInStock(productEntity.getQuantityInStock());
            product.setSupplierId(productEntity.getSupplierId());
            product.setProductDetails(new HashSet<>());
            product.setOrderDetailsList(new ArrayList<>());

            productList.add(product);
        }

        return productList;
    }

    @Override
    public boolean update(Product product) throws RuntimeException{
        ProductEntity productEntity = new ModelMapper().map(product, ProductEntity.class);
        return productDao.update(productEntity);
    }

    @Override
    public boolean delete(Integer code){
        return productDao.delete(code);
    }

    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        return productDao.getIds();
    }

    @Override
    public Product search(Integer id){
        Product product = null;
        ProductEntity productEntity = productDao.search(id);
        if(productEntity!=null){
            product = new Product();
            product.setCode(productEntity.getCode());
            product.setDescription(productEntity.getDescription());
            product.setCategory(productEntity.getCategory());
            product.setSize(productEntity.getSize());
            product.setUnitPrice(productEntity.getUnitPrice());
            product.setQuantityInStock(productEntity.getQuantityInStock());
            product.setSupplierId(productEntity.getSupplierId());
            product.setProductDetails(new HashSet<>());
            product.setOrderDetailsList(new ArrayList<>());
        }
        return product;
    }
}
