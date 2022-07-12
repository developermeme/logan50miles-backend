package com.Logan50miles.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Entity.ShopAdmin;
import com.Logan50miles.Entity.SliderMobile;
import com.Logan50miles.Entity.Sliders;
import com.Logan50miles.Entity.StateTax;
import com.Logan50miles.Entity.VendorTax;

public interface ShopAdminService {

	ResponseEntity<String> shopLogin(String shopName, String pwd);

	ResponseEntity<String> shopRegisteration(ShopAdmin shopAdmin, MultipartFile file) throws IOException;

	List<Orders> listOrdersByShop(String shopId);

	List<Orders> getStatics(String id, String from, String to) throws ParseException;

	List<Orders> getStaticsWhole(String from, String to) throws ParseException;

	Sliders addSlider(Sliders sliders, MultipartFile file) throws IOException;

	SliderMobile addSliders(SliderMobile sliderMobile, MultipartFile file) throws IOException;

	List<Sliders> getBanners();

	List<SliderMobile> getSlidersmobile();

	List<ShopAdmin> getVendors();

	String forgorPassword(String shopName);

	String updatePassword(String shopName, String password);

	ResponseEntity<String> confirmOtp(String confirmationToken);

	String shopUpdateStatus(int shopId, String pstatus);

	VendorTax addTax(VendorTax vendorTax);

	StateTax addStateTax(StateTax stateTax);

	List<VendorTax> getVendorTaxs();

	VendorTax getTax(String country);

	String updateTax(int id, StateTax stateTax);

	String deleteTax(int id);

	String deleteFile(int id);

	String deleteMobileSlide(int id);

	String updateVendor(ShopAdmin existence1);

	Sliders updateSlider(Sliders sliders, MultipartFile file) throws IOException;
}
