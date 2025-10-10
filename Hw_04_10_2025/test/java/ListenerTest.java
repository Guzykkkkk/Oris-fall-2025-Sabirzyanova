import org.example.FourthOctober.listeners.ProjectStartupListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListenerTest {
    @Test
    void testListenerCreation() {
        ProjectStartupListener listener = new ProjectStartupListener();
        assertNotNull(listener);
    }
}
