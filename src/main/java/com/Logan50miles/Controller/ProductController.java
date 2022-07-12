package com.Logan50miles.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.Logan50miles.Entity.Swatches;
import com.Logan50miles.Repository.SwatchesRepository;
import com.Logan50miles.Service.ProductsService;
import com.Logan50miles.Util.ResourceNotFoundException;


@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/ophelia")
public class ProductController {
	
	@Autowired
	private ProductsService productsService;
	@Autowired
	private SwatchesRepository swatchesRepository;

	@GetMapping("mainproducts")
	public List<MainCategory> getMainProducts() {
		return productsService.getMaincategory();
	}

	@PostMapping("mainproducts")
	public MainCategory addMainCategory(MainCategory mainCategory, MultipartFile file, MultipartFile file1)
			throws IOException {
		return productsService.addMainCategory(mainCategory, file, file1);
	}

	@GetMapping("products")
	public List<Categories> getCategories() {
		return productsService.getProducts();
	}

	@GetMapping("preorder")
	public List<Products> getPreOrders(String curDate) throws Exception {
		return productsService.getPreOrder(curDate);
	}

	@PostMapping("products")
	public Products addProduct(Products products, MultipartFile file, MultipartFile file1) throws IOException {
		return productsService.addProducts(products, file, file1);
	}

	@PostMapping("category")
	public Categories addCategories(Categories categories, MultipartFile file, MultipartFile file1) throws IOException {
		return productsService.addCategories(categories, file, file1);
	}

	@PostMapping("offers")
	public String addOffers(int id, Products productDetails, MultipartFile file, MultipartFile file1)
			throws ResourceNotFoundException, IOException {
		return productsService.addOffers(id, productDetails, file, file1);
	}

	@PostMapping("delete/product")
	public String deleteProduct(int id) {
		productsService.deleteById(id);
		return "deleted";
	}

	@GetMapping("products/featured")
	public List<Products> getFeatured() {
		return productsService.showfeatured();
	}

	@GetMapping("products/product")
	public Products showSingleProduct(int pId) {
		return productsService.viewSingleProduct(pId);
	}

	@PostMapping("favorites")
	public String addFavourite(String phone, int pId) {
		return productsService.addFavourites(phone, pId);
	}

	@GetMapping("favorites")
	public List<Products> getFavorites(String phone) {
		return productsService.getFavourite(phone);
	}

	@PostMapping("delete/favorite")
	public String deletefav(int id) {
		productsService.deleteFavById(id);
		return "deleted";
	}

	@GetMapping("products/allproducts")
	public List<Products> getAllProducts(String mt, String st) {
		return productsService.getProductsByCategory(mt, st);
	}

	@GetMapping("products/home")
	public List<Products> getHomeProducts() {
		return productsService.allProductsHome();
	}

	@GetMapping("swatch")
	public List<Swatches> getSwatches() {
		return swatchesRepository.findAll();
	}

	@PostMapping("swatch")
	public Swatches addSwatches(Swatches swatches, int pid, String psize) {
		HashMap<Integer, String> psizes = new HashMap<>();
		psizes.put(pid, psize);
		swatches.setPsize(psizes);
		return swatchesRepository.save(swatches);
	}

	@GetMapping("product/serach")
	public List<Products> getSearch(String productname) {
		return productsService.getProductsBySearch(productname);
	}

	@PostMapping("products/size")
	public String addProductsize(ProductSize productSize) {
		return productsService.addProductSize(productSize);
	}

	@PostMapping("product/size")
	public String updateProductSize(int id, String psize, int qty, double price, String sku, double width,
			double height, double length) {
		return productsService.updateProductSize(id, psize, qty, price, sku, width, height, length);
	}

	@PostMapping("product/size/delete")
	public String deletePsize(int id) {
		productsService.deletePsize(id);
		return "deleted";

	}

	@PostMapping("product/relevant/add")
	public String relevantProducts(int mcId) {
		return productsService.addRelevantProducts(mcId);
	}

	@GetMapping("product/relevant/get")
	public List<RelevantProducts> getRelevantProducts(int mcId) {
		return productsService.getRelevantProducts(mcId);
	}

	@PostMapping("products/image")
	public String addProductImage(ProductImage productImage, MultipartFile file) throws IOException {
		return productsService.addProductImage(productImage, file);
	}

