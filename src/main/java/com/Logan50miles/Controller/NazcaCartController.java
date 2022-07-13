package com.Logan50miles.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Logan50miles.Entity.Address;
import com.Logan50miles.Entity.Mailconfiguration;
import com.Logan50miles.Entity.OrderItems;
import com.Logan50miles.Entity.OrderStatus;
import com.Logan50miles.Entity.Orders;
import com.Logan50miles.Entity.PreOrderOfferSetter;
import com.Logan50miles.Entity.ProductSize;
import com.Logan50miles.Entity.Products;
import com.Logan50miles.Entity.Referral;
import com.Logan50miles.Entity.ReferralPlan;
import com.Logan50miles.Entity.UserCartTemp;
import com.Logan50miles.Entity.Zipcode;
import com.Logan50miles.Model.CashFreeModel;
import com.Logan50miles.Model.ItemInfo;
import com.Logan50miles.Model.NazcaShoppingCart;
import com.Logan50miles.Model.ProductInfo;
import com.Logan50miles.Model.ZipCodes;
import com.Logan50miles.Repository.AddressRepository;
import com.Logan50miles.Repository.MailConfigurationRepository;
import com.Logan50miles.Repository.OrderItemRepository;
import com.Logan50miles.Repository.OrderRepository;
import com.Logan50miles.Repository.OrderStatusrepository;
import com.Logan50miles.Repository.PreOrderOfferSetterRepository;
import com.Logan50miles.Repository.ProductSizeRepository;
import com.Logan50miles.Repository.ProductsRepository;
import com.Logan50miles.Repository.ReferralPlanRepository;
import com.Logan50miles.Repository.ReferralRepository;
import com.Logan50miles.Repository.ReferralSubscriptionRepository;
import com.Logan50miles.Repository.UserCartRepository;
import com.Logan50miles.Repository.UserRepository;
import com.Logan50miles.Repository.ZipcodeRepository;
import com.Logan50miles.Util.Mailer;
import com.Logan50miles.Util.PaymentRequest;
import com.Logan50miles.Util.ResourceNotFoundException;
import com.Logan50miles.Util.StripePayment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping("ophelia")
@CrossOrigin(originPatterns = "*")
public class NazcaCartController {
	@Autowired
	ProductSizeRepository productSizeRepository;
	@Autowired
	UserCartRepository userCartRepository;
	@Autowired
	ProductsRepository productRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	OrderStatusrepository orderStatusrepository;
	@Autowired
	private ReferralRepository referralRepository;
	@Autowired
	private ReferralSubscriptionRepository referralSubscriptionRepository;
	@Autowired
	private UserRepository userRrepository;
	@Autowired
	private PreOrderOfferSetterRepository preOrderOfferSetterRepository;
	@Autowired
	MailConfigurationRepository mailConfigurationRepository;
	@Autowired
	ReferralPlanRepository referralplnRepository;
	@Autowired
	ZipcodeRepository zipcodeRepository;
	@Autowired
	StripePayment service;
	
	public static final String ACCOUNT_SID = "AC7a6eead9ea9475bc2385fa3232a32387";
	public static final String AUTH_TOKEN = "32e31b41496bec37af5db3ac10f770cd";

	@PostMapping("addcart/nazcas")
	public String addNazcaCart(@RequestBody List<UserCartTemp> usercart) {
		if (usercart != null) {
			for (UserCartTemp user : usercart) {
				this.addProductCart(user.getpId(), user.getQty(), user.getCustId(), user.getSize(), user.getPrice());

			}
			return "added";
		} else {
			return "emptycart";
		}
	}

