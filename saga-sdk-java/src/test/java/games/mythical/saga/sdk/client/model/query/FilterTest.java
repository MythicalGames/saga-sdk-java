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
        var firstExpression = (Expression) filter.getFilter().poll();
        assertEquals("test", firstExpression.getAttributeName());
        assertEquals(FilterConditional.EQUALS, firstExpression.getConditional());
        assertEquals("test value", firstExpression.getValue());

        assertEquals(FilterOperation.AND, ((Operation) filter.getFilter().poll()).getOperation());

        var secondExpression = (Expression) filter.getFilter().poll();
        assertEquals("test2", secondExpression.getAttributeName());
        assertEquals(FilterConditional.NOT_EQUALS, secondExpression.getConditional());
        assertEquals("other test value", secondExpression.getValue());
        assertNull(filter.getFilter().poll());
    }
}
