package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repIdUser = new ConcurrentHashMap<>();
    private Map<String, Integer> repEmailId = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public synchronized User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }

        User oldUser = repIdUser.put(user.getId(), user);

        if (oldUser != null) {
            repEmailId.remove(oldUser.getEmail());
        }
        repEmailId.put(user.getEmail(), user.getId());

        return user;
    }

    @Override
    public boolean delete(int id) {
        return repIdUser.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return repIdUser.get(id);
    }

    @Override
    public synchronized User getByEmail(String email) {
        return repIdUser.get(repEmailId.get(email));
    }

    @Override
    public List<User> getAll() {
        return repIdUser.values()
                .stream()
                .sorted(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Comparator.comparing(User::getEmail, String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());
    }
}
