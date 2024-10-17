import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Products{
    public Map<String, Integer> item_list;

    // Constructor takes an input of List of products and initializes the inventory
    Products(List<List<String>> Product){
        item_list = new HashMap<>();

        for(List<String> produce: Product){
            String item = produce.get(0);
            try {
                Integer quantity = Integer.parseInt(produce.get(1));
                item_list.put(item, quantity);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format for item: " + item);
            }
        }
    }

    // Alert when stock is below a certain threshold
    public String lowQuantityAlert(String item){
        return "Item " + item + " is low in quantity. Please restock the item.";
    }

    // Process orders by reducing stock levels and checking if restocking is needed
    public List<String> process_order(List<List<String>> order_list){
        List<String> messages = new ArrayList<>();
        String orderProcessed = "Order Processed!";
        
        for(List<String> order: order_list){
            String item = order.get(0);
            try {
                Integer orderQuantity = Integer.parseInt(order.get(1));

                if (!item_list.containsKey(item)) {
                    messages.add("Item " + item + " does not exist in the inventory.");
                    continue;
                }

                int quantity = item_list.get(item);

                if (quantity < orderQuantity) {
                    messages.add("Inventory doesn't have enough of " + item + " to make this purchase.");
                } else {
                    int newStockLevel = quantity - orderQuantity;
                    item_list.put(item, newStockLevel);

                    if (newStockLevel < 10) {
                        messages.add(lowQuantityAlert(item));
                    }
                }
            } catch (NumberFormatException e) {
                messages.add("Invalid quantity format for item: " + item);
            }
        }
        
        messages.add(orderProcessed);
        return messages;
    }

    // Restock items by adding quantities to existing stock or initializing new products
    // This function also add new items, if not present
    public String restock_items(List<List<String>> items){
        for(List<String> item : items){
            String product = item.get(0);
            try {
                Integer quantity = Integer.parseInt(item.get(1));

                if (!item_list.containsKey(product)) {
                    item_list.put(product, quantity); // Add ew product to inventory
                } else {
                    item_list.put(product, item_list.get(product) + quantity); // Update existing product
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format for item: " + product);
            }
        }
        return "Restock Completed!";
    }

    // method to display the current inventory
    public void displayInventory(){
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : item_list.entrySet()) {
            System.out.println("Product: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }
}
