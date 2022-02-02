package kata.lacombe;

import java.time.LocalDateTime;

public class DefaultDateProvider implements DateProvider {
    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
