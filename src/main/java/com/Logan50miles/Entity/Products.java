package com.Logan50miles.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.poiji.annotation.ExcelCellName;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mcId;
	@ExcelCellName("Productname")
	private String productname;
	@ExcelCellName("Productcode")
	private String productcode;
	@ExcelCellName("Offer")
	private int offer;
	@ExcelCellName("Imageurl")
	private String imageurl;
	@ExcelCellName("Price")
	private double price;
	@ExcelCellName("Phone")
	private String phone;
	@ExcelCellName("Date")
	private String date;
	@ExcelCellName("Quantity")
    private String quantity;
	@ExcelCellName("Descpription")
    private String descpription;
	@ExcelCellName("Descpription1")
    private String descpription1;
	@ExcelCellName("Maincategory")
    private String maincategory;
	@ExcelCellName("Subcategory")
    private String subcategory;
	@ExcelCellName("Productcolor")
    private String productcolor;
	@ExcelCellName("Fabric")
    private String fabric;
	@ExcelCellName("Minqty")
    private int minqty;
	@ExcelCellName("DeliveryTime")
    private String deliveryTime;
	@ExcelCellName("Sizechart")
    private String sizechart;
	@ExcelCellName("Tax")
	private double tax;
    private String group;
	@OneToMany(mappedBy="mcId",cascade = {CascadeType.ALL})
	private List<ProductSize> productSize;
	@OneToMany(mappedBy="mcId",cascade = {CascadeType.ALL})
	private List<ProductImage> productImages;
	@OneToMany(mappedBy="mcId",cascade = {CascadeType.ALL})
	private List<ProductSpecification> productSpecs;
	@OneToMany(mappedBy="mcId",cascade = {CascadeType.ALL})
	private List<MetaTags> metatags;
	@OneToMany(mappedBy="mcId",cascade = {CascadeType.ALL})
	private List<Review> reviews;
	@OneToMany(mappedBy="mcId",cascade = {CascadeType.ALL})
	private List<Ratings> ratings;
	private String gsthsn;
    private String subcategory1;
    private String childcategory;
    private String shopbyproducts;
    private String pntw;
	public Products() {}
     
	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}


	public int getMcId() {
		return mcId;
	}

	public void setMcId(int mcId) {
		this.mcId = mcId;
	}

	public int getOffer() {
		return offer;
	}

	public void setOffer(int offer) {
		this.offer = offer;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDescpription() {
		return descpription;
	}

	public void setDescpription(String descpription) {
		this.descpription = descpription;
	}

	public List<ProductSize> getProductSize() {
		return productSize;
	}

	public void setProductSize(List<ProductSize> productSize) {
		this.productSize = productSize;
	}

	public int getMinqty() {
		return minqty;
	}

	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}

	public String getDescpription1() {
		return descpription1;
	}

	public void setDescpription1(String descpription1) {
		this.descpription1 = descpription1;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMaincategory() {
		return maincategory;
	}

	public void setMaincategory(String maincategory) {
		this.maincategory = maincategory;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}



	public String getProductcolor() {
		return productcolor;
	}

	public void setProductcolor(String productcolor) {
		this.productcolor = productcolor;
	}

	public String getSizechart() {
		return sizechart;
	}

	public void setSizechart(String sizechart) {
		this.sizechart = sizechart;
	}

	public List<ProductSpecification> getProductSpecs() {
		return productSpecs;
	}

	public void setProductSpecs(List<ProductSpecification> productSpecs) {
		this.productSpecs = productSpecs;
	}

	public List<MetaTags> getMetatags() {
		return metatags;
	}

	public void setMetatags(List<MetaTags> metatags) {
		this.metatags = metatags;
	}

	public String getGsthsn() {
		return gsthsn;
	}

	public void setGsthsn(String gsthsn) {
		this.gsthsn = gsthsn;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Ratings> getRatings() {
		return ratings;
	}

	public void setRatings(List<Ratings> ratings) {
		this.ratings = ratings;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubcategory1() {
		return subcategory1;
	}

	public void setSubcategory1(String subcategory1) {
		this.subcategory1 = subcategory1;
	}

	public String getChildcategory() {
		return childcategory;
	}

	public void setChildcategory(String childcategory) {
		this.childcategory = childcategory;
	}

	public String getShopbyproducts() {
		return shopbyproducts;
	}

	public void setShopbyproducts(String shopbyproducts) {
		this.shopbyproducts = shopbyproducts;
	}

	public String getPntw() {
		return pntw;
	}

	public void setPntw(String pntw) {
		this.pntw = pntw;
	}

	
}
