package games.mythical.saga.sdk.client.model.query;

import games.mythical.sga.sdk.proto.common.FilterConditional;
import games.mythical.sga.sdk.proto.common.FilterOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FilterTest {

    @Test
    public void testFilterBuilding() {
        Filter filter = new Filter();
        filter.equal("test", "test value")
                .and()
                .notEqual("test2", "other test value");

        // filter contains things in the correct order
        assertEquals("test", filter.getFilter().poll().getValue());
        assertEquals(FilterConditional.EQUALS, filter.getFilter().poll().getValue());
        assertEquals("test value", filter.getFilter().poll().getValue());
        assertEquals(FilterOperation.AND, filter.getFilter().poll().getValue());
        assertEquals("test2", filter.getFilter().poll().getValue());
        assertEquals(FilterConditional.NOT_EQUALS, filter.getFilter().poll().getValue());
        assertEquals("other test value", filter.getFilter().poll().getValue());
        assertNull(filter.getFilter().poll());
    }
}
