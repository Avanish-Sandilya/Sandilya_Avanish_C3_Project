import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestaurantTest {
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime));
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime mockCurrentTime = LocalTime.of(12, 0);
        when(restaurant.getCurrentTime()).thenReturn(mockCurrentTime);

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime mockCurrentTime = LocalTime.of(23, 0);
        when(restaurant.getCurrentTime()).thenReturn(mockCurrentTime);

        assertFalse(restaurant.isRestaurantOpen());
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }

   //Test case for part 3 of assignment
    @Test
    public void calculate_order_value_should_return_correct_value_for_given_items() throws itemNotFoundException {

        int sweetCornSoupPrice = 119;
        int vegetableLasagnePrice = 269;


        restaurant.addToMenu("Sweet corn soup", sweetCornSoupPrice);
        restaurant.addToMenu("Noodles", vegetableLasagnePrice);


        int expectedOrderValue = sweetCornSoupPrice + vegetableLasagnePrice;


        assertEquals(expectedOrderValue, restaurant.calculateOrderValue(Arrays.asList("Sweet corn soup", "Noodles")));
    }
}