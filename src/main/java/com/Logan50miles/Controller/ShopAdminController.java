package com.Logan50miles.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Entity.ShopAdmin;
import com.Logan50miles.Entity.SliderMobile;
import com.Logan50miles.Entity.Sliders;
import com.Logan50miles.Entity.StateTax;
import com.Logan50miles.Entity.VendorTax;
import com.Logan50miles.Service.ShopAdminService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ophelia")
public class ShopAdminController {

	@Autowired
	private ShopAdminService shopAdminService;

	@GetMapping("shop/login")
	public ResponseEntity<String> adminLogin(String shopName, String pwd) {
		return shopAdminService.shopLogin(shopName, pwd);

	}

	@PostMapping("shop/register")
	public ResponseEntity<String> adminregistration(ShopAdmin shopAdmin, MultipartFile file) throws IOException {
		return shopAdminService.shopRegisteration(shopAdmin, file);
	}

	@GetMapping("shop/orders")
	public List<Orders> getListOrdersByShop(String shopId) {
		return shopAdminService.listOrdersByShop(shopId);
	}

	@GetMapping("shop/orders/stats")
	public List<Orders> getOrderstatics(String id, String from, String to) throws ParseException {
		return shopAdminService.getStatics(id, from, to);
	}

	@PostMapping("banner")
	public Sliders addSlider(Sliders sliders, MultipartFile file) throws IOException {
		return shopAdminService.addSlider(sliders, file);
	}

	@PostMapping("update/banner")
	public Sliders updateSlider(Sliders sliders, MultipartFile file) throws IOException {
		return shopAdminService.updateSlider(sliders, file);
	}

	@GetMapping("banners")
	public List<Sliders> getSliders() {
		return shopAdminService.getBanners();
	}

	@PostMapping("delete/banner")
	public String deleteSliders(int id) {
		shopAdminService.deleteFile(id);
		return "deleted";
	}

	@PostMapping("slider/mobile")
	public SliderMobile addSliders(SliderMobile sliderMobile, MultipartFile file) throws IOException {
		return shopAdminService.addSliders(sliderMobile, file);
	}

	@PostMapping("slider/delete")
	public String deleteSlide(int id) {
		shopAdminService.deleteMobileSlide(id);
		return "deleted";
	}

	@GetMapping("slider/mobile")
	public List<SliderMobile> getMobileSlider() {
		return shopAdminService.getSlidersmobile();
	}

	@GetMapping("shop/vendors")
	public List<ShopAdmin> getVendors() {
		return shopAdminService.getVendors();
	}

	@PostMapping("shop/forgotpassword")
	public String forgotPassword(String shopName) {
		return shopAdminService.forgorPassword(shopName);

	}

	@PostMapping("shop/confirmotp")
	public ResponseEntity<String> confirmOtp(@RequestParam String confirmationToken) {
		return shopAdminService.confirmOtp(confirmationToken);
	}

	@PostMapping("shop/updatepassword")
	public String updatePassword(String shopName, String password) {
		return shopAdminService.updatePassword(shopName, password);

	}

	@PostMapping("vendortax")
	public VendorTax addTax(VendorTax vendorTax) {
		return shopAdminService.addTax(vendorTax);
	}

	@GetMapping("vendortax")
	public List<VendorTax> getList() {
		return shopAdminService.getVendorTaxs();
	}

	@PostMapping("statetax")
	public StateTax addStateTax(StateTax stateTax) {
		return shopAdminService.addStateTax(stateTax);
	}

	@GetMapping("statetax")
	public VendorTax getTax(String country) {
		return shopAdminService.getTax(country);
	}

	@PostMapping("update/tax")
	public String updateTax(int id, StateTax stateTax) {
		return shopAdminService.updateTax(id, stateTax);
	}

	@PostMapping("delete/tax")
	public String deleteTax(int id) {
		return shopAdminService.deleteTax(id);
	}

	@PostMapping("shop/updatevendor")
	public String updatePassword(ShopAdmin existence) {
		return shopAdminService.updateVendor(existence);
	}

	@PostMapping("shop/update/status")
	public String shopUpdateStatus(int shopId, String pstatus) {
		return shopAdminService.shopUpdateStatus(shopId, pstatus);
	}
}
