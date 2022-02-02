package kata.lacombe;

import java.time.LocalDateTime;

public interface DateProvider {
    default LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
