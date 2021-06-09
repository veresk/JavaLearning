package ru.javaLearning.lesson8;

import org.hibernate.Session;

public class Repository<T> {
    private final Session session;

    private final Class<T> type;

    public Repository(Class<T> type, Session session) {
        this.type = type;
        this.session = session;
    }

    public T get(long id) {
        return session.get(type, id);
    }

    public void save(T item) {
        session.save(item);
    }
}