	@PostMapping("products/image/delete")
	public String pImageDelete(@RequestParam int keyName) {
		productsService.deletePImage(keyName);
		return "deleted";

	}

	@PostMapping("delete/maincategory")
	public String deleteMainCategory(int cid) {
		productsService.deleteMainCategory(cid);
		return "deleted";
	}

	@PostMapping("delete/subcategory")
	public String deleteSubCategory(int id) {
		productsService.deleteSubCategory(id);
		return "deleted";
	}

	@PostMapping("addbulk")
	public String addBulk(MultipartFile file) throws Exception {
		return productsService.addBulk(file);
	}

	@PostMapping("bulkdata")
	public void addBulkData(String file) throws IOException {
		productsService.addBulkData(file);
	}

	@PostMapping("filter")
	public Filters addFiletr(Filters filters) {
		return productsService.addFilters(filters);
	}

	@PostMapping("filtertype")
	public FilterType addFilterType(FilterType filterType) {
		return productsService.addFilterType(filterType);
	}

	@PostMapping("delete/filter")
	public String deleteFilter(int id) {
		return productsService.deleteFilter(id);
	}

	@PostMapping("delete/filtertype")
	public String deleteFilterType(int id) {
		return productsService.deleteFilterType(id);
	}

	@GetMapping("getFilters")
	public List<Filters> getAllFilters() {
		return productsService.getAllFilters();
	}

	@GetMapping("getFilters/category")
	public List<Filters> getAllFiltersByCatgory(String category) {
		return productsService.getByCategory(category);
	}

	@PostMapping("addFilteration")
	public Filteration addFilteration(Filteration filteration) {
		return productsService.addFilteration(filteration);
	}

	@PostMapping("delete/filteration")
	public String deleteFilteration(int id) {
		return productsService.deleteFilteration(id);
	}

	@PostMapping("add/specification")
	public ProductSpecification addProductSpecification(ProductSpecification productSpecification) {
		return productsService.addProductSpecification(productSpecification);
	}

	@PostMapping("update/specification")
	public String updateSpecification(ProductSpecification productSpecification) {
		return productsService.updateSpecification(productSpecification);
	}

	@PostMapping("delete/specification")
	public String deleteSpecification(int id) {
		return productsService.deleteSpecification(id);
	}

	@PostMapping("add/metahome")
	public MetaTagHome addMetaTagHome(MetaTagHome metaTagHome) {
		return productsService.addMetaTagHome(metaTagHome);
	}

	@PostMapping("update/metahome")
	public String updateMetaTagHome(MetaTagHome metaTagHome) {
		return productsService.updatehomemeta(metaTagHome);
	}

	@PostMapping("delete/metahome")
	public String deleteMetahome(int id) {
		return productsService.deleteMetaHome(id);
	}

	@GetMapping("get/metahome")
	public List<MetaTagHome> getAllMetaHome() {
		return productsService.getAllMetaHome();
	}

	@GetMapping("get/metahome/where")
	public List<MetaTagHome> getAllMetaHome(String where) {
		return productsService.getMetaHoomeBy(where);
	}

	@PostMapping("add/meta")
	public MetaTags addMetaTagHome(MetaTags metaTag) {
		return productsService.addMetaTags(metaTag);
	}

	@PostMapping("update/meta")
	public String updateMetaTag(MetaTags metaTag) {
		return productsService.updatemeta(metaTag);
	}

	@PostMapping("delete/meta")
	public String deleteMeta(int id) {
		return productsService.deleteMetaTag(id);
	}

	@PostMapping("add/statedelivery")
	public StateDelivery addStateDelivery(StateDelivery stateDelivery) {
		return productsService.addStateDelivery(stateDelivery);
	}

	@GetMapping("get/statedelivery")
	public List<StateDelivery> getStateDeliveries() {
		return productsService.getStatesDelivery();
	}

	@PostMapping("delete/statedelivery")
	public String deleteStateDelivery(int id) {
		return productsService.deleteStateDelivery(id);
	}

	@PostMapping("add/bulk/products")
	public String addBulkxl(MultipartFile file) throws IOException {
		return productsService.addBulkXl(file);
	}

	@PostMapping("add/bulk/products/size")
	public String addBulkxlsizes(MultipartFile file) throws IOException {
		return productsService.addBulkXlSize(file);
	}