	@PostMapping("addnazcacart")
	public String addProductCart(int productId, int qty, String cusId, String size, double price) {
		UserCartTemp userCartTemp = new UserCartTemp();
		List<UserCartTemp> userCartList = userCartRepository.findByCustomerId(cusId);
		ArrayList<Integer> l = new ArrayList<Integer>();
		List<String> s = new ArrayList<String>();
		for (int i = 0; i < userCartList.size(); i++) {
			int id = userCartList.get(i).getpId();
			String si = userCartList.get(i).getSize();
			l.add(id);
			s.add(si);
		}
		if (userCartList.isEmpty()) {
			userCartTemp.setpId(productId);
			userCartTemp.setQty(qty);
			userCartTemp.setCustId(cusId);
			userCartTemp.setSize(size);
			userCartTemp.setPrice(price);
			userCartRepository.save(userCartTemp);

		} else {
			for (int i = 0; i < l.size(); i++) {

				UserCartTemp cartItem = userCartList.get(i);

				if (cartItem.getpId() == productId && cartItem.getSize().equals(size)) {

					cartItem.setpId(productId);
					cartItem.setQty(qty);
					cartItem.setCustId(cusId);
					cartItem.setSize(size);
					cartItem.setPrice(price);
					userCartRepository.save(cartItem);

				} else if (cartItem.getpId() == productId && cartItem.getSize() != size) {
					if (!s.contains(size)) {
						userCartTemp.setpId(productId);
						userCartTemp.setQty(qty);
						userCartTemp.setCustId(cusId);
						userCartTemp.setSize(size);
						userCartTemp.setPrice(price);
						userCartRepository.save(userCartTemp);
					}
				} else if (!l.contains(productId)) {

					userCartTemp.setpId(productId);
					userCartTemp.setQty(qty);
					userCartTemp.setCustId(cusId);
					userCartTemp.setSize(size);
					userCartTemp.setPrice(price);
					userCartRepository.save(userCartTemp);

				}
			}

		}

		return "added";

	}

	@GetMapping("viewnazcacart")
	public NazcaShoppingCart viewCart(String cusId) throws ParseException {

		return this.viewNazcaCart(cusId);
	}

	@PostMapping("deliverylocation")
	public NazcaShoppingCart addLocation(String cusId, int addId) throws ParseException {
		NazcaShoppingCart nazcaShoppingCart = this.viewNazcaCart(cusId);
		nazcaShoppingCart.setAddId(addId);
		return nazcaShoppingCart;
	}

	@PostMapping("placeorder")
	public String placeOrder(String cusId, int addId, String pstatus, String tnxid) throws ParseException {
		NazcaShoppingCart nazcaShoppingCart = this.viewNazcaCart(cusId);
		nazcaShoppingCart.setAddId(addId);

		this.saveOrder(addId, cusId, pstatus, tnxid);
		updateReferralWallets(cusId, nazcaShoppingCart.getTotalPrice());
		List<UserCartTemp> userCartTemp = userCartRepository.findByCustomerId(cusId);
		userCartRepository.deleteAll(userCartTemp);
		return "placed";

	}

	@PostMapping("admin/order/status")
	public String adminOrderStatusUpdate(int oid, String status) {
		OrderStatus os = orderStatusrepository.findByOrderId(oid);
		os.setId(os.getId());
		os.setOrders(os.getOrders());
		os.setsPhone(os.getStatus());
		os.setStatus(status);
		orderStatusrepository.save(os);
		return "updated";
	}

	@PostMapping("admin/ordercode/status")
	public String adminOrderStatusUpdate(String oid, String status) {
		OrderStatus os = orderStatusrepository.findByOrdercode(oid);
		os.setId(os.getId());
		os.setOrders(os.getOrders());
		os.setsPhone(os.getStatus());
		os.setStatus(status);
		orderStatusrepository.save(os);
		return "updated";
	}

