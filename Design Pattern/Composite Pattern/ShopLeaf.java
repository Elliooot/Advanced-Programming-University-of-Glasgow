public class ShopLeaf implements ShopComponent {
	private Double basePrice;
	private Boolean canBeDiscounted;
	private String name;
	// Constructor - set the properties of the item
	public ShopLeaf(Double base,Boolean disc,String n) {
		basePrice = base;
		canBeDiscounted = disc;
		name = n;
	}
	// Comp price - only discounts if discounting is allowed
	public Double compPrice(Double discount) {
		if(canBeDiscounted) {
			return basePrice*(1.0-(discount/100.0));
		} else {
			return basePrice;
		}
	}

//	@Override
	public Double compDelivery() {
		if(canBeDiscounted) {
			return basePrice * 0.05;
		}else{
			return 0.0;
		}
	}

	// Nice display
	public String toString() {
		return name;
	}
}