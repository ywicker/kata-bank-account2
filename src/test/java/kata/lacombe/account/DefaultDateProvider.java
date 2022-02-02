
package kata.lacombe.account;

import kata.lacombe.DateProvider;

import java.time.LocalDateTime;

public class DefaultDateProvider implements DateProvider {
    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