	public void saveOrder(int idx, String cusId, String pstatus, String tnxid) throws ParseException {

		NazcaShoppingCart nazcaShoppingCart = this.viewNazcaCart(cusId);
		Address addr = addressRepository.findByAddId(idx);
		Orders order = new Orders();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateobj = new Date();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		int id = 101;

		order.setAddId(idx);
		order.setPaymentStatus(pstatus);
		order.setName(addr.getName());
		order.setPhone(addr.getPhone());
		order.setFlatNo(addr.getFlatNo());
		order.setStreet(addr.getStreet());
		order.setLandMark(addr.getLandMark());
		order.setCity(addr.getCity());
		order.setPin(addr.getPin());
		order.setState(addr.getState());
		order.setCountry(addr.getCountry());
		order.setOrderId(this.getMaxOrderNum() + 1);
		order.setPrice(nazcaShoppingCart.getTotalPrice());
		order.setDate(dateobj);
		order.setUserId(cusId);
		order.setTnxid(tnxid);
		if (order.getOrderId() == 999999) {
			id = id + 1;
		}
		yearInString = yearInString.substring(0, 2) + "-" + yearInString.substring(2);
		order.setOrdercode(id + yearInString + order.getOrderId());
		order.setTax(Double.toString(nazcaShoppingCart.getTax()));

		order.setShopId(nazcaShoppingCart.getCartItem().get(0).getProductInfo().getsPhone());
//	order.setDiscount(Double.toString(nazcaShoppingCart.getDiscount()));
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(order.getOrderId());
		orderStatus.setOrders(order);
		orderStatus.setsPhone(order.getUserId());
		orderStatus.setStatus("New Order");
		orderStatus.setOrdercode(order.getOrdercode());
		order.setOrderStatus(orderStatus);

//	orderStatusrepository.save(orderStatus);
		orderRepository.save(order);

		String address = null;
		String text =

				"<table width='100%' border='1' align='center'>" + "<tr align='center'>"
						+ "<td><b>Product Name <b></td>" + "<td><b>Size<b></td>" + "<td><b>Color<b></td>"
						+ "<td><b>Qty<b></td>" + "<td><b>Subtotal<b></td>" + "</tr>";
		address = "<h4><u>Delivery Address:</u></h4>" + "<h5>Name:" + order.getName() + "</h5>" + "<h5>Phone:"
				+ order.getPhone() + "</h5>" + "<h5>City:" + order.getCity() + "</h5>" + "<h5>Street:"
				+ order.getStreet() + "</h5>" + "<h5>Landmark:>" + order.getLandMark() + "</h5>" + "<h5>Pincode:"
				+ order.getPin() + "</h5>" + "<p align='right'> Total Paid:" + order.getPrice() + "</p>"
				+ "<p align='right'> Total Tax:" + order.getTax() + "</p>" + "<p align='right'> Payment Status:"
				+ order.getPaymentStatus() + "</p>";

		for (ItemInfo info : nazcaShoppingCart.getCartItem()) {
			OrderItems items = new OrderItems(order);

			items.setQuantity(info.getQuantity());
			items.setName(info.getProductInfo().getProductName());
			items.setSubt(info.getSubTotal());
			items.setColor(info.getProductInfo().getColor());
			items.setSize(info.getProductInfo().getSize());
			items.setImgUrl(info.getProductInfo().getImageurl());
			items.setTaxAmount((info.getSubTotal() * info.getProductInfo().getTax()) / 100);
			items.setSku(info.getProductInfo().getSku() + "-" + info.getProductInfo().getSize());
			items.setStatus("New Order");
			ProductSize p = productSizeRepository.findBySku(items.getSku());
			items.setLength(p.getLength());
			items.setWidth(p.getWidth());
			items.setHeight(p.getHeight());
			items.setGsthsn(info.getProductInfo().getGsthsn());
			if (!order.getState().equalsIgnoreCase("Tamilnadu")) {
				items.setGsttaxigstn(String.valueOf(items.getTaxAmount()));
				items.setGsttaxrateigstn(Double.toString(info.getProductInfo().getTax()));

				items.setGsttaxcgstn("NA");
				items.setGsttaxsgstn("NA");
				items.setGsttaxratesgstn("NA");
				items.setGsttaxratecgstn("NA");
				System.out.println("3");
			} else {
				System.out.println("4");
				items.setGsttaxigstn("NA");
				items.setGsttaxrateigstn("NA");
				items.setGsttaxcgstn(String.valueOf(items.getTaxAmount() / 2));
				items.setGsttaxsgstn(String.valueOf(items.getTaxAmount() / 2));
				items.setGsttaxratesgstn(Double.toString(info.getProductInfo().getTax() / 2));
				items.setGsttaxratecgstn(Double.toString(info.getProductInfo().getTax() / 2));
				System.out.println("5");
			}

			items.setGsttaxtotal(String.valueOf(items.getTaxAmount()));

			// items.setDeliveryCharge(deliveryCharge);

			if (0 > dateobj.compareTo(df.parse(info.getProductInfo().getEnddate()))) {
				items.setItemType("PreOrder");
			} else {
				items.setItemType("GeneralOrder");
			}
			orderItemRepository.save(items);

			text = text + "<tr align='center'>" + "<td>" + items.getName() + "</td>" + "<td>" + items.getSize() + "<td>"
					+ items.getColor() + "<td>" + items.getQuantity() + "</td>" + "<td>" + items.getSubt() + "</td>"
					+ "</tr>";
		}
		Mailconfiguration m = mailConfigurationRepository.findAll().stream().filter(x -> x.getType().equals("general"))
				.findAny().orElse(null);
		Mailer mail = new Mailer();
		mail.sendMail("LOGAN50MILES SHOP ORDER CONFIRMATION", address + text, order.getUserId(), m.getEmail(), m.getPassword());
	}

