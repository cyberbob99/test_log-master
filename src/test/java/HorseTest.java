import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    void nullNameException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    void nullNameMessage(){
        try {
            new Horse(null, 1, 1);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void blankNameException(String names){
        assertThrows(IllegalArgumentException.class, () -> new Horse(names, 1, 1));
    }

    @ParameterizedTest
    @ValueSource (strings = {"", " ", "\t", "\n"})
    void blankNameMessage(String names){
        try{
            new Horse(names, 1, 1);
        }catch (IllegalArgumentException e){
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    void negativeSpeedException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Bob", -1, 1));
    }

    @Test
    void negativeSpeedMessage(){
        try{
            new Horse("Bob", -1, 1);
        }catch (IllegalArgumentException e){
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    void negativeDistanceException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Bob", 1, -1));
    }

    @Test
    void negativeDistanceMessage(){
        try {
            new Horse("Bob", 1, -1);
        }catch (IllegalArgumentException e){
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void testName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Bob", 1, 1);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("Bob", nameValue);
    }

    @Test
    void getNameTest() {
        Horse horse = new Horse("Bob", 3);
        assertEquals("Bob", horse.getName());

    }

    @Test
    void getSpeedTest() {
        Horse horse = new Horse("Bob", 3);
        assertEquals(3, horse.getSpeed());
    }

    @Test
    void getDistanceTestThreeParametersConstractor() {
        Horse horse = new Horse("Bob", 3, 4);
        assertEquals(4, horse.getDistance());
    }

    @Test
    void getDistanceTestTwoParametersConstractor() {
        Horse horse = new Horse("Bob", 3);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void moveUsesGetRandom(){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("Bob", 3, 4).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource (doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void moveUsesGetRandomDouble(double random){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            Horse horse = new Horse("Bob", 3, 4);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(4 + 3 * random, horse.getDistance());
        }
    }


}