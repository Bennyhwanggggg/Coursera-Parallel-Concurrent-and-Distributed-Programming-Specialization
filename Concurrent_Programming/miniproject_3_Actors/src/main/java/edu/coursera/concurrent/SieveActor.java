package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;
import static edu.rice.pcdp.PCDP.finish;

/**
 * An actor-based implementation of the Sieve of Eratosthenes.
 *
 * TODO Fill in the empty SieveActorActor actor class below and use it from
 * countPrimes to determin the number of primes <= limit.: Done!
 */
public final class SieveActor extends Sieve {
    /**
     * {@inheritDoc}
     *
     * TODO Use the SieveActorActor class to calculate the number of primes <=
     * limit in parallel. You might consider how you can model the Sieve of
     * Eratosthenes as a pipeline of actors, each corresponding to a single
     * prime number.: Done!
     */
    @Override
    public int countPrimes(final int limit) {
        final SieveActorActor sieveActor = new SieveActorActor();
        finish(() -> {
            for (int i = 3; i <= limit; i += 2) {
                sieveActor.send(i);
            }
            sieveActor.send(0);
        });

        // Sum up the number of local primes from each actor in the chain.
        int totalPrimes = 1;
        SieveActorActor currentActor = sieveActor;
        while (currentActor != null) {
            totalPrimes += currentActor.numLocalPrimes;
            currentActor = currentActor.nextActor;
        }

        return totalPrimes;
    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {
        /**
         * TODO: Setup this class: Done!
         *
         */
        static final int MAX_LOCAL_PRIMES = 1000;

        SieveActorActor nextActor = null;
        int[] localPrimes = new int[MAX_LOCAL_PRIMES];
        int numLocalPrimes = 0;
        /**
         * Process a single message sent to this actor.
         *
         * TODO complete this method.: Done!
         *
         * @param msg Received message
         */
        @Override
        public void process(final Object msg) {
            final int candidate = (Integer) msg;
            if (candidate <= 0) {
                if (nextActor != null) {
                    nextActor.send(msg);
                }
                return;
            }
            if (!isLocalPrime(candidate)) {  // If it's not locally prime, ignore it.
                return;
            }
            if (numLocalPrimes < MAX_LOCAL_PRIMES) {  // If there is still room, remember the candidate and stop.
                localPrimes[numLocalPrimes++] = candidate;
                return;
            }
            if (nextActor == null) {  // No room, send the candidate down the chain.
                nextActor = new SieveActorActor();
            }
            nextActor.send(msg);
        }

        boolean isLocalPrime(int candidate) {
            for (int i = 0; i < numLocalPrimes; i++) {
                // If the candidate divides on anything from local store, it's not a prime.
                if (candidate % localPrimes[i] == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
