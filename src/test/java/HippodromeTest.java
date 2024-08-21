import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    @Test
    void emptyArrayHippodrome() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }
    @Test
    void emptyArrayHippodromeMessage() {
        try{
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e){
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    void nullHippodrome() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void nullHippodromeMessage() {
        try{
            new Hippodrome(null);
        }catch (IllegalArgumentException e){
            assertEquals("Horses cannot be null.", e.getMessage());
        }


    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(""+i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());

    }

    @Test
    void move() {

        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("Horse1", 1, 1);
        Horse horse2 = new Horse("Horse2", 2, 2);
        Horse horse3 = new Horse("Horse3", 3, 3);
        Horse horse4 = new Horse("Horse4", 4, 4);
        Horse horse5 = new Horse("Horse5", 5, 5);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4, horse5));

        assertSame(horse5, hippodrome.getWinner());
    }
}