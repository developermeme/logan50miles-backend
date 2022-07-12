package com.Logan50miles.Implementation;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Categories;
import com.Logan50miles.Entity.ChildCategory;
import com.Logan50miles.Entity.Favourites;
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
import com.Logan50miles.Repository.CategoriesRepository;
import com.Logan50miles.Repository.ChildCategoryRepository;
import com.Logan50miles.Repository.FavouriteRepository;
import com.Logan50miles.Repository.FilterRepository;
import com.Logan50miles.Repository.FilterTypeRepository;
import com.Logan50miles.Repository.FilterationRepository;
import com.Logan50miles.Repository.HSNRepository;
import com.Logan50miles.Repository.MainCategoriesRepository;
import com.Logan50miles.Repository.MetaHomeRepository;
import com.Logan50miles.Repository.MetaTagRepository;
import com.Logan50miles.Repository.MetalRepository;
import com.Logan50miles.Repository.ProductImageRepository;
import com.Logan50miles.Repository.ProductSizeRepository;
import com.Logan50miles.Repository.ProductSpecificationRepository;
import com.Logan50miles.Repository.ProductsRepository;
import com.Logan50miles.Repository.RelevantProductsRepository;
import com.Logan50miles.Repository.ShopByProductsRepository;
import com.Logan50miles.Repository.StateDeliveryRepository;
import com.Logan50miles.Repository.StoneRepository;
import com.Logan50miles.Repository.SubcategoryRepository;
import com.Logan50miles.Service.ProductsService;
import com.Logan50miles.Util.ResourceNotFoundException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ProductsImpl implements ProductsService {

	@Autowired
	private CategoriesRepository categoriesRepository;
	@Autowired
	private ProductsRepository productsrepository;
	@Autowired
	private MainCategoriesRepository mainCategoriesRepository;
	@Autowired
	private FavouriteRepository favouriteRepository;
	
	@Autowired
	MetalRepository metalRepository;
	@Autowired
	StoneRepository stoneRepository;
	@Autowired
	ProductSizeRepository productSizeRepository;
	@Autowired
	ProductImageRepository productImageRepository;
	@Autowired
	RelevantProductsRepository relevantProductsRepository;
	@Autowired
	FilterRepository filterRepository;
	@Autowired
	FilterTypeRepository filterTypeRepository;
	@Autowired
	FilterationRepository filterationRepository;
	@Autowired
	ProductSpecificationRepository productSpecificationRepository;
	@Autowired
	MetaTagRepository metaTagRepository;
	@Autowired
	MetaHomeRepository metaHomeRepository;
	@Autowired
	StateDeliveryRepository stateDeliveryRepository;
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	HSNRepository hSNRepository;
	@Autowired
	SubcategoryRepository subcategoryRepository;
	@Autowired
	ChildCategoryRepository childCategoryRepository;
	@Autowired
	ShopByProductsRepository shopByProductsRepository;
	//AWS S3 BUCKET ACCESS
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${app.awsServices.bucketName}")
	private String bucketName;

	private AmazonS3 s3Client;

	private String db2 = "https://mylogantown.s3.amazonaws.com/event/";
    private String db ="https://mylogantown.s3.amazonaws.com/event/";
	Logger logger = Logger.getLogger(ProductsImpl.class.getName());

	@Override
	public List<MainCategory> getMaincategory() {
		return mainCategoriesRepository.findAll();
	}

	@Override
	public List<Categories> getProducts() {
		return categoriesRepository.findAll();
	}

	@Override
	public Categories addCategories(Categories categories,MultipartFile file,MultipartFile file1) throws IOException {
		if(file!=null) {
			this.uploadImage(file,"event");
			categories.setCurl(db + file.getOriginalFilename());
		}
		if(file1!=null) {
			this.uploadImage(file1,"event");
			categories.setMobileurl(db + file1.getOriginalFilename());
		}
		return categoriesRepository.save(categories);
	}
	@Override
	public Subcategory addSubcategory(Subcategory categories,MultipartFile file) throws IOException {
		if(file!=null) {
			this.uploadImage(file,"event");
			categories.setCurl(db + file.getOriginalFilename());
		}
		return subcategoryRepository.save(categories);
	}
	@Override
	public ChildCategory addChildcategory(ChildCategory categories,MultipartFile file) throws IOException {
		if(file!=null) {
			this.uploadImage(file,"event");
			categories.setCurl(db + file.getOriginalFilename());
		}
		return childCategoryRepository.save(categories);
	}
	@Override
	public String deleteSubcategory(int id) {
		subcategoryRepository.deleteById(id);
		return "deleted";
	}
	@Override
	public String deleteChildcategory(int id) {
		childCategoryRepository.deleteById(id);
		return "deleted";
	}
	
	@Override
	public MainCategory addMainCategory(MainCategory mainCategory,MultipartFile file,MultipartFile file1) throws IOException {
		if(file!=null) {
			this.uploadImage(file,"event");
			mainCategory.setImageUrl(db + file.getOriginalFilename());
		}else {
			mainCategory.setImageUrl(db + "profile.jpg");
		}
		if(file1!=null) {
			this.uploadImage(file1,"event");
			mainCategory.setMobileurl(db + file1.getOriginalFilename());
		}else {
			mainCategory.setMobileurl(db + "profile.jpg");
		}
		return mainCategoriesRepository.save(mainCategory);
	}
	@Override
	public String updateMaincategory(MainCategory mainCategory,MultipartFile file,MultipartFile file1) throws IOException {
		MainCategory ex =mainCategoriesRepository.findById(mainCategory.getMaincatId()).orElseThrow(null);
		if(ex!=null) {
			if(file!=null) {
				this.uploadImage(file,"event");
				ex.setImageUrl(db + file.getOriginalFilename());
			}
			if(file1!=null) {
				this.uploadImage(file1,"event");
				mainCategory.setMobileurl(db + file1.getOriginalFilename());
			}
			ex.setMainCatName(mainCategory.getMainCatName());
			mainCategoriesRepository.save(ex);
		}
		
		return "updated";
	}
	@Override
	public String updateCategory(Categories categories,MultipartFile file,MultipartFile file1) throws IOException {
		Categories ex =categoriesRepository.findById(categories.getCatId()).orElseThrow(null);
		if(ex!=null) {
			if(file!=null) {
				this.uploadImage(file,"event");
				ex.setCurl(db + file.getOriginalFilename());
			}if(file1!=null) {
				this.uploadImage(file1,"event");
				ex.setMobileurl(db + file1.getOriginalFilename());
			}
			ex.setcName(categories.getcName());
			categoriesRepository.save(ex);
		}
		
		return "updated";
	}
    @Override
    public String deleteMainCategory(int id) {
    	mainCategoriesRepository.deleteById(id);
    	return "deleted";
    }
    @Override
    public String deleteSubCategory(int cid) {
    	categoriesRepository.deleteById(cid);
    	return "deleted";
    }
	@Override
	public Products addProducts(Products products, MultipartFile file,MultipartFile file1) throws IOException {
		if (file != null) {
			this.uploadImage(file,"event");
			products.setImageurl(db2 + file.getOriginalFilename());
		} else {
			products.setImageurl(db2 + "default.jpg");
		}
		if (file1 != null) {
			this.uploadImage(file1,"event");
			products.setSizechart(db2 + file1.getOriginalFilename());
		} else {
			products.setSizechart(db2 + "default.jpg");
		}
		return productsrepository.save(products);
	}

	@Override
	public String deleteById(int id) {
	
		productsrepository.deleteById(id);
		return "deleted";
	}

	@Override
	public String addOffers(int id, Products productDetails,MultipartFile file,MultipartFile file1)
			throws ResourceNotFoundException,IOException {
		
		Products product = productsrepository.findByProductId(id);
		product.setMcId(id);
		if(file!=null) {
			this.uploadImage(file,"event");
			product.setImageurl(db2 + file.getOriginalFilename());
		}else {
			product.setImageurl(product.getImageurl());
		}
		if(file1!=null) {
			this.uploadImage(file1,"event");
			product.setSizechart(db2 + file1.getOriginalFilename());
		}else {
			product.setSizechart(product.getImageurl());
		}
		if (productDetails.getProductname() != null) {
			product.setProductname(productDetails.getProductname());
		} else {
			product.setProductname(product.getProductname());
		}
		if (productDetails.getDescpription() != null) {
			product.setDescpription(productDetails.getDescpription());
		} else {
			product.setDescpription(product.getDescpription());
		}if (productDetails.getDescpription1() != null) {
			product.setDescpription1(productDetails.getDescpription1());
		} else {
			product.setDescpription1(product.getDescpription1());
		}
		if (productDetails.getOffer() >= 0) {
			product.setOffer(productDetails.getOffer());
		} else {
			product.setOffer(product.getOffer());

		}
		if (productDetails.getPrice() != 0) {
			product.setPrice(productDetails.getPrice());
		} else {
			product.setPrice(product.getPrice());
		}
		if (productDetails.getQuantity() != null) {
			product.setQuantity(productDetails.getQuantity());
		} else {
			product.setQuantity(product.getQuantity());
		}

		if (productDetails.getMaincategory() != null) {
			product.setMaincategory(productDetails.getMaincategory());
		} else {
			product.setMaincategory(product.getMaincategory());
		}
		if (productDetails.getSubcategory() != null) {
			product.setSubcategory(productDetails.getSubcategory());
		} else {
			product.setSubcategory(product.getSubcategory());
		}
       
        if(productDetails.getDeliveryTime()!=null) {
        	product.setDeliveryTime(productDetails.getDeliveryTime());
        }else{
        	product.setDeliveryTime(product.getDeliveryTime());
        }
        if(productDetails.getProductcolor()!=null) {
        	product.setProductcolor(productDetails.getProductcolor());
        }else{
        	product.setProductcolor(product.getProductcolor());
        }
        if(productDetails.getProductcode()!=null) {
        	product.setProductcode(productDetails.getProductcode());
        }else {
        	product.setProductcode(product.getProductcode());
        }
       
        if(productDetails.getDate()!=null) {
        	product.setDate(productDetails.getDate());
        }else {
        	product.setDate(product.getDate());
        }
        if(productDetails.getTax()!=0) {
        	product.setTax(productDetails.getTax());
        }else {
        	product.setTax(product.getTax());
        }
   
        if(productDetails.getFabric()!=null) {
        	product.setFabric(productDetails.getFabric());
        }else{
        	product.setFabric(product.getFabric());
        }
        if(productDetails.getGsthsn()!=null) {
        	product.setGsthsn(productDetails.getGsthsn());
        }else {
        	product.setGsthsn(product.getGsthsn());
        }
        if(productDetails.getGroup()!=null) {
        	product.setGroup(productDetails.getGroup());
        }
        if(productDetails.getShopbyproducts()!=null) {
        	product.setShopbyproducts(productDetails.getShopbyproducts());
        }
        if(productDetails.getPntw()!=null) {
        	product.setPntw(productDetails.getPntw());
        }
		productsrepository.save(product);
		return "offer updated";
	}

	@Override
	public List<Products> getPreOrder(String curDates) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = dateFormat.parse(curDates);
		List<Products> allproducts = productsrepository.findAll();
		List<Products> productList=new ArrayList<Products>();
		for (int i = 0; i < allproducts.size(); i++) {
			 
			if (0 > curDate.compareTo(dateFormat.parse(allproducts.get(i).getDate()))) {
			    
				Products p=productsrepository.findByProductId(allproducts.get(i).getMcId());
				 productList.add(p);
				
			}
			
		}

		return productList;
	}

	
	@Override
	public Products viewSingleProduct(int pId) {
		return productsrepository.findByProductId(pId);
	}

	@Override
	public List<Products> showfeatured() {

		List<Products> catalogue = productsrepository.findAll();
		List<Integer> list = new ArrayList<Integer>();
		List<Products>  product=new ArrayList<Products>();
		for (Products p : catalogue) {
			list.add(p.getMcId());
		}

		int numberOfElements = 6;
		List<Integer> pro = getRandomElement(list, numberOfElements);

		for (int i = 0; i < pro.size(); i++) {

			Products prod = productsrepository.findByProductId(pro.get(i));
			product.add(prod);
		}
		return product;
	}
	@Override
	public List<Products> showfeaturedByCategory(String categoryname) {

		List<Products> catalogue = productsrepository.findByCatgeory(categoryname);
		    
        	Collections.shuffle(catalogue);
        
		return catalogue;
	}
	public static int getRandom(int[] array) {
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
	
	public List<Integer> getRandomElement(List<Integer> list, int totalItems) {
		Random rand = new Random();

		List<Integer> newList = new ArrayList<>();
		for (int i = 0; i < totalItems; i++) {
			int randomIndex = rand.nextInt(list.size());
			newList.add(list.get(randomIndex));
		}
		return newList;
	}

	@Override
	public String addFavourites(String phone, int pId) {
		Favourites fav = new Favourites();
		if (wishCheck(phone, pId) == "added") {
			fav.setProductId(pId);
			fav.setUserid(phone);
			favouriteRepository.save(fav);
		}
		return "wishlists";
	}

	public String wishCheck(String phone, int pId) {
		List<Favourites> fav = favouriteRepository.getByUserid(phone);
		String status = null;
		ArrayList<Integer> pid = new ArrayList<Integer>();
		for (int i = 0; i < fav.size(); i++) {
			int id = fav.get(i).getProductId();
			pid.add(id);
		}
		if (pid.contains(pId)) {
			deleteExist(phone, pId);
			status = "removed";
		} else {
			status = "added";
		}
		return status;
	}

	public String deleteExist(String phone, int pid) {
		Favourites del = favouriteRepository.getFav(pid, phone);
		if (del != null) {
			favouriteRepository.delete(del);
		}
		return "deleted";
	}
	@Override
    public List<Products> getFavourite(String phone){
    	List<Favourites> fav = favouriteRepository.getByUserid(phone);
    	List<Products> products=new ArrayList<Products>();
    	for (int i = 0; i < fav.size(); i++) {
    	Products products2 = productsrepository.findByProductId(fav.get(i).getProductId());
    	products.add(products2);
    	}
    	return products;
    }


	@Override
	public String deleteFavById(Integer id) {
		Favourites fav = favouriteRepository.getOne(id);
		favouriteRepository.delete(fav);
		return "deleted";
	}
	@Override
    public List<Products> getProductsByCategory(String mt,String st){
    	if(mt!=null&&st!=null) {
    		return productsrepository.findByCatgeory(mt,st);
    	}else {
    		return productsrepository.findByCatgeory(mt);
    	}
    }
	@Override
	 public List<Products> getProductsByCategory(String mt,String st,String ct,String cc ){
	    	if(mt!=null&&st!=null&&ct!=null&&cc!=null) {
	    		return productsrepository.findByMaincategoryAndSubcategoryAndSubcategory1AndChildcategory(mt,st,ct,cc);
	    	}else if(mt!=null&&st!=null&&ct!=null) {
	    		return productsrepository.findByMaincategoryAndSubcategoryAndSubcategory1(mt,st,ct);
	    	}else if(mt!=null&&st!=null) {
	    		return productsrepository.findByMaincategoryAndSubcategory(mt,st);
	    	}else {
	    		return productsrepository.findByMaincategory(mt);
	    	}
	    }
	@Override
	public List<Products> shopByproducts(String product){
		return productsrepository.findBySubcategory(product);
	}
	@Override
    public List<Products> getProductsByCollection(String collection){
    	
    		return productsrepository.findByDescpription1IgnoreCaseContaining(collection);
    
    }

	@Override
	public List<Products> allProductsHome() {
		return productsrepository.findAll();
	}
	@Override
    public List<Products> getShopByProduct(String shop){
    	return productsrepository.findByShopbyproducts(shop);
    }
	@Override
	public List<Products> getProductsBySearch(String pname) {
		return productsrepository.findAll().stream().filter(x->x.getProductname().toLowerCase().contains(pname.toLowerCase())||x.getMaincategory().toLowerCase().contains(pname.toLowerCase())||x.getSubcategory().toLowerCase().contains(pname.toLowerCase())).collect(Collectors.toList());

	}

	@Override
	public String addProductSize(ProductSize productSize) {
		productSizeRepository.save(productSize);
		return "added";
	}

	@Override
	public String updateProductSize(int id, String psize, int qty,double price,String sku,double width,double height,double length) {
		ProductSize productSize = productSizeRepository.findBySid(id);
		productSize.setSid(id);
		productSize.setMcId(productSize.getMcId());
		productSize.setPsize(psize);
		productSize.setQty(qty);
		productSize.setPrice(price);
		productSize.setSku(sku);
		productSize.setHeight(height);
		productSize.setWidth(width);
		productSize.setLength(length);
		productSizeRepository.save(productSize);
		return "updated";
	}

	@Override
	public String deletePsize(int id) {
		ProductSize productSize = productSizeRepository.findBySid(id);
		productSizeRepository.delete(productSize);
		return "deleted";
	}

	@Override
	public String addRelevantProducts(int mcId) {
		Products pro = productsrepository.findByProductId(mcId);
		if (pro != null) {
			RelevantProducts rp = new RelevantProducts();
			rp.setMcId(pro);
			relevantProductsRepository.save(rp);
		} else {
			List<Products> prod = productsrepository.findAll();
			for (Products pr : prod) {
				RelevantProducts rel = new RelevantProducts();
				rel.setMcId(pr);
				relevantProductsRepository.save(rel);
			}
		}
		return "relevant products created";
	}

	@Override
	public List<RelevantProducts> getRelevantProducts(int mcId) {
		return relevantProductsRepository.findByMcId(mcId);
	}

	@Override
	public String addProductImage(ProductImage productImage, MultipartFile file) throws IOException {
		if (file != null) {
			this.uploadImage(file,"event");
			productImage.setImageUrl(db2 + file.getOriginalFilename());
		} else {
			productImage.setImageUrl(db2 + "default.jpg");
		}
		productImageRepository.save(productImage);
		return "added";
	}

	@Override
	public String deletePImage(int imId) {
		productImageRepository.deleteById(imId);
		return "deleted";

	}
	
	@Override
	public String addBulk( MultipartFile file1) throws Exception {

		// JSONParser jsonParser = new JSONParser();
	       
		try {

			this.uploadImage(file1,"products");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "added";
	}
	@Override
	public void addBulkData(String file) throws IOException {
		S3Object object = s3Client.getObject(new GetObjectRequest(bucketName+"/"+"event",file));
		InputStream objectData = object.getObjectContent();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Products>> typeReference = new TypeReference<List<Products>>() {
		};
		try {
			List<Products> users = mapper.readValue(objectData, typeReference);
			productsrepository.saveAll(users);
			System.out.println("Product Saved!");
		} catch (IOException e) {
			System.out.println("Unable to save Product: " + e.getMessage());
		}
		objectData.close();
	}
	@Override
	public Filters addFilters(Filters filters) {
		return filterRepository.save(filters);
	}
	@Override
	public List<Filters> getAllFilters(){
		return filterRepository.findAll();
		
	}
	@Override
	public List<Filters> getByCategory(String filtername){
		return filterRepository.findByFilterName(filtername);
	}
	@Override
	public String deleteFilter(int id) {
		filterRepository.deleteById(id);
		return "deleted";
	}
	@Override
	public FilterType addFilterType(FilterType filters) {
		return filterTypeRepository.save(filters);
	}
	@Override
	public String deleteFilterType(int id) {
		filterTypeRepository.deleteById(id);
		return "deleted";
	}
	@Override
	public Filteration addFilteration(Filteration filteration) {
		return filterationRepository.save(filteration);
	}
	@Override
	public String deleteFilteration(int id) {
		filterationRepository.deleteById(id);
		return "deleted";
	}
	@Override
	public ProductSpecification addProductSpecification(ProductSpecification productSpecification) {
		return productSpecificationRepository.save(productSpecification);
	}
	@Override
	public String deleteSpecification(int id) {
		productSpecificationRepository.deleteById(id);
		return "deleted";
	}
	@Override
	public ShopByProducts addShopByProducts(ShopByProducts shopByProducts,MultipartFile file) throws IOException {
		if (file != null) {
			this.uploadImage(file,"event");
			shopByProducts.setUrl(db2 + file.getOriginalFilename());
		} else {
			shopByProducts.setUrl(db2 + "default.jpg");
		}
		return shopByProductsRepository.save(shopByProducts);
	}
	@Override
	public List<ShopByProducts> getShopByProducts(){
		return shopByProductsRepository.findAll();
	}
	@Override
	public String deleteShopByProducts(int id) {
		 shopByProductsRepository.deleteById(id);
		 return "deleted";
	}
	
	@Override
	public String updateSpecification(ProductSpecification productSpecification) {
		ProductSpecification existence=productSpecificationRepository.findBySpId(productSpecification.getSpid());
		existence.setSpecification(productSpecification.getSpecification());
		existence.setMcId(existence.getMcId());
		existence.setSpid(existence.getSpid());
		productSpecificationRepository.save(existence);
		return "updated";
	}
	@Override
	public MetaTagHome addMetaTagHome(MetaTagHome metaTagHome) {
		return metaHomeRepository.save(metaTagHome);
	}
	@Override
	public List<MetaTagHome> getAllMetaHome(){
		return metaHomeRepository.findAll();
	}
	@Override
	public List<MetaTagHome> getMetaHoomeBy(String where){
		List<MetaTagHome> metahome=new ArrayList<MetaTagHome>();
		List<MetaTagHome> metahomeall=metaHomeRepository.findAll();
		
		switch(where){
		case "home":
		 metahome=metahomeall.stream().filter(m->m.getWhere().equals("home")).collect(Collectors.toList());	
		 break;
		case "footer":
			 metahome=metahomeall.stream().filter(m->m.getWhere().equals("footer")).collect(Collectors.toList());	
			 break;
		case "header":
			 metahome=metahomeall.stream().filter(m->m.getWhere().equals("header")).collect(Collectors.toList());	
			 break;
		case "men":
			 metahome=metahomeall.stream().filter(m->m.getWhere().equals("men")).collect(Collectors.toList());	
			 break;
		case "women":
			 metahome=metahomeall.stream().filter(m->m.getWhere().equals("women")).collect(Collectors.toList());	
			 break;
		case "kids":
			 metahome=metahomeall.stream().filter(m->m.getWhere().equals("kids")).collect(Collectors.toList());	
			 break;
		case "junior":
			 metahome=metahomeall.stream().filter(m->m.getWhere().equals("junior")).collect(Collectors.toList());	
			 break;
		}
		return metahome;
		
	}
	@Override
	public String deleteMetaHome(int id) {
		metaHomeRepository.deleteById(id);
		return "deleted";
	}
    @Override
	public String updatehomemeta(MetaTagHome metaTagHome) {
		MetaTagHome exist=metaHomeRepository.findAllByHomeId(metaTagHome.getHid());
		exist.setHid(metaTagHome.getHid());
	    exist.setDescription(metaTagHome.getDescription());
		exist.setFacebook(metaTagHome.getFacebook());
		exist.setInstagram(metaTagHome.getInstagram());
		exist.setKeyword(metaTagHome.getKeyword());
		exist.setLinkedin(metaTagHome.getLinkedin());
		exist.setPintrest(metaTagHome.getPintrest());
		exist.setTwitter(metaTagHome.getTwitter());
		exist.setWhere(metaTagHome.getWhere());
		metaHomeRepository.save(exist);
		return "updated";
	}
    @Override
    public MetaTags addMetaTags(MetaTags metaTags) {
    	return metaTagRepository.save(metaTags);
    }
    @Override
	public String deleteMetaTag(int id) {
    	metaTagRepository.deleteById(id);
		return "deleted";
	}
    @Override
   	public String updatemeta(MetaTags metaTags) {
    	MetaTags exist=metaTagRepository.findAllByMId(metaTags.getMid());
   		exist.setMid(metaTags.getMid());
   		exist.setMcId(metaTags.getMcId());
   	    exist.setDescription(metaTags.getDescription());
   		exist.setFacebook(metaTags.getFacebook());
   		exist.setInstagram(metaTags.getInstagram());
   		exist.setKeyword(metaTags.getKeyword());
   		exist.setLinkedin(metaTags.getLinkedin());
   		exist.setPintrest(metaTags.getPintrest());
   		exist.setTwitter(metaTags.getTwitter());
   		
   		metaTagRepository.save(exist);
   		return "updated";
   	}
    @Override
    public StateDelivery addStateDelivery(StateDelivery stateDelivery) {
    	return stateDeliveryRepository.save(stateDelivery);
    }
    @Override
    public List<StateDelivery> getStatesDelivery(){
    	return stateDeliveryRepository.findAll();
    }
    @Override
    public String deleteStateDelivery(int id) {
    	stateDeliveryRepository.deleteById(id);
    	return "deleted";
    }
    @Override
    public List<Products> getProductsByGroup(String group){
    	return productsrepository.findByGroup(group);
    }
	@Override
	@Transactional
	public String addBulkXl(MultipartFile file) throws IOException {

		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			XSSFSheet worksheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			 
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = worksheet.getRow(i);
				 String code = null;
				 
				
                 if(formatter.formatCellValue(row.getCell(0))!=null) {
                	 code = formatter.formatCellValue(row.getCell(0));
                
				 if(code!=null) {
					 Products ex = productsrepository.findByProductcode(code);
					 

					
				 if (ex != null) {
				     System.out.println(i);
					 String productname=formatter.formatCellValue(row.getCell(1));
					 String productcode=formatter.formatCellValue(row.getCell(0));
					 int offer=(int)row.getCell(8).getNumericCellValue();					
					 String imageurl=formatter.formatCellValue(row.getCell(9));				
					 double price=0;						
					 String phone=formatter.formatCellValue(row.getCell(12));	
					 String date=formatter.formatCellValue(row.getCell(14));	
				     String quantity="0";						
				     String descpription=formatter.formatCellValue(row.getCell(4));				
				     String descpription1=formatter.formatCellValue(row.getCell(5));		
				     String maincategory=formatter.formatCellValue(row.getCell(2));	
				     String subcategory=formatter.formatCellValue(row.getCell(3));			
				     String productcolor=formatter.formatCellValue(row.getCell(13));	
				     String fabric=formatter.formatCellValue(row.getCell(6));				
				     int minqty=(int)(row.getCell(7).getNumericCellValue());
				     String deliveryTime=formatter.formatCellValue(row.getCell(11));				
				     String sizechart=formatter.formatCellValue(row.getCell(10));			
					 double tax=row.getCell(15).getNumericCellValue();
					 String gsthsn=formatter.formatCellValue(row.getCell(18));
		             String subcategory1=formatter.formatCellValue(row.getCell(19));
		             String childcategory=formatter.formatCellValue(row.getCell(20));
		             String group=formatter.formatCellValue(row.getCell(21));
		             String productsby=formatter.formatCellValue(row.getCell(22)); 
		             String twnp=formatter.formatCellValue(row.getCell(23));
						ex.setMcId(ex.getMcId());
						if (productcode != null ) {
							ex.setProductcode(productcode);
						}
						if (productname != null) {
							ex.setProductname(productname);
						}
						if (maincategory != null) {
							ex.setMaincategory(maincategory);
						}
						if (subcategory != null) {
							ex.setSubcategory(subcategory);
						}
						if (descpription != null) {
							ex.setDescpription(descpription);
						}
						if (descpription1 != null) {
							ex.setDescpription1(descpription1);
						}
						if (fabric != null) {
							ex.setFabric(fabric);
						}
						if (minqty != 0) {
							ex.setMinqty(minqty);
						}
						if (offer != 0) {
							ex.setOffer(offer);
						}
						if (imageurl != null) {
							ex.setImageurl(imageurl);
							
						}
						if (sizechart != null) {
							ex.setSizechart(sizechart);
						}
						
						if (deliveryTime != null) {
							ex.setDeliveryTime(deliveryTime);
						}
						if (phone != null) {
							ex.setPhone(phone);
						}
						if (productcolor != null) {
							ex.setProductcolor(productcolor);
						}
						if (date != null) {
							ex.setDate(date);
						}
						if (tax != 0) {
							ex.setTax(tax);
						}
						if(gsthsn!=null) {
							ex.setGsthsn(gsthsn);
						}
						if(subcategory1!=null) {
							ex.setSubcategory1(subcategory1);
						}
						if(childcategory!=null) {
							ex.setChildcategory(childcategory);
						}
						if(group!=null) {
							ex.setGroup(group);
						}
						if(productsby!=null) {
							ex.setShopbyproducts(productsby);
						}
						if(twnp!=null) {
							ex.setPntw(twnp);
						}
						ex.setPrice(price);
						ex.setQuantity(quantity);
						

						productsrepository.save(ex);
//					    
						
						if(formatter.formatCellValue(row.getCell(16))!=null) {
						
							
							if(formatter.formatCellValue(row.getCell(16))!=null) {
								 
								 String img = formatter.formatCellValue(row.getCell(16));
									List<String> imgList = new ArrayList<String>(Arrays.asList(img.split(",")));
									for (int j = 0; j < imgList.size(); j++) {
			                         List<ProductImage> e=productImageRepository.findByImageUrl(imgList.get(j)).stream().filter(x->x.getMcId().equals(ex)).collect(Collectors.toList());
			                         for(int c=0;c<e.size();c++) {
			                        	 productImageRepository.deleteById(e.get(c).getImId());
			                         }
			                        
									}
							 }
							
							String img = formatter.formatCellValue(row.getCell(16));
							List<String> imgList = new ArrayList<String>(Arrays.asList(img.split(",")));
							for (int j = 0; j < imgList.size(); j++) {
								ProductImage image = new ProductImage();
								image.setMcId(ex);
								image.setImageUrl(imgList.get(j));
								productImageRepository.save(image);
							//	entityManager.persist(image);

							}
						}
                         
						if(formatter.formatCellValue(row.getCell(17))!=null) {
							if(formatter.formatCellValue(row.getCell(17))!=null) {
								String spec = formatter.formatCellValue(row.getCell(17));
								List<String> specList = new ArrayList<String>(Arrays.asList(spec.split(",")));
								for (int j = 0; j < specList.size(); j++) {
								List<ProductSpecification> sp=productSpecificationRepository.findBySpecification(specList.get(j)).stream().filter(x->x.getMcId().equals(ex)).collect(Collectors.toList());
								 for(int c=0;c<sp.size();c++) {
									 productSpecificationRepository.deleteById(sp.get(c).getSpid());
		                         }
								}
							}
							String spec = formatter.formatCellValue(row.getCell(17));
							List<String> specList = new ArrayList<String>(Arrays.asList(spec.split(",")));
							for (int j = 0; j < specList.size(); j++) {
								ProductSpecification specs = new ProductSpecification();
								specs.setMcId(ex);
								specs.setSpecification(specList.get(j));
								productSpecificationRepository.save(specs);
							//	entityManager.persist(specs);

							}
						}
					
				} else {
					
					 String productname=formatter.formatCellValue(row.getCell(1));
					 String productcode=formatter.formatCellValue(row.getCell(0));
					 int offer=(int)row.getCell(8).getNumericCellValue();					
					 String imageurl=formatter.formatCellValue(row.getCell(9));				
					 double price=0;						
					 String phone=formatter.formatCellValue(row.getCell(12));	
					 String date=formatter.formatCellValue(row.getCell(14));	
				     String quantity="0";						
				     String descpription=formatter.formatCellValue(row.getCell(4));				
				     String descpription1=formatter.formatCellValue(row.getCell(5));		
				     String maincategory=formatter.formatCellValue(row.getCell(2));	
				     String subcategory=formatter.formatCellValue(row.getCell(3));			
				     String productcolor=formatter.formatCellValue(row.getCell(13));	
				     String fabric=formatter.formatCellValue(row.getCell(6));				
				     int minqty=(int)(row.getCell(7).getNumericCellValue());
				     String deliveryTime=formatter.formatCellValue(row.getCell(11));				
				     String sizechart=formatter.formatCellValue(row.getCell(10));			
					 double tax=row.getCell(15).getNumericCellValue();
					 String gsthsn=formatter.formatCellValue(row.getCell(18));
					 String subcategory1=formatter.formatCellValue(row.getCell(19));
		             String childcategory=formatter.formatCellValue(row.getCell(20));
		             String group=formatter.formatCellValue(row.getCell(21));
		             String productsby=formatter.formatCellValue(row.getCell(22));
		             String twnp=formatter.formatCellValue(row.getCell(23));

						Products data = new Products();
						if (productcode != null ) {
							data.setProductcode(productcode);
						}
						if (productname != null) {
							data.setProductname(productname);
						}
						if (maincategory != null) {
							data.setMaincategory(maincategory);
						}
						if (subcategory != null) {
							data.setSubcategory(subcategory);
						}
						if (descpription != null) {
							data.setDescpription(descpription);
						}
						if (descpription1 != null) {
							data.setDescpription1(descpription1);
						}
						if (fabric != null) {
							data.setFabric(fabric);
						}
						if (minqty != 0) {
							data.setMinqty(minqty);
						}
						if (offer != 0) {
							data.setOffer(offer);
						}
						if (imageurl != null) {
							data.setImageurl(imageurl);
						}
						if (sizechart != null) {
							data.setSizechart(sizechart);
						}
						
						if (deliveryTime != null) {
							data.setDeliveryTime(deliveryTime);
						}
						if (phone != null) {
							data.setPhone(phone);
						}
						if (productcolor != null) {
							data.setProductcolor(productcolor);
						}
						if (date != null) {
							data.setDate(date);
						}
						if (tax != 0) {
							data.setTax(tax);
						}
						if(gsthsn!=null) {
							data.setGsthsn(gsthsn);
						}
						if(subcategory1!=null) {
							data.setSubcategory1(subcategory1);
						}
						if(childcategory!=null) {
							data.setChildcategory(childcategory);
						}
						if(group!=null) {
							data.setGroup(group);
						}
						if(productsby!=null) {
							data.setShopbyproducts(productsby);
						}
						if(twnp!=null) {
							data.setPntw(twnp);
						}
						data.setPrice(price);
						data.setQuantity(quantity);
						entityManager.persist(data);
						
						if(formatter.formatCellValue(row.getCell(16))!=null) {
							String img = formatter.formatCellValue(row.getCell(16));
							List<String> imgList = new ArrayList<String>(Arrays.asList(img.split(",")));
							for (int j = 0; j < imgList.size(); j++) {
								ProductImage image = new ProductImage();
								image.setMcId(data);
								image.setImageUrl(imgList.get(j));
								entityManager.persist(image);

							}
						}
                         
						if(formatter.formatCellValue(row.getCell(17))!=null) {
							String spec = formatter.formatCellValue(row.getCell(17));
							List<String> specList = new ArrayList<String>(Arrays.asList(spec.split(",")));
							for (int j = 0; j < specList.size(); j++) {
								ProductSpecification specs = new ProductSpecification();
								specs.setMcId(data);
								specs.setSpecification(specList.get(j));
								entityManager.persist(specs);

							}
						}
		
				} 
				 }
			}
		}
		}
		return "added";
	}
	
	public void deleteProduct(List<ProductImage> img) {
		entityManager.remove(img);
	}
    @Override
    public Map<String, List<Products>> getProductsAsKeyValuePair(){
    	ConcurrentHashMap<String, List<Products>> products=new ConcurrentHashMap<>();
    	
    	List<Products> men=productsrepository.findByCatgeory("MEN");
    	
    	List<Products> women=productsrepository.findAll().stream().filter(x->x.getMaincategory().equals("WOMEN")).collect(Collectors.toList());
    
    	List<Products> kids=productsrepository.findAll().stream().filter(x->x.getMaincategory().equals("KIDS")).collect(Collectors.toList());
    	
    	List<Products> junior=productsrepository.findAll().stream().filter(x->x.getMaincategory().equals("JUNIOR")).collect(Collectors.toList());
       
    	products.put("MEN", men);
    	products.put("WOMEN",women);
    	products.put("KIDS",kids);
    	products.put("JUNIOR",junior);
    	
    	
    	return products;
    	
    }
    @Override
    @Transactional
    public String addBulkXlSize(MultipartFile file) throws IOException {
    	try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			XSSFSheet worksheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = worksheet.getRow(i);
				String code = formatter.formatCellValue(row.getCell(1));
				ProductSize ps = productSizeRepository.findBySku(code);		
               if(ps!=null) {
					  System.out.println(code);
					ps.setMcId(ps.getMcId());
					if (formatter.formatCellValue(row.getCell(1)) != null) {
                      ps.setSku(formatter.formatCellValue(row.getCell(1)));
					}
					if (formatter.formatCellValue(row.getCell(2)) != null) {
                      ps.setPsize(formatter.formatCellValue(row.getCell(2)));
					}
					if (row.getCell(3).getNumericCellValue()!= 0) {
                      ps.setPrice(row.getCell(3).getNumericCellValue());
					}
					if (Integer.parseInt(formatter.formatCellValue(row.getCell(4)))!= 0) {
                      ps.setQty(Integer.parseInt(formatter.formatCellValue(row.getCell(4))));
					}
					if (row.getCell(5).getNumericCellValue()!= 0) {
	                      ps.setWidth(row.getCell(5).getNumericCellValue());
					}
					if (row.getCell(6).getNumericCellValue()!= 0) {
	                      ps.setHeight(row.getCell(6).getNumericCellValue());
					}
					if (row.getCell(7).getNumericCellValue()!= 0) {
	                      ps.setLength(row.getCell(7).getNumericCellValue());
					}
					entityManager.persist(ps);
					
				 }else {
					 System.out.println("hi");
					    Products p=productsrepository.findByProductcode(formatter.formatCellValue(row.getCell(0)));
					    ProductSize psize=new ProductSize();
					    System.out.println(code);
					    psize.setMcId(p);
						if (formatter.formatCellValue(row.getCell(1)) != null) {
							psize.setSku(formatter.formatCellValue(row.getCell(1)));
						}
						if (formatter.formatCellValue(row.getCell(2)) != null) {
							psize.setPsize(formatter.formatCellValue(row.getCell(2)));
						}
						if (row.getCell(3).getNumericCellValue()!= 0) {
							psize.setPrice(row.getCell(3).getNumericCellValue());
						}
						if (Integer.parseInt(formatter.formatCellValue(row.getCell(4)))!= 0) {
							psize.setQty(Integer.parseInt(formatter.formatCellValue(row.getCell(4))));
						}
						if (row.getCell(5).getNumericCellValue()!= 0) {
							psize.setWidth(row.getCell(5).getNumericCellValue());
						}
						if (row.getCell(6).getNumericCellValue()!= 0) {
							psize.setHeight(row.getCell(6).getNumericCellValue());
						}
						if (row.getCell(7).getNumericCellValue()!= 0) {
							psize.setLength(row.getCell(7).getNumericCellValue());
						}
						entityManager.persist(psize);
						
				 }
				}
    	}
    	return "added sizes";
    }
    @Override
    public List<Products> searchProductAdmin(String type,String input){
    	if(type.equals("URL")) {
    		String url=input.replace("https://nazca.in/info/", "");
    		int y = Integer.valueOf(url.replaceAll(".+/", ""));
    		return productsrepository.findAll().stream().filter(x->x.getMcId()==y).collect(Collectors.toList());
    	}else if(type.equals("ID")){
    		return productsrepository.findAll().stream().filter(x->x.getProductcode().equals(input)).collect(Collectors.toList());
    	}else {
    		return productsrepository.findAll().stream().filter(x->x.getMaincategory().equals(input)).collect(Collectors.toList());
    	}
    	
    	
    }
    @Override
    public String addPhotos(MultipartFile file) throws IOException {
        
    	if(file!=null) {
    		this.uploadImage(file, "products");
    	}
    	
    	return file.getOriginalFilename();
    }


	@PostConstruct
	private void initAmazon() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
	}

	public void uploadImage(MultipartFile image,String name) throws IOException {

		if (!image.isEmpty()) {

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(image.getContentType());

			this.s3Client.putObject(new PutObjectRequest(bucketName +"/"+name, image.getOriginalFilename(),
					image.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicReadWrite));


		}

	}
	
// HSN
	@Override
	public HSNConfig addHsnConfig(HSNConfig hsnConfig) {
		return hSNRepository.save(hsnConfig);
	}
	@Override
	public List<HSNConfig> getAllHSN(){
		return hSNRepository.findAll();
	}
	@Override
	public HSNConfig getHSN(int id) {
		return hSNRepository.findById(id).orElseThrow(null);			
	}
	@Override
	public HSNConfig getHSN(String hsn) {
		return hSNRepository.findByHsnnumber(hsn);
	}
	@Override
	public String deleteHSN(int id){
		hSNRepository.deleteById(id);
		return "deleted";
	}

}
