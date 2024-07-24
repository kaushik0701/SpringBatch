import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

class ObjektutilsTest {

    @Test
    void testIsObjectEmpty_withNull() {
        assertTrue(Objektutils.isObjectEmpty(null));
    }

    @Test
    void testIsObjectEmpty_withEmptyObject() {
        class TestObject {
            private String field1 = "";
            private Integer field2 = null;
        }

        TestObject obj = new TestObject();
        assertTrue(Objektutils.isObjectEmpty(obj));
    }

    @Test
    void testIsObjectEmpty_withNonEmptyStringField() {
        class TestObject {
            private String field1 = "not empty";
            private Integer field2 = null;
        }

        TestObject obj = new TestObject();
        assertFalse(Objektutils.isObjectEmpty(obj));
    }

    @Test
    void testIsObjectEmpty_withNonStringField() {
        class TestObject {
            private String field1 = "";
            private Integer field2 = 1;
        }

        TestObject obj = new TestObject();
        assertFalse(Objektutils.isObjectEmpty(obj));
    }
}