	@PostMapping("send/password")
	public String sendPasswordUsername(String username, String password, String phone) {
		String address = "<h4><u>Login Credentials are following:</u></h4>" + "<h5>USER NAME: " + username + "</h5>"
				+ "<h5>PASSWORD: " + password + "</h5>";
		try {
			Mailconfiguration m = mailConfigurationRepository.findAll().stream()
					.filter(x -> x.getType().equals("general")).findAny().orElse(null);
			Mailer mail = new Mailer();
			mail.sendMail(" LOGAN50MILES LOGIN CREDENTIALS", address, username, m.getEmail(), m.getPassword());
			this.verifivcationOtp(phone,
					"Greeting from Logan50Miles dear customer your Username :" + username + "password:" + password);
			return "send";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@PostMapping("help")
	public String sendHelp(String message, String email, String id) {
		String address = "<h5>Hi From: " + email + "</h5>" + "<p>" + message + "</p>";

		try {
			Mailconfiguration m = mailConfigurationRepository.findAll().stream()
					.filter(x -> x.getType().equals("general")).findAny().orElse(null);
			Mailer mail = new Mailer();
			mail.sendMail(id, address, email, m.getEmail(), m.getPassword());
			return "send";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private int getMaxOrderNum() {
		String value = orderRepository.findOrderId();
		if (value == null) {
			return 0;
		}
		return Integer.valueOf(value);

	}

	public NazcaShoppingCart viewNazcaCart(String cusId) throws ParseException {

		NazcaShoppingCart nazcaShoppingCart = new NazcaShoppingCart();

		List<ItemInfo> item = this.findItemInCart(cusId);

		nazcaShoppingCart.setCartItem(item);
		double total = 0;
		double discount = 0;
		double tax = 0;
		for (int i = 0; i < item.size(); i++) {

			total = total + item.get(i).getSubTotal();
			discount = discount + item.get(i).getDeduction();
			tax = tax + item.get(i).getTax();

		}
		nazcaShoppingCart.setTax(tax);
		nazcaShoppingCart.setTotalPrice(total);
		nazcaShoppingCart.setDiscount(discount);
		return nazcaShoppingCart;

	}

	public List<ItemInfo> findItemInCart(String cusId) throws ParseException {
//	PreOrderOfferSetter preOrderOfferSetter=new PreOrderOfferSetter();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		PreOrderOfferSetter pos = preOrderOfferSetterRepository.findByPosId(1);
		Date dateobj = new Date();
		List<UserCartTemp> userCartTemp = userCartRepository.findByCustomerId(cusId);
		List<ItemInfo> cartItem = new ArrayList<ItemInfo>();
		for (int i = 0; i < userCartTemp.size(); i++) {
			Products product = productRepository.getProductDetails(userCartTemp.get(i).getpId());
			ProductInfo productInfo = new ProductInfo(product);
			productInfo.setCartId(userCartTemp.get(i).getCartPId());
			productInfo.setSize(userCartTemp.get(i).getSize());
			productInfo.setQty(userCartTemp.get(i).getQty());
			productInfo.setPrice(userCartTemp.get(i).getPrice());
			ItemInfo item = new ItemInfo();
			item.setProductInfo(productInfo);
			item.setQuantity(userCartTemp.get(i).getQty());

			if (productInfo.getOffer() == 0) {
				if (0 > dateobj.compareTo(df.parse(productInfo.getEnddate()))) {
					item.setSubTotal((userCartTemp.get(i).getQty() * productInfo.getPrice()) * 50 / 100);
					item.setDeduction((userCartTemp.get(i).getQty() * productInfo.getPrice())
							- ((userCartTemp.get(i).getQty() * productInfo.getPrice()) * 0.5));
					item.setTax((item.getSubTotal() * item.getProductInfo().getTax()) / 100);
					cartItem.add(item);
				} else {
					item.setSubTotal(userCartTemp.get(i).getQty() * productInfo.getPrice());
					item.setDeduction(0);
					item.setTax((item.getSubTotal() * item.getProductInfo().getTax()) / 100);
					cartItem.add(item);
				}
			} else {
				double off = productInfo.getOffer();
				double per = 100;
				double s = (off / per);
				double ded = ((userCartTemp.get(i).getQty() * productInfo.getPrice()));

				item.setSubTotal(ded - (ded * s));
				item.setDeduction(ded * s);
				item.setTax((item.getSubTotal() * item.getProductInfo().getTax()) / 100);
				cartItem.add(item);
			}
		}
		return cartItem;
	}

	public void updateReferralWallet(String cusId, double bill) {
		String level3Id;
		String level3Code;
		String level2Id;
		String level2Code;
		String level1Id;
		String level1IdCode;
		String subcriptionTypeL3;
		String subcriptionTypeL2;
		String subcriptionTypeL1;

		String level4Idbuyer = userRrepository.findByEmail(cusId).getAffiliateCode();

		if (!level4Idbuyer.equals("None affiliated")) {
			level3Id = referralRepository.findByRefCod(level4Idbuyer).getCusId();
			level3Code = userRrepository.findByEmail(level3Id).getAffiliateCode();
			subcriptionTypeL3 = referralSubscriptionRepository.findByCusId(level3Id).getPlan();

			// 1ST PERSON
			double earn = (bill * 0.25) / 100;
			Referral referral = referralRepository.findByCusId(level3Id);
			referral.setRefAmount(referral.getRefAmount() + earn);
			referralRepository.save(referral);

			if (!level3Code.equals("None affiliated")) {
				level2Id = referralRepository.findByRefCod(level3Code).getCusId();
				level2Code = userRrepository.findByEmail(level2Id).getAffiliateCode();
				subcriptionTypeL2 = referralSubscriptionRepository.findByCusId(level2Id).getPlan();

				// 2ND PERSON
				if (subcriptionTypeL2.equals("GOLD") || subcriptionTypeL2.equals("DIAMOND")) {
					double earn2 = (bill * 0.5) / 100;
					Referral referral2 = referralRepository.findByCusId(level2Id);
					referral2.setRefAmount(referral2.getRefAmount() + earn2);
					referralRepository.save(referral2);
				}
				// else {
				// if(!level2Code.equals("None affiliated")) {
				// level1Id=referralRepository.findByRefCod(level2Code).getCusId();
				// level1IdCode=userRrepository.findByPhone(level1Id).getAffiliateCode();
				// subcriptionTypeL1=referralSubscriptionRepository.findByCusId(level1Id).getPlan();

				// System.out.println(level1Id);
				// System.out.println(level1IdCode);
				// System.out.println(subcriptionTypeL1);

				// 3RD LAST PERSON
				// if(subcriptionTypeL1.equals("DIAMOND") ) {
				// double earn3=(bill*0.25)/100;
				// Referral referral3=referralRepository.findByCusId(level1Id);
				// referral3.setRefAmount(referral3.getRefAmount()+earn3);
				// referralRepository.save(referral3);
				// }
				//
				// }
				// }
				if (!level2Code.equals("None affiliated")) {
					level1Id = referralRepository.findByRefCod(level2Code).getCusId();
					level1IdCode = userRrepository.findByEmail(level1Id).getAffiliateCode();
					subcriptionTypeL1 = referralSubscriptionRepository.findByCusId(level1Id).getPlan();

					System.out.println(level1Id);
					System.out.println(level1IdCode);
					System.out.println(subcriptionTypeL1);

					// 3RD LAST PERSON
					if (subcriptionTypeL1.equals("DIAMOND")) {
						double earn3 = (bill * 1) / 100;
						Referral referral3 = referralRepository.findByCusId(level1Id);
						referral3.setRefAmount(referral3.getRefAmount() + earn3);
						referralRepository.save(referral3);
					}

				}
			}

		}

	}

	public void updateReferralWallets(String cusId, double bill) {
		String level3Id;
		String level3Code;
		String level2Id;
		String level2Code;
		String level1Id;
		String level1IdCode;
		String subcriptionTypeL3;
		String subcriptionTypeL2;
		String subcriptionTypeL1;

		String level4Idbuyer = userRrepository.findByEmail(cusId).getAffiliateCode();

		if (!level4Idbuyer.equals("None affiliated")) {
			level3Id = referralRepository.findByRefCod(level4Idbuyer).getCusId();
			level3Code = userRrepository.findByEmail(level3Id).getAffiliateCode();
			subcriptionTypeL3 = referralSubscriptionRepository.findByCusId(level3Id).getPlan();
			switch (subcriptionTypeL3) {
			case "SILVER":
				ReferralPlan rp = referralplnRepository.findByPlanName("SILVER");
				double earn = (bill * rp.getBonusPercentage()) / 100;
				Referral referral = referralRepository.findByCusId(level3Id);
				referral.setRefAmount(referral.getRefAmount() + earn);
				referralRepository.save(referral);
				break;
			case "GOLD":
				ReferralPlan rpr = referralplnRepository.findByPlanName("GOLD");
				double earng = (bill * rpr.getBonusPercentage()) / 100;
				Referral referralg = referralRepository.findByCusId(level3Id);
				referralg.setRefAmount(referralg.getRefAmount() + earng);
				referralRepository.save(referralg);
				break;
			default:
				ReferralPlan rr = referralplnRepository.findByPlanName("DIAMOND");
				double earnd = (bill * rr.getBonusPercentage()) / 100;
				Referral referrald = referralRepository.findByCusId(level3Id);
				referrald.setRefAmount(referrald.getRefAmount() + earnd);
				referralRepository.save(referrald);

			}

			if (!level3Code.equals("None affiliated")) {
				level2Id = referralRepository.findByRefCod(level3Code).getCusId();
				level2Code = userRrepository.findByEmail(level2Id).getAffiliateCode();
				subcriptionTypeL2 = referralSubscriptionRepository.findByCusId(level2Id).getPlan();

				// 2RD LAST PERSON
				if (!subcriptionTypeL2.equals("SILVER")) {
					switch (subcriptionTypeL2) {

					case "GOLD":
						ReferralPlan rpg = referralplnRepository.findByPlanName("GOLD");
						double earngg = (bill * rpg.getBonusPercentage()) / 100;
						Referral referralgg = referralRepository.findByCusId(level2Id);
						referralgg.setRefAmount(referralgg.getRefAmount() + earngg);
						referralRepository.save(referralgg);
						break;
					default:
						ReferralPlan rrd = referralplnRepository.findByPlanName("DIAMOND");
						double earndd = (bill * rrd.getBonusPercentage()) / 100;
						Referral referraldd = referralRepository.findByCusId(level2Id);
						referraldd.setRefAmount(referraldd.getRefAmount() + earndd);
						referralRepository.save(referraldd);

					}
				}

				if (!level2Code.equals("None affiliated")) {
					level1Id = referralRepository.findByRefCod(level2Code).getCusId();
					level1IdCode = userRrepository.findByEmail(level1Id).getAffiliateCode();
					subcriptionTypeL1 = referralSubscriptionRepository.findByCusId(level1Id).getPlan();

					// 3RD LAST PERSON
					if (subcriptionTypeL1.equals("DIAMOND")) {
						ReferralPlan rpg = referralplnRepository.findByPlanName("DIAMOND");
						double earn3 = (bill * rpg.getBonusPercentage()) / 100;
						Referral referral3 = referralRepository.findByCusId(level1Id);
						referral3.setRefAmount(referral3.getRefAmount() + earn3);
						referralRepository.save(referral3);
					}

				}
			}

		}

	}

	@PostMapping("delete/cart/item")
	public String deleteItem(int id) {
		userCartRepository.deleteById(id);
		return "deleted";
	}

	@PostMapping("htv/remove/cart")
	public String deleteCart(String cusId) {
		List<UserCartTemp> userCartList = userCartRepository.findByCustomerId(cusId);
		userCartRepository.deleteAll(userCartList);
		return "deleted";
	}

	@PostMapping("add/zipcode")
	public Zipcode addZipcode(Zipcode zipcode) {
		return zipcodeRepository.save(zipcode);
	}

	@GetMapping("get/zipcode")
	public List<Zipcode> getAllZipcode() {
		return zipcodeRepository.findAll();
	}

	@PostMapping("delete/zipcode")
	public String deleteZipcode(int id) {
		zipcodeRepository.deleteById(id);
		return "deleted";
	}

	@GetMapping("verify/delivery")
	public String verifyDelivery(String zipcode) throws IOException {
		String location = null;
		List<ZipCodes> participantJsonList = new ArrayList<ZipCodes>();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = new JSONObject();
		json.put("pincode", zipcode);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(
				"https://api.ecomexpress.in/apiv2/pincodes/?username=MEMEWORLDWIDEINDIAPRIVATELIMITED378320&password=rvbrvjCume");
		StringEntity params = new StringEntity(json.toString());
		request.addHeader("content-type", "application/json");
		request.setEntity(params);

		HttpResponse httpResponse = httpClient.execute(request);
		HttpEntity responseEntity = httpResponse.getEntity();
		if (responseEntity != null) {
			participantJsonList = mapper.readValue(EntityUtils.toString(responseEntity),
					new TypeReference<List<ZipCodes>>() {
					});
			for (ZipCodes code : participantJsonList) {

				location = code.getPincode().equals(zipcode) ? "Available" : "Unavailable";
				if (location.equals("Available"))
					break;

			}
		}

		return location;
	}

	@PostMapping("payment/stripe")
	public ResponseEntity<String> completePayment(@RequestBody PaymentRequest request) throws StripeException {
		String chargeId = service.charge(request);
		return chargeId != null ? new ResponseEntity<String>(chargeId, HttpStatus.OK)
				: new ResponseEntity<String>("Please check the credit card details entered", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("update/delivery/item/status")
	public String updateDeliveryItemStatus(int id, String status) throws ResourceNotFoundException {
		OrderItems items = orderItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		items.setStatus(status);
		orderItemRepository.save(items);
		return "updated";
	}

	@PostMapping("update/delivery/item/gst")
	public String updateDeliveryItemGstDetails(OrderItems i) throws ResourceNotFoundException {
		OrderItems items = orderItemRepository.findById(i.getOrderItemId())
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not found"));
		items.setGsthsn(i.getGsthsn());
		items.setGstern(i.getGstern());
		items.setDiscount(i.getDiscount());
		items.setGsttaxratecgstn(i.getGsttaxratecgstn());
		items.setGsttaxratesgstn(i.getGsttaxratesgstn());
		items.setGsttaxrateigstn(i.getGsttaxigstn());
		items.setGsttaxtotal(i.getGsttaxtotal());
		items.setGsttaxcgstn(i.getGsttaxcgstn());
		items.setGsttaxsgstn(i.getGsttaxsgstn());
		items.setGsttaxigstn(i.getGsttaxigstn());
		orderItemRepository.save(items);
		return "updated";
	}

	public PaymentIntent Gettable(long amnt, String currency, String method) throws StripeException {
		Stripe.apiKey = "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";

		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(amnt).setCurrency(currency)
				.addPaymentMethodType(method).build();

		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;
	}

	@PostMapping("/stripe/pay")
	public Map payment(long amnt, String currency, String method) throws StripeException {
		PaymentIntent paymentIntent = this.Gettable(amnt, currency, method);
		Map map = new HashMap();
		map.put("client_secret", paymentIntent.getClientSecret());

		return map;
	}

	@PostMapping("/cashfree")
	public String cashFree(CashFreeModel cashFreeModel) throws IOException {
		String req = "{\"order_id\":\"" + cashFreeModel.getOrder_id() + "\",\"order_amount\":\""
				+ cashFreeModel.getOrder_amount() + "\",\"order_currency\":\"" + cashFreeModel.getOrder_currency()
				+ "\",\"customer_details\":{\"customer_id\":\"" + cashFreeModel.getCustomer_details().getCustomer_id()
				+ "\",\"customer_email\":\"" + cashFreeModel.getCustomer_details().getCustomer_email()
				+ "\",\"customer_phone\":\"" + cashFreeModel.getCustomer_details().getCustomer_phone() + "\"}}";
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String result = null;
		try {
			HttpPost request = new HttpPost("https://api.cashfree.com/pg/orders");
			StringEntity params = new StringEntity(req);

			request.addHeader("Accept", "application/json");
			request.addHeader("Content-Type", "application/json");
			request.addHeader("x-api-version", "2022-01-01");
			request.addHeader("x-client-id", "200010c6a55e52a3671e222ced010002");
			request.addHeader("x-client-secret", "cbb5fd17b62b7fc5d89a495b2007cf50dd30b1aa");
			request.setEntity(params);
			CloseableHttpResponse response = httpClient.execute(request);
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = sb.toString();

		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.close();
		}

		return result;
	}

	@ExceptionHandler
	public String handleError(StripeException ex) {
		return ex.getMessage();
	}

//MMB PAYMENT GATEWAY
	public PaymentIntent MmbGettable(long amnt, String currency, String method) throws StripeException {
		Stripe.apiKey = "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";

		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(amnt).setCurrency(currency)
				.addPaymentMethodType(method).build();

		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;
	}

	@PostMapping("mmb/stripe/pay")
	public Map MmbPayment(long amnt, String currency, String method) throws StripeException {
		PaymentIntent paymentIntent = this.MmbGettable(amnt, currency, method);
		Map map = new HashMap();
		map.put("client_secret", paymentIntent.getClientSecret());

		return map;
	}

	public void verifivcationOtp(String phone, String body) {
		// Find your Account Sid and Token at twilio.com/console
		// and set the environment variables. See http://twil.io/secure

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber("+13237464865"), body).create();

		System.out.println(message.getBody());

	}
}