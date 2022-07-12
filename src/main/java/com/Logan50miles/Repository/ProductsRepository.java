package com.Logan50miles.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Logan50miles.Entity.Products;


public interface ProductsRepository extends JpaRepository<Products, Integer>{
    @Query("from Products where mcId=:productId")
	Products getProductDetails(int productId);
    @Query("from Products where mcId=:id")
    Products findByProductId(int id);
    @Query("from Products where subtown LIKE %:productname% ")
   	List<Products> findBySearch(String productname);
    @Query("from Products where pcolor LIKE %:color% ")
	List<Products> findByColor(String color);
    @Query("from Products where maincategory=:maincategory AND subcategory=:subcategory")
	List<Products> findByCatgeory(String maincategory, String subcategory);
    @Query("from Products where maincategory=:maincategory")
	List<Products> findByCatgeory(String maincategory);
	@Query("from Products where productcode=:code")
    Products findByProductcode(String code);
	List<Products> findByDescpription1IgnoreCaseContaining(String collection);
	List<Products> findByGroup(String group);
	List<Products> findByMaincategoryAndSubcategoryAndSubcategory1AndChildcategory(String mt, String st, String ct,
			String cc);
	List<Products> findByMaincategoryAndSubcategoryAndSubcategory1(String mt, String st, String ct);
	List<Products> findByMaincategoryAndSubcategory(String mt, String st);
	List<Products> findByMaincategory(String mt);
	List<Products> findBySubcategory(String product);
	List<Products> findByShopbyproducts(String shop);
	

}
