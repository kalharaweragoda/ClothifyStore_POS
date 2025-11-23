package service.custom.impl;

import dto.Supplier;
import entity.SupplierEntity;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.SupplierDao;
import service.custom.SupplierService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    @Inject
    SupplierDao supplierDao;

    @Override
    public boolean add(Supplier supplier) throws SQLException {
        SupplierEntity supplierEntity = new ModelMapper().map(supplier, SupplierEntity.class);
        supplierEntity.setId(null);
        return supplierDao.add(supplierEntity);
    }

    @Override
    public boolean search(String email) throws SQLException {
        SupplierEntity supplierEntity = supplierDao.search(email);
        return supplierEntity != null;
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        List<SupplierEntity> supplierEntityList = supplierDao.getAll();
        List<Supplier> supplierList = new ArrayList<>();
        for (SupplierEntity supplierEntity:supplierEntityList){
            Supplier supplier = new Supplier();
            supplier.setId(supplierEntity.getId());
            supplier.setName(supplierEntity.getName());
            supplier.setEmail(supplierEntity.getEmail());
            supplier.setCompany(supplierEntity.getCompany());
            supplier.setPhoneNo(supplierEntity.getPhoneNo());
            supplier.setProductDetails(new HashSet<>());
            supplierList.add(supplier);
        }

        return supplierList;
    }

    @Override
    public boolean update(Supplier supplier) throws Exception {
        SupplierEntity supplierEntity = new ModelMapper().map(supplier, SupplierEntity.class);
        return supplierDao.update(supplierEntity);
    }

    @Override
    public boolean delete(String email) throws SQLException {
        return supplierDao.delete(email);
    }

    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        return supplierDao.getIds();
    }

    @Override
    public Supplier search(Integer id) throws SQLException {
        SupplierEntity supplierEntity = supplierDao.search(id);
        Supplier supplier = new Supplier();
        supplier.setId(supplierEntity.getId());
        supplier.setName(supplierEntity.getName());
        supplier.setEmail(supplierEntity.getEmail());
        supplier.setCompany(supplierEntity.getCompany());
        supplier.setPhoneNo(supplierEntity.getPhoneNo());
        supplier.setProductDetails(new HashSet<>());
        return supplier;
    }
}