	@GetMapping("get/product/keyvalue/pair")
	public Map<String, List<Products>> getProductsKeyvalue() {
		return productsService.getProductsAsKeyValuePair();
	}

	@GetMapping("get/search/product")
	public List<Products> searchProductAdmin(String type, String input) {
		return productsService.searchProductAdmin(type, input);
	}

	@PostMapping("add/photos")
	public String addPhotos(MultipartFile file) throws IOException {
		return productsService.addPhotos(file);
	}

	@PostMapping("update/category")
	public String updateMaincategory(MainCategory mainCategory, MultipartFile file, MultipartFile file1)
			throws IOException {
		return productsService.updateMaincategory(mainCategory, file, file1);
	}

	@PostMapping("update/subcategory")
	public String updateCategory(Categories categories, MultipartFile file, MultipartFile file1) throws IOException {
		return productsService.updateCategory(categories, file, file1);
	}

	@GetMapping("get/featured/product/category")
	public List<Products> showfeaturedByCategory(String categoryname) {
		return productsService.showfeaturedByCategory(categoryname);
	}

	@PostMapping("add/hsn")
	public HSNConfig addHsnConfig(HSNConfig hsnConfig) {
		return productsService.addHsnConfig(hsnConfig);
	}

	@GetMapping("get/hsn/all")
	public List<HSNConfig> getAllHSN() {
		return productsService.getAllHSN();
	}

	@GetMapping("get/hsn/id")
	public HSNConfig getHSN(int id) {
		return productsService.getHSN(id);
	}

	@GetMapping("get/hsn")
	public HSNConfig getHSN(String hsn) {
		return productsService.getHSN(hsn);
	}

	@PostMapping("delete/hsn")
	public String deleteHSN(int id) {
		return productsService.deleteHSN(id);
	}

	@GetMapping("get/products/collection")
	public List<Products> getProductsByCollection(String collection) {
		return productsService.getProductsByCollection(collection);
	}

	@GetMapping("get/products/group")
	public List<Products> getProductsByGroup(String group) {
		return productsService.getProductsByGroup(group);
	}

	@PostMapping("add/subcategory1")
	public Subcategory addSubcategory(Subcategory categories, MultipartFile file) throws IOException {
		return productsService.addSubcategory(categories, file);
	}

	@PostMapping("add/childcategory")
	public ChildCategory addChildcategory(ChildCategory categories, MultipartFile file) throws IOException {
		return productsService.addChildcategory(categories, file);
	}

	@PostMapping("delete/subcategory1")
	public String deleteSubcategory(int id) {
		return productsService.deleteSubcategory(id);
	}

	@PostMapping("delete/childcategory")
	public String deleteChildcategory(int id) {
		return productsService.deleteChildcategory(id);
	}

	@GetMapping("shopbycategory")
	public List<Products> getProductsByCategory(String mt, String st, String ct, String cc) {
		return productsService.getProductsByCategory(mt, st, ct, cc);
	}

	@GetMapping("shopbyproducts")
	public List<Products> shopByproducts(String product) {
		return productsService.shopByproducts(product);
	}

	@GetMapping("shopby/products")
	public List<Products> getShopByProduct(String shop) {
		return productsService.getShopByProduct(shop);
	}

	@PostMapping("add/shopbyproducts")
	ShopByProducts addShopByProducts(ShopByProducts shopByProducts, MultipartFile file) throws IOException {
		return productsService.addShopByProducts(shopByProducts, file);
	}

	@GetMapping("get/shopbyproducts")
	public List<ShopByProducts> getShopByProducts() {
		return productsService.getShopByProducts();
	}

	@PostMapping("delete/shopbyproducts")
	public String deleteShopByProducts(int id) {
		return productsService.deleteShopByProducts(id);
	}
//'12737','https://nazca-images.s3.ap-south-1.amazonaws.com/products/omjb002-1.jpg','6907'
//'13187','https://nazca-images.s3.ap-south-1.amazonaws.com/products/omjb002-1.jpg','6907'
//'13313','https://nazca-images.s3.ap-south-1.amazonaws.com/products/omjb002-1.jpg','6907'
//'6908','https://nazca-images.s3.ap-south-1.amazonaws.com/products/omjb002-2.jpg','6907'

}
