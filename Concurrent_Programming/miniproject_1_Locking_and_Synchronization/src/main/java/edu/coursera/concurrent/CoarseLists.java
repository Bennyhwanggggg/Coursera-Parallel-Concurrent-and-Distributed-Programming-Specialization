package edu.coursera.concurrent;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Wrapper class for two lock-based concurrent list implementations.
 */
public final class CoarseLists {
    /**
     * An implementation of the ListSet interface that uses Java locks to
     * protect against concurrent accesses.
     *
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java ReentrantLock object
     * to protect against those concurrent accesses. You may refer to
     * SyncList.java for help understanding the list management logic, and for
     * guidance in understanding where to place lock-based synchronization.
     */
    public static final class CoarseList extends ListSet {
        /**
         * TODO Declare a lock for this class to be used in implementing the concurrent add, remove, and contains methods below.: Done!
         */
        private final ReentrantLock lock = new ReentrantLock();

        /**
         * Default constructor.
         */
        public CoarseList() {
            super();
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.: Done!
         */
        @Override
        boolean add(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    return false;
                } else {
                    final Entry entry = new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            } finally {
                lock.unlock();
            }

        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.: Done!
         */
        @Override
        boolean remove(final Integer object) {
            try {
                lock.lock();

                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                lock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.: Done!
         */
        @Override
        boolean contains(final Integer object) {
            lock.lock();
            Entry pred = this.head;
            Entry curr = pred.next;

            while (curr.object.compareTo(object) < 0) {
                pred = curr;
                curr = curr.next;
            }
            lock.unlock();
            return object.equals(curr.object);
        }
    }

    /**
     * An implementation of the ListSet interface that uses Java read-write
     * locks to protect against concurrent accesses.
     *
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java
     * ReentrantReadWriteLock object to protect against those concurrent
     * accesses. You may refer to SyncList.java for help understanding the list
     * management logic, and for guidance in understanding where to place
     * lock-based synchronization.: Done!
     */
    public static final class RWCoarseList extends ListSet {
        /**
         * TODO Declare a read-write lock for this class to be used in implementing the concurrent add, remove, and contains methods below.: Done!
         */
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        /**
         * Default constructor.
         */
        public RWCoarseList() {
            super();
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.: Done!
         */
        @Override
        boolean add(final Integer object) {
            lock.writeLock().lock();

            Entry pred = this.head;
            Entry curr = pred.next;

            while (curr.object.compareTo(object) < 0) {
                pred = curr;
                curr = curr.next;
            }

            if (object.equals(curr.object)) {
                lock.writeLock().unlock();
                return false;
            } else {
                final Entry entry = new Entry(object);
                entry.next = curr;
                pred.next = entry;
                lock.writeLock().unlock();
                return true;
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.: Done!
         */
        @Override
        boolean remove(final Integer object) {
            lock.readLock().lock();

            Entry pred = this.head;
            Entry curr = pred.next;

            while (curr.object.compareTo(object) < 0) {
                pred = curr;
                curr = curr.next;
            }

            if (object.equals(curr.object)) {
                pred.next = curr.next;
                lock.readLock().unlock();
                return true;
            } else {
                lock.readLock().unlock();
                return false;
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.: Done!
         */
        @Override
        boolean contains(final Integer object) {
            try {
                lock.readLock().lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                return object.equals(curr.object);
            } finally {
                lock.readLock().unlock();
            }
        }
    }
}
