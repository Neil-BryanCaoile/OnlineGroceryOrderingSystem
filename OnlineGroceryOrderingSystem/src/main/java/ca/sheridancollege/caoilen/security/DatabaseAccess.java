package ca.sheridancollege.caoilen.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.caoilen.beans.CartItemId;
import ca.sheridancollege.caoilen.beans.Item;
import ca.sheridancollege.caoilen.beans.ItemType;
import ca.sheridancollege.caoilen.beans.User;

@Repository
public class DatabaseAccess {
	
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM sec_user where userName=:userName";
		parameters.addValue("userName", userName);
		
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		
		if (users.size()>0)
			return users.get(0);
		else
			return null;
	}

	public List<String> getRolesById(long userId) {
		
		ArrayList<String> roles = new ArrayList<String>();
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "select user_role.userId, sec_role.roleName "
				+ "FROM user_role, sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId "
				+ "and userId=:userId";
		
		parameters.addValue("userId", userId);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for (Map<String, Object> row : rows) {
			roles.add((String)row.get("roleName"));
		}
		
		return roles;
	}
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void addUser(String userName, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User " 
				+ "(userName, encryptedPassword, ENABLED)" 
				+ " values (:userName, :encryptedPassword, 1)";
		
		parameters.addValue("userName", userName);	
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, parameters);
		
	}
	
	public void addToCart(long itemId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into CART (itemId)" 
				+ "values (:itemId);";
		parameters.addValue("itemId", itemId);
		jdbc.update(query, parameters);
	}
	
	public List<CartItemId> getCartItemId() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM CART";
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<CartItemId>(CartItemId.class));
	}
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId)" 
				+ "values (:userId, :roleId);";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);	
	}	
	
	public List<Item> getItemAllItemList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM ITEMS";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Item>(Item.class));
	}
	public void insertItem(Item item) {
		//Used to specify parameters
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		//Specify query
		String query = "INSERT INTO ITEMS(itemName,itemPrice,itemType,itemSrcImg) VALUES (:name,:price,:type,:itemSrcImg)";
		
		//Specify the name to insert to DB
		namedParameters.addValue("name", item.getItemName());
		namedParameters.addValue("price", item.getItemPrice());
		namedParameters.addValue("type", item.getItemType());
		namedParameters.addValue("itemSrcImg", item.getItemSrcImg());
				
				
		//Insert Student to DB
		int rowsAffected =jdbc.update(query, namedParameters);
				
				
		//Check to see if inserted
		if(rowsAffected > 0) {
			System.out.println("A student was inserted into DB");	
		}
	}
	
	public void deleteItem(Long itemId ) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM ITEMS WHERE itemId = :itemId";
		
		namedParameters.addValue("itemId", itemId);
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if (rowsAffected > 0)
			System.out.println("Deleted student " + itemId + " from database");
	}
	public void deleteCartItem(Long itemId ) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM CART WHERE itemId = :itemId";
		
		namedParameters.addValue("itemId", itemId);
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if (rowsAffected > 0)
			System.out.println("Deleted cart-item with id " + itemId + " from database");
	}
	public void deleteAllCartItem() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM CART";
		
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if (rowsAffected > 0)
			System.out.println("Deleted all cart  Items from database");
	}
	
	public List<ItemType> getAllItemTypes() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM ITEM_TYPES";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<ItemType>(ItemType.class));
	}
	
	public List<Item> getItemListById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM ITEMS WHERE itemId = :id";
		namedParameters.addValue("id", id);
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Item>(Item.class));
	}
	
	
	public List<Item> getAllSweetList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM ITEMS WHERE itemType='sweet'";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Item>(Item.class));
	}
	
	public List<Item> getAllMeatList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM ITEMS WHERE itemType='meat'";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Item>(Item.class));
	}
	public List<Item> getAllVegetableList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM ITEMS WHERE itemType='vegetable'";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Item>(Item.class));
	}
}
