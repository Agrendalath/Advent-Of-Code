package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Day7Test {
    @Test
    public void supportsTLS() {
        assertTrue("WA0", Day7.supportsTLS("abba[mnop]qrst"));
        assertFalse("WA1", Day7.supportsTLS("abcd[bddb]xyyx"));
        assertFalse("WA2", Day7.supportsTLS("aaaa[qwer]tyui"));
        assertTrue("WA3", Day7.supportsTLS("abba[mnop]qrst"));
    }

    @Test
    public void fistExample() {
        assertFalse("WA0", Day7.supportsTLS("wysextplwqpvipxdv[srzvtwbfzqtspxnethm]syqbzgtboxxzpwr[kl" +
                "jvjjkjyojzrstfgrw]obdhcczonzvbfby[svotajtpttohxsh]cooktbyumlpxostt"));
        assertTrue("WA1", Day7.supportsTLS("rxpusykufgqujfe[rypwoorxdemxffui]cvvcufcqmxoxcphp[witynpl" +
                "rfvquduiot]vcysdcsowcxhphp[gctucefriclxaonpwe]jdprpdvpeumrhokrcjt"));
    }

    @Test
    public void supportsSSL() {
        assertTrue("WA0", Day7.supportsSSL("aba[bab]xyz"));
        assertFalse("WA0", Day7.supportsSSL("xyx[xyx]xyx"));
        assertTrue("WA0", Day7.supportsSSL("aaa[kek]eke"));
        assertTrue("WA0", Day7.supportsSSL("zazbz[bzb]cdb"));
    }
}
