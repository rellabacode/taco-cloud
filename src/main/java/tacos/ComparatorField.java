package tacos;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Comparator;

@Slf4j
public class ComparatorField implements Comparator<Field> {


    @Override
    public int compare(Field o1, Field o2) {
        log.info("Comparing "+o1.getName() + " "+o2.getName() + " " + o1.getName().compareTo(o2.getName()));
        o1.setAccessible(true);
        o2.setAccessible(true);
        return o1.getName().compareTo(o2.getName());
    }
}
