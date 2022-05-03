package ca.sheridancollege.caoilen.beans;




import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {
	
	//Fields
	private Long itemId;
	private String itemName;
	private double itemPrice;
	private String itemSrcImg;
	private String itemType;
}