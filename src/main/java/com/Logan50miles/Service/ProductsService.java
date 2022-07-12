package com.Logan50miles.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Categories;
import com.Logan50miles.Entity.ChildCategory;
import com.Logan50miles.Entity.FilterType;
import com.Logan50miles.Entity.Filteration;
import com.Logan50miles.Entity.Filters;
import com.Logan50miles.Entity.HSNConfig;
import com.Logan50miles.Entity.MainCategory;
import com.Logan50miles.Entity.MetaTagHome;
import com.Logan50miles.Entity.MetaTags;
import com.Logan50miles.Entity.ProductImage;
import com.Logan50miles.Entity.ProductSize;
import com.Logan50miles.Entity.ProductSpecification;
import com.Logan50miles.Entity.Products;
import com.Logan50miles.Entity.RelevantProducts;
import com.Logan50miles.Entity.ShopByProducts;
import com.Logan50miles.Entity.StateDelivery;
import com.Logan50miles.Entity.Subcategory;
import com.Logan50miles.Util.ResourceNotFoundException;


public interface ProductsService {

	List<MainCategory> getMaincategory();

	List<Categories> getProducts();

	String deleteById(int id);

	List<Products> getPreOrder(String curDates) throws Exception;

	Products viewSingleProduct(int pId);

	List<Products> showfeatured();

	String deleteFavById(Integer id);

	String addFavourites(String phone, int pId);

	List<Products> allProductsHome();

	List<Products> getProductsBySearch(String pname);

	String addProductSize(ProductSize productSize);

	String deletePsize(int id);

	String addRelevantProducts(int mcId);

	List<RelevantProducts> getRelevantProducts(int mcId);

    String addProductImage(ProductImage productImage, MultipartFile file) throws IOException;

	String deletePImage(int keyName);

	String deleteMainCategory(int id);

	String deleteSubCategory(int cid);

	String addBulk(MultipartFile file1) throws Exception;

	void addBulkData(String file) throws IOException;

	Filters addFilters(Filters filters);

	List<Filters> getByCategory(String filtername);

	List<Filters> getAllFilters();

	String deleteFilter(int id);

	String deleteFilterType(int id);

	FilterType addFilterType(FilterType filters);

	Filteration addFilteration(Filteration filteration);

	String deleteFilteration(int id);

	Products addProducts(Products products, MultipartFile file, MultipartFile file1) throws IOException;

	String addOffers(int id, Products productDetails, MultipartFile file, MultipartFile file1)
			throws ResourceNotFoundException, IOException;

	List<Products> getProductsByCategory(String mt, String st);

	List<Products> getFavourite(String phone);

	ProductSpecification addProductSpecification(ProductSpecification productSpecification);

	String deleteSpecification(int id);

	String updateSpecification(ProductSpecification productSpecification);

	String deleteMetaHome(int id);

	List<MetaTagHome> getMetaHoomeBy(String where);

	List<MetaTagHome> getAllMetaHome();

	MetaTagHome addMetaTagHome(MetaTagHome metaTagHome);
	
	String updatehomemeta(MetaTagHome metaTagHome);

	MetaTags addMetaTags(MetaTags metaTags);

	String deleteMetaTag(int id);

	String updatemeta(MetaTags metaTags);

	StateDelivery addStateDelivery(StateDelivery stateDelivery);

	List<StateDelivery> getStatesDelivery();

	String deleteStateDelivery(int id);

	Map<String, List<Products>> getProductsAsKeyValuePair();

	String addBulkXl(MultipartFile file) throws IOException;


	String addBulkXlSize(MultipartFile file) throws IOException;

	List<Products> searchProductAdmin(String type, String input);

	String addPhotos(MultipartFile file) throws IOException;

	String updateCategory(Categories categories, MultipartFile file,MultipartFile file1) throws IOException;

	List<Products> showfeaturedByCategory(String categoryname);

	HSNConfig addHsnConfig(HSNConfig hsnConfig);

	List<HSNConfig> getAllHSN();

	HSNConfig getHSN(int id);

	HSNConfig getHSN(String hsn);

	String deleteHSN(int id);

	List<Products> getProductsByCollection(String collection);

	String updateProductSize(int id, String psize, int qty, double price, String sku, double width, double height,
			double length);

	List<Products> getProductsByGroup(String group);

	Subcategory addSubcategory(Subcategory categories, MultipartFile file) throws IOException;

	ChildCategory addChildcategory(ChildCategory categories, MultipartFile file) throws IOException;

	String deleteSubcategory(int id);

	String deleteChildcategory(int id);

	List<Products> getProductsByCategory(String mt, String st, String ct, String cc);

	List<Products> shopByproducts(String product);

	List<Products> getShopByProduct(String shop);


	List<ShopByProducts> getShopByProducts();

	String deleteShopByProducts(int id);

	ShopByProducts addShopByProducts(ShopByProducts shopByProducts, MultipartFile file) throws IOException;

	MainCategory addMainCategory(MainCategory mainCategory, MultipartFile file, MultipartFile file1) throws IOException;

	Categories addCategories(Categories categories, MultipartFile file, MultipartFile file1) throws IOException;

	String updateMaincategory(MainCategory mainCategory, MultipartFile file, MultipartFile file1) throws IOException;


}
