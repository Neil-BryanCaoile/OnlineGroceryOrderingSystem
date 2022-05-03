package ca.sheridancollege.caoilen.controller;



import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.caoilen.beans.CartItemId;
import ca.sheridancollege.caoilen.beans.Item;
import ca.sheridancollege.caoilen.beans.ItemType;
import ca.sheridancollege.caoilen.security.DatabaseAccess;

@Controller
public class HomeController {
	
	@Autowired
	@Lazy
	private DatabaseAccess da;
	@GetMapping("/admin/deleteItem/{itemId}")
	public String deleteItem(Model model, @PathVariable Long itemId){
		
		da.deleteItem(itemId);
		
		List<Item> items = da.getItemAllItemList();
		model.addAttribute("item", new Item());
		model.addAttribute("itemsLists", items);
		return "/admin/database.html";
	}
	@GetMapping("/cart/deleteCartItem/{itemId}")
	public String deleteCartItem(Model model, @PathVariable Long itemId){
		
		da.deleteCartItem(itemId);
		
		List<CartItemId> cartItemId = da.getCartItemId();
		List<Item> items = da.getItemAllItemList();
		List<Item> cartItems = new ArrayList<Item>();
			
		DecimalFormat df = new DecimalFormat("0.00");//For decimal formating = 0.00
		df.setRoundingMode(RoundingMode.UP);//For decimal formating = 0.00
		double totalAmount= 0.00;
		
		//Get the total Price
		for(CartItemId iId : cartItemId) {
			for(Item i: items) {
				if( iId.getItemId() == i.getItemId()){
					cartItems.add(i);
					totalAmount += i.getItemPrice();
					break;
					}				
				}
		}
		
		//Check if it is a user then discount the total price
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			double discount = 0.1 * totalAmount;
			totalAmount = totalAmount - discount  ;
			model.addAttribute("discount", df.format(discount));
			model.addAttribute("totalAmountDiscount", df.format(totalAmount));
		}
		
		
		model.addAttribute("cartItemLists", cartItems);
		model.addAttribute("totalAmount", df.format(totalAmount));
		return "cart.html";
	}
	@GetMapping("/admin")
	public String goDatabase(Model model) {
		List<Item> items = da.getItemAllItemList();
		List<ItemType> itemTypes = da.getAllItemTypes();
		
		model.addAttribute("item", new Item());
		model.addAttribute("itemsLists", items);
		model.addAttribute("itemTypes", itemTypes);
		return "/admin/database.html";
	}

	@GetMapping("/orderSuccess")
	public String goOrderSuccess(){
		da.deleteAllCartItem();
		return "orderSuccess.html";
	}
	
	@GetMapping("/")
	public String goHome(Model model, HttpSession session) {

		
		List<Item> items = da.getItemAllItemList();
		List<CartItemId> cartItems = da.getCartItemId();
		
		int totalCartList = 0;
		for (CartItemId i: cartItems) {
			totalCartList++;
		}
		
		model.addAttribute("itemsLists", items);
		
		session.setAttribute("totalCartList", totalCartList);
		
		return "index.html";
				
	}
	@GetMapping("/cart")
	public String goCart(Model model, HttpSession session ) {
		
		List<CartItemId> cartItemId = da.getCartItemId();
		List<Item> items = da.getItemAllItemList();
		List<Item> cartItems = new ArrayList<Item>();
			
		DecimalFormat df = new DecimalFormat("0.00");//For decimal formating = 0.00
		df.setRoundingMode(RoundingMode.UP);//For decimal formating = 0.00
		double totalAmount= 0.00;
		
		//Get the total Price
		for(CartItemId iId : cartItemId) {
			for(Item i: items) {
				if( iId.getItemId() == i.getItemId()){
					cartItems.add(i);
					totalAmount += i.getItemPrice();
					break;
					}				
				}
		}
		
		//Check if it is a user then discount the total price
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			double discount = 0.1 * totalAmount;
			double totalAmountDiscount = totalAmount - discount  ;
			model.addAttribute("discount", df.format(discount));
			model.addAttribute("totalAmountDiscount", df.format(totalAmountDiscount ));
		}
		
		
		model.addAttribute("cartItemLists", cartItems);
		model.addAttribute("totalAmount", df.format(totalAmount));
		return "cart.html";
	}
	
	
	@GetMapping("/sweets")
	public String showSweets(Model model, HttpSession session) {
		
		List<CartItemId> cartItems = da.getCartItemId();
		
		int totalCartList = 0;
		for (CartItemId i: cartItems) {
			totalCartList++;
		}
		
		model.addAttribute("itemsLists", da.getAllSweetList());
		
		session.setAttribute("totalCartList", totalCartList);
		
		return "index.html";
				
	}
	
	@GetMapping("/meats")
	public String showMeats(Model model, HttpSession session) {
		
		List<CartItemId> cartItems = da.getCartItemId();
		
		int totalCartList = 0;
		for (CartItemId i: cartItems) {
			totalCartList++;
		}
		
		model.addAttribute("itemsLists", da.getAllMeatList());
		
		session.setAttribute("totalCartList", totalCartList);
		
		return "index.html";
				
	}
	
	@GetMapping("/vegetables")
	public String showVegetables(Model model, HttpSession session) {
		
		List<CartItemId> cartItems = da.getCartItemId();
		
		int totalCartList = 0;
		for (CartItemId i: cartItems) {
			totalCartList++;
		}
		
		model.addAttribute("itemsLists", da.getAllVegetableList());
		
		session.setAttribute("totalCartList", totalCartList);
		
		return "index.html";
				
	}
	

	
	
	
	@GetMapping("/admin/editItem/{itemId}")
	public String editItem(Model model, @PathVariable Long itemId){
		
		Item item = da.getItemListById(itemId).get(0);
		List<Item> items = da.getItemAllItemList();
		List<ItemType> itemTypes = da.getAllItemTypes();
		
		model.addAttribute("item", item);
		
		da.deleteItem(itemId);
		
		model.addAttribute("itemsLists", items);
		model.addAttribute("itemTypes", itemTypes);
		return "/admin/database.html";
	}
	
	@PostMapping("/insertItem")
	public String insertItem(Model model, @ModelAttribute Item item) {
		
		
		//Insert the student specified by the user to the DB
		da.insertItem(item);
		
		//Add the list to the model for the view to use it
		List<Item> items = da.getItemAllItemList();
		List<ItemType> itemTypes = da.getAllItemTypes();
		model.addAttribute("itemsLists", items);		
		model.addAttribute("itemsLists", items);
		//Create a blank student
		model.addAttribute("item", new Item());
		
		return "/admin/database.html";
	}
	

	
	
	@GetMapping("/user")
	public String goHomeUser() {
		return "/user/index.html";
	}
	
	@GetMapping("/login")
	public String goLoginPage() {
		return "login.html";
	}
	
	@GetMapping("/access-denied")
	public String goAccessDenied() {
		return "/error/access-denied.html";
	}
	
	@GetMapping("/register")
	public String goRegistration(){
		return "registration.html";
	}
	
	@PostMapping("/register")
	public String processRegistration(@RequestParam String name, @RequestParam String password){
		
		
		da.addUser(name, password);
		long userId=da.findUserAccount(name).getUserId();
		da.addRole(userId, 2);
		
		
		return "login.html";
	}
	
	@PostMapping("/")
	public String addItemToCart(@RequestParam int itemId){
		
		da.addToCart(itemId);
		
		System.out.print("The item is added to cart: " + itemId);
		return "redirect:/";
	}
	
	
	
	
	
	
}
