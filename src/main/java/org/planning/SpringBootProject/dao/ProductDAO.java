package org.planning.SpringBootProject.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import jakarta.persistence.NoResultException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.planning.SpringBootProject.entity.Product;
import org.planning.SpringBootProject.form.ProductForm;
import org.planning.SpringBootProject.model.ProductInfo;
import org.planning.SpringBootProject.pagination.PaginationResult;
import org.planning.SpringBootProject.pagination.Paging;
import org.planning.SpringBootProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Transactional
@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findProduct(String code) {
        try {
            String sql = "Select e from " + Product.class.getName() + " e Where e.code =:code ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<Product> query = session.createQuery(sql, Product.class);
            query.setParameter("code", code);
            return (Product) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

//    public ProductInfo findProductInfo(String code) {
//        Product product = this.findProduct(code);
//        if (product == null) {
//            return null;
//        }
//        return new ProductInfo(product.getCode(), product.getName(), product.getPrice());
//    }

//    public List<Product> getAllProducts() {
//        try {
//            String sql = "Select e from " + Product.class.getName() + " e where e.isDelete = true";
//
//            Session session = this.sessionFactory.getCurrentSession();
//            Query<Product> query = session.createQuery(sql, Product.class);
//            return query.getResultList();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }


//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    public void deleteProduct(String code) {
//        try {
//            Session session = this.sessionFactory.getCurrentSession();
//
//            String hql = "FROM Product p WHERE p.code = :code";
//            Query query = session.createQuery(hql);
//            query.setParameter("code", code);
//            Product product = (Product) query.getSingleResult();
//
//            // Nếu sản phẩm tồn tại, cập nhật isDelete thành false
//            if (product != null) {
//                product.setDelete(false);
//            } else {
//
//                throw new NoResultException("Không tìm thấy sản phẩm với mã " + code);
//            }
//        } catch (NoResultException e) {
//            e.printStackTrace();
//
//        }
//    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {

        Session session = this.sessionFactory.getCurrentSession();
        String code = productForm.getCode();

        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setQuanityProduct(productForm.getQuantity());

        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            product.setDelete(true);
            session.persist(product);
        }
        // Nếu có lỗi tại DB, ngoại lệ sẽ ném ra ngay lập tức
        session.flush();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Paging<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName) {

        Pageable pageable = PageRequest.of(page - 1, maxResult);
        Page<Product> productPage = productRepository.findByLikeNameAndIsDeletedTrue(likeName, pageable);

        List<ProductInfo> productInfos = productPage.getContent().stream()
                .map(product -> new ProductInfo(product.getCode(), product.getName(), product.getPrice(), product.getQuanityProduct()))
                .collect(Collectors.toList());
        int totalRecords = productRepository.getTotalRecords(likeName);
        return new Paging<>(productInfos, totalRecords, page, maxResult);
    }

//    private int getTotalRecords(String likeName) throws SQLException {
//        String countSql = "SELECT COUNT(*) FROM products WHERE name LIKE ?";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(countSql)) {
//            statement.setString(1, "%" + likeName + "%");
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getInt(1);
//                }
//            }
//        }
//        return 0;
//    }
}


