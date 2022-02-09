package kata.lacombe.providers;

import java.time.LocalDateTime;

public interface DateProvider {
    default LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
