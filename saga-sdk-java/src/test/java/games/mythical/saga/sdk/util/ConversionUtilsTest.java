package games.mythical.saga.sdk.util;

import games.mythical.saga.sdk.proto.common.LargeDecimal;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionUtilsTest {

    @Test
    public void testConvertProtoLargeDecimal() {
        var protoDecimal = LargeDecimal.newBuilder()
                .setUnits(12345L)
                .setExa(678_900_000_000_000_000L) // 18 total values to represent decimal value of 0.6789
                .build();

        var decimal = ConversionUtils.protoDecimalToBigDecimal(protoDecimal);

        assertEquals(new BigDecimal("12345.6789"), decimal);
    }

    @Test
    public void testConvertJavaBigDecimal() {
        var EXPECTED_BIG_DECIMAL = new BigDecimal("9876.54321");
        var protoDecimal = ConversionUtils.bigDecimalToProtoDecimal(EXPECTED_BIG_DECIMAL);

        assertEquals(9876L, protoDecimal.getUnits());
        assertEquals(543_210_000_000_000_000L, protoDecimal.getExa());

        var decimal = ConversionUtils.protoDecimalToBigDecimal(protoDecimal);

        assertEquals(EXPECTED_BIG_DECIMAL, decimal);
    }
}
