package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a Lock to protect the critical section.
     */
    // TODO - you fill in here
	final private ReentrantLock mRL;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	final private Condition mAvailableCond; 

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	volatile private int mAvailable;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.    	   	
    	 
    	mRL = new ReentrantLock(fair);

    	mAvailable = permits;
    	mAvailableCond = mRL.newCondition(); 
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	try {
    		mRL.lockInterruptibly();
    		
        	while (mAvailable <= 0) {			
    			mAvailableCond.await();
    		}
        	
        	mAvailable--;
        	
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			mRL.unlock();
		}    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
    	try {
    		mRL.lock();
    		
        	while (mAvailable <= 0) {			
    			mAvailableCond.awaitUninterruptibly();;
    		}
        	
        	mAvailable--;
        	
		} finally{
			mRL.unlock();
		} 
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here.
    	try {
    		mRL.lock();
        	
        	mAvailable++;
        	mAvailableCond.signalAll();
        	
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			mRL.unlock();
		} 
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return mAvailable;
    }
}
