package config;

import com.google.inject.AbstractModule;
import repository.custom.*;
import repository.custom.impl.*;
import service.custom.*;
import service.custom.impl.*;

public class AppModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(EmployeeService.class).to(EmployeeServiceImpl.class);
        bind(EmployeeDao.class).to(EmployeeDaoImpl.class);

        bind(CustomerService.class).to(CustomerServiceImpl.class);
        bind(CustomerDao.class).to(CustomerDaoImpl.class);

        bind(SupplierService.class).to(SupplierServiceImpl.class);
        bind(SupplierDao.class).to(SupplierDaoImpl.class);

        bind(ProductService.class).to(ProductServiceImpl.class);
        bind(ProductDao.class).to(ProductDaoImpl.class);

        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(OrderDao.class).to(OrderDaoImpl.class);

        bind(OrderReturnService.class).to(OrderReturnServiceImpl.class);
        bind(OrderReturnDao.class).to(OrderReturnDaoImpl.class);
    }
}
