import com.doomhowl.gcputils.GcpUtilsPlugin
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull

class GcpUtilsTest {
    @Test
    void testGetAccessToken() {
        def token = GcpUtilsPlugin.getAccessToken()
        assertNotNull(token)
        assertFalse(token.isEmpty())
    }
}
